package com.ego.item.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.TbItemChild;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.item.dao.JedisPoolDao;
import com.ego.item.service.TbItemService;
import com.ego.pojo.TbItem;

/**
 * @ClassName:TbItemServiceImpl
 * @Description:
 * @Company: 北京尚学堂科技有限公司 www.bjsxt.com
 * @author mengqx
 * @date 2017年8月14日
 */

@Service
public class TbItemServiceImpl implements TbItemService {

	// 必须调用dubbo
	@Reference
	private TbItemDubboService tbItemDubboService;
	// jedis的操作类。如何拿到jedisCluster。
	@Resource
	private JedisPoolDao jedisPoolDaoImpl;
	// 取key item:
	@Value("${redis.item.key}")
	private String key;

	@Override
	public TbItemChild showItem(long id) {
		// 看redis中是否有数据
		// 取得到redis中是key item:id<--->${item.id }
		String mykey = key + id;
		if (jedisPoolDaoImpl.exists(mykey)) {
			// 如果存在在取redis中的数据
			String json = jedisPoolDaoImpl.get(mykey);
			if (json != null && !"".equals(json)) {
				// 将json对象转换成java对象,TbItemChild:该对象其实就是前台要显示的数据。第一次加载的时候，是从数据库中获取的信息。
				// 在数据库中获取到的信息，直接存成TbItemChild类。所有，我们在redis中取的时候，就直接转换成TbItemChild。
				TbItemChild child = JsonUtils.jsonToPojo(json, TbItemChild.class);
				return child;
			}
		}
		// 如果redis中没有,则去数据库中取得,并放入redis中
		TbItem tbItem = tbItemDubboService.selById(id);
		// 创建符合前台页面显示对象。
		TbItemChild child = new TbItemChild();
		// 给前台显示对象赋值！
		child.setBarcode(tbItem.getBarcode());
		child.setCid(tbItem.getCid());
		child.setCreated(tbItem.getCreated());
		child.setId(tbItem.getId());
		child.setImage(tbItem.getImage());
		// 前台页面显示的是一个图片数组,图片数组中的数据来源于：数据库中的表。
		child.setImages(tbItem.getImage() != null && !"".equals(tbItem.getImage()) ? tbItem.getImage().split(",")
				: new String[1]);
		child.setNum(tbItem.getNum());
		child.setPrice(tbItem.getPrice());
		child.setSellPoint(tbItem.getSellPoint());
		child.setStatus(tbItem.getStatus());
		child.setTitle(tbItem.getTitle());
		child.setUpdated(tbItem.getUpdated());
		// 所有属性赋值完成之后。应该将对象存在到redis中去
		jedisPoolDaoImpl.set(mykey, JsonUtils.objectToJson(child));
		return child;
	}

}
