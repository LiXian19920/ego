package com.ego.manage.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ego.commons.pojo.EasyUIDatagrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.manage.service.TbItemService;
import com.ego.pojo.TbItem;

/**
* @ClassName:TbItemController
* @Description:
* @Company: 北京尚学堂科技有限公司 www.bjsxt.com
* @author mengqx
* @date 2017年8月2日
*/

@Controller
public class TbItemController {
	
	@Autowired
	private TbItemService  tbItemService;
	
	@RequestMapping("/item/list")
	@ResponseBody
	public EasyUIDatagrid show(int page,int rows){
		return tbItemService.show(page, rows);
	}
	
	@RequestMapping("/rest/item/delete")
	@ResponseBody
	public EgoResult delete(String ids){
		EgoResult er = new EgoResult();
		try {
			int updStatusById = tbItemService.updStatusById(ids, (byte)3);
			
			if (updStatusById>0) {
				er.setStatus(200);
			}else{
				er.setStatus(400);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return er;
	}
	@RequestMapping("/rest/item/reshelf")
	@ResponseBody
	public EgoResult reshelf(String ids){
		EgoResult er = new EgoResult();
		try {
			int updStatusById = tbItemService.updStatusById(ids, (byte)1);
			
			if (updStatusById>0) {
				er.setStatus(200);
			}else{
				er.setStatus(400);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return er;
	}
	@RequestMapping("/rest/item/instock")
	@ResponseBody
	public EgoResult instock(String ids){
		EgoResult er = new EgoResult();
		try {
			int updStatusById = tbItemService.updStatusById(ids, (byte)2);
			
			if (updStatusById>0) {
				er.setStatus(200);
			}else{
				er.setStatus(400);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return er;
	}
	
	@RequestMapping("pic/upload")
	@ResponseBody
	public Map<String,Object> show(MultipartFile uploadFile){
		
		return	tbItemService.upload(uploadFile);
	}
	
	
	@RequestMapping("item/save")
	@ResponseBody
	public EgoResult save(TbItem item,String desc,String itemParams){
		EgoResult er = new EgoResult();
		int res = tbItemService.insItem(item, desc,itemParams);
		if (res>0) {
			er.setStatus(200);
		}else{
			er.setStatus(400);
		}
		return er;
	}
}
