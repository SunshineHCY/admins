package com.pcaifu.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pcaifu.system.dao.ResourceDaoI;
import com.pcaifu.system.dao.ResourceTypeDaoI;
import com.pcaifu.system.dao.UserDaoI;
import com.pcaifu.system.model.SysUser;
import com.pcaifu.system.model.Sysresource;
import com.pcaifu.system.model.Sysresourcetype;
import com.pcaifu.system.model.Sysrole;
import com.pcaifu.system.pageModel.Resource;
import com.pcaifu.system.pageModel.SessionInfo;
import com.pcaifu.system.pageModel.Tree;
import com.pcaifu.system.service.ResourceServiceI;

@Service
public class ResourceServiceImpl implements ResourceServiceI {

	@Autowired
	private ResourceDaoI resourceDao;

	@Autowired
	private ResourceTypeDaoI resourceTypeDao;

	@Autowired
	private UserDaoI userDao;

	@Override
	public List<Tree> tree(SessionInfo sessionInfo) {
		List<Sysresource> l = null;
		List<Tree> lt = new ArrayList<Tree>();

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("resourceTypeId", "0");// 菜单类型的资源

		if (sessionInfo != null) {
			params.put("userId", sessionInfo.getId());// 自查自己有权限的资源
			l = resourceDao.find("select distinct t from Sysresource t join fetch t.sysresourcetype type join fetch t.sysroles role join role.sysusers user where type.id = :resourceTypeId and user.id = :userId order by t.seq", params);
		} else {
			l = resourceDao.find("select distinct t from Sysresource t join fetch t.sysresourcetype type where type.id = :resourceTypeId order by t.seq", params);
		}

		if (l != null && l.size() > 0) {
			for (Sysresource r : l) {
				Tree tree = new Tree();
				BeanUtils.copyProperties(r, tree);
				if (r.getSysresource() != null) {
					tree.setPid(r.getSysresource().getId());
				}
				tree.setText(r.getName());
				tree.setIconCls(r.getIcon());
				Map<String, Object> attr = new HashMap<String, Object>();
				attr.put("url", r.getUrl());
				tree.setAttributes(attr);
				lt.add(tree);
			}
		}
		return lt;
	}

	@Override
	public List<Tree> allTree(SessionInfo sessionInfo) {
		List<Sysresource> l = null;
		List<Tree> lt = new ArrayList<Tree>();

		Map<String, Object> params = new HashMap<String, Object>();
		if (sessionInfo != null) {
			params.put("userId", sessionInfo.getId());// 自查自己有权限的资源
			l = resourceDao.find("select distinct t from Sysresource t join fetch t.sysresourcetype type join fetch t.sysroles role join role.sysusers user where user.id = :userId order by t.seq", params);
		} else {
			l = resourceDao.find("select distinct t from Sysresource t join fetch t.sysresourcetype type order by t.seq", params);
		}

		if (l != null && l.size() > 0) {
			for (Sysresource r : l) {
				Tree tree = new Tree();
				BeanUtils.copyProperties(r, tree);
				if (r.getSysresource() != null) {
					tree.setPid(r.getSysresource().getId());
				}
				tree.setText(r.getName());
				tree.setIconCls(r.getIcon());
				Map<String, Object> attr = new HashMap<String, Object>();
				attr.put("url", r.getUrl());
				tree.setAttributes(attr);
				lt.add(tree);
			}
		}
		return lt;
	}

	@Override
	public List<Resource> treeGrid(SessionInfo sessionInfo) {
		List<Sysresource> l = null;
		List<Resource> lr = new ArrayList<Resource>();

		Map<String, Object> params = new HashMap<String, Object>();
		if (sessionInfo != null) {
			params.put("userId", sessionInfo.getId());// 自查自己有权限的资源
			l = resourceDao.find("select distinct t from Sysresource t join fetch t.sysresourcetype type join fetch t.sysroles role join role.sysusers user where user.id = :userId order by t.seq", params);
		} else {
			l = resourceDao.find("select distinct t from Sysresource t join fetch t.sysresourcetype type order by t.seq", params);
		}

		if (l != null && l.size() > 0) {
			for (Sysresource t : l) {
				Resource r = new Resource();
				BeanUtils.copyProperties(t, r);
				if (t.getSysresource() != null) {
					r.setPid(t.getSysresource().getId());
					r.setPname(t.getSysresource().getName());
				}
				r.setTypeId(t.getSysresourcetype().getId());
				r.setTypeName(t.getSysresourcetype().getName());
				if (t.getIcon() != null && !t.getIcon().equalsIgnoreCase("")) {
					r.setIconCls(t.getIcon());
				}
				lr.add(r);
			}
		}

		return lr;
	}

	@Override
	public void add(Resource resource, SessionInfo sessionInfo) {
		Sysresource t = new Sysresource();
		BeanUtils.copyProperties(resource, t);
		if (resource.getPid() != null && !resource.getPid().equalsIgnoreCase("")) {
			t.setSysresource(resourceDao.get(Sysresource.class, resource.getPid()));
		}
		if (resource.getTypeId() != null && !resource.getTypeId().equalsIgnoreCase("")) {
			t.setSysresourcetype(resourceTypeDao.get(Sysresourcetype.class, resource.getTypeId()));
		}
		if (resource.getIconCls() != null && !resource.getIconCls().equalsIgnoreCase("")) {
			t.setIcon(resource.getIconCls());
		}
		resourceDao.save(t);

		// 由于当前用户所属的角色，没有访问新添加的资源权限，所以在新添加资源的时候，将当前资源授权给当前用户的所有角色，以便添加资源后在资源列表中能够找到
		SysUser user = userDao.get(SysUser.class, sessionInfo.getId());
		Set<Sysrole> roles = user.getSysroles();
		for (Sysrole r : roles) {
			r.getSysresources().add(t);
		}
	}

	@Override
	public void delete(String id) {
		Sysresource t = resourceDao.get(Sysresource.class, id);
		del(t);
	}

	private void del(Sysresource t) {
		if (t.getSysresources() != null && t.getSysresources().size() > 0) {
			for (Sysresource r : t.getSysresources()) {
				del(r);
			}
		}
		resourceDao.delete(t);
	}

	@Override
	public void edit(Resource resource) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", resource.getId());
		Sysresource t = resourceDao.get("select distinct t from Sysresource t where t.id = :id", params);
		if (t != null) {
			BeanUtils.copyProperties(resource, t);
			if (resource.getTypeId() != null && !resource.getTypeId().equalsIgnoreCase("")) {
				t.setSysresourcetype(resourceTypeDao.get(Sysresourcetype.class, resource.getTypeId()));// 赋值资源类型
			}
			if (resource.getIconCls() != null && !resource.getIconCls().equalsIgnoreCase("")) {
				t.setIcon(resource.getIconCls());
			}
			if (resource.getPid() != null && !resource.getPid().equalsIgnoreCase("")) {// 说明前台选中了上级资源
				Sysresource pt = resourceDao.get(Sysresource.class, resource.getPid());
				isChildren(t, pt);// 说明要将当前资源修改到当前资源的子/孙子资源下
				t.setSysresource(pt);
			} else {
				t.setSysresource(null);// 前台没有选中上级资源，所以就置空
			}
		}
	}

	/**
	 * 判断是否是将当前节点修改到当前节点的子节点
	 * 
	 * @param t
	 *            当前节点
	 * @param pt
	 *            要修改到的节点
	 * @return
	 */
	private boolean isChildren(Sysresource t, Sysresource pt) {
		if (pt != null && pt.getSysresource() != null) {
			if (pt.getSysresource().getId().equalsIgnoreCase(t.getId())) {
				pt.setSysresource(null);
				return true;
			} else {
				return isChildren(t, pt.getSysresource());
			}
		}
		return false;
	}

	@Override
	public Resource get(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		Sysresource t = resourceDao.get("from Sysresource t where t.id = :id", params);
		Resource r = new Resource();
		BeanUtils.copyProperties(t, r);
		if (t.getSysresource() != null) {
			r.setPid(t.getSysresource().getId());
			r.setPname(t.getSysresource().getName());
		}
		r.setTypeId(t.getSysresourcetype().getId());
		r.setTypeName(t.getSysresourcetype().getName());
		if (t.getIcon() != null && !t.getIcon().equalsIgnoreCase("")) {
			r.setIconCls(t.getIcon());
		}
		return r;
	}

}
