package com.pcaifu.system.pageModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * EasyUI DataGrid模型
 * 
 * @author 拍财富
 * 
 */
public class DataGrid {

	private Long total = 0L;
	private List rows = new ArrayList();
	private Map<String, Object> summary = new HashMap<String, Object>();

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public List getRows() {
		return rows;
	}

	public void setRows(List rows) {
		this.rows = rows;
	}

	public Map<String, Object> getSummary() {
		return summary;
	}

	public void setSummary(Map<String, Object> summary) {
		this.summary = summary;
	}

}
