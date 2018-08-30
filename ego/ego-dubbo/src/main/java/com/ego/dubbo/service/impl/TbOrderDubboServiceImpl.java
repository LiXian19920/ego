package com.ego.dubbo.service.impl;

import javax.annotation.Resource;

import com.ego.dubbo.service.TbOrderDubboService;
import com.ego.mapper.TbOrderMapper;
import com.ego.pojo.TbOrder;

/**
* @ClassName:TbOrderDubboServiceImpl
* @Description:
* @Company: 北京尚学堂科技有限公司 www.bjsxt.com
* @author mengqx
* @date 2017年8月17日
*/

public class TbOrderDubboServiceImpl implements TbOrderDubboService{

	@Resource
	private TbOrderMapper tbOrderMapper;
	@Override
	public int insOrder(TbOrder order) {
		// TODO Auto-generated method stub
		return tbOrderMapper.insertSelective(order);
	}

}
