package com.ego.dubbo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ego.commons.pojo.EasyUIDatagrid;
import com.ego.dubbo.service.TbContentDubboService;
import com.ego.mapper.TbContentMapper;
import com.ego.pojo.TbContent;
import com.ego.pojo.TbContentExample;
import com.ego.pojo.TbContentExample.Criteria;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * @ClassName:TbContentDubboServiceImpl
 * @Description:
 * @Company: 北京尚学堂科技有限公司 www.bjsxt.com
 * @author mengqx
 * @date 2017年8月8日
 */

public class TbContentDubboServiceImpl implements TbContentDubboService {

	@Autowired
	private TbContentMapper tbContentMapper;

	@Override
	public List<TbContent> selByCidPage(long cid, int page, int rows) {
		TbContentExample example = new TbContentExample();
		// 分页查找使用分页插件
		PageHelper.startPage(page, rows);

		if (cid != 0) {
			example.createCriteria().andCategoryIdEqualTo(cid);
		}
		// 查询所有数据
		List<TbContent> list = tbContentMapper.selectByExampleWithBLOBs(example);

		PageInfo<TbContent> pi = new PageInfo<>(list);
		
		return pi.getList();
	}
	
	@Override
	public long selCountByCid(long cid) {
		TbContentExample example = new TbContentExample();
		if (cid != 0) {
			// 从前台页面接受节点传过来的categoryid
			example.createCriteria().andCategoryIdEqualTo(cid);
		}
		// 查询共有多少条数据
		return tbContentMapper.countByExample(example);
	}

	@Override
	public int updContent(TbContent content) {
		return tbContentMapper.updateByPrimaryKeyWithBLOBs(content);
	}

	@Override
	public List<TbContent> selById(TbContent content) {
		TbContentExample example = new TbContentExample();
		
		Criteria c = example.createCriteria();
		
		if (content.getId()!=null) {
			c.andIdEqualTo(content.getId());
		}
		return tbContentMapper.selectByExampleWithBLOBs(example);
	}

	@Override
	public List<TbContent> selAllBy(long category_id) {
		TbContentExample example = new TbContentExample();
		//	自定义sql语句将传过来的id进行拼接查询数据。
		example.createCriteria().andCategoryIdEqualTo(category_id);
		return tbContentMapper.selectByExampleWithBLOBs(example);
	}

	@Override
	public int insContent(TbContent content) {
		return tbContentMapper.insertSelective(content);
	}

	@Override
	public int delById(Long id) {
		return tbContentMapper.deleteByPrimaryKey(id);
	}


}
