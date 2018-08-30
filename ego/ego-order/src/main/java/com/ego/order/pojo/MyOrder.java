package com.ego.order.pojo;

import java.util.List;

import com.ego.pojo.TbOrderItem;
import com.ego.pojo.TbOrderShipping;

/**
* @ClassName:MyOrder
* @Description:
* @Company: 北京尚学堂科技有限公司 www.bjsxt.com
* @author mengqx
* @date 2017年8月17日
*/

public class MyOrder {
	private TbOrderShipping orderShipping;
	private String payment;
	private Integer paymentType;
	private List<TbOrderItem> orderItems;
	public TbOrderShipping getOrderShipping() {
		return orderShipping;
	}
	public void setOrderShipping(TbOrderShipping orderShipping) {
		this.orderShipping = orderShipping;
	}
	public String getPayment() {
		return payment;
	}
	public void setPayment(String payment) {
		this.payment = payment;
	}
	public Integer getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(Integer paymentType) {
		this.paymentType = paymentType;
	}
	public List<TbOrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<TbOrderItem> orderItems) {
		this.orderItems = orderItems;
	}
}
