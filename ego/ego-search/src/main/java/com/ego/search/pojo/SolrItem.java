package com.ego.search.pojo;

import java.util.Arrays;

/**
* @ClassName:SolrItem
* @Description:
* @Company: 北京尚学堂科技有限公司 www.bjsxt.com
* @author mengqx
* @date 2017年8月11日
*/

public class SolrItem {
	private long id;
	private String title;
	private String [] images;
	private long price;
	private String sellPoint;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String[] getImages() {
		return images;
	}
	public void setImages(String[] images) {
		this.images = images;
	}
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	public String getSellPoint() {
		return sellPoint;
	}
	public void setSellPoint(String sellPoint) {
		this.sellPoint = sellPoint;
	}
	@Override
	public String toString() {
		return "SolrItem [id=" + id + ", title=" + title + ", images=" + Arrays.toString(images) + ", price=" + price
				+ ", sellPoint=" + sellPoint + "]";
	}
	public SolrItem(long id, String title, String[] images, long price, String sellPoint) {
		super();
		this.id = id;
		this.title = title;
		this.images = images;
		this.price = price;
		this.sellPoint = sellPoint;
	}
	public SolrItem() {
		super();
	}

}
