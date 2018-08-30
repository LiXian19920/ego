package com.ego.search.service;

import java.io.IOException;
import java.util.Map;

import org.apache.solr.client.solrj.SolrServerException;

/**
* @ClassName:TbItemService
* @Description:
* @Company: 北京尚学堂科技有限公司 www.bjsxt.com
* @author mengqx
* @date 2017年8月11日
*/

public interface TbItemService {
	/**
	 * 
	 * @Description: solr初始化方法
	 * @throws SolrServerException
	 * @throws IOException 
	 * @author mengqx
	 * @date   2017年8月11日
	 */
	void initItem() throws SolrServerException, IOException;
	/**
	 * 
	 * @Description:  该方法返回一个Map：map中存储了一个集合。同时要实现一个分页功能-totalPages
     * 				  map.put("query",list);map.put("totalPages",total);
	 * @param q   前台查询条件
	 * @param page 前台传过来的 默认值1.
	 * @return
	 * @throws SolrServerException 
	 * @author mengqx
	 * @date   2017年8月11日
	 */
	Map<String,Object> showItem(String q,int page) throws SolrServerException;

}
