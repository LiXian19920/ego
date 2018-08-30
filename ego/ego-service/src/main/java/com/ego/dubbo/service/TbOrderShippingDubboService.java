package com.ego.dubbo.service;

import com.ego.pojo.TbOrderShipping;

/**
* @ClassName:TbOrderShippingDubboService
* @Description:
* @Company: 北京尚学堂科技有限公司 www.bjsxt.com
* @author mengqx
* @date 2017年8月17日
*/

public interface TbOrderShippingDubboService {
	/**
	 * 
	 * @Description:  新增
	 * @param shipping
	 * @return 
	 * @author mengqx
	 * @date   2017年8月17日
	 */
	int insShipping(TbOrderShipping shipping);
}
