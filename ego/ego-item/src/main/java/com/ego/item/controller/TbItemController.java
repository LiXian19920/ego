package com.ego.item.controller;

import javax.annotation.Resource;
import javax.jws.WebParam.Mode;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ego.item.service.TbItemService;

/**
* @ClassName:TbItemController
* @Description:
* @Company: 北京尚学堂科技有限公司 www.bjsxt.com
* @author mengqx
* @date 2017年8月14日
*/
@Controller
public class TbItemController {

	@Resource
	private TbItemService tbItemService;

	/**
	 * 
	 * @Description:  根据前台页面分析，后台应该将数据存储在作用域中。前台${item.title }，后台应该存储item
	 * @return 
	 * @author mengqx
	 * @date   2017年8月14日
	 */
	@RequestMapping("item/{id}.html")
	public String showItem(@PathVariable long id,Model model){
		//	将查询到的数据存储到item作用域中。
		model.addAttribute("item", tbItemService.showItem(id));
		return "item";
	}
	
}
