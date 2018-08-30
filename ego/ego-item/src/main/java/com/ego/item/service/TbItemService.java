package com.ego.item.service;

import com.ego.commons.pojo.TbItemChild;

/**
* @ClassName:TbItemService
* @Description:
* @Company: 北京尚学堂科技有限公司 www.bjsxt.com
* @author mengqx
* @date 2017年8月14日
*/

public interface TbItemService {
	/**
	 * 
	 * @Description:  该方法符合前台页面显示逻辑。
	 * @param id
	 * @return 
	 * @author mengqx
	 * @date   2017年8月14日
	 */
	TbItemChild showItem(long id); 
}
