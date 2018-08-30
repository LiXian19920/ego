package com.ego.item.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.item.service.TbItemDescService;

/**
 * @ClassName:TbItemDescController
 * @Description:
 * @Company: 北京尚学堂科技有限公司 www.bjsxt.com
 * @author mengqx
 * @date 2017年8月14日
 */

@Controller
public class TbItemDescController {

	@Resource
	private TbItemDescService tbItemDescService;

	// produces:解决中文乱码问题：
	@RequestMapping(value = "item/desc/{id}.html", produces = "text/html;charset=utf-8")
	@ResponseBody
	public String showDesc(@PathVariable long id) {
		// tbItemDescService.selByItemId(id) 取得商品描述对象。
		// tbItemDescService.selByItemId(id).getItemDesc()：
		// 获取商品描述对象的属性：我们页面需要的就是一个描述内容。
		return tbItemDescService.selByItemId(id).getItemDesc();
	}

}
