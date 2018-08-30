package com.ego.dubbo.service.impl;

import javax.annotation.Resource;

import com.ego.dubbo.service.TbOrderItemDubboService;
import com.ego.mapper.TbOrderItemMapper;
import com.ego.pojo.TbOrderItem;

/**
* @ClassName:TbOrderItemDubboServiceImpl
* @Description:
* @Company: 北京尚学堂科技有限公司 www.bjsxt.com
* @author mengqx
* @date 2017年8月17日
*/

public class TbOrderItemDubboServiceImpl implements TbOrderItemDubboService{

	@Resource
	private TbOrderItemMapper tbOrderItemMapper;
	@Override
	public int insOrderItem(TbOrderItem item) {
		// TODO Auto-generated method stub
		return tbOrderItemMapper.insertSelective(item);
	}

}
