package com.ego.dubbo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

import com.ego.dubbo.service.TbItemCatDubboService;
import com.ego.dubbo.utils.MyDubboUtils;
import com.ego.mapper.TbItemCatMapper;
import com.ego.pojo.TbItemCat;
import com.ego.pojo.TbItemCatExample;

/**
* @ClassName:TbItemCatDubboServiceImpl
* @Description:
* @Company: 北京尚学堂科技有限公司 www.bjsxt.com
* @author mengqx
* @date 2017年8月3日
*/

public class TbItemCatDubboServiceImpl implements TbItemCatDubboService{

	@Autowired
	private TbItemCatMapper tbItemCatMapper;
		
	@Resource
	private MyDubboUtils myDubboUtils;
	
	@Override
	public List<TbItemCat> selByPid(long pid) {
		TbItemCatExample example = new TbItemCatExample();
		//	安装升序进行排序
		example.setOrderByClause("sort_order asc");
		//  pid相当于点击父节点传过来的id. 1:代表正常显示
		example.createCriteria().andParentIdEqualTo(pid).andStatusEqualTo(1);

		return tbItemCatMapper.selectByExample(example);
	}

	@Override
	public TbItemCat selById(long id) {
		return tbItemCatMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<TbItemCat> selAllCat() {
		//	此时该实现类应该去使用工具类MyDubboUtils类中的方法
		return myDubboUtils.selByPid(0);
	}

}
