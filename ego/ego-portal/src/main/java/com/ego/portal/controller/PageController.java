package com.ego.portal.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ego.portal.service.TbContentService;



/**
* @ClassName:PageController
* @Description:
* @Company: 北京尚学堂科技有限公司 www.bjsxt.com
* @author mengqx
* @date 2017年8月7日
*/

@Controller
public class PageController {
	
	@Autowired
	private TbContentService tbContentService;
	
	@RequestMapping("/")
	public String welcome(Model model){
		model.addAttribute("ad1", tbContentService.showBigPic());
		return "index";
	}
}
