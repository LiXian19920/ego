package com.ego.dubbo.service;

import com.ego.pojo.TbOrderItem;

/**
* @ClassName:TbOrderItemDubboService
* @Description:
* @Company: 北京尚学堂科技有限公司 www.bjsxt.com
* @author mengqx
* @date 2017年8月17日
*/

public interface TbOrderItemDubboService {
	/**
	 * 
	 * @Description:  新增
	 * @param item
	 * @return 
	 * @author mengqx
	 * @date   2017年8月17日
	 */
	int insOrderItem(TbOrderItem item);
}
