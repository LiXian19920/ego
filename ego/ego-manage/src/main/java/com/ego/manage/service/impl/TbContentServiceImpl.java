package com.ego.manage.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EasyUIDatagrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.commons.utils.IDUtils;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbContentDubboService;
import com.ego.manage.dao.JedisPoolDao;
import com.ego.manage.service.TbContentService;
import com.ego.pojo.TbContent;

/**
 * @ClassName:TbContentServiceImpl
 * @Description:
 * @Company: 北京尚学堂科技有限公司 www.bjsxt.com
 * @author mengqx
 * @date 2017年8月8日
 */
@Service
public class TbContentServiceImpl implements TbContentService {

	@Reference
	private TbContentDubboService tbContentDubboService;

	// 读取my.properties配置文件中的redis可以。
	@Value("${redis.bigpic.key}")
	private String key;

	// 要想对缓存数据进行修改必须有JedisPoolDaoImpl 该类中才会有操作redis 的方法。

	@Resource
	private JedisPoolDao jedisPoolDaoImpl;

	@Override
	public EasyUIDatagrid showContent(long categoryId, int page, int rows) {
		EasyUIDatagrid datagrid = new EasyUIDatagrid();
		// 查询所有数据
		List<TbContent> list = tbContentDubboService.selByCidPage(categoryId, page, rows);
		// 查找该节点下的总条数
		long total = tbContentDubboService.selCountByCid(categoryId);
		datagrid.setRows(list);
		datagrid.setTotal(total);

		return datagrid;
	}

	@Override
	public EgoResult updContent(TbContent content) {
		EgoResult er = new EgoResult();
		// 设置修改的时间
		// 赋予了更新时间
		content.setUpdated(new Date());
		// 去数据库中查询一下
		List<TbContent> listone = tbContentDubboService.selById(content);
		content.setCreated(listone.get(0).getCreated());
		// 直接更新
		int index = tbContentDubboService.updContent(content);
		if (index > 0) {
			// 看缓存中是否有数据
			if (jedisPoolDaoImpl.exists(key)) {
				// 取得json中的值
				String json = jedisPoolDaoImpl.get(key);
				if (json != null && !("".equals(json))) {
					// 声明一个变量来记录循环中i的值
					int listIndex = -1;
					// 将json字符串转换成java实体类对象
					List<TbContent> list = JsonUtils.jsonToList(json, TbContent.class);
				System.out.println(list+"缓存数据！");
					// 找出更新对象，在换成中存在的位置！
					for (int i = 0; i < list.size(); i++) {
//						报错！
						
						if ((long) list.get(i).getId() == (long) content.getId()) {
							listIndex = i;
						}
					}
					if (listIndex == -1) {
						// 此时数据库中的数据跟缓存中的数据不一致了！所以要删除缓存中的数据！ 缓存中的数据，必须跟数据库同步！
						jedisPoolDaoImpl.del(key);
					} else {
						list.remove(listIndex);
						// list.add("","") //直接将原来的替换成修改的内容。list.set("","")
						// 修改原下标的值。
						list.add(listIndex, content);
						// 将修改的对象，从新赋值给redis。
						jedisPoolDaoImpl.set(key, JsonUtils.objectToJson(list));
					}
				}
			}
			er.setStatus(200);
		}
		return er;
	}

	@Override
	public EgoResult save(TbContent content) {
		EgoResult er = new EgoResult();
		Date date = new Date();
		content.setCreated(date);
		content.setUpdated(date);
		//	少个ID
		content.setId(IDUtils.genItemId());
		
		int index = tbContentDubboService.insContent(content);
		String json = jedisPoolDaoImpl.get(key);

		if (index > 0) {
			if (jedisPoolDaoImpl.exists(key)) {
				if (json != null && !"".equals(json)) {
					// 缓存中的json 转换成java对象集合
					List<TbContent> list = JsonUtils.jsonToList(json, TbContent.class);
					// 将新插入的数据存储到list中
					list.add(content);
					// 再将新增后的结果放到缓存中去
					String result = jedisPoolDaoImpl.set(key, JsonUtils.objectToJson(list));
					if (result.equalsIgnoreCase("OK")) {
						er.setStatus(200);
					} else {
						er.setStatus(400);
						// 所有的缓存中的数据必须要跟我们的数据库要保持高度一致！
						jedisPoolDaoImpl.del(key);
					}

				} else {
					er.setStatus(200);
				}
			} else {
				er.setStatus(200);
			}
		} else {
			er.setStatus(400);
		}
		return er;
	}

	@Override
	public EgoResult delByIds(String ids) {
		EgoResult er = new EgoResult();
		// 直接进行物理删除
		String str[] = ids.split(",");
		// 记录删除的标记
		int index = 0;
		for (String string : str) {
			index += tbContentDubboService.delById(Long.parseLong(string));
		}
		if (index == str.length) {
			// 去到缓存中的数据。
			String json = jedisPoolDaoImpl.get(key);
			// 判断缓存中有json
			if (jedisPoolDaoImpl.exists(key)) {
				// 如果key中的数据不为空
				if (json != null && !"".equals(json)) {
					// 因为我们进行的是批量删除所以对象可能为多个。
					List<TbContent> listContent = new ArrayList<>();
					// 将缓存中的数据进行删除,将json中的数据转换成java集合对象
					List<TbContent> list = JsonUtils.jsonToList(json, TbContent.class);
					// 根据id找到对应TbContent对象
					for (String s : str) {//2,5
						for (int i = 0; i < list.size(); i++) {
							if (s.equals(list.get(i).getId() + "")) {
								//  记录下。。要删除的对象
								listContent.add(list.get(i));
								//	当找到之后，必须要跳出循环体！
								break;
							}
						}
					}
					for (TbContent tbContent : listContent) {
						System.out.println(tbContent.getId()+"\t");
					}
					// 该循环用来删除以及被记录下的对象
					for (TbContent tbContent : listContent) {
						// 从缓存key中的集合对象中 list 删除
						list.remove(tbContent);
					}
					// 将删除后的集合对象转换为json数据存储到缓存中！
					jedisPoolDaoImpl.set(key, JsonUtils.objectToJson(list));
					er.setStatus(200);
				}else{
					er.setStatus(200);
				}
			}else{
				er.setStatus(200);
			}
		}else{
			er.setStatus(400);
		}
		return er;
	}

}
