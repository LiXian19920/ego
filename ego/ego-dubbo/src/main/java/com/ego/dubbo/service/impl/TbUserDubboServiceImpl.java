package com.ego.dubbo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.util.DigestUtils;

import com.ego.dubbo.service.TbUserDubboService;
import com.ego.mapper.TbUserMapper;
import com.ego.pojo.TbUser;
import com.ego.pojo.TbUserExample;

/**
* @ClassName:TbUserDubboServiceImpl
* @Description:
* @Company: 北京尚学堂科技有限公司 www.bjsxt.com
* @author mengqx
* @date 2017年8月15日
*/

public class TbUserDubboServiceImpl implements TbUserDubboService{
	@Resource
	private TbUserMapper tbUserMapper;
	@Override
	public TbUser selByUser(TbUser user) {
		TbUserExample example = new TbUserExample();
//		拼接sql语句,注意password是md5，加密类型。
		example.createCriteria().andUsernameEqualTo(user.getUsername()).andPasswordEqualTo(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
		List<TbUser> list = tbUserMapper.selectByExample(example);
		if (list.size()>0) {
			return list.get(0);
		}
		return null;
	}

}
