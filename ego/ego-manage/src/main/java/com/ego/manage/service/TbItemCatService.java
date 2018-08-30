package com.ego.manage.service;

import java.util.List;

import com.ego.commons.pojo.EasyUITree;

/**
* @ClassName:TbItemCatService
* @Description:
* @Company: 北京尚学堂科技有限公司 www.bjsxt.com
* @author mengqx
* @date 2017年8月3日
*/

public interface TbItemCatService {
//	manage 要跟jsp联系。jsp显示的是Tree。
	List<EasyUITree> show(long pid);
	
}
