package com.ego.manage.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EasyUITree;
import com.ego.commons.pojo.EgoResult;
import com.ego.commons.utils.IDUtils;
import com.ego.dubbo.service.TbContentCategoryDubboService;
import com.ego.manage.service.TbContentCategoryService;
import com.ego.pojo.TbContentCategory;

/**
 * @ClassName:TbContentCategoryServiceImpl
 * @Description:
 * @Company: 北京尚学堂科技有限公司 www.bjsxt.com
 * @author mengqx
 * @date 2017年8月8日
 */

@Service
public class TbContentCategoryServiceImpl implements TbContentCategoryService {

	@Reference
	private TbContentCategoryDubboService tbContentCategoryDubboService;

	@Override
	public List<EasyUITree> selByPid(long pid) {
		List<EasyUITree> listTree = new ArrayList<>();
		// 查询数据库中所有的数据
		List<TbContentCategory> list = tbContentCategoryDubboService.selByPid(pid);
		// 循环遍历List<TbContentCategory> 数据库中所有的数据，转换成List<EasyUITree>
		for (TbContentCategory category : list) {
			// 先去创建一个EasyUITree 对象
			EasyUITree tree = new EasyUITree();
			// 给tree进行赋值
			tree.setId(category.getId());
			tree.setText(category.getName());
			tree.setState(category.getIsParent() ? "closed" : "open");
			// 将tree对象添加到listTree中
			listTree.add(tree);
		}
		return listTree;
	}

	@Override
	public EgoResult insCategory(TbContentCategory category) {

		EgoResult er = new EgoResult();

		// 先去查询
		List<TbContentCategory> list = tbContentCategoryDubboService.selByCategory(category);

		if (list.size() > 0 && list != null) {
			er.setStatus(400);
		} else {
			// 正常 走添加
			Date date = new Date();
			category.setId(IDUtils.genItemId());
			category.setSortOrder(1);
			category.setStatus(1);
			category.setCreated(date);
			category.setUpdated(date);
			category.setIsParent(false);
			int result = tbContentCategoryDubboService.insCategory(category);
			// 正常。。。。。。
			if (result > 0) {
				// 更新父节点
				TbContentCategory categoryParent = new TbContentCategory();
				categoryParent.setId(category.getParentId());
				categoryParent.setIsParent(true);
				int index = tbContentCategoryDubboService.updCategory(categoryParent);
				if (index > 0) {
					// 修改成功
					er.setStatus(200);
					// 将修改对象返回给前台
					er.setData(category);
				}
			}
		}
		return er;
	}

	@Override
	public EgoResult updCategory(TbContentCategory category) {
		EgoResult er = new EgoResult();

		// id:90
		// name:小广告234
		TbContentCategory cate = new TbContentCategory();
		cate.setId(category.getId());
		// 查出父节点
		List<TbContentCategory> list = tbContentCategoryDubboService.selByCategory(cate);
		if (list != null && list.size() > 0) {
			cate = list.get(0);
		}
		// 创建一个对象
		TbContentCategory cateParent = new TbContentCategory();
		cateParent.setName(category.getName());
		cateParent.setParentId(cate.getParentId());
		// cateParent.setParentId(tbContentCategoryDubboService.selByCategory(category).get(0).getParentId());
		// 查询当前name 是否存在
		List<TbContentCategory> list2 = tbContentCategoryDubboService.selByCategory(cateParent);

		if (list2 != null && list2.size() > 0) {
			er.setStatus(400);
		} else {
			// 直接就修改了！
			int index = tbContentCategoryDubboService.updCategory(category);
			if (index > 0) {
				er.setStatus(200);
			} else {
				er.setStatus(400);
			}
		}
		return er;
	}

	@Override
	public EgoResult delById(TbContentCategory category) {
		EgoResult er = new EgoResult();

		// 根据传过来的Id查询当前信息
		List<TbContentCategory> list = tbContentCategoryDubboService.selByCategory(category);
		// 当前的分类信息
		TbContentCategory tbContentCategory = list.get(0);
		// 获取当前父节点下有多少个状态为status为1的数据
		TbContentCategory cateParent = new TbContentCategory();
		cateParent.setParentId(tbContentCategory.getParentId());
		cateParent.setStatus(1);
		List<TbContentCategory> list2 = tbContentCategoryDubboService.selByCategory(cateParent);
		// 此段代码是表示直接更新！
		category.setStatus(2);
		int index = tbContentCategoryDubboService.updCategory(category);
		
		if (index > 0) {
			// 这种情况下：说明父节点下只有一个子节点
			if (!(list2 != null && list2.size() > 1)) {
				TbContentCategory afterCategory = new TbContentCategory();
				// 根据传过来的Id找到对象。将对象的父ID传给要更新的父节点对象。
				afterCategory.setId(tbContentCategory.getParentId());
				// 更改父节点的状态为子节点。
				afterCategory.setIsParent(false);
				int res = tbContentCategoryDubboService.updCategory(afterCategory);
				if (res > 0) {
					er.setStatus(200);
				}
			}
			// 当父节点下有多个子节点的时候也会被更新
			else {
				er.setStatus(200);
			}
		}
		return er;
	}

}
