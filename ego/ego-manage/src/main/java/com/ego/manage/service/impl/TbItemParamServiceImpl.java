package com.ego.manage.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EasyUIDatagrid;
import com.ego.dubbo.service.TbItemCatDubboService;
import com.ego.dubbo.service.TbItemParamDubboService;
import com.ego.manage.pojo.TbItemParamChild;
import com.ego.manage.service.TbItemParamService;
import com.ego.pojo.TbItemParam;

/**
 * @ClassName:TbItemParamServiceImpl
 * @Description:
 * @Company: 北京尚学堂科技有限公司 www.bjsxt.com
 * @author mengqx
 * @date 2017年8月4日
 */

@Service
public class TbItemParamServiceImpl implements TbItemParamService {

	@Reference
	private TbItemParamDubboService tbItemParamDubboService;

	@Reference
	private TbItemCatDubboService tbItemCatDubboService;

	@Override
	public EasyUIDatagrid showPage(int page, int rows) {
		EasyUIDatagrid datagrid = tbItemParamDubboService.showPage(page, rows);
		// 因为我们前台需要的是 TBbItemParamChild 的datagrid数据。 所有先要取得到 TbItemParam。然后进行转换
		List<TbItemParam> list = (List<TbItemParam>) datagrid.getRows();
		// 创建TbItemParamChild 集合对象
		List<TbItemParamChild> childList = new ArrayList<>();
		for (TbItemParam param : list) {
			// 创建TbItemParamChild 对象
			TbItemParamChild child = new TbItemParamChild();
			child.setCreated(param.getCreated());
			child.setId(param.getId());
			child.setItemCatId(param.getItemCatId());
			child.setParamData(param.getParamData());
			child.setUpdated(param.getUpdated());
			// 设置商品类目名称
			child.setItemCatName(tbItemCatDubboService.selById(child.getItemCatId()).getName());
			childList.add(child);
		}
		datagrid.setRows(childList);

		return datagrid;
	}

	@Override
	public TbItemParam selParamByCatId(long catid) {
		return tbItemParamDubboService.selByItemcatid(catid);
	}

	@Override
	public int insParam(long catid, String paramData) {
		TbItemParam param = new TbItemParam();
		Date date = new Date();
		param.setCreated(date);
		param.setItemCatId(catid);
		param.setParamData(paramData);
		param.setUpdated(date);
		return tbItemParamDubboService.insParamSelective(param);
	}

	@Override
	public int delByIds(String ids) {
		// 因为我们不能确定前台传过来的id个数，因为方法中传入的id类型为long
		String[] str = ids.split(",");
		// 定义一个变量来接受方法的返回值
		int index = 0;
		for (String string : str) {
			index += tbItemParamDubboService.delById(Long.parseLong(string));
		}
		if (index == str.length) {
			return 1;
		}
		return 0;
	}
}
