package com.ego.cart.pojo;
/**
* @ClassName:Cart 构建购物车的类
* @Description:
* @Company: 北京尚学堂科技有限公司 www.bjsxt.com
* @author mengqx
* @date 2017年8月16日
*/

public class Cart {
	//商品id
	private long id;
	//商品单价
	private long price;
	//商品名称
	private String title;
	//数量
	private int num;
	//商品图片
	private String[] images;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String[] getImages() {
		return images;
	}
	public void setImages(String[] images) {
		this.images = images;
	}

}
