package com.ego.passport.service.impl;

import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EgoResult;
import com.ego.commons.utils.CookieUtils;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbUserDubboService;
import com.ego.passport.dao.JedisPoolDao;
import com.ego.passport.service.TbUserService;
import com.ego.pojo.TbUser;

/**
 * @ClassName:TbUserServiceImpl
 * @Description:
 * @Company: 北京尚学堂科技有限公司 www.bjsxt.com
 * @author mengqx
 * @date 2017年8月15日
 */
@Service
public class TbUserServiceImpl implements TbUserService {

	@Reference
	private TbUserDubboService tbUserDubboService;

	// 先判断redis中是否有值，
	@Resource
	private JedisPoolDao jedisPoolDaoImpl;
	// 需要读取配置文件中的key
	@Value("${redis.user.key}")
	private String key;

	@Override
	public EgoResult login(TbUser user, HttpServletRequest request, HttpServletResponse response) {

		EgoResult er = new EgoResult();

		TbUser tbUser = tbUserDubboService.selByUser(user);
		if (tbUser != null) {

			// Cookie cookie = new Cookie("user","admin");
			// 取得到一个uuid的值，作为user：value
			String uuid = UUID.randomUUID().toString();
			// 设置cookie
			CookieUtils.setCookie(request, response, "TT_TOKEN", uuid);
			// user:uuid user:12323 将登录的用户存储到redis中！
			jedisPoolDaoImpl.set(key + uuid, JsonUtils.objectToJson(tbUser));
			er.setStatus(200);
			er.setMsg("OK");
			// er.setData(tbUser);
		} else {
			er.setStatus(400);
			er.setMsg("你长得太丑不允许登录！");
		}
		return er;
	}

	//当用户登录之后，将TT_TOKEN，存储了值。该方法用相应ego.js中
	//	http://localhost:8084/user/token/验证
	@Override
	public EgoResult getToken(String token) {
		EgoResult er = new EgoResult();
		//		判断登录的时候，是否将token存入到redis中 key + uuid
		if (jedisPoolDaoImpl.exists(key+token)) {
			//		取出redis中的值
			String json = jedisPoolDaoImpl.get(key+token);
			
			er.setStatus(200);
			er.setData(JsonUtils.jsonToPojo(json, TbUser.class));
			er.setMsg("OK");
		}else{
			er.setStatus(400);
			er.setMsg("人丑取得媳妇儿！");
		}
		
		return er;
	}

	@Override
	public EgoResult logout(String token, HttpServletRequest request, HttpServletResponse response) {
		EgoResult er = new EgoResult();
//		删除redis中的数据
		Long del = jedisPoolDaoImpl.del(key+token);
//		清空cookie中TT_TOKEN的数据！
		CookieUtils.deleteCookie(request, response, "TT_TOKEN");
		if (del>0) {
			er.setStatus(200);
			er.setMsg("OK");
			er.setData("");
		}else{
			er.setStatus(400);
		}
		return er;
	}

}
