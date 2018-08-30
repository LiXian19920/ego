package com.ego.dubbo.service;

import com.ego.pojo.TbItemDesc;

/**
* @ClassName:TbItemDescDubboService
* @Description:
* @Company: 北京尚学堂科技有限公司 www.bjsxt.com
* @author mengqx
* @date 2017年8月3日
*/

public interface TbItemDescDubboService {
	int insDesc(TbItemDesc desc);
	/**
	 * 
	 * @Description:  根据商品id查询商品描述
	 * @param itemId
	 * @return 
	 * @author mengqx
	 * @date   2017年8月11日
	 */
	TbItemDesc selByItemid(long itemId);
}
