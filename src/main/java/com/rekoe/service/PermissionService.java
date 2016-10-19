package com.rekoe.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nutz.ioc.loader.annotation.IocBean;

import com.rekoe.domain.Permission;

@IocBean(fields = "dao")
public class PermissionService extends BaseService<Permission> {

	public Map<Long, String> getPermissionMap() {
		Map<Long, String> map = new HashMap<Long, String>();
		List<Permission> permissions = query(null, null);
		for (Permission permission : permissions) {
			map.put(permission.getId(), permission.getName());
		}
		return map;
	}
}
