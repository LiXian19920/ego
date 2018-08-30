package com.ego.commons.pojo;

import java.io.Serializable;
import java.util.List;

/**
* @ClassName:EasyUIDatagrid
* @Description:
* @Company: 北京尚学堂科技有限公司 www.bjsxt.com
* @author mengqx
* @date 2017年8月2日
*/

public class EasyUIDatagrid implements Serializable{
	private long total;
	private List<?> rows;
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public List<?> getRows() {
		return rows;
	}
	public void setRows(List<?> rows) {
		this.rows = rows;
	}
}
