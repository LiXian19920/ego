package com.ego.dubbo.service;

import com.ego.commons.pojo.EasyUIDatagrid;
import com.ego.pojo.TbItemParam;

/**
* @ClassName:TbItemParamDubboService
* @Description:
* @Company: 北京尚学堂科技有限公司 www.bjsxt.com
* @author mengqx
* @date 2017年8月4日
*/

public interface TbItemParamDubboService {
	/**
	 * 
	 * @Description:  查询所有商品规格
	 * @param page
	 * @param rows
	 * @return 
	 * @author mengqx
	 * @date   2017年8月4日
	 */
	EasyUIDatagrid showPage(int page,int rows);
	/**
	 * 
	 * @Description:  根据id查询商品规格参数
	 * @param itemCatId
	 * @return 
	 * @author mengqx
	 * @date   2017年8月4日
	 */
	TbItemParam selByItemcatid(long itemCatId);
	/**
	 * 
	 * @Description:  添加新的商品规格参数
	 * @param param
	 * @return 
	 * @author mengqx
	 * @date   2017年8月4日
	 */
	int insParamSelective(TbItemParam param);
	/**
	 * 
	 * @Description:  根据id删除数据
	 * @param id
	 * @return 
	 * @author mengqx
	 * @date   2017年8月4日
	 */
	int delById(long id);
}
