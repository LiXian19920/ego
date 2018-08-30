package com.ego.manage.service;

import com.ego.commons.pojo.EasyUIDatagrid;
import com.ego.pojo.TbItemCat;
import com.ego.pojo.TbItemParam;

/**
* @ClassName:TbItemParamService
* @Description:
* @Company: 北京尚学堂科技有限公司 www.bjsxt.com
* @author mengqx
* @date 2017年8月4日
*/

public interface TbItemParamService {
	EasyUIDatagrid  showPage(int page,int rows);
	TbItemParam selParamByCatId(long catid);
//	添加的时候，需要添加一个商品类目，还需要添加paramData
	int insParam(long catid,String paramData);
//	根据id进行批量删除
	int delByIds(String ids);
}
