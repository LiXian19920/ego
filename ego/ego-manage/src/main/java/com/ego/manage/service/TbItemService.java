package com.ego.manage.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.ego.commons.pojo.EasyUIDatagrid;
import com.ego.pojo.TbItem;

/**
* @ClassName:TbItemService
* @Description:
* @Company: 北京尚学堂科技有限公司 www.bjsxt.com
* @author mengqx
* @date 2017年8月2日
*/

public interface TbItemService {
	EasyUIDatagrid show(int page,int rows);
	/**
	 * 
	 * @Description:  伪删除，修改状态
	 * @param ids
	 * @param status
	 * @return 
	 * @author mengqx
	 * @date   2017年8月9日
	 */
	int updStatusById(String ids,byte status);
	Map<String,Object> upload(MultipartFile file);
	//	该接口要实现一次性添加两个表数据。实体类，desc 前台页面的值！修改一下。将TbItemParamItem对象的值，封装成json串。
	int insItem(TbItem item,String desc,String itemParams);
	
	
	
	
}
