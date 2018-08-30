package com.ego.dubbo.service;

import java.util.List;

import com.ego.pojo.TbContentCategory;

/**
* @ClassName:TbContentCategoryDubboService
* @Description:
* @Company: 北京尚学堂科技有限公司 www.bjsxt.com
* @author mengqx
* @date 2017年8月8日
*/

public interface TbContentCategoryDubboService {
	/**
	 * 
	 * @Description:  根据id查询所有列表
	 * @param pid
	 * @return 
	 * @author mengqx
	 * @date   2017年8月8日
	 */
	List<TbContentCategory> selByPid(long pid);
	
	/**
	 * 
	 * @Description:  根据前台传过来的数据进行添加操作
	 * @param category
	 * @return 
	 * @author mengqx
	 * @date   2017年8月8日
	 */
	int insCategory(TbContentCategory category);
	/**
	 * 
	 * @Description:  根据前台传过来的值，对数据库进行查询，看数据库中是否有该值
	 * @param category
	 * @return 
	 * @author mengqx
	 * @date   2017年8月8日
	 */
	List<TbContentCategory> selByCategory(TbContentCategory category);
	/**
	 * 
	 * @Description:  创建节点的时候，如果在该节点下，没有任何子节点。则需要修改当前节点为父节点。
	 * @param category
	 * @return 
	 * @author mengqx
	 * @date   2017年8月8日
	 */
	int updCategory(TbContentCategory category);

}
