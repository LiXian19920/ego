package com.ego.manage.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EasyUIDatagrid;
import com.ego.commons.utils.FtpUtil;
import com.ego.commons.utils.IDUtils;
import com.ego.dubbo.service.TbItemDescDubboService;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.dubbo.service.TbItemParamItemDubboService;
import com.ego.manage.service.TbItemService;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemDesc;
import com.ego.pojo.TbItemParamItem;

/**
 * @ClassName:TbItemServiceImpl
 * @Description:
 * @Company: 北京尚学堂科技有限公司 www.bjsxt.com
 * @author mengqx
 * @date 2017年8月2日
 */

@Service
public class TbItemServiceImpl implements TbItemService {
	// 通过Reference注解来找到dubbo暴露的服务接口
	@Reference
	private TbItemDubboService tbItemDubboService;

	@Reference
	private TbItemDescDubboService tbItemDescDubboService;

	@Reference
	private TbItemParamItemDubboService tbItemParamItemDubboService;
	
	// 从配置文件中读取图片服务器的信息。
	@Value("${vsftpd.host}")
	private String host;
	@Value("${vsftpd.port}")
	private int port;
	@Value("${vsftpd.username}")
	private String username;
	@Value("${vsftpd.password}")
	private String password;
	@Value("${vsftpd.basePath}")
	private String basePath;
	@Value("${vsftpd.filePath}")
	private String filePath;
	@Value("${nginx.url}")
	private String nginxUrl;

	@Override
	public EasyUIDatagrid show(int page, int rows) {
		return tbItemDubboService.showPage(page, rows);
	}

	@Override
	public int updStatusById(String ids, byte status) {
		String[] str = ids.split(",");
		for (String string : str) {
			tbItemDubboService.updStatusById(Long.parseLong(string), status);
		}
		return 1;
	}

	/**
	 * 图片上传流程：应该是上传到图片服务器。vsftpd,..... 在使用java代码连接充当fileZila。来完成图片上传！
	 * 在真实的项目中，用户名密码等信息都是不确定，所以要把所有的配置参数放在配置文件中！
	 */
	@Override
	public Map<String, Object> upload(MultipartFile file) {
		Map<String, Object> map = new HashMap<>();

		// 定义一个filename。g.jpg
		String fineName = IDUtils.genItemId()
				+ file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		// 定义一个InputStream
		InputStream input = null;
		try {
			input = file.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}

		boolean flag = FtpUtil.uploadFile(host, port, username, password, basePath, filePath, fineName, input);
		if (flag) {
			map.put("error", 0);
			map.put("url", nginxUrl + fineName);
		} else {
			map.put("error", 1);
			map.put("message", "上传图片失败");
		}
		return map;
	}

	@Override
	public int insItem(TbItem item, String desc,String itemParams) {
		// 设置商品id
		long id = IDUtils.genItemId();
		// 创建一个date对象
		Date date = new Date();
		item.setId(id);
		item.setCreated(date);
		item.setUpdated(date);
		int index = tbItemDubboService.insItem(item);

		TbItemDesc tbItemDesc = new TbItemDesc();
		tbItemDesc.setItemId(id);
		tbItemDesc.setItemDesc(desc);
		tbItemDesc.setCreated(date);
		tbItemDesc.setUpdated(date);
		index += tbItemDescDubboService.insDesc(tbItemDesc);
		
		TbItemParamItem paramItem = new TbItemParamItem();
		//	就是从前台传过来的json串。存储的内容就是商品的规格
		paramItem.setParamData(itemParams);
		paramItem.setItemId(id);
		paramItem.setCreated(date);
		paramItem.setUpdated(date);
		index += tbItemParamItemDubboService.insParamItemSelective(paramItem);
		if (index == 3) {
			return 1;
		} else {
			return 0;
		}
	}
}
