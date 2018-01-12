package com.pcaifu.news.dao.user.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.paicaifu.news.entity.UserInfo;
import com.pcaifu.news.criteria.user.UserManageCriteria;
import com.pcaifu.news.dao.user.UserManageDao;
import com.pcaifu.system.dao.impl.BaseDaoImpl;
import com.pcaifu.system.pageModel.DataGrid;
import com.pcaifu.system.pageModel.PageHelper;
import com.ppdai.riches.common.utils.StringUtils;

@Repository
public class UserManageDaoImpl extends BaseDaoImpl<UserInfo> implements UserManageDao {

	/**
	 * 获取用户管理列表
	 */
	@Override
	public DataGrid getList(UserManageCriteria criteria, PageHelper ph) throws Exception {
		DataGrid dg = new DataGrid();

		StringBuffer hql = new StringBuffer(" from UserInfo t where isactive=1");

		Map<String, Object> params = new HashMap<String, Object>();
		addCriteria(hql, params, criteria);

		List<UserInfo> ul = this.find(hql.toString(), params, ph.getPage(), ph.getRows());
		dg.setRows(ul);

		Long total = this.count(" select count(*) " + hql.toString(), params);
		Map<String, Object> summary = dg.getSummary();
		summary.put("totalCount", total);
		dg.setTotal(total);

		return dg;
	}

	private void addCriteria(StringBuffer hql, Map<String, Object> params, UserManageCriteria criteria) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		if (StringUtils.isNotBlank(criteria.getUserId())) {
			hql.append(" and t.uiUserId = :userId");
			params.put("userId", criteria.getUserId());
		}

		if (StringUtils.isNotBlank(criteria.getNickName())) {
			hql.append(" and t.uiNickname = :nickName");
			params.put("nickName", criteria.getNickName());
		}

		if (StringUtils.isNotBlank(criteria.getMobile())) {
			hql.append(" and t.uiRegMobile = :mobile");
			params.put("mobile", criteria.getMobile());
		}

		if (StringUtils.isNotBlank(criteria.getUserId())) {
			hql.append(" and t.uiUserId = :userId");
			params.put("userId", criteria.getUserId());
		}

		if (StringUtils.isNotBlank(criteria.getBeginDate())) {
			hql.append(" and t.inserttime >= :beginDate");
			params.put("beginDate", Timestamp.valueOf(criteria.getBeginDate() + " 00:00:00"));
		}

		if (StringUtils.isNotBlank(criteria.getEndDate())) {
			hql.append(" and t.inserttime <= :endDate");
			params.put("endDate", Timestamp.valueOf(criteria.getEndDate() + " 23:59:59"));
		}

		if (StringUtils.isNotBlank(criteria.getTerminalType())) {
			hql.append(" and t.uiTerminalType = :terminalType");
			params.put("terminalType", criteria.getTerminalType());
		}
	}

	/**
	 * 按条件查询用户信息
	 */
	@Override
	public List<UserInfo> query(UserManageCriteria criteria) {
		StringBuffer hql = new StringBuffer(" from UserInfo t where isactive=1");

		Map<String, Object> params = new HashMap<String, Object>();
		addCriteria(hql, params, criteria);

		return this.find(hql.toString(), params);
	}

}
