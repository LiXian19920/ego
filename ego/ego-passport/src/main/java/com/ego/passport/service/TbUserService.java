package com.ego.passport.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbUser;

/**
* @ClassName:TbUserService
* @Description:
* @Company: 北京尚学堂科技有限公司 www.bjsxt.com
* @author mengqx
* @date 2017年8月15日
*/

public interface TbUserService {
	/**
	 * 
	 * @Description:  登录方法
	 * @param user
	 * @return 
	 * @author mengqx
	 * @date   2017年8月15日
	 */
	
	EgoResult login(TbUser user,HttpServletRequest request,HttpServletResponse response);
	/**
	 * 
	 * @Description:  对应sso-接口文档中的返回值：如果不是jsonp。status=200.
	 * @param token
	 * @return 
	 * @author mengqx
	 * @date   2017年8月15日
	 */
	EgoResult getToken(String token);
	/**
	 * 
	 * @Description:  用户退出功能
	 * @param token
	 * @param request
	 * @param response
	 * @return 
	 * @author mengqx
	 * @date   2017年8月15日
	 */
	EgoResult logout(String token,HttpServletRequest request,HttpServletResponse response);
}
