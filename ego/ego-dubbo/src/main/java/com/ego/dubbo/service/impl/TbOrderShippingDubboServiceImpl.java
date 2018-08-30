package com.ego.dubbo.service.impl;

import javax.annotation.Resource;

import com.ego.dubbo.service.TbOrderShippingDubboService;
import com.ego.mapper.TbOrderShippingMapper;
import com.ego.pojo.TbOrderShipping;

/**
* @ClassName:TbOrderShippingDubboServiceImpl
* @Description:
* @Company: 北京尚学堂科技有限公司 www.bjsxt.com
* @author mengqx
* @date 2017年8月17日
*/

public class TbOrderShippingDubboServiceImpl implements TbOrderShippingDubboService {

	@Resource
	private TbOrderShippingMapper tbOrderShippingMapper;
	@Override
	public int insShipping(TbOrderShipping shipping) {
		// TODO Auto-generated method stub
		return tbOrderShippingMapper.insertSelective(shipping);
	}

}
