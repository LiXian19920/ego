package com.ego.item.controller;

import javax.annotation.Resource;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.item.service.TbItemCatService;

/**
* @ClassName:TbItemCatController
* @Description:
* @Company: 北京尚学堂科技有限公司 www.bjsxt.com
* @author mengqx
* @date 2017年8月7日
*/
@Controller
public class TbItemCatController {
	
	@Resource
	private TbItemCatService tbItemCatService;
	
	/**
	 * 跨域调用：使用的返回值是谁？MappingJacksonValue,
	 * callback：跨域调用的参数名。
	 */
	@RequestMapping("rest/itemcat/all")
	@ResponseBody
	public MappingJacksonValue showCat(String callback){
//		使用jsonp跨域访问必须要调用的函数返回值！
		MappingJacksonValue mjv = new MappingJacksonValue(tbItemCatService.showCat());
//		将参数存放到MappingJacksonValue 中！
		mjv.setJsonpFunction(callback);
		return mjv;
	}
}
