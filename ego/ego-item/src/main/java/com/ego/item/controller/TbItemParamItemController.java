package com.ego.item.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.item.service.TbItemParamItemService;

/**
* @ClassName:TbItemParamItemController
* @Description:
* @Company: 北京尚学堂科技有限公司 www.bjsxt.com
* @author mengqx
* @date 2017年8月14日
*/
@Controller
public class TbItemParamItemController {

	@Resource
	private TbItemParamItemService tbItemParamItemService;
	
	@RequestMapping(value="item/param/{id}.html",produces="text/html;charset=utf-8")
	@ResponseBody
	public String showPram(@PathVariable long id){
		return tbItemParamItemService.showParam(id);
	}
	
}
