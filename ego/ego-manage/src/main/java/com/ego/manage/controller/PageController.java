package com.ego.manage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
* @ClassName:PageController
* @Description:
* @Company: 北京尚学堂科技有限公司 www.bjsxt.com
* @author mengqx
* @date 2017年8月2日
*/

@Controller
public class PageController {
	
	@RequestMapping("/")
	public String welcome(){
		return "index";
	}
	
	@RequestMapping("{page}")
	public String showPage(@PathVariable(value="page") String page){
		return page;
	}
	
	
}
