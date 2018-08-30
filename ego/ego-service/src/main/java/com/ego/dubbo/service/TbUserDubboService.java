package com.ego.dubbo.service;

import com.ego.pojo.TbUser;

/**
* @ClassName:TbUserDubboService
* @Description:
* @Company: 北京尚学堂科技有限公司 www.bjsxt.com
* @author mengqx
* @date 2017年8月15日
*/

public interface TbUserDubboService {
	/**
	 * 
	 * @Description:  用户登录
	 * @param user
	 * @return 
	 * @author mengqx
	 * @date   2017年8月15日
	 */
	TbUser selByUser(TbUser user);
}
