package com.ego.dubbo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ego.commons.pojo.EasyUIDatagrid;
import com.ego.dubbo.service.TbItemParamDubboService;
import com.ego.mapper.TbItemParamMapper;
import com.ego.pojo.TbItemParam;
import com.ego.pojo.TbItemParamExample;
import com.ego.pojo.TbItemParamExample.Criteria;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * @ClassName:TbItemParamDubboServiceImpl
 * @Description:
 * @Company: 北京尚学堂科技有限公司 www.bjsxt.com
 * @author mengqx
 * @date 2017年8月4日
 */

public class TbItemParamDubboServiceImpl implements TbItemParamDubboService {

	@Autowired
	private TbItemParamMapper tbItemParamMapper;

	@Override
	public EasyUIDatagrid showPage(int page, int rows) {

		// 使用分页插件
		PageHelper.startPage(page, rows);

		// selectByExampleWithBLOBs 可以查询数据库中存储类型为text的数据。
		List<TbItemParam> list = tbItemParamMapper.selectByExampleWithBLOBs(new TbItemParamExample());

		// 使用pageInfo对象。
		PageInfo<TbItemParam> pi = new PageInfo<>(list);

		EasyUIDatagrid datagrid = new EasyUIDatagrid();

		datagrid.setTotal(pi.getTotal());

		datagrid.setRows(pi.getList());
		

		return datagrid;
	}

	@Override
	public TbItemParam selByItemcatid(long itemCatId) {
//		selectByExampleWithBLOBs ,selectByPrimaryKey:该方法不支持数据库中属性是text。
//		而我们要找的paramData：正好是text,存储的json。
//		return tbItemParamMapper.selectByPrimaryKey(itemCatId);
//		TbItemParamExample-- createCriteria() --- 自定义sql语句查询。 mybatis:逆向工程中，每个java类都会生成一个Example类。可以用来自定义查询条件
		TbItemParamExample example = new TbItemParamExample();
		example.createCriteria().andItemCatIdEqualTo(itemCatId);
//		该方法返回一个list集合
		List<TbItemParam> list = tbItemParamMapper.selectByExampleWithBLOBs(example);
//		我们需要的是根据id查询一个对象。
		if (list.size()>0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public int insParamSelective(TbItemParam param) {
//		insertSelective 可以为空，insert 不行！
		return tbItemParamMapper.insertSelective(param);
		
	}

	@Override
	public int delById(long id) {
		return tbItemParamMapper.deleteByPrimaryKey(id);
	}


}
