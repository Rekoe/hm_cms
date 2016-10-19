package com.rekoe.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;

import com.rekoe.common.page.Pagination;
import com.rekoe.domain.Permission;
import com.rekoe.domain.Role;

@IocBean(fields = "dao")
public class RoleService extends BaseService<Role> {

	public Role insert(Role role) {
		role = dao().insert(role);
		return dao().insertRelation(role, "permissions");
	}

	public int delete(long id) {
		int del = dao().delete(Role.class, id);
		if (del > 0) {
			dao().clear("system_role_permission", Cnd.where("roleid", "=", id));
			dao().clear("system_user_role", Cnd.where("roleid", "=", id));
		}
		return del;
	}

	public Role get(long id) {
		return dao().fetchLinks(fetch(id), "permissions");
	}

	public Role fetchByName(String name) {
		if (StringUtils.isBlank(name)) {
			throw Lang.makeThrow("name not be blank");
		}
		return fetch(Cnd.where("name", "=", name));
	}

	public List<String> getPermissionNameList(Role role) {
		List<String> permissionNameList = new ArrayList<String>();
		for (Permission permission : role.getPermissions()) {
			permissionNameList.add(permission.getName());
		}
		return permissionNameList;
	}

	public void updateRoleRelation(Role role, List<Permission> perms) {
		dao().clearLinks(role, "permissions");
		if (role.getPermissions() != null) {
			role.getPermissions().clear();
		}
		dao().update(role);
		if (!Lang.isEmpty(perms)) {
			role.setPermissions(perms);
			dao().insertRelation(role, "permissions");
		}
	}

	public Map<Long, String> rolePermissions() {
		Map<Long, String> map = new HashMap<Long, String>();
		List<Role> roles = query(null, null);
		for (Role role : roles) {
			map.put(role.getId(), role.getName());
		}
		return map;
	}

	public void addPermission(Long roleId, long permissionId) {
		dao().insert("system_role_permission", Chain.make("roleid", roleId).add("permissionid", permissionId));
	}

	public void removePermission(Long roleId, long permissionId) {
		dao().clear("system_role_permission", Cnd.where("roleid", "=", roleId).and("permissionid", "=", permissionId));
	}

	public Pagination<Role> getRoleListByPager(int pageNumber) {
		return getPagination(pageNumber, null);
	}

	public List<Role> loadRoles(Long[] ids) {
		return dao().query(getEntityClass(), Cnd.where("id", "in", ids));
	}
}
