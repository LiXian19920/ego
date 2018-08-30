package com.ego.dubbo.service;

import com.ego.pojo.TbItemParamItem;

/**
* @ClassName:TbItemParamItemDubboService
* @Description:
* @Company: 北京尚学堂科技有限公司 www.bjsxt.com
* @author mengqx
* @date 2017年8月4日
*/

public interface TbItemParamItemDubboService {
	/**
	 * 
	 * @Description:  添加商品规格数据。
	 * @param item
	 * @return 
	 * @author mengqx
	 * @date   2017年8月4日
	 */
	int insParamItemSelective(TbItemParamItem item);
	/**
	 * 
	 * @Description:  根据商品Id查找商品规格参数
	 * @param id
	 * @return 
	 * @author mengqx
	 * @date   2017年8月14日
	 */
	TbItemParamItem selByItemId(long id);
}
