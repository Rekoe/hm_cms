package com.rekoe.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;

import com.rekoe.common.page.Pagination;
import com.rekoe.domain.Role;
import com.rekoe.domain.User;

@IocBean(fields = "dao")
public class UserService extends BaseService<User> {

	public void update(long uid, String password, boolean isLocked, int[] ids) {
		User user = fetch(uid);
		dao().clearLinks(user, "roles");
		if (ids != null && ids.length != 0) {
			user.setRoles(dao().query(Role.class, Cnd.where("id", "in", ids)));
		}
		if (StringUtils.isNotBlank(password)) {
			String salt = new SecureRandomNumberGenerator().nextBytes().toBase64();
			user.setSalt(salt);
			user.setPassword(new Sha256Hash(password, salt, 1024).toBase64());
		}
		user.setLocked(isLocked);
		dao().update(user);
		if (!Lang.isEmpty(user.getRoles())) {
			dao().insertRelation(user, "roles");
		}
	}

	public void updatePwd(long uid, String password) {
		String salt = new SecureRandomNumberGenerator().nextBytes().toBase64();
		dao().update(User.class, Chain.make("password", new Sha256Hash(password, salt, 1024).toBase64()).add("salt", salt), Cnd.where("id", "=", uid));
	}

	public User view(Long id) {
		User user = fetch(id);
		return dao().fetchLinks(user, null);
	}

	public User fetchByName(String name) {
		User user = fetch(Cnd.where("name", "=", name));
		if (user != null) {
			dao().fetchLinks(user, null);
		}
		return user;
	}

	public List<String> getRoleNameList(User user) {
		List<String> roleNameList = new ArrayList<String>();
		for (Role role : user.getRoles()) {
			roleNameList.add(role.getName());
		}
		return roleNameList;
	}

	public void addRole(Long userId, Long roleId) {
		User user = fetch(userId);
		Role role = new Role();
		role.setId(roleId);
		user.setRoles(Lang.list(role));
		dao().insertRelation(user, "roles");
	}

	public void removeRole(Long userId, Long roleId) {
		dao().clear("system_user_role", Cnd.where("userid", "=", userId).and("roleid", "=", roleId));
	}

	public Pagination<User> getPagination(int pageNumber) {
		return getPagination(pageNumber, null);
	}

	public void removeUserUpdata(User user) {
		dao().clear("system_user_role", Cnd.where("userid", "=", user.getId()));
		dao().clear("system_user_server", Cnd.where("userid", "=", user.getId()));
	}

	public void insertRelations(User user) {
		dao().insertRelation(user, "servers|roles");
	}

	public void updateLock(User user) {
		dao().update(user, "^(locked)$");
	}

	public void loadRolePermission(User user) {
		List<Role> roleList = user.getRoles();
		for (Role role : roleList) {
			dao().fetchLinks(role, "permissions");
		}
	}
}
