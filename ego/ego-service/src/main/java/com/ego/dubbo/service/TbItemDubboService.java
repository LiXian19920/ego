package com.ego.dubbo.service;

import java.util.List;

import com.ego.commons.pojo.EasyUIDatagrid;
import com.ego.pojo.TbItem;

/**
* @ClassName:TbItemDubboService
* @Description:
* @Company: 北京尚学堂科技有限公司 www.bjsxt.com
* @author mengqx
* @date 2017年8月2日
*/

public interface TbItemDubboService {
	/**
	 * 
	 * @Description:  分页功能
	 * @param page
	 * @param rows
	 * @return 
	 * @author mengqx
	 * @date   2017年8月2日
	 */
	public EasyUIDatagrid showPage(int page, int rows);
	/**
	 * 
	 * @Description:  实现上架，下架，删除
	 * @param id
	 * @param status
	 * @return 
	 * @author mengqx
	 * @date   2017年8月2日
	 */
	int updStatusById(long id,byte status);
	/**
	 * 
	 * @Description:添加商品  
	 * @param item
	 * @return 
	 * @author mengqx
	 * @date   2017年8月11日
	 */
	int insItem(TbItem item);

	/**
	 * 
	 * @Description:  查询所有商品
	 * @return 
	 * @author mengqx
	 * @date   2017年8月11日
	 */
	List<TbItem> selAll();
	/**
	 * 
	 * @Description:  根据商品ID查询商品信息
	 * @param id
	 * @return 
	 * @author mengqx
	 * @date   2017年8月14日
	 */
	TbItem selById(long id);
	
}
