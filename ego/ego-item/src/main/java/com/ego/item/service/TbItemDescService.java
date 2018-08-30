package com.ego.item.service;

import com.ego.pojo.TbItemDesc;

/**
* @ClassName:TbItemDescService
* @Description:
* @Company: 北京尚学堂科技有限公司 www.bjsxt.com
* @author mengqx
* @date 2017年8月14日
*/

public interface TbItemDescService {
	/**
	 * 
	 * @Description:  根据商品id查询商品描述信息
	 * @param id
	 * @return 
	 * @author mengqx
	 * @date   2017年8月14日
	 */
	TbItemDesc selByItemId(long id);
}
