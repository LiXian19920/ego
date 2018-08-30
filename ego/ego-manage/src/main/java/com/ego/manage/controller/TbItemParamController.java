package com.ego.manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.commons.pojo.EasyUIDatagrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.manage.service.TbItemParamService;
import com.ego.pojo.TbItemParam;

/**
* @ClassName:TbItemParamController
* @Description:
* @Company: 北京尚学堂科技有限公司 www.bjsxt.com
* @author mengqx
* @date 2017年8月4日
*/
@Controller
public class TbItemParamController {
	
	@Autowired
	private TbItemParamService tbItemParamService;
	
	@RequestMapping("/item/param/list")
	@ResponseBody
	public EasyUIDatagrid showPage(int page,int rows){
		
		return tbItemParamService.showPage(page, rows);
	}
	
	@RequestMapping("item/param/query/itemcatid/{catid}")
	@ResponseBody
	public EgoResult selByCatId(@PathVariable long catid){
		EgoResult er = new EgoResult();
		TbItemParam param = tbItemParamService.selParamByCatId(catid);
		if (param!=null) {
			er.setStatus(200);
			//	将商品的规格参数返给页面！ 注意给是param对象并不是param.getParamData()
			er.setData(param);
		}else{
			er.setStatus(400);
		}
		return er;
	}
	
	@RequestMapping("/item/param/save/{catid}")
	@ResponseBody
	public EgoResult save(@PathVariable long catid,String paramData){
		EgoResult er = new EgoResult();
		int index = tbItemParamService.insParam(catid, paramData);
		if (index>0) {
			er.setStatus(200);
		}else{
			er.setStatus(400);
		}
		return er;
	}
	
	@RequestMapping("item/param/delete")
	@ResponseBody
	public EgoResult delete(String ids){
		EgoResult er = new EgoResult();
		int index = tbItemParamService.delByIds(ids);
		if (index>0) {
			er.setStatus(200);
		}else{
			er.setStatus(400);
		}
		return er;
	}
}
