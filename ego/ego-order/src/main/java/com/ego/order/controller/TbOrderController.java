package com.ego.order.controller;


import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ego.order.pojo.MyOrder;
import com.ego.order.service.TbOrderService;


/**
* @ClassName:TbOrderController
* @Description:
* @Company: 北京尚学堂科技有限公司 www.bjsxt.com
* @author mengqx
* @date 2017年8月17日
*/
@Controller
public class TbOrderController {

	//	调用service
	@Resource
	private TbOrderService tbOrderService;
	//	http://localhost:8080/pay/order/paystatus.jhtml
	@RequestMapping("order/create.html")
	public String createOrder(MyOrder order , Model model){
		try {
			Map<String, Object> map = tbOrderService.createOrder(order);
			//	应该将我们取到的值，存入作用域：
			model.addAttribute("orderId", map.get("orderId"));
			model.addAttribute("date",map.get("date"));
			model.addAttribute("payment", order.getPayment());
			return "success";
		} catch (Exception e) {
			model.addAttribute("message", "服务器异常");
			return "error/exception";

		}
	}
	
}
