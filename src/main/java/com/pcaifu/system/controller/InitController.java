package com.pcaifu.system.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pcaifu.system.pageModel.SessionInfo;
import com.pcaifu.system.util.ConfigUtil;

/**
 * 初始化数据库控制器/跳往登录界面
 * 
 * @author 拍财富
 * 
 */
@Controller
@RequestMapping("/manage")
public class InitController {

	/**
	 * 初始化数据库后转向到首页
	 * 
	 * @return
	 */
	@RequestMapping("/init")
	public String init(HttpSession session) {
		if (session != null) {
			session.invalidate();
		}
		// initService.init();
		return "redirect:/";
	}

	@RequestMapping("/login")
	public String initLogin(HttpServletRequest request) {
		return "user/login";
	}

	@RequestMapping("/Application.do")
	public String index(HttpServletRequest request) {
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(ConfigUtil.getSessionInfoName());
		if ((sessionInfo != null) && (sessionInfo.getId() != null)) {
			return "/index";
		}
		return initLogin(request);
	}

}
