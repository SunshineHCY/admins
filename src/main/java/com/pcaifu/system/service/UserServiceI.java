package com.pcaifu.system.service;

import java.util.List;

import com.pcaifu.system.pageModel.DataGrid;
import com.pcaifu.system.pageModel.PageHelper;
import com.pcaifu.system.pageModel.SessionInfo;
import com.pcaifu.system.pageModel.User;

/**
 * 用户Service
 * 
 * @author 拍财富
 * 
 */
public interface UserServiceI {

	/**
	 * 用户登录
	 * 
	 * @param user
	 *            里面包含登录名和密码
	 * @return 用户对象
	 */
	public User login(User user);

	/**
	 * 用户注册
	 * 
	 * @param user
	 *            里面包含登录名和密码
	 * @throws Exception
	 */
	public void reg(User user) throws Exception;

	/**
	 * 获取用户数据表格
	 * 
	 * @param user
	 * @return
	 */
	public DataGrid dataGrid(User user, PageHelper ph);

	/**
	 * 添加用户
	 * 
	 * @param user
	 */
	public void add(User user) throws Exception;

	/**
	 * 获得用户对象
	 * 
	 * @param id
	 * @return
	 */
	public User get(String id);

	/**
	 * 编辑用户
	 * 
	 * @param user
	 */
	public void edit(User user) throws Exception;

	/**
	 * 删除用户
	 * 
	 * @param id
	 */
	public void delete(String id);

	/**
	 * 用户授权
	 * 
	 * @param ids
	 * @param user
	 *            需要user.roleIds的属性值
	 */
	public void grant(String ids, User user);

	/**
	 * 获得用户能访问的资源地址
	 * 
	 * @param id
	 *            用户ID
	 * @return
	 */
	public List<String> resourceList(String id);

	/**
	 * 编辑用户密码
	 * 
	 * @param user
	 */
	public void editPwd(User user);

	/**
	 * 修改用户自己的密码
	 * 
	 * @param sessionInfo
	 * @param oldPwd
	 * @param pwd
	 * @return
	 */
	public boolean editCurrentUserPwd(SessionInfo sessionInfo, String oldPwd, String pwd);

}
