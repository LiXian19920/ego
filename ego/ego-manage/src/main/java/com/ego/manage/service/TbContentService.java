package com.ego.manage.service;

import com.ego.commons.pojo.EasyUIDatagrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbContent;

/**
* @ClassName:TbContentService
* @Description:
* @Company: 北京尚学堂科技有限公司 www.bjsxt.com
* @author mengqx
* @date 2017年8月8日
*/

public interface TbContentService {
	/**
	 * 
	 * @Description:  返回数据表格给前台页面
	 * @param categoryId
	 * @param page
	 * @param rows
	 * @return 
	 * @author mengqx
	 * @date   2017年8月8日
	 */
	EasyUIDatagrid showContent(long categoryId,int page,int rows);
	
	/**
	 * 
	 * @Description:  修改方法
	 * @param content
	 * @return 
	 * @author mengqx
	 * @date   2017年8月9日
	 */
	EgoResult updContent(TbContent content);
	
	/**
	 * 
	 * @Description:  大广告位的新增方法
	 * @param content
	 * @return 
	 * @author mengqx
	 * @date   2017年8月9日
	 */
	EgoResult save(TbContent content);
	/**
	 * 
	 * @Description:  批量物理删除
	 * @param ids
	 * @return 
	 * @author mengqx
	 * @date   2017年8月9日
	 */
	EgoResult delByIds(String ids);
	
}