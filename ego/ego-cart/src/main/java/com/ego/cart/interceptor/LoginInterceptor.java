package com.ego.cart.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ego.cart.dao.JedisPoolDao;
import com.ego.commons.utils.CookieUtils;

/**
* @ClassName:LoginInterceptor
* @Description:
* @Company: 北京尚学堂科技有限公司 www.bjsxt.com
* @author mengqx
* @date 2017年8月15日
*/

public class LoginInterceptor implements HandlerInterceptor{

	@Resource
	private JedisPoolDao jedisPoolDao;
	
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2) throws Exception {
//		读取redis中的key user:uuid ,user:token
		String uuid = CookieUtils.getCookieValue(arg0, "TT_TOKEN");
//		判断redis中是否有数据
		if (jedisPoolDao.exists("user:"+uuid)) {
			String json = jedisPoolDao.get("user:"+uuid);
			if (json!=null && !"".equals(json)) {
				return true;
			}
		}
//		如果没有登录的话，则要用户进行登录在购物！
		arg1.sendRedirect("http://localhost:8084/user/showLogin");
		
		return false;
	}

}
