package com.rekoe.module.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.nutz.dao.Cnd;
import org.nutz.integration.shiro.annotation.NutzRequiresPermissions;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import com.rekoe.common.Message;
import com.rekoe.common.page.Pagination;
import com.rekoe.domain.Permission;
import com.rekoe.domain.PermissionCategory;
import com.rekoe.domain.Role;
import com.rekoe.module.BaseAction;
import com.rekoe.service.PermissionCategoryService;
import com.rekoe.service.PermissionService;
import com.rekoe.service.RoleService;

@IocBean
@At("/admin/role")
public class AdminRoleAct extends BaseAction {

	@Inject
	private RoleService roleService;

	@Inject
	private PermissionService permissionService;

	@Inject
	private PermissionCategoryService permissionCategoryService;

	@At
	@Ok("fm:template.admin.user.role.list")
	@NutzRequiresPermissions(value = "system.role:view", name = "浏览角色", tag = "角色管理", enable = true)
	public Pagination<Role> list(@Param(value = "pageNumber", df = "1") int pageNumber) {
		return roleService.getPagination(pageNumber, null);
	}

	@At
	@Ok("fm:template.admin.user.role.edit")
	@NutzRequiresPermissions(value = "system.role:edit", name = "编辑角色", tag = "角色管理", enable = true)
	public Role edit(@Param("id") long id, HttpServletRequest req) {
		Role role = roleService.fetch(id);
		role = roleService.fetchLinks(role, "permissions");
		List<PermissionCategory> pcList = permissionCategoryService.list();
		req.setAttribute("pcList", pcList);
		return role;
	}

	@At
	@Ok("json")
	@RequiresPermissions("system.role:edit")
	public Message update(@Param("::role.") Role tempRole, @Param("name") String name, @Param("authorities") int[] permIds, HttpServletRequest req) {
		Role $role = roleService.fetch(tempRole.getId());
		if (!Lang.isEmpty($role)) {
			List<Permission> perms = permissionService.query(Cnd.where("id", "in", permIds), null);
			$role.setDescription(tempRole.getDescription());
			$role.setName(name);
			roleService.updateRoleRelation($role, perms);
		} else {
			return Message.error("admin.message.blank", req);
		}
		return Message.success("admin.message.success", req);
	}

	@At
	@Ok("fm:template.admin.user.role.add")
	@NutzRequiresPermissions(value = "system.role:add", name = "添加角色", tag = "角色管理", enable = true)
	public List<PermissionCategory> add() {
		return permissionCategoryService.list();
	}

	@At
	@Ok("json")
	@RequiresPermissions("system.role:add")
	public Message save(@Param("name") String name, @Param("description") String desc, @Param("authorities") int[] ids, HttpServletRequest req) {
		Role role = roleService.fetchByName(name);
		if (Lang.isEmpty(role)) {
			role = new Role();
			role.setDescription(desc);
			role.setName(name);
			role.setPermissions(permissionService.query(Cnd.where("id", "in", ids), null));
			roleService.insert(role);
			return Message.success("admin.message.success", req);
		}
		return Message.error("admin.message.duplicate", req);
	}

	@At
	@Ok("json")
	@NutzRequiresPermissions(value = "system.role:delete", name = "删除角色", tag = "角色管理", enable = true)
	public Message delete(@Param("ids") long[] uids, HttpServletRequest req) {
		roleService.clear(Cnd.where("id", "in", uids));
		return Message.success("admin.message.success", req);
	}
}