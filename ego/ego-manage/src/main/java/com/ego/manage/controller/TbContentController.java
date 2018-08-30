package com.ego.manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.commons.pojo.EasyUIDatagrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.manage.service.TbContentService;
import com.ego.pojo.TbContent;

/**
* @ClassName:TbContentController
* @Description:
* @Company: 北京尚学堂科技有限公司 www.bjsxt.com
* @author mengqx
* @date 2017年8月8日
*/

@Controller
public class TbContentController {

	@Autowired
	private TbContentService tbContentService;
	/**
	 * 
	 * @Description:  
	 * @param categoryId 从前台页面取过来的。一定要跟方法的参数同名。
	 * @param page 从前台页面取过来的。一定要跟方法的参数同名。
	 * @param rows 从前台页面取过来的。一定要跟方法的参数同名。
	 * @return 
	 * @author mengqx
	 * @date   2017年8月8日
	 */
	@RequestMapping("content/query/list")
	@ResponseBody
	public EasyUIDatagrid showContent(long categoryId,int page,int rows){
		return tbContentService.showContent(categoryId, page, rows);
	}
	
	@RequestMapping("rest/content/edit")
	@ResponseBody
	public EgoResult update(TbContent content){
		return  tbContentService.updContent(content);
	}
	
	
	@RequestMapping("/content/save")
	@ResponseBody
	public EgoResult save(TbContent content){
		return  tbContentService.save(content);
	}
	/**
	 * 
	 * @Description:  批量删除
	 * @param ids 根据前台传过来的参数决定！
	 * @return 
	 * @author mengqx
	 * @date   2017年8月9日
	 */
	@RequestMapping("content/delete")
	@ResponseBody
	public EgoResult delete(String ids){
		return  tbContentService.delByIds(ids);
	}

	
	
	
	
}
