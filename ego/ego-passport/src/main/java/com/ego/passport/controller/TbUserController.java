package com.ego.passport.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.commons.pojo.EgoResult;
import com.ego.passport.service.TbUserService;
import com.ego.pojo.TbUser;

/**
* @ClassName:TbUserController
* @Description:
* @Company: 北京尚学堂科技有限公司 www.bjsxt.com
* @author mengqx
* @date 2017年8月15日
*/
@Controller
public class TbUserController {

	
	@Resource
	private TbUserService tbUserService;
	
	//	springmvc中有一个注解@RequestHeader：取得请求头信息。
	@RequestMapping("user/showLogin")
	public String showLogin(Model model,@RequestHeader("Referer") String referer){
		if (referer!=null) {
			model.addAttribute("redirect", referer);
		}else{
			model.addAttribute("redirect", "");
		}
		
		return "login";
	}
	
	@RequestMapping("user/login")
	@ResponseBody
	public EgoResult login(TbUser user,HttpServletRequest req,HttpServletResponse res){
		EgoResult er = tbUserService.login(user,req,res);
		return er;
	}
	
	//sso-接口文档中callback是可选参数。如果callback有的话。返回值应该是MappingJacksonValue
	//如果callback没有。则返回状态是200.
	@RequestMapping("user/token/{token}")
	@ResponseBody
	public Object getToken(String callback, @PathVariable String token){
		
		EgoResult er = tbUserService.getToken(token);
		if (callback==null) {
			return er;
		}else{
			MappingJacksonValue mjv = new MappingJacksonValue(er);
			mjv.setJsonpFunction(callback);
			return mjv;
		}
	}
	
	
//	退出功能
	@RequestMapping("user/logout/{token}")
	@ResponseBody
	public Object logout(String callback,@PathVariable String token,HttpServletRequest request,HttpServletResponse response){
		EgoResult er = tbUserService.logout(token, request, response);
		if (callback==null && "".equals(callback)) {
			return er;
		}else{
			MappingJacksonValue mjv = new MappingJacksonValue(er);
			mjv.setJsonpFunction(callback);
			return mjv;
		}
	}
}
