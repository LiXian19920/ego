package com.ego.manage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.commons.pojo.EasyUITree;
import com.ego.manage.service.TbItemCatService;

/**
* @ClassName:TbItemCatController
* @Description:
* @Company: 北京尚学堂科技有限公司 www.bjsxt.com
* @author mengqx
* @date 2017年8月3日
*/

@Controller
public class TbItemCatController {

	@Autowired
	private TbItemCatService tbItemCatService;
	
	// id :就是前台传过来的父节点id 。 默认值0.查找所有的默认节点
	@RequestMapping("/item/cat/list")
	@ResponseBody
	public List<EasyUITree> showCat(@RequestParam(defaultValue="0") int id){
		return tbItemCatService.show(id);
	}
}

