package com.ego.cart.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ego.cart.pojo.Cart;
import com.ego.commons.pojo.EgoResult;

/**
* @ClassName:CartService
* @Description:
* @Company: 北京尚学堂科技有限公司 www.bjsxt.com
* @author mengqx
* @date 2017年8月16日
*/

public interface CartService {
	/**
	 * 
	 * @Description:  添加购物车信息
	 * @param itemId 商品的id
	 * @param num 商品的数量
	 * @param request 我们要通过request对象在cookie中取得用户信息。"TT_TOKEN" value : uuid.
	 * @throws Exception 
	 * @author mengqx
	 * @date   2017年8月16日
	 */
	void addCart(long itemId,int num,HttpServletRequest request) throws Exception ;
	/**
	 * 
	 * @Description: 显示购物车中的数据：从redis中取得 
	 * @param request
	 * @return 
	 * @author mengqx
	 * @date   2017年8月16日
	 */
	List<Cart> showCart(HttpServletRequest request);
	/**
	 * 
	 * @Description:  修改购物车数量
	 * @param itemId
	 * @param num
	 * @param request
	 * @return 
	 * @author mengqx
	 * @date   2017年8月16日
	 */
	
	EgoResult upItemNum(long itemId,int num,HttpServletRequest request);
	
	/**
	 * 
	 * @Description:  根据id删除购物车中的商品
	 * @param id
	 * @param request
	 * @return 
	 * @author mengqx
	 * @date   2017年8月16日
	 */
	EgoResult delete(long id,HttpServletRequest request);
	/**
	 * 
	 * @Description:  根据购物车中传来的id查询商品信息
	 * @param request
	 * @param id
	 * @return 
	 * @author mengqx
	 * @date   2017年8月17日
	 */
	List<Cart> showCartOrder(HttpServletRequest request,List<Long> id);
}
