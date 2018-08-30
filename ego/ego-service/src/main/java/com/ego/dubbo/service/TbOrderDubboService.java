package com.ego.dubbo.service;

import com.ego.pojo.TbOrder;

/**
* @ClassName:TbOrderDubboService
* @Description:
* @Company: 北京尚学堂科技有限公司 www.bjsxt.com
* @author mengqx
* @date 2017年8月17日
*/

public interface TbOrderDubboService {
	/**
	 * 
	 * @Description:  新增数据
	 * @param order
	 * @return 
	 * @author mengqx
	 * @date   2017年8月17日
	 */
	int insOrder(TbOrder order);
}
