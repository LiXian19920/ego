package com.ego.item.pojo;

import java.util.List;

/**
* @ClassName:ParamItem
* @Description:
* @Company: 北京尚学堂科技有限公司 www.bjsxt.com
* @author mengqx
* @date 2017年8月14日
*/

public class ParamItem {
//	表中对应的group
	private String group;
//	params中 k，v的集合。[{"k":"品牌","v":"苹果（Apple）"},{"k":"型号","v":"iPhone 6 A1586"}]
	private List<MyKeyValue> params;
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public List<MyKeyValue> getParams() {
		return params;
	}
	public void setParams(List<MyKeyValue> params) {
		this.params = params;
	}

}
