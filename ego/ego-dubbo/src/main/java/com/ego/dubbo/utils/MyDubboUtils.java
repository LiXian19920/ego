package com.ego.dubbo.utils;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ego.mapper.TbItemCatMapper;
import com.ego.pojo.TbItemCat;
import com.ego.pojo.TbItemCatExample;

/**
 * @ClassName:MyDubboUtils 使用递归查找菜单
 * @Description:
 * @Company: 北京尚学堂科技有限公司 www.bjsxt.com
 * @author mengqx
 * @date 2017年8月7日
 */

public class MyDubboUtils {

	@Autowired
	private TbItemCatMapper tbItemCatMapper;

	public List<TbItemCat> selByPid(long pid) {
		TbItemCatExample example = new TbItemCatExample();
		// 去赋予查询的顺应
		example.setOrderByClause("sort_order asc");
		// 定义查询条件
		example.createCriteria().andParentIdEqualTo(pid).andStatusEqualTo(1);
		// 改集合中是全部数据!
		List<TbItemCat> list = tbItemCatMapper.selectByExample(example);
		// 使用递归进行遍历

		for (TbItemCat tbItemCat : list) {
			if (tbItemCat.getIsParent()) {
				// 循环出来的集合对象中有父节点。则继续进行查询
				tbItemCat.setChildren(selByPid(tbItemCat.getId()));
			}
		}
		return list;
	}
}
