package com.ego.commons.pojo;
/**
* @ClassName:EasyUITree
* @Description:
* @Company: 北京尚学堂科技有限公司 www.bjsxt.com
* @author mengqx
* @date 2017年8月3日
*/

public class EasyUITree {
//	商品的id
	private long id;
//	赋予商品的name
	private String text;
//	赋予商品的
	private String state;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
}
