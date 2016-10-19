package com.rekoe.module.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.nutz.integration.shiro.annotation.NutzRequiresPermissions;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Attr;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
import org.nutz.web.Webs;

import com.rekoe.common.Message;
import com.rekoe.common.page.Pagination;
import com.rekoe.domain.Permission;
import com.rekoe.domain.PermissionCategory;
import com.rekoe.domain.User;
import com.rekoe.service.PermissionCategoryService;
import com.rekoe.service.PermissionService;

@IocBean
@At("/admin/permission")
public class AdminPermissionAct {

	@Inject
	private PermissionCategoryService permissionCategoryService;

	@Inject
	private PermissionService permissionService;

	@At
	@Ok("fm:template.admin.user.permission.list")
	@NutzRequiresPermissions(value = "system.permission:view", name = "浏览权限", tag = "权限管理", enable = true)
	public Pagination<Permission> list(@Param(value = "pageNumber", df = "1") int pageNumber) {
		return permissionService.getPagination(pageNumber, null);
	}

	@At("/list_category/?")
	@Ok("fm:template.admin.user.permission.list")
	@RequiresPermissions({ "system.permission:view" })
	public Pagination<PermissionCategory> listCategory(String id, @Param(value = "pageNumber", df = "1") int pageNumber) {
		return permissionCategoryService.getPagination(pageNumber, null);
	}

	@At
	@Ok("fm:template.admin.user.permission.edit")
	@NutzRequiresPermissions(value = "system.permission:edit", name = "编辑分类", tag = "权限管理", enable = true)
	public List<PermissionCategory> edit(long id, HttpServletRequest req) {
		Permission permission = permissionService.fetch(id);
		req.setAttribute("permission", permission);
		return add();
	}

	@At
	@Ok("fm:template.admin.user.permission.add")
	@NutzRequiresPermissions(value = "system.permission:add", name = "添加权限", tag = "权限管理", enable = true)
	public List<PermissionCategory> add() {
		List<PermissionCategory> list = permissionCategoryService.list();
		return list;
	}

	@At
	@Ok("json")
	@NutzRequiresPermissions(value = "system.permission:delete", name = "删除权限", tag = "权限管理", enable = false)
	public Message delete(@Attr(Webs.ME) User user, @Param("id") long id, HttpServletRequest req) {
		Permission permission = permissionService.fetch(id);
		if (permission.isLocked() && !user.isSystem()) {
			return Message.error("admin.permissionCategory.deleteLockedNotAllowed", req);
		}
		permissionService.delete(id);
		return Message.success("admin.common.success", req);
	}

	@At
	@Ok(">>:/admin/permission/list.rk")
	@RequiresPermissions({ "system.permission:edit" })
	public Message update(@Param("::permission.") Permission permission, @Param("description") String description, @Param("name") String name, @Param("id") String id, HttpServletRequest req) {
		permission.setName(name);
		permission.setDescription(description);
		permissionService.update(permission);
		return Message.success("admin.common.success", req);
	}

	@At
	@Ok("json")
	@RequiresPermissions({ "system.permission:add" })
	public Message save(@Param("name") String name, @Param("permissionCategoryId") String permissionCategoryId, @Param("description") String description, HttpServletRequest req) {
		Permission permission = new Permission();
		permission.setName(name);
		permission.setDescription(description);
		permission.setPermissionCategoryId(permissionCategoryId);
		permissionService.insert(permission);
		return Message.success("admin.common.success", req);
	}
}