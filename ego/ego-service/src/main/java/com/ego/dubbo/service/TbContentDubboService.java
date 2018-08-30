package com.ego.dubbo.service;

import java.util.List;

import com.ego.pojo.TbContent;

/**
* @ClassName:TbContentDubboService
* @Description:
* @Company: 北京尚学堂科技有限公司 www.bjsxt.com
* @author mengqx
* @date 2017年8月8日
*/

public interface TbContentDubboService {
	/**
	 * 
	 * @Description:  查询某一节点的内容
	 * @param cid
	 * @param page
	 * @param rows
	 * @return 
	 * @author mengqx
	 * @date   2017年8月8日
	 */
	List<TbContent> selByCidPage(long cid,int page,int rows);
	/**
	 * 
	 * @Description:  查询某一节点内容的总条数
	 * @param cid
	 * @return 
	 * @author mengqx
	 * @date   2017年8月8日
	 */
	long selCountByCid(long cid);
	/**
	 * 
	 * @Description:  修改方法
	 * @param content
	 * @return 
	 * @author mengqx
	 * @date   2017年8月9日
	 */
	int updContent(TbContent content);
	/**
	 * 
	 * @Description:  根据id查询TbContent对象
	 * @param content
	 * @return 
	 * @author mengqx
	 * @date   2017年8月9日
	 */
	List<TbContent> selById(TbContent content);
	
	/**
	 * 
	 * @Description:  根据categoryid查询所有数据
	 * @param category_id
	 * @return 
	 * @author mengqx
	 * @date   2017年8月9日
	 */
	List<TbContent> selAllBy(long category_id);
	/**
	 * 
	 * @Description:  新增大广告位方法
	 * @param content
	 * @return 
	 * @author mengqx
	 * @date   2017年8月9日
	 */
	int insContent(TbContent content);
	/**
	 * 
	 * @Description:根据前台页面传过来的内容进行批量删除  
	 * @param id
	 * @return 
	 * @author mengqx
	 * @date   2017年8月9日
	 */
	int delById(Long id);
}
