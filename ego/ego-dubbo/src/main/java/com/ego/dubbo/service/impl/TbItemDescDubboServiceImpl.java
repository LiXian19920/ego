package com.ego.dubbo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.ego.dubbo.service.TbItemDescDubboService;
import com.ego.mapper.TbItemDescMapper;
import com.ego.pojo.TbItemDesc;

/**
* @ClassName:TbItemDescDubboServiceImpl
* @Description:
* @Company: 北京尚学堂科技有限公司 www.bjsxt.com
* @author mengqx
* @date 2017年8月3日
*/

public class TbItemDescDubboServiceImpl implements TbItemDescDubboService{

	@Autowired
	private TbItemDescMapper tbItemDescMapper;
	
	@Override
	public int insDesc(TbItemDesc desc) {
		return tbItemDescMapper.insertSelective(desc);
	}

	@Override
	public TbItemDesc selByItemid(long itemId) {
		return tbItemDescMapper.selectByPrimaryKey(itemId);
	}


}
