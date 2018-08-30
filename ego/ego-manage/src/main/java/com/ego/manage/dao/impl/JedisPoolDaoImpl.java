package com.ego.manage.dao.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;

import com.ego.manage.dao.JedisPoolDao;

import redis.clients.jedis.JedisCluster;

/**
* @ClassName:JedisPoolDaoImpl
* @Description:
* @Company: 北京尚学堂科技有限公司 www.bjsxt.com
* @author mengqx
* @date 2017年8月9日
* @Repository :dao 层的注解  @Component: 将类在spring 中进行自动注入，注入的名称是类的首字母小写。
* @service： 专用于service层的注解
*/

@Repository
public class JedisPoolDaoImpl implements JedisPoolDao{

//	相当取得到JedisCluster对象
	@Resource
	private JedisCluster jedisCluster;
	
	@Override
	public Boolean exists(String key) {
		
		return jedisCluster.exists(key);
	}

	@Override
	public String get(String key) {
		// TODO Auto-generated method stub
		return jedisCluster.get(key);
	}

	@Override
	public Long del(String key) {
		// TODO Auto-generated method stub
		return jedisCluster.del(key);
	}

	@Override
	public String set(String key, String value) {
		// TODO Auto-generated method stub
		return jedisCluster.set(key, value);
	}

}
