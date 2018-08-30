package com.ego.item.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.dubbo.service.TbItemCatDubboService;
import com.ego.item.pojo.CatMenu;
import com.ego.item.pojo.PortalCatMenu;
import com.ego.item.service.TbItemCatService;
import com.ego.pojo.TbItemCat;

/**
 * @ClassName:TbItemCatServiceImpl
 * @Description:
 * @Company: 北京尚学堂科技有限公司 www.bjsxt.com
 * @author mengqx
 * @date 2017年8月7日
 */
@Service
public class TbItemCatServiceImpl implements TbItemCatService {

	@Reference
	private TbItemCatDubboService tbItemCatDubboService;

	@Override
	public CatMenu showCat() {
		CatMenu cm = new CatMenu();
		List<Object> list = getAllCat(tbItemCatDubboService.selAllCat());
		// 将转换过来的数据存在data中。
		cm.setData(list);
		return cm;
	}
	/**
	 * 
	 * @Description: 用来进行数据拼装。
	 * @return
	 * @author mengqx
	 * @date 2017年8月7日
	 */
	public List<Object> getAllCat(List<TbItemCat> list) {
		List<Object> listObj = new ArrayList<>();
		for (TbItemCat tbItemCat : list) {
			// 如果tbItemcat 是父节点。怎进行转换{u,n,i}
			if (tbItemCat.getIsParent()) {
				PortalCatMenu pm = new PortalCatMenu();
				pm.setU("/products/" + tbItemCat.getId() + ".html");
				pm.setN("<a href='/products/" + tbItemCat.getId() + ".html'>" + tbItemCat.getName() + "</a>");
				// 递归取得所有的子节点
				pm.setI(getAllCat(tbItemCat.getChildren()));
				listObj.add(pm);
			}else{
				listObj.add("/products/"+tbItemCat.getId()+"|"+tbItemCat.getName());
				//	/products/1|冰箱，洗衣机，电视剧
			}
		}
		return listObj;
	}

}
