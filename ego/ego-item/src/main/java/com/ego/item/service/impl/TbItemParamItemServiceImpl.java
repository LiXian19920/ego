package com.ego.item.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbItemParamItemDubboService;
import com.ego.item.dao.JedisPoolDao;
import com.ego.item.pojo.MyKeyValue;
import com.ego.item.pojo.ParamItem;
import com.ego.item.service.TbItemParamItemService;
import com.ego.pojo.TbItemParamItem;

/**
 * @ClassName:TbItemParamItemServiceImpl
 * @Description:
 * @Company: 北京尚学堂科技有限公司 www.bjsxt.com
 * @author mengqx
 * @date 2017年8月14日
 */

@Service
public class TbItemParamItemServiceImpl implements TbItemParamItemService {

	@Reference
	private TbItemParamItemDubboService tbItemParamItemDubboService;

	// redis
	@Resource
	private JedisPoolDao jedisPoolDaoImpl;

	// key
	@Value("${redis.param.key}")
	private String key;

	@Override
	public String showParam(long itemId) {
		String mykey = key + itemId;

		// 看缓冲中是否有数据
		if (jedisPoolDaoImpl.exists(mykey)) {
			String json = jedisPoolDaoImpl.get(mykey);
			if (json != null && !"".equals(json)) {
				return json;
			}
		}
		// 如果没有则从数据库中取值。
		TbItemParamItem itemParamItem = tbItemParamItemDubboService.selByItemId(itemId);
		// 将TbItemParamItem类中的规格参数取出来，编程我们封装好的实体类，类型
		List<ParamItem> list = JsonUtils.jsonToList(itemParamItem.getParamData(), ParamItem.class);
		// 创建一个StringBuffer 对象，将页面显示的内容平成一个字符串。将字符串返回去。
		StringBuffer sf = new StringBuffer();
		sf.append("<table width='100%' style='color:#999999;'>");
		// 表格中的一行数据包含了group ，还包含了params ： k,v.
		for (ParamItem paramItem : list) {
			// 获取一组数据中的k，v。
			List<MyKeyValue> params = paramItem.getParams();
			for (int i = 0; i < params.size(); i++) {
				// 取得第一行数据。将group 添加到页面
				if (i == 0) {
					sf.append("<tr>");
					sf.append("<td width='10%'>");
					sf.append(paramItem.getGroup());
					sf.append("</td>");
					sf.append("<td width='15%' align='right'>" + params.get(i).getK() + "</td>");
					sf.append("<td width='15%' align='right'>" + params.get(i).getV() + "</td>");
					sf.append("</tr>");
				} else {
					// 不是第一行，则不添加group 组。将group 这个单元格设置为空！
					sf.append("<tr>");
					sf.append("<td width='10%'>");
					sf.append("</td>");
					sf.append("<td width='15%' align='right'>" + params.get(i).getK() + "</td>");
					sf.append("<td style='padding-left:30px;'>" + params.get(i).getV() + "</td>");
					sf.append("</tr>");
				}
			}
			sf.append("<tr><td colspan='3'><hr style='color:#999999;'/></td></tr>");
		}
		sf.append("</table>");
		
		// 将StringBuffer转换成String对象
		String json = sf.toString();
		//将从数据库拼接好的字符串保存到redis缓存中！
		jedisPoolDaoImpl.set(mykey, json);
		return json;
	}

}
