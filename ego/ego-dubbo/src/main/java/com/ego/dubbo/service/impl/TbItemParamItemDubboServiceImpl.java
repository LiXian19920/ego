package com.ego.dubbo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ego.dubbo.service.TbItemParamItemDubboService;
import com.ego.mapper.TbItemParamItemMapper;
import com.ego.pojo.TbItemParamItem;
import com.ego.pojo.TbItemParamItemExample;

/**
* @ClassName:TbItemParamItemDubboServiceImpl
* @Description:
* @Company: 北京尚学堂科技有限公司 www.bjsxt.com
* @author mengqx
* @date 2017年8月4日
*/

public class TbItemParamItemDubboServiceImpl implements TbItemParamItemDubboService{

	@Autowired
	private TbItemParamItemMapper tbItemParamItemMapper;
	@Override
	public int insParamItemSelective(TbItemParamItem item) {
		return tbItemParamItemMapper.insertSelective(item);
	}
	@Override
	public TbItemParamItem selByItemId(long id) {
		//	因为规格参数中数据类型是text 所以应该使用selectByExampleWithBLOBs。
		TbItemParamItemExample example = new TbItemParamItemExample();
		example.createCriteria().andItemIdEqualTo(id);
		List<TbItemParamItem> list = tbItemParamItemMapper.selectByExampleWithBLOBs(example);
		if (list.size()>0) {
			return list.get(0);
		}
		return null;
	}

}
