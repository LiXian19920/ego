package com.ego.commons.pojo;

import com.ego.pojo.TbItem;

/**
* @ClassName:TbItemChild
* @Description:
* @Company: 北京尚学堂科技有限公司 www.bjsxt.com
* @author mengqx
* @date 2017年8月14日
*/

public class TbItemChild extends TbItem{
	//	迎合前台页面显示图片数组的属性
	private String images[];

	public String[] getImages() {
		return images;
	}

	public void setImages(String[] images) {
		this.images = images;
	}
}
