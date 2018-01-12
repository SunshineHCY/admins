package com.pcaifu.system.service;

import java.util.List;

import com.pcaifu.system.pageModel.ResourceType;

/**
 * 资源类型服务
 * 
 * @author 拍财富
 * 
 */
public interface ResourceTypeServiceI {

	/**
	 * 获取资源类型
	 * 
	 * @return
	 */
	public List<ResourceType> getResourceTypeList();

}
