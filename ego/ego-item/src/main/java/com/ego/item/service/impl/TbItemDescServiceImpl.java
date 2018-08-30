package com.ego.item.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbItemDescDubboService;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.item.dao.JedisPoolDao;
import com.ego.item.service.TbItemDescService;
import com.ego.pojo.TbItemDesc;

/**
 * @ClassName:TbItemDescServiceImpl
 * @Description:
 * @Company: 北京尚学堂科技有限公司 www.bjsxt.com
 * @author mengqx
 * @date 2017年8月14日
 */
@Service
public class TbItemDescServiceImpl implements TbItemDescService {

	@Reference
	private TbItemDescDubboService tbItemDescDubboService;

	// redis
	@Resource
	private JedisPoolDao jedisPoolDaoImpl;

	// key
	@Value("${redis.desc.key}")
	private String key;

	@Override
	public TbItemDesc selByItemId(long id) {
		String mykey = key + id;
		// 先查看缓存
		if (jedisPoolDaoImpl.exists(mykey)) {
			// 如果reids中有值，则将redis中的数据取出来
			String json = jedisPoolDaoImpl.get(mykey);
			if (json != null && !"".equals(json)) {
				// 将redis中的数据转换成java对象
				return JsonUtils.jsonToPojo(json, TbItemDesc.class);
			}
		}
		// 从数据库中取值
		TbItemDesc itemDesc = tbItemDescDubboService.selByItemid(id);
		jedisPoolDaoImpl.set(mykey, JsonUtils.objectToJson(itemDesc));
		return itemDesc;
	}

}
