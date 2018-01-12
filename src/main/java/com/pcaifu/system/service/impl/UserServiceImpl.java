package com.pcaifu.system.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pcaifu.system.dao.RoleDaoI;
import com.pcaifu.system.dao.UserDaoI;
import com.pcaifu.system.model.SysUser;
import com.pcaifu.system.model.Sysresource;
import com.pcaifu.system.model.Sysrole;
import com.pcaifu.system.pageModel.DataGrid;
import com.pcaifu.system.pageModel.PageHelper;
import com.pcaifu.system.pageModel.SessionInfo;
import com.pcaifu.system.pageModel.User;
import com.pcaifu.system.service.UserServiceI;
import com.pcaifu.system.util.MD5Util;

@Service
public class UserServiceImpl implements UserServiceI {

	@Autowired
	private UserDaoI userDao;

	@Autowired
	private RoleDaoI roleDao;

	@Override
	public User login(User user) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", user.getName());
		params.put("pwd", MD5Util.md5(user.getPwd()));
		SysUser t = userDao.get("from SysUser t where t.name = :name and t.pwd = :pwd", params);
		if (t != null) {
			BeanUtils.copyProperties(t, user);
			return user;
		}
		return null;
	}

	@Override
	synchronized public void reg(User user) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", user.getName());
		if (userDao.count("select count(*) from SysUser t where t.name = :name", params) > 0) {
			throw new Exception("登录名已存在！");
		} else {
			SysUser u = new SysUser();
			u.setId(UUID.randomUUID().toString());
			u.setName(user.getName());
			u.setPwd(MD5Util.md5(user.getPwd()));
			u.setCreatedatetime(new Date());
			userDao.save(u);
		}
	}

	@Override
	public DataGrid dataGrid(User user, PageHelper ph) {
		DataGrid dg = new DataGrid();
		List<User> ul = new ArrayList<User>();
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = " from SysUser t ";
		List<SysUser> l = userDao.find(hql + whereHql(user, params) + orderHql(ph), params, ph.getPage(), ph.getRows());
		if (l != null && l.size() > 0) {
			for (SysUser t : l) {
				User u = new User();
				BeanUtils.copyProperties(t, u);
				Set<Sysrole> roles = t.getSysroles();
				if (roles != null && !roles.isEmpty()) {
					String roleIds = "";
					String roleNames = "";
					boolean b = false;
					for (Sysrole tr : roles) {
						if (b) {
							roleIds += ",";
							roleNames += ",";
						} else {
							b = true;
						}
						roleIds += tr.getId();
						roleNames += tr.getName();
					}
					u.setRoleIds(roleIds);
					u.setRoleNames(roleNames);
				}
				ul.add(u);
			}
		}
		dg.setRows(ul);
		dg.setTotal(userDao.count("select count(*) " + hql + whereHql(user, params), params));
		return dg;
	}

	private String whereHql(User user, Map<String, Object> params) {
		String hql = "";
		if (user != null) {
			hql += " where 1=1 ";
			if (user.getName() != null) {
				hql += " and t.name like :name";
				params.put("name", "%%" + user.getName() + "%%");
			}
			if (user.getCreatedatetimeStart() != null) {
				hql += " and t.createdatetime >= :createdatetimeStart";
				params.put("createdatetimeStart", user.getCreatedatetimeStart());
			}
			if (user.getCreatedatetimeEnd() != null) {
				hql += " and t.createdatetime <= :createdatetimeEnd";
				params.put("createdatetimeEnd", user.getCreatedatetimeEnd());
			}
			if (user.getModifydatetimeStart() != null) {
				hql += " and t.modifydatetime >= :modifydatetimeStart";
				params.put("modifydatetimeStart", user.getModifydatetimeStart());
			}
			if (user.getModifydatetimeEnd() != null) {
				hql += " and t.modifydatetime <= :modifydatetimeEnd";
				params.put("modifydatetimeEnd", user.getModifydatetimeEnd());
			}
		}
		return hql;
	}

	private String orderHql(PageHelper ph) {
		String orderString = "";
		if (ph.getSort() != null && ph.getOrder() != null) {
			orderString = " order by t." + ph.getSort() + " " + ph.getOrder();
		}
		return orderString;
	}

	@Override
	synchronized public void add(User user) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", user.getName());
		if (userDao.count("select count(*) from SysUser t where t.name = :name", params) > 0) {
			throw new Exception("登录名已存在！");
		} else {
			SysUser u = new SysUser();
			BeanUtils.copyProperties(user, u);
			u.setCreatedatetime(new Date());
			u.setPwd(MD5Util.md5(user.getPwd()));
			userDao.save(u);
		}
	}

	@Override
	public User get(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		SysUser t = userDao.get("select distinct t from SysUser t left join fetch t.sysroles role where t.id = :id", params);
		User u = new User();
		BeanUtils.copyProperties(t, u);
		if (t.getSysroles() != null && !t.getSysroles().isEmpty()) {
			String roleIds = "";
			String roleNames = "";
			boolean b = false;
			for (Sysrole role : t.getSysroles()) {
				if (b) {
					roleIds += ",";
					roleNames += ",";
				} else {
					b = true;
				}
				roleIds += role.getId();
				roleNames += role.getName();
			}
			u.setRoleIds(roleIds);
			u.setRoleNames(roleNames);
		}
		return u;
	}

	@Override
	synchronized public void edit(User user) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", user.getId());
		params.put("name", user.getName());
		if (userDao.count("select count(*) from SysUser t where t.name = :name and t.id != :id", params) > 0) {
			throw new Exception("登录名已存在！");
		} else {
			SysUser u = userDao.get(SysUser.class, user.getId());
			BeanUtils.copyProperties(user, u, new String[] { "pwd", "createdatetime" });
			u.setModifydatetime(new Date());
		}
	}

	@Override
	public void delete(String id) {
		userDao.delete(userDao.get(SysUser.class, id));
	}

	@Override
	public void grant(String ids, User user) {
		if (ids != null && ids.length() > 0) {
			List<Sysrole> roles = new ArrayList<Sysrole>();
			if (user.getRoleIds() != null) {
				for (String roleId : user.getRoleIds().split(",")) {
					roles.add(roleDao.get(Sysrole.class, roleId));
				}
			}
			for (String id : ids.split(",")) {
				if (id != null && !id.equalsIgnoreCase("")) {
					SysUser t = userDao.get(SysUser.class, id);
					t.setSysroles(new HashSet<Sysrole>(roles));
				}
			}
		}
	}

	@Override
	public List<String> resourceList(String id) {
		List<String> resourceList = new ArrayList<String>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		SysUser t = userDao.get("from SysUser t join fetch t.sysroles role join fetch role.sysresources resource where t.id = :id", params);
		if (t != null) {
			Set<Sysrole> roles = t.getSysroles();
			if (roles != null && !roles.isEmpty()) {
				for (Sysrole role : roles) {
					Set<Sysresource> resources = role.getSysresources();
					if (resources != null && !resources.isEmpty()) {
						for (Sysresource resource : resources) {
							if (resource != null && resource.getUrl() != null) {
								resourceList.add(resource.getUrl());
							}
						}
					}
				}
			}
		}
		return resourceList;
	}

	@Override
	public void editPwd(User user) {
		if (user != null && user.getPwd() != null && !user.getPwd().trim().equalsIgnoreCase("")) {
			SysUser u = userDao.get(SysUser.class, user.getId());
			u.setPwd(MD5Util.md5(user.getPwd()));
			u.setModifydatetime(new Date());
		}
	}

	@Override
	public boolean editCurrentUserPwd(SessionInfo sessionInfo, String oldPwd, String pwd) {
		SysUser u = userDao.get(SysUser.class, sessionInfo.getId());
		if (u.getPwd().equalsIgnoreCase(MD5Util.md5(oldPwd))) {// 说明原密码输入正确
			u.setPwd(MD5Util.md5(pwd));
			u.setModifydatetime(new Date());
			return true;
		}
		return false;
	}
}
