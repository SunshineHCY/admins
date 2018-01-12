package com.pcaifu.system.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class ExcelSheetDTO {
	private LinkedHashMap<String, String> title = new LinkedHashMap<String, String>();
	
	private List<Object> datas = new ArrayList<Object>();

	public void addColumn(String key, String name) {
		title.put(key, name);
	}
	
	public void addAll(List<?> data) {
		datas.addAll(data);
	}
	
	public void putAll(List<?> data) {
		datas.clear();
		datas.addAll(data);
	}
	
	public LinkedHashMap<String, String> getTitle() {
		return title;
	}
	
	public List<Object> getDatas() {
		return datas;
	}
}