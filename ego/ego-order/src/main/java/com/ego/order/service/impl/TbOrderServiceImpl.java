package com.ego.order.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.utils.IDUtils;
import com.ego.dubbo.service.TbOrderDubboService;
import com.ego.dubbo.service.TbOrderItemDubboService;
import com.ego.dubbo.service.TbOrderShippingDubboService;
import com.ego.order.pojo.MyOrder;
import com.ego.order.service.TbOrderService;
import com.ego.pojo.TbOrder;
import com.ego.pojo.TbOrderItem;
import com.ego.pojo.TbOrderShipping;

/**
* @ClassName:TbOrderServiceImpl
* @Description:
* @Company: 北京尚学堂科技有限公司 www.bjsxt.com
* @author mengqx
* @date 2017年8月17日
*/

@Service
public class TbOrderServiceImpl implements TbOrderService{

	@Reference
	private TbOrderDubboService tbOrderDubboService;
	
	@Reference
	private TbOrderItemDubboService tbOrderItemDubboService;
	
	@Reference
	private TbOrderShippingDubboService tbOrderShippingDubboService;
	
	/**
	 * 第一件事：向数据库中插入数据：插入三个表
	 * 第二件事：将前台页面需要的值，保存到map中。以便在controller中调用。
	 */
	@Override
	public Map<String, Object> createOrder(MyOrder order) {
		
//		向表中插入数据
		TbOrder tbOrder = new TbOrder();
//		订单ID
		String orderId = IDUtils.genItemId()+"";
		
		Date date = new Date();
		tbOrder.setOrderId(orderId);
//		总金额
		tbOrder.setPayment(order.getPayment());
//		设置支付类型
		tbOrder.setPaymentType(order.getPaymentType());
		
		tbOrder.setCreateTime(date);
		tbOrder.setUpdateTime(date);
		tbOrderDubboService.insOrder(tbOrder);
		
//		
		List<TbOrderItem> list = order.getOrderItems();
		for (TbOrderItem tbOrderItem : list) {
			tbOrderItem.setId(IDUtils.genItemId()+"");
			tbOrderItem.setOrderId(orderId);
			tbOrderItemDubboService.insOrderItem(tbOrderItem);
		}
		
		
		TbOrderShipping shipping = order.getOrderShipping();
		shipping.setOrderId(orderId);
		
		shipping.setCreated(date);
		shipping.setUpdated(date);
		
		tbOrderShippingDubboService.insShipping(shipping);
		
//		创建Map 对象 。将数据存储到map集合中。
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("orderId", orderId);
		
		Calendar c =Calendar.getInstance();
		//日历类
		c.add(Calendar.DAY_OF_MONTH, 2);
		map.put("date", c.getTime());

		return map;
	}

}
