package com.ego.manage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.commons.pojo.EasyUITree;
import com.ego.commons.pojo.EgoResult;
import com.ego.manage.service.TbContentCategoryService;
import com.ego.pojo.TbContentCategory;

/**
* @ClassName:TbContentCategoryController
* @Description:
* @Company: 北京尚学堂科技有限公司 www.bjsxt.com
* @author mengqx
* @date 2017年8月8日
*/
@Controller
public class TbContentCategoryController {
	
	@Autowired
	private TbContentCategoryService tbContentCategoryService;
	
	
	@RequestMapping("content/category/list")
	@ResponseBody
	public List<EasyUITree> showCategory(@RequestParam(defaultValue="0") long id){
		return  tbContentCategoryService.selByPid(id);
	}
	
	@RequestMapping("content/category/create")
	@ResponseBody
	public EgoResult create(TbContentCategory category ){
		return  tbContentCategoryService.insCategory(category);
	}
	
	@RequestMapping("content/category/update")
	@ResponseBody
	public EgoResult update(TbContentCategory category ){
		return  tbContentCategoryService.updCategory(category);
	}
	
	@RequestMapping("content/category/delete/")
	@ResponseBody
	public EgoResult delete(TbContentCategory category ){
		return  tbContentCategoryService.delById(category);
	}
	
}
