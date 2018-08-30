package com.ego.portal.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbContentDubboService;
import com.ego.pojo.TbContent;
import com.ego.portal.dao.JedisPoolDao;
import com.ego.portal.service.TbContentService;

import redis.clients.jedis.JedisCluster;

/**
 * @ClassName:TbContentServiceImpl
 * @Description:
 * @Company: 北京尚学堂科技有限公司 www.bjsxt.com
 * @author mengqx
 * @date 2017年8月9日
 */

@Service
public class TbContentServiceImpl implements TbContentService {

	@Reference
	private TbContentDubboService tbContentDubboService;

	@Resource
	private JedisPoolDao jedisPoolDaoImpl;

	// 读取配置文件中的值
	@Value("${redis.bigpic.key}")
	private String key;
	@Value("${bigpic.id}")
	private long categoryId;

	@Override
	public String showBigPic() {
		// 看缓存
		String json = jedisPoolDaoImpl.get(key);
		// 如果缓存中没有数据，则去数据库中。
		if (!jedisPoolDaoImpl.exists(key) || !(json != null && !json.equals(""))) {
			// 通过dubbo进行查询数据库
			List<TbContent> list = tbContentDubboService.selAllBy(categoryId);
			System.out.println(list.size()+"\t");
			
			String slist = JsonUtils.objectToJson(list);
			// 覆盖缓存中值！
			jedisPoolDaoImpl.set(key, slist);
			json = slist;
		}
		// 如果缓存中有数据 Map<String, Object> : 相当于{"width":"670"}
		// List--[{"":""},{"":""}]
		List<Map<String, Object>> listObj = new ArrayList<>();
		// 将redis中的数据转换成我们对象集合的方式
		System.out.println("json="+json+"\t");
		List<TbContent> list = JsonUtils.jsonToList(json, TbContent.class);

		for (TbContent content : list) {
			Map<String, Object> map = new HashMap<>();
			map.put("srcB", content.getPic2());
			map.put("height", 240);
			map.put("alt", "广告信息维护中");
			map.put("width", 550);
			map.put("src", content.getPic());
			map.put("widthB", 550);
			map.put("href", content.getUrl());
			map.put("heightB", 240);
			listObj.add(map);
		}
		// 将最终的查询结果返回给前台页面
		return JsonUtils.objectToJson(listObj);
	}

}
