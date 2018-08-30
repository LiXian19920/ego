package com.ego.manage.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EasyUITree;
import com.ego.dubbo.service.TbItemCatDubboService;
import com.ego.manage.service.TbItemCatService;
import com.ego.pojo.TbItemCat;

/**
* @ClassName:TbItemCatServiceImpl
* @Description:
* @Company: 北京尚学堂科技有限公司 www.bjsxt.com
* @author mengqx
* @date 2017年8月3日
*/

@Service
public class TbItemCatServiceImpl implements TbItemCatService {

	@Reference
	private TbItemCatDubboService tbItemCatDubboService;
	
	@Override
	public List<EasyUITree> show(long pid) {
//		我们需要的是id，name，is_parent.
		List<EasyUITree> list = new ArrayList<>();
//		该方法返回的是商品类目的详细信息。
		List<TbItemCat> cat = tbItemCatDubboService.selByPid(pid);
		for (TbItemCat tbItemCat : cat) {
			EasyUITree et = new EasyUITree();
			et.setId(tbItemCat.getId());
			et.setState(tbItemCat.getIsParent()?"closed":"open");
			et.setText(tbItemCat.getName());
			list.add(et);
		}
		return list;
	}

}
