package com.pcaifu.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pcaifu.system.dao.ResourceDaoI;
import com.pcaifu.system.dao.RoleDaoI;
import com.pcaifu.system.model.Sysresource;
import com.pcaifu.system.model.Sysrole;
import com.pcaifu.system.pageModel.Role;
import com.pcaifu.system.pageModel.Tree;
import com.pcaifu.system.service.RoleServiceI;

@Service
public class RoleServiceImpl implements RoleServiceI {

	@Autowired
	private RoleDaoI roleDao;

	@Autowired
	private ResourceDaoI resourceDao;

	@Override
	public void add(Role role) {
		Sysrole t = new Sysrole();
		BeanUtils.copyProperties(role, t);
		if (role.getPid() != null && !role.getPid().equalsIgnoreCase("")) {
			t.setSysrole(roleDao.get(Sysrole.class, role.getPid()));
		}
		roleDao.save(t);
	}

	@Override
	public Role get(String id) {
		Role r = new Role();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		Sysrole t = roleDao.get("select distinct t from Sysrole t left join fetch t.sysresources resource where t.id = :id", params);
		if (t != null) {
			BeanUtils.copyProperties(t, r);
			if (t.getSysrole() != null) {
				r.setPid(t.getSysrole().getId());
				r.setPname(t.getSysrole().getName());
			}
			Set<Sysresource> s = t.getSysresources();
			if (s != null && !s.isEmpty()) {
				boolean b = false;
				String ids = "";
				String names = "";
				for (Sysresource tr : s) {
					if (b) {
						ids += ",";
						names += ",";
					} else {
						b = true;
					}
					ids += tr.getId();
					names += tr.getName();
				}
				r.setResourceIds(ids);
				r.setResourceNames(names);
			}
		}
		return r;
	}

	@Override
	public void edit(Role role) {
		Sysrole t = roleDao.get(Sysrole.class, role.getId());
		if (t != null) {
			BeanUtils.copyProperties(role, t);
			if (role.getPid() != null && !role.getPid().equalsIgnoreCase("")) {
				t.setSysrole(roleDao.get(Sysrole.class, role.getPid()));
			}
			if (role.getPid() != null && !role.getPid().equalsIgnoreCase("")) {// 说明前台选中了上级资源
				Sysrole pt = roleDao.get(Sysrole.class, role.getPid());
				isChildren(t, pt);// 说明要将当前资源修改到当前资源的子/孙子资源下
				t.setSysrole(pt);
			} else {
				t.setSysrole(null);// 前台没有选中上级资源，所以就置空
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
	private boolean isChildren(Sysrole t, Sysrole pt) {
		if (pt != null && pt.getSysrole() != null) {
			if (pt.getSysrole().getId().equalsIgnoreCase(t.getId())) {
				pt.setSysrole(null);
				return true;
			} else {
				return isChildren(t, pt.getSysrole());
			}
		}
		return false;
	}

	@Override
	public List<Role> treeGrid() {
		List<Role> rl = new ArrayList<Role>();
		List<Sysrole> tl = roleDao.find("select distinct t from Sysrole t left join fetch t.sysresources resource order by t.seq");
		if (tl != null && tl.size() > 0) {
			for (Sysrole t : tl) {
				Role r = new Role();
				BeanUtils.copyProperties(t, r);
				r.setIconCls("status_online");
				if (t.getSysrole() != null) {
					r.setPid(t.getSysrole().getId());
					r.setPname(t.getSysrole().getName());
				}
				Set<Sysresource> s = t.getSysresources();
				if (s != null && !s.isEmpty()) {
					boolean b = false;
					String ids = "";
					String names = "";
					for (Sysresource tr : s) {
						if (b) {
							ids += ",";
							names += ",";
						} else {
							b = true;
						}
						ids += tr.getId();
						names += tr.getName();
					}
					r.setResourceIds(ids);
					r.setResourceNames(names);
				}
				rl.add(r);
			}
		}
		return rl;
	}

	@Override
	public void delete(String id) {
		Sysrole t = roleDao.get(Sysrole.class, id);
		del(t);
	}

	private void del(Sysrole t) {
		if (t.getSysroles() != null && t.getSysroles().size() > 0) {
			for (Sysrole r : t.getSysroles()) {
				del(r);
			}
		}
		roleDao.delete(t);
	}

	@Override
	public List<Tree> tree() {
		List<Sysrole> l = roleDao.find("from Sysrole t order by t.seq");
		List<Tree> lt = new ArrayList<Tree>();
		if (l != null && l.size() > 0) {
			for (Sysrole t : l) {
				Tree tree = new Tree();
				BeanUtils.copyProperties(t, tree);
				tree.setText(t.getName());
				tree.setIconCls("status_online");
				if (t.getSysrole() != null) {
					tree.setPid(t.getSysrole().getId());
				}
				lt.add(tree);
			}
		}
		return lt;
	}

	@Override
	public void grant(Role role) {
		Sysrole t = roleDao.get(Sysrole.class, role.getId());
		if (role.getResourceIds() != null && !role.getResourceIds().equalsIgnoreCase("")) {
			String ids = "";
			boolean b = false;
			for (String id : role.getResourceIds().split(",")) {
				if (b) {
					ids += ",";
				} else {
					b = true;
				}
				ids += "'" + id + "'";
			}
			t.setSysresources(new HashSet<Sysresource>(resourceDao.find("select distinct t from Sysresource t where t.id in (" + ids + ")")));
		} else {
			t.setSysresources(null);
		}
	}

}
