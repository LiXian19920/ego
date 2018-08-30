package com.ego.dubbo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ego.commons.pojo.EasyUIDatagrid;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.mapper.TbItemMapper;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * @ClassName:TbItemDubboServiceImpl
 * @Description:
 * @Company: 北京尚学堂科技有限公司 www.bjsxt.com
 * @author mengqx
 * @date 2017年8月2日
 */

public class TbItemDubboServiceImpl implements TbItemDubboService {

	@Autowired
	private TbItemMapper tbItemMapper;

	@Override
	public EasyUIDatagrid showPage(int page, int rows) {
		// 利用分页插件
		PageHelper.startPage(page, rows);
		// 查询数据
		List<TbItem> list = tbItemMapper.selectByExample(new TbItemExample());
		// 利用pageInfo类来进行分页显示
		PageInfo<TbItem> pi = new PageInfo<>(list);
		// 方法返回值是一个EasyUIDatagrid
		EasyUIDatagrid datagrid = new EasyUIDatagrid();
		datagrid.setTotal(pi.getTotal());
		datagrid.setRows(pi.getList());
		
		return datagrid;
	}

	@Override
	public int updStatusById(long id, byte status) {
		TbItem item = new TbItem();
		item.setId(id);
		item.setStatus(status);
		return tbItemMapper.updateByPrimaryKeySelective(item);
	}

	@Override
	public int insItem(TbItem item) {
		return tbItemMapper.insertSelective(item);
	}

	@Override
	public List<TbItem> selAll() {
		return tbItemMapper.selectByExample(new TbItemExample());
	}

	@Override
	public TbItem selById(long id) {
//		因为查询的是单个商品。所有选择根据id查询即可！
		return tbItemMapper.selectByPrimaryKey(id);
	}

}
