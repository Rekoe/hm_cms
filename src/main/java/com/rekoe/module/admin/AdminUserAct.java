package com.rekoe.module.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.integration.shiro.annotation.NutzRequiresPermissions;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.mvc.NutConfigException;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Attr;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
import org.nutz.web.Webs;
import org.nutz.web.ajax.Ajax;

import com.rekoe.common.Message;
import com.rekoe.common.page.Pagination;
import com.rekoe.domain.Role;
import com.rekoe.domain.Site;
import com.rekoe.domain.User;
import com.rekoe.service.RoleService;
import com.rekoe.service.SiteService;
import com.rekoe.service.UserService;

@IocBean
@At("/admin/user")
public class AdminUserAct {

	@Inject
	private UserService userService;
	@Inject
	private RoleService roleService;

	@Inject
	private SiteService siteService;

	@At
	@Ok("fm:template.admin.user.list")
	@NutzRequiresPermissions(value = { "system.user:view" }, name = "浏览账号", tag = "账号管理", enable = true)
	public Pagination<User> list(@Param(value = "pageNumber", df = "1") int pageNumber) {
		return userService.getPagination(pageNumber);
	}

	@At
	@Ok("fm:template.admin.user.add")
	@NutzRequiresPermissions(value = { "system.user:add" }, name = "添加账号", tag = "账号管理", enable = true)
	public List<Role> add() {
		return roleService.query();
	}

	@At
	@Ok("json")
	@NutzRequiresPermissions(value = "system.user:delete", name = "删除账号", tag = "账号管理", enable = true)
	public Message delete(@Param("ids") int[] uids, HttpServletRequest req) {
		userService.clear(Cnd.where("id", "in", uids));
		return Message.success("admin.message.success", req);
	}

	@At("/check/username")
	@Ok("raw")
	@RequiresUser
	public boolean checkName(@Param("username") String username) {
		User user = userService.fetchByName(username);
		return Lang.isEmpty(user) ? true : false;
	}

	@At
	@Ok("fm:template.admin.user.edit")
	@Fail("json")
	@NutzRequiresPermissions(value = { "system.user:edit" }, name = "编辑账号", tag = "账号管理", enable = true)
	public User edit(@Attr(Webs.ME) User user, @Param("id") long id, HttpServletRequest req) {
		User editUser = userService.view(id);
		if (Lang.isEmpty(editUser) || editUser.isLocked()) {
			throw new NutConfigException(String.format("先解除帐号 %s 的锁定状态", editUser.getName()));
		}
		boolean isSupper = user.isSystem();
		if (user.getId() != editUser.getId()) {
			if (!isSupper && editUser.isSystem()) {
				throw new NutConfigException("不可以编辑比自己权限高的账号");
			}
		}
		List<Role> roleList = user.getRoles();
		if (isSupper) {
			roleList = roleService.query();
		}
		List<Site> serverList = siteService.list();
		int systemSize = serverList.size();
		int userSize = editUser.getSites().size();
		boolean allServer = userSize >= systemSize;
		req.setAttribute("allServer", allServer);
		req.setAttribute("serverList", serverList);
		req.setAttribute("roleList", roleList);
		return editUser;
	}

	@At
	@Ok("json")
	@RequiresPermissions({ "system.user:edit" })
	public Object update(@Param("id") long id, @Param("allServer") Boolean allServer, @Param("siteIds") Long[] siteIds, @Param("roleIds") Long[] roleIds) {
		User user = userService.fetch(id);
		userService.removeUserUpdata(user);
		allServer = user.isSystem() ? true : allServer;
		if (allServer) {
			List<Site> serverList = siteService.query();
			Map<Long, Site> serverMap = user.getSites();
			if (Lang.isEmpty(serverMap)) {
				serverMap = new HashMap<>();
				user.setSites(serverMap);
			}
			for (Site server : serverList) {
				serverMap.put(server.getId(), server);
			}
		} else {
			List<Site> serverList = siteService.loadAllByIds(siteIds);
			Map<Long, Site> serverMap = user.getSites();
			if (Lang.isEmpty(serverMap)) {
				serverMap = new HashMap<>();
				user.setSites(serverMap);
			}
			for (Site server : serverList) {
				serverMap.put(server.getId(), server);
			}
		}
		user.setRoles(roleService.loadRoles(roleIds));
		userService.insertRelations(user);
		return user;
	}

	/**
	 * 锁定用户
	 */
	@At
	@Ok("json")
	@NutzRequiresPermissions(value = "system.user:lock", name = "锁定账号", tag = "账号管理", enable = true)
	public Message lock(@Param("id") long id, HttpServletRequest req) {
		User user = userService.view(id);
		if (Lang.isEmpty(user)) {
			return Message.error("common.error.lock.account.empty", req);
		}
		if (user.isSystem()) {
			return Message.error("common.error.lock.account.system", req);
		}
		boolean lock = user.isLocked();
		user.setLocked(!lock);
		userService.updateLock(user);
		return Message.success("button.submit.success", req);
	}

	@At
	@Ok("fm:template.admin.account.change_pwd")
	@RequiresUser
	public void change_pwd() {

	}

	@At
	@Ok("json")
	@RequiresUser
	public Object pwd_updata(@Param("oldpwd") String oldpwd, @Param("newpwd") String newpwd, @Param("rewpwd") String rewpwd, @Attr(Webs.ME) User user) {
		if (StringUtils.isNotBlank(newpwd)) {
			if (Lang.equals(newpwd, rewpwd)) {
				String oldSalt = user.getSalt();
				String $oldPwd = new Sha256Hash(oldpwd, oldSalt, 1024).toBase64();
				if (Lang.equals($oldPwd, user.getPassword())) {
					RandomNumberGenerator rng = new SecureRandomNumberGenerator();
					String salt = rng.nextBytes().toBase64();
					String hashedPasswordBase64 = new Sha256Hash(newpwd, salt, 1024).toBase64();
					user.setSalt(salt);
					user.setPassword(hashedPasswordBase64);
					userService.update(Chain.make("salt", salt).add("password", hashedPasswordBase64), Cnd.where("id", "=", user.getId()));
					return Ajax.ok();
				} else {
					return Ajax.fail().setMsg("旧的密码错误");
				}
			} else {
				return Ajax.fail().setMsg("两次输入的密码不一致");
			}
		} else {
			return Ajax.fail().setMsg("密码不能为空");
		}
	}
}