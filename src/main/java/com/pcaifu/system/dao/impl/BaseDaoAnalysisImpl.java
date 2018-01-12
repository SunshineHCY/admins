package com.pcaifu.system.dao.impl;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.pcaifu.system.dao.BaseDaoAnalysisI;
import com.ppdai.riches.common.utils.GsonUtils;

@Repository
public class BaseDaoAnalysisImpl<T> implements BaseDaoAnalysisI<T> {

	@Autowired
	private SessionFactory sessionFactoryAnalysis;

	@Autowired
	private JdbcTemplate jdbcTemplateAnalysis;

	/**
	 * 获得当前事物的session
	 * 
	 * @return org.hibernate.Session
	 */
	public Session getCurrentSession() {
		return this.sessionFactoryAnalysis.getCurrentSession();
	}

	@Override
	public Serializable save(T o) {
		if (o != null) {
			return this.getCurrentSession().save(o);
		}
		return null;
	}

	@Override
	public T get(Class<T> c, Serializable id) {
		return (T) this.getCurrentSession().get(c, id);
	}

	@Override
	public T get(String hql) {
		Query q = this.getCurrentSession().createQuery(hql);
		List<T> l = q.list();
		if (l != null && l.size() > 0) {
			return l.get(0);
		}
		return null;
	}

	@Override
	public T get(String hql, Map<String, Object> params) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		List<T> l = q.list();
		if (l != null && l.size() > 0) {
			return l.get(0);
		}
		return null;
	}

	@Override
	public void delete(T o) {
		if (o != null) {
			this.getCurrentSession().delete(o);
		}
	}

	@Override
	public void update(T o) {
		if (o != null) {
			this.getCurrentSession().update(o);
		}
	}

	@Override
	public void saveOrUpdate(T o) {
		if (o != null) {
			this.getCurrentSession().saveOrUpdate(o);
		}
	}

	@Override
	public List<T> find(String hql) {
		Query q = this.getCurrentSession().createQuery(hql);
		return q.list();
	}

	@Override
	public List<T> find(String hql, Map<String, Object> params) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.list();
	}

	@Override
	public List<T> find(String hql, Map<String, Object> params, int page, int rows) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}

	@Override
	public List<T> find(String hql, int page, int rows) {
		Query q = this.getCurrentSession().createQuery(hql);
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}

	@Override
	public Long count(String hql) {
		Query q = this.getCurrentSession().createQuery(hql);
		return (Long) q.uniqueResult();
	}

	@Override
	public Long count(String hql, Map<String, Object> params) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return (Long) q.uniqueResult();
	}

	@Override
	public int executeHql(String hql) {
		Query q = this.getCurrentSession().createQuery(hql);
		return q.executeUpdate();
	}

	@Override
	public int executeHql(String hql, Map<String, Object> params) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.executeUpdate();
	}

	@Override
	public List<T> findBySql(String sql, List<Object> params, Class<T> clazz) {
		// 参数解析
		Object[] args = new Object[params.size()];
		int i = 0;
		for (Object o : params) {
			args[i] = o;
			i++;
		}
		List<Map<String, Object>> listMap = jdbcTemplateAnalysis.queryForList(sql, args);

		if (null == listMap || listMap.isEmpty()) {
			return new ArrayList<T>();
		}
		try {
			// TODO 更好的实现 将查出来的对象转成实体
			String json = GsonUtils.getJson(listMap);
			return GsonUtils.fromJsonList(json, clazz);
		} catch (Exception e) {
			throw new RuntimeException("Please checked SQL field VS Bean field..");
		}
	}

	@Override
	public List<T> findBySql(String sql, List<Object> params, Class<T> clazz, int pageNo, int pageSize) {
		// 参数解析
		Object[] args = new Object[params.size()];
		int i = 0;
		for (Object o : params) {
			args[i] = o;
			i++;
		}

		// 开始
		int start = 0;
		if (1 < pageNo) {
			start = (pageNo - 1) * pageSize;
		}

		if (0 != pageSize) {
			sql += " limit " + start + "," + pageSize;
		}

		List<Map<String, Object>> listMap = jdbcTemplateAnalysis.queryForList(sql, args);

		if (null == listMap || listMap.isEmpty()) {
			return new ArrayList<T>();
		}
		try {
			// TODO 更好的实现 将查出来的对象转成实体
			String json = GsonUtils.getJson(listMap);
			return GsonUtils.fromJsonList(json, clazz);
		} catch (Exception e) {
			throw new RuntimeException("Please checked SQL field VS Bean field..");
		}
	}

	@Override
	public List<Object[]> findBySql(String sql) {
		SQLQuery q = this.getCurrentSession().createSQLQuery(sql);
		return q.list();
	}

	@Override
	public List<Object[]> findBySql(String sql, int page, int rows) {
		SQLQuery q = this.getCurrentSession().createSQLQuery(sql);
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}

	@Override
	public List<Object[]> findBySql(String sql, Map<String, Object> params) {
		SQLQuery q = this.getCurrentSession().createSQLQuery(sql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.list();
	}

	@Override
	public List<Object[]> findBySql(String sql, Map<String, Object> params, int page, int rows) {
		SQLQuery q = this.getCurrentSession().createSQLQuery(sql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}

	@Override
	public int executeSql(String sql) {
		SQLQuery q = this.getCurrentSession().createSQLQuery(sql);
		return q.executeUpdate();
	}

	@Override
	public int executeSql(String sql, Map<String, Object> params) {
		SQLQuery q = this.getCurrentSession().createSQLQuery(sql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.executeUpdate();
	}

	@Override
	public BigInteger countBySql(String sql) {
		SQLQuery q = this.getCurrentSession().createSQLQuery(sql);
		return (BigInteger) q.uniqueResult();
	}

	@Override
	public BigInteger countBySql(String sql, Map<String, Object> params) {
		SQLQuery q = this.getCurrentSession().createSQLQuery(sql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return (BigInteger) q.uniqueResult();
	}

	@Override
	public int bulkExecuteHql(String hql, Object[] params) {
		Query query = this.getCurrentSession().createQuery(hql);
		setParameter(query, params);
		int count = query.executeUpdate();
		return count;
	}

	private Query setParameter(Query query, Object[] values) {
		int i = 0;
		if (values != null) {
			for (Object value : values) {
				query.setParameter(i, value);
				i++;
			}
		}
		return query;
	}

	@Override
	public int[] batchExecuteSql(String sql, List<Object[]> params) {
		return jdbcTemplateAnalysis.batchUpdate(sql, params);
	}

	/**
	 * 执行Hql语句返回字符串值
	 */
	@Override
	public String getCommaHQL(String hql, Object[] values) {
		String result = null;
		List<?> list = find(hql, values);
		if (list != null && list.size() > 0) {
			if (list.get(0) != null) {
				result = list.get(0).toString();
			}
		}
		return result;
	}

	@Override
	public List<?> find(String queryString, Object[] values) {
		Query query = this.getCurrentSession().createQuery(queryString.toString());
		setParameter(query, values);
		return query.list();
	}
}
