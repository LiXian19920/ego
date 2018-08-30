package com.ego.manage.service;

import java.util.List;

import com.ego.commons.pojo.EasyUITree;
import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbContentCategory;

/**
* @ClassName:TbContentCategoryService
* @Description:
* @Company: 北京尚学堂科技有限公司 www.bjsxt.com
* @author mengqx
* @date 2017年8月8日
*/

public interface TbContentCategoryService {
	/**
	 * 
	 * @Description:  根据前台页面传过来的id进行查询显示，并封装成EasyUITree集合。
	 * @param pid
	 * @return 
	 * @author mengqx
	 * @date   2017年8月8日
	 */
	List<EasyUITree> selByPid(long pid);
	/**
	 * 
	 * @Description:  返回最终结果给前台页面。
	 * @param category
	 * @return 
	 * @author mengqx
	 * @date   2017年8月8日
	 */
	EgoResult insCategory(TbContentCategory category);
	/**
	 * 
	 * @Description:  根据前台页面传过来的对象，进行修改最终返回一个EgoResult。
	 * @param category
	 * @return 
	 * @author mengqx
	 * @date   2017年8月8日
	 */
	EgoResult updCategory(TbContentCategory category);
	
	/**
	 * 
	 * @Description:  删除方法
	 * @param category
	 * @return 
	 * @author mengqx
	 * @date   2017年8月8日
	 */
	EgoResult delById(TbContentCategory category);
	
}
