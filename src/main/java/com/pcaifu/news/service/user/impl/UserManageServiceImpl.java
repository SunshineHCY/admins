package com.pcaifu.news.service.user.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paicaifu.news.entity.UserInfo;
import com.pcaifu.news.criteria.user.UserManageCriteria;
import com.pcaifu.news.dao.user.UserManageDao;
import com.pcaifu.news.service.user.UserManageService;
import com.pcaifu.system.pageModel.DataGrid;
import com.pcaifu.system.pageModel.PageHelper;

@Service
public class UserManageServiceImpl implements UserManageService {
	@Autowired
	private UserManageDao userManageDao;

	/**
	 * 获取用户管理列表
	 */
	@Override
	public DataGrid getList(UserManageCriteria criteria, PageHelper ph) throws Exception {
		return userManageDao.getList(criteria, ph);
	}

	/**
	 * 按条件查询用户
	 */
	@Override
	public List<UserInfo> query(UserManageCriteria criteria) {
		return userManageDao.query(criteria);
	}

}
