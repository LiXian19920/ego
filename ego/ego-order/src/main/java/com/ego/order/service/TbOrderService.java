package com.ego.order.service;

import java.util.Map;

import com.ego.order.pojo.MyOrder;

/**
* @ClassName:TbOrderService
* @Description:
* @Company: 北京尚学堂科技有限公司 www.bjsxt.com
* @author mengqx
* @date 2017年8月17日
*/

public interface TbOrderService {
	/**
	 * 
	 * @Description:  订单生成方法
	 * @param order
	 * @return 
	 * @author mengqx
	 * @date   2017年8月17日
	 */
	Map<String,Object> createOrder(MyOrder order);
}
