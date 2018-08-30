package com.ego.dubbo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ego.dubbo.service.TbContentCategoryDubboService;
import com.ego.mapper.TbContentCategoryMapper;
import com.ego.pojo.TbContentCategory;
import com.ego.pojo.TbContentCategoryExample;
import com.ego.pojo.TbContentCategoryExample.Criteria;


/**
 * @ClassName:TbContentCategoryDubboServiceImpl
 * @Description:
 * @Company: 北京尚学堂科技有限公司 www.bjsxt.com
 * @author mengqx
 * @date 2017年8月8日
 */

public class TbContentCategoryDubboServiceImpl implements TbContentCategoryDubboService {

	@Autowired
	private TbContentCategoryMapper tbContentCategoryMapper;

	@Override
	public List<TbContentCategory> selByPid(long pid) {
		TbContentCategoryExample example = new TbContentCategoryExample();
		// 对查出的结果进行排序
		example.setOrderByClause("sort_order asc");
		example.createCriteria().andParentIdEqualTo(pid).andStatusEqualTo(1);
		// 将集合返回去。
		return tbContentCategoryMapper.selectByExample(example);
	}

	@Override
	public int insCategory(TbContentCategory category) {
		
		return tbContentCategoryMapper.insertSelective(category);
	}
	
	@Override
	public List<TbContentCategory> selByCategory(TbContentCategory cate) {
		//	TbContentCategoryExample可以使用自定义的sql语句：where后面的拼接sql。 
		TbContentCategoryExample example = new  TbContentCategoryExample();
		
		if(cate.getSortOrder()!=null){
			example.setOrderByClause("sort_order asc");
		}
		//	example.createCriteria().andParentIdEqualTo(pid).andStatusEqualTo(1) 动态sql查询 
		Criteria c = example.createCriteria();
		if(cate.getId()!=null){
			c.andIdEqualTo(cate.getId());
		}
		if(cate.getIsParent()!=null){
			c.andIsParentEqualTo(cate.getIsParent());
		}
		if(cate.getName()!=null){
			c.andNameEqualTo(cate.getName());
		}
		if(cate.getParentId()!=null){
			c.andParentIdEqualTo(cate.getParentId());
		}
		if(cate.getStatus()!=null){
			c.andStatusEqualTo(cate.getStatus());
		}
		return tbContentCategoryMapper.selectByExample(example);
	}

	@Override
	public int updCategory(TbContentCategory category) {
		return tbContentCategoryMapper.updateByPrimaryKeySelective(category);
	}

}
