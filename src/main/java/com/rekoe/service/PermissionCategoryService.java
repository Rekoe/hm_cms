package com.rekoe.service;

import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.IocBean;

import com.rekoe.domain.PermissionCategory;

@IocBean(fields = "dao")
public class PermissionCategoryService extends BaseService<PermissionCategory> {

	public List<PermissionCategory> list() {
		List<PermissionCategory> list = query(null, null);
		for (PermissionCategory pc : list) {
			dao().fetchLinks(pc, "permissions");
		}
		return list;
	}

	public PermissionCategory get(String id) {
		return dao().fetch(getEntityClass(), Cnd.where("id", "=", id));
	}

	public void remove(String id) {
		dao().clear(getEntityClass(), Cnd.where("id", "=", id));
	}
}
