package com.pcaifu.news.controller.user;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.paicaifu.news.entity.UserInfo;
import com.pcaifu.news.criteria.user.UserManageCriteria;
import com.pcaifu.news.service.user.UserManageService;
import com.pcaifu.system.controller.BaseController;
import com.pcaifu.system.pageModel.DataGrid;
import com.pcaifu.system.pageModel.PageHelper;
import com.pcaifu.system.util.ExcelUtil;

@Controller
@RequestMapping("/userMgr")
public class UserManagerController extends BaseController {
	private static final Logger logger = Logger.getLogger(UserManagerController.class);

	@Autowired
	UserManageService userManageService;

	/**
	 * 跳转到界面
	 * 
	 * @return
	 */
	@RequestMapping("/main")
	private String main() {
		return "/manage/user/UserManage";
	}

	/**
	 * 获取列表数据
	 * 
	 * @param criteria
	 * @param ph
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	private DataGrid dataGrid(UserManageCriteria criteria, PageHelper ph) {
		if (null == criteria) {
			criteria = new UserManageCriteria();
		}
		DataGrid data = new DataGrid();
		try {
			data = userManageService.getList(criteria, ph);
		} catch (Exception e) {
			logger.error("用户管理列表查询失败：" + e.getMessage(), e);
		}
		return data;
	}

	/**
	 * 用户信息下载
	 * 
	 * @param criteria
	 * @return
	 */
	@RequestMapping("/export")
	@ResponseBody
	public String export(UserManageCriteria criteria) {
		if (null == criteria) {
			criteria = new UserManageCriteria();
		}

		LinkedHashMap<String, String> title = new LinkedHashMap<String, String>();
		title.put("uiUserId", "用户ID");
		title.put("uiNickname", "用户昵称");
		title.put("uiRegMobile", "注册手机号");
		title.put("inserttime", "注册时间");
		title.put("uiTerminalType", "终端类型");
		title.put("uiGoldcoinNum", "持有金币数");

		String fileName = "userReport.xls";

		try {
			List<UserInfo> result = userManageService.query(criteria);
			List<Object> listContent = new ArrayList<Object>();
			listContent.addAll(result);
			ExcelUtil.exportExcel(fileName, title, listContent);
		} catch (Exception e) {
			logger.error("用户信息下载失败" + e.getMessage(), e);
		}

		return "none";
	}

}
