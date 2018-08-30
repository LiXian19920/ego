package com.ego.dubbo.service;

import java.util.List;

import com.ego.pojo.TbItemCat;

/**
* @ClassName:TbItemCatDubboService
* @Description:
* @Company: 北京尚学堂科技有限公司 www.bjsxt.com
* @author mengqx
* @date 2017年8月3日
*/

public interface TbItemCatDubboService {
	//	根据父节点的id查询。
	List<TbItemCat> selByPid(long pid);
	//  根据id查询TbItemCat
	TbItemCat selById(long id);
	/**
	 * 
	 * @Description: 根据层次查询所有数据  
	 * @return 
	 * @author mengqx
	 * @date   2017年8月7日
	 */
	List<TbItemCat> selAllCat();
	
}
