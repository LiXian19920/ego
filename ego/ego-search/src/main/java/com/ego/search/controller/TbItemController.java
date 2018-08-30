package com.ego.search.controller;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.search.service.TbItemService;

/**
 * @ClassName:TbItemController
 * @Description:
 * @Company: 北京尚学堂科技有限公司 www.bjsxt.com
 * @author mengqx
 * @date 2017年8月11日
 */

@Controller
public class TbItemController {

	@Autowired
	private TbItemService tbItemService;

	@RequestMapping(value = "solr/init", produces = "application/json;charset=utf-8")
	@ResponseBody
	public String init() {
		// 获得系统时间单位是毫秒 开始
		long start = System.currentTimeMillis();
		try {
			tbItemService.initItem();
		} catch (Exception e) {
			e.printStackTrace();
			return "初始化失败!";
		}
		// 获取结束时间
		long stop = System.currentTimeMillis();
		return "初始化成功,使用时间" + (stop - start) / 1000 + "秒";

	}
	
	
	/**
	 * 实现商品搜索功能 springmvc ：不会拦截.html
	 * 控制器名称.html不能在springmvc.xml中配置<mvc:resource />配置html的映射
	 * 如果希望配置html映射,还需要有的控制器以.html结尾.控制器的路径中不能出现/html/
	 * @param q 前台页面传过来的值！ q=手机 
	 * @param page ：表示当前页:默认值：1
	 * @return 直接返回到查询页面
	 */
	@RequestMapping(value={"search.html","search"})
	public String showItemList(String q,Model model,@RequestParam(defaultValue="1") int page){
//		当在搜索的时候，请求方式是get，springmvc 拦截不会对get请求方式做编码处理。
//		method = "post/get" .
		try {
//			处理前台请求方式为get的处理。
			q = new String(q.getBytes("ISO-8859-1"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("转换后的q:"+q);
		
		try {
			Map<String, Object> map = tbItemService.showItem(q, page);
			model.addAttribute("itemList",map.get("list"));
			model.addAttribute("totalPages", map.get("totalPages"));
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		
		model.addAttribute("query", q);
		model.addAttribute("page",page);
		return "search";
	}

}
