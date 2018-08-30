package com.ego.cart.controller;


import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.cart.pojo.Cart;
import com.ego.cart.service.CartService;
import com.ego.commons.pojo.EgoResult;
/**
* @ClassName:CartController
* @Description:
* @Company: 北京尚学堂科技有限公司 www.bjsxt.com
* @author mengqx
* @date 2017年8月15日
*/
@Controller
public class CartController {

	@Resource
	private CartService cartService;
	@RequestMapping("cart/add/{itemId}.html")
	public String addCart(@PathVariable long itemId,@RequestParam(defaultValue="1") int num,HttpServletRequest request,Model model){
		try {
			cartService.addCart(itemId, num, request);
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
			return "error/exception";
		}
		return "cartSuccess";
	}
	/**
	 * 
	 * @Description:  显示所有购物信息
	 * @param model
	 * @param request
	 * @return 
	 * @author mengqx
	 * @date   2017年8月17日
	 */
	@RequestMapping("cart/cart.html")
	public String showCart(Model model,HttpServletRequest request){
		List<Cart> list = cartService.showCart(request);
		if (list.size()>0 && list!=null) {
			model.addAttribute("cartList", list);
		}
		return "cart";
	}
	/**
	 * 
	 * @Description:  
	 * @param itemId 商品ID
	 * @param num 修改后的数量
	 * @param request
	 * @return 
	 * @author mengqx
	 * @date   2017年8月16日
	 */
	@RequestMapping("/cart/update/num/{itemId}/{num}.action")
	@ResponseBody
	public EgoResult updNum(@PathVariable long itemId,@PathVariable int num,HttpServletRequest request){
		System.out.println(itemId+"\t"+num);
		EgoResult er = cartService.upItemNum(itemId, num, request);
		return er;
	}

	
	@RequestMapping("cart/delete/{id}.action")
	@ResponseBody
	public EgoResult delete(@PathVariable long id,HttpServletRequest request){
		return  cartService.delete(id, request);
	}
	
	@RequestMapping("order/order-cart.html")
	public String showCartOrder(@RequestParam(value="id") List<Long> id,Model model,HttpServletRequest request){
		List<Cart> list = cartService.showCartOrder(request, id);
		if (list!=null && list.size()>0 ) {
			model.addAttribute("cartList", list);
			return "order-cart";
		}else{
			model.addAttribute("message", "库存不足.");
			return "info/info";
		}
	}
	
	
	
}
