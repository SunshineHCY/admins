package com.pcaifu.news.service.user;

import java.util.List;

import com.paicaifu.news.entity.UserInfo;
import com.pcaifu.news.criteria.user.UserManageCriteria;
import com.pcaifu.system.pageModel.DataGrid;
import com.pcaifu.system.pageModel.PageHelper;

public interface UserManageService {

	/**
	 * 获取用户管理列表
	 * 
	 * @param criteria
	 * @param ph
	 * @return
	 */
	DataGrid getList(UserManageCriteria criteria, PageHelper ph) throws Exception;

	/**
	 * 按条件查询用户
	 * 
	 * @param criteria
	 * @return
	 */
	List<UserInfo> query(UserManageCriteria criteria);

}
