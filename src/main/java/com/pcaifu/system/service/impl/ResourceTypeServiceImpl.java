package com.pcaifu.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pcaifu.system.dao.ResourceTypeDaoI;
import com.pcaifu.system.model.Sysresourcetype;
import com.pcaifu.system.pageModel.ResourceType;
import com.pcaifu.system.service.ResourceTypeServiceI;

@Service
public class ResourceTypeServiceImpl implements ResourceTypeServiceI {

	@Autowired
	private ResourceTypeDaoI resourceType;

	@Override
	public List<ResourceType> getResourceTypeList() {
		List<Sysresourcetype> l = resourceType.find("from Sysresourcetype t");
		List<ResourceType> rl = new ArrayList<ResourceType>();
		if (l != null && l.size() > 0) {
			for (Sysresourcetype t : l) {
				ResourceType rt = new ResourceType();
				BeanUtils.copyProperties(t, rt);
				rl.add(rt);
			}
		}
		return rl;
	}

}
