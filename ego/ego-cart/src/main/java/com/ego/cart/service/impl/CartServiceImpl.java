package com.ego.cart.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ego.cart.dao.JedisPoolDao;
import com.ego.cart.pojo.Cart;
import com.ego.cart.service.CartService;
import com.ego.commons.pojo.EgoResult;
import com.ego.commons.pojo.TbItemChild;
import com.ego.commons.utils.CookieUtils;
import com.ego.commons.utils.JsonUtils;
import com.ego.pojo.TbUser;

/**
 * @ClassName:CartServiceImpl
 * @Description:
 * @Company: 北京尚学堂科技有限公司 www.bjsxt.com
 * @author mengqx
 * @date 2017年8月16日
 */

@Service
public class CartServiceImpl implements CartService {

	// 需要操作redis
	@Resource
	private JedisPoolDao jedisPoolDaoImpl;
	// 需要key
	@Value("${redis.item.key}")
	private String itemKey;
	@Value("${redis.cart.key}")
	private String cartKey;
	@Value("${redis.user.key}")
	private String userKey;

	@Override
	public void addCart(long itemId, int num, HttpServletRequest request) throws Exception {
		// 第一步先把商品取出来
		String itemJson = jedisPoolDaoImpl.get(itemKey + itemId);
		// 将商品信息转换成我们的java对象
		TbItemChild child = JsonUtils.jsonToPojo(itemJson, TbItemChild.class);
		// 准备做添加操作！
		// 取得用户的uuid
		String cookieValue = CookieUtils.getCookieValue(request, "TT_TOKEN");
		// 取得用户对象
		String jsonUser = jedisPoolDaoImpl.get(userKey + cookieValue);
		// 将用户json数据转换成java对象
		TbUser user = JsonUtils.jsonToPojo(jsonUser, TbUser.class);
		// 开始设置购物车的cartKey
		String cartKeyRedis = cartKey + user.getId();

		if (jedisPoolDaoImpl.exists(cartKeyRedis)) {
			// 如果存在则将购物车的key取出
			String json = jedisPoolDaoImpl.get(cartKeyRedis);
			// 将购物车的json对象数据转换成java对象
			List<Cart> list = JsonUtils.jsonToList(json, Cart.class);
			// 当点击添加购物的时候，是不是看一下原来购物车中是否有已经存在，通过商品id来判断
			int index = -1;
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getId() == itemId) {
					// 此时说明该商品已经在购物车中存在！
					index = i;
				}
			}
			// 不存在的时候怎么做！
			if (index == -1) {
				// 新增商品
				Cart cart = new Cart();
				cart.setId(child.getId());
				cart.setImages(child.getImages());
				cart.setNum(num);
				cart.setPrice(child.getPrice());
				cart.setTitle(child.getTitle());
				list.add(cart);
			} else {
				// 如果商品已经存在。数量加传过来的参数
				Cart cart = list.get(index);
				// 商品数量++
				cart.setNum(cart.getNum() + num);
			}
			// 将碧改变后的商品集合添加到redis中
			jedisPoolDaoImpl.set(cartKeyRedis, JsonUtils.objectToJson(list));
		} else {
			// redis中不存在购物车的情况下
			List<Cart> list = new ArrayList<>();
			Cart cart = new Cart();
			cart.setId(child.getId());
			cart.setImages(child.getImages());
			cart.setNum(num);
			cart.setPrice(child.getPrice());
			cart.setTitle(child.getTitle());
			list.add(cart);
			// 第一次添加的时候，也需要将数据存储到redis中。
			jedisPoolDaoImpl.set(cartKeyRedis, JsonUtils.objectToJson(list));
		}
	}

	@Override
	public List<Cart> showCart(HttpServletRequest request) {
		// 取得用户的uuid
		String cookieValue = CookieUtils.getCookieValue(request, "TT_TOKEN");
		// 取得用户信息
		String jsonUser = jedisPoolDaoImpl.get(userKey + cookieValue);
		// 取得购物车json的信息
		String jsonCart = jedisPoolDaoImpl.get(cartKey + JsonUtils.jsonToPojo(jsonUser, TbUser.class).getId());
		// 直接将购物车josn数据转换成list集合
		List<Cart> list = JsonUtils.jsonToList(jsonCart, Cart.class);
		return list;
	}

	@Override
	public EgoResult upItemNum(long itemId, int num, HttpServletRequest request) {
		String cookieValue = CookieUtils.getCookieValue(request, "TT_TOKEN");
		String jsonUser = jedisPoolDaoImpl.get(userKey + cookieValue);
		String cartKeyRedis = cartKey + JsonUtils.jsonToPojo(jsonUser, TbUser.class).getId();
		// cart:36
		String jsonCart = jedisPoolDaoImpl.get(cartKeyRedis);
		// 将购物车数据转换成java对象
		List<Cart> list = JsonUtils.jsonToList(jsonCart, Cart.class);
		for (Cart cart : list) {
			if (cart.getId() == itemId) {
				cart.setNum(num);
				break;
			}
		}
		// 将修改后的数据存储到redis中
		String result = jedisPoolDaoImpl.set(cartKeyRedis, JsonUtils.objectToJson(list));

		EgoResult er = new EgoResult();
		if (result.equals("OK")) {
			er.setStatus(200);
		} else {
			er.setStatus(400);
		}
		return er;
	}

	@Override
	public EgoResult delete(long id, HttpServletRequest request) {
		String cookieValue = CookieUtils.getCookieValue(request, "TT_TOKEN");
		String jsonUser = jedisPoolDaoImpl.get(userKey + cookieValue);
		String cartKeyRedis = cartKey + JsonUtils.jsonToPojo(jsonUser, TbUser.class).getId();
		String jsonCart = jedisPoolDaoImpl.get(cartKeyRedis);

		// 转换对象
		List<Cart> list = JsonUtils.jsonToList(jsonCart, Cart.class);
		// 根据商品id进行删除 ,如果在集合循环过程中remvoe。会导致异常，异常原因：现在正在变量集合，同时，你还要操作集合。多操作。
		// 所有，应该怎么先记录一下当前这个对象。在循环外面删除。
		Cart cartRemove = null;
		for (Cart cart : list) {
			if (cart.getId() == id) {
				cartRemove = cart;
			}
		}
		// 找到要删除的商品，直接干掉！
		list.remove(cartRemove);
		// 将删除后的数据，保存到redis数据库中
		String result = jedisPoolDaoImpl.set(cartKeyRedis, JsonUtils.objectToJson(list));
		EgoResult er = new EgoResult();
		if (result.equals("OK")) {
			er.setStatus(200);
		} else {
			er.setStatus(400);
		}
		return er;
	}

	@Override
	public List<Cart> showCartOrder(HttpServletRequest request, List<Long> ids) {
//		从哪里取得数据：redis中 cart:用户id：cookie-uuid; 取得user对象。
//		取得到cart集合对象。变量购物车中的数据：【通过传过来的id进行遍历查找。购买的数量，和库存不一致！】
//		取得cookie中的uuid
		String cookieValue = CookieUtils.getCookieValue(request, "TT_TOKEN");
//	            取得userjson数据
		String jsonUser  = jedisPoolDaoImpl.get(userKey+cookieValue);
//		将user 的json数据转换成java对象
		TbUser tbUser = JsonUtils.jsonToPojo(jsonUser, TbUser.class);
//		取得cartkey
		String jsonCart = jedisPoolDaoImpl.get(cartKey+tbUser.getId());
//		将购物车json数据转换成java集合对象
		List<Cart> list = JsonUtils.jsonToList(jsonCart, Cart.class);
//		变量购物车集合找到要购买的商品
//		新建一个集合来存储想要购买的商品
		List<Cart> listCartOrder = new ArrayList<>();
		
		boolean numEnough=true;
		
		for (int i = 0; i < ids.size(); i++) {
			for (Cart cart : list) {
//	           如果传过来的id和购物车中的id相等。则就是我们想要的数据					
				if (cart.getId()==(long)ids.get(i)) {
					listCartOrder.add(cart);
//					如果库存不足的，该方法返回null;如何找到原商品的总数量：
					String jsonItem = jedisPoolDaoImpl.get(itemKey+cart.getId());
//					将jsonItem变为java对象
					TbItemChild child = JsonUtils.jsonToPojo(jsonItem, TbItemChild.class);
					
					if (cart.getNum()>child.getNum()) {
						numEnough=false;
					}
					break;
				}
			}
		}
		if (numEnough) {
			return listCartOrder;
		}else{
			return null;
		}
	}
}
