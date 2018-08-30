package com.ego.search.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.dubbo.service.TbItemCatDubboService;
import com.ego.dubbo.service.TbItemDescDubboService;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.pojo.TbItem;
import com.ego.search.pojo.SolrItem;
import com.ego.search.service.TbItemService;

/**
 * @ClassName:TbItemServiceImpl
 * @Description:
 * @Company: 北京尚学堂科技有限公司 www.bjsxt.com
 * @author mengqx
 * @date 2017年8月11日
 */

@Service
public class TbItemServiceImpl implements TbItemService {

	// 需要用到dubbo提供的服务
	@Reference
	private TbItemDubboService tbItemDubboService;
	@Reference
	private TbItemCatDubboService tbItemCatDubboService;
	@Reference
	private TbItemDescDubboService tbItemDescDubboService;
	// 主方法就是来完成向solr初始化的！ @Resource 默认是按照name注入
	@Resource
	private CloudSolrServer solrServer;

	// 获取每页显示的条数！
	@Value("${solr.item.rows}")
	private int rows;

	@Override
	public void initItem() throws SolrServerException, IOException {
		// 向solrCloud添加数据
		List<TbItem> list = tbItemDubboService.selAll();
		for (TbItem tbItem : list) {
			SolrInputDocument doc = new SolrInputDocument();
			doc.addField("id", tbItem.getId());
			doc.addField("item_title", tbItem.getTitle());
			doc.addField("item_sell_point", tbItem.getSellPoint());
			doc.addField("item_price", tbItem.getPrice());

			// 以下设计的内容则要向页面返回多条数据
			doc.addField("item_image", tbItem.getImage());
			// 通过观察表与表的关系 商品描述表的id为商品表的cid。
			doc.addField("item_category_name", tbItemCatDubboService.selById(tbItem.getCid()).getName());
			doc.addField("item_desc", tbItemDescDubboService.selByItemid(tbItem.getId()).getItemDesc());

			// 将doc对象添加到SolrCloud
			solrServer.add(doc);
		}
		// 必须对SolrInputDocument提交生效
		solrServer.commit();
	}
	
	@Override
	public Map<String, Object> showItem(String q, int page) throws SolrServerException {
		// 从数据库，还是从solr？答案是：从solr中查询数据！查询出来的集合是新建的实体类。
		List<SolrItem> list = new ArrayList<>();
		// 创建查询对象。
		SolrQuery query = new SolrQuery();
		// 设置查询条件 相当于可视化管理界面中的q：item_title:手机
		query.setQuery("item_keywords:" + q);
		// 分页 开始条数是 (当前页-1)*每页显示的条数
		query.setStart(rows * (page - 1));
		query.setRows(rows);
		// 设置高亮
		query.setHighlight(true);
		// 设置高亮要显示的字段，标题，买点。
		query.addHighlightField("item_title item_sell_point");
		// 设置高亮的前置
		query.setHighlightSimplePre("<span style='font-weight:bold;color:red;'>");
		// 设置高亮的后置
		query.setHighlightSimplePost("</span>");
		// 相当于可视化管理界面中的查询按钮
		QueryResponse response = solrServer.query(query);
		// 返回的数据结果集 --- response
		SolrDocumentList solrDocumentList = response.getResults();
		// 迭代器:
		Iterator<SolrDocument> iter = solrDocumentList.iterator();
		// 获取查询结果集中设置的高亮
		Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
		// 循环迭代器
		while (iter.hasNext()) {
			// 将迭代器中的每个值变成SolrDocument
			SolrDocument doc = iter.next();
			// 页面的实体类：将查询出来的结果付给实体类显示到页面上！
			SolrItem item = new SolrItem();
			// 设置页面返回的Id值
			item.setId(Long.parseLong(doc.getFieldValue("id").toString()));
			// 通过id取得到高亮的map集合
			Map<String, List<String>> map = highlighting.get(doc.getFieldValue("id").toString());
			// 在获取高亮的时候，必须判断是否为空！
			if (map != null) {
				// 取得到数据--取得单独item_title
				List<String> listTitle = map.get("item_title");
				if (listTitle != null && listTitle.size() > 0) {
					// 设置数据
					item.setTitle(listTitle.get(0));
				} else {
					// 直接输出
					item.setTitle(doc.getFieldValue("item_title").toString());
				}
				List<String> listSellPoint = map.get("item_sell_point");
				if (listSellPoint != null && listSellPoint.size() > 0) {
					item.setSellPoint(listSellPoint.get(0));
				} else {
					item.setSellPoint(doc.getFieldValue("item_sell_point").toString());
				}
			} else {
				// 如果集合中没有高亮则显示原始数据。
				item.setTitle(doc.getFieldValue("item_title").toString());
				item.setSellPoint(doc.getFieldValue("item_sell_point").toString());
			}
			// 设置价钱！
			item.setPrice(Long.parseLong(doc.getFieldValue("item_price").toString()));
			// 因为图片在前台实现的是一个数组。
			if (doc.getFieldValue("item_image") == null || doc.getFieldValue("item_image").toString().equals("")) {
				// 前台页面中"${item.images[0]}"，所以，我必须设置一个数组，并且还要给一个初始化长度。
				String[] images = new String[1];
				item.setImages(images);
			} else {
				// String 类中split(",")表示以，分割返回一个数组。跟前台一样
				item.setImages(doc.getFieldValue("item_image").toString().split(","));
			}
			// 将从solr中取得到的数据添加到前台页面集合类List<SolrItem>
			list.add(item);
		}

		Map<String, Object> mapResult = new HashMap<>();
		// 将前台封装的数据存在map中
		mapResult.put("list", list);
		// 查询solr、中总共数据条数
		long count = solrDocumentList.getNumFound();
		// 求出总页数！
		mapResult.put("totalPages", (count % rows == 0 ? count / rows : count / rows + 1));
		// 将map返回！
		return mapResult;

	}

}
