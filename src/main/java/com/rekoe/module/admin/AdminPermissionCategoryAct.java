package com.rekoe.module.admin;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.integration.shiro.annotation.NutzRequiresPermissions;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import com.rekoe.common.Message;
import com.rekoe.common.page.Pagination;
import com.rekoe.domain.PermissionCategory;
import com.rekoe.service.PermissionCategoryService;

/**
 * @author 科技㊣²º¹³<br/>
 *         2014年2月3日 下午4:48:45<br/>
 *         http://www.rekoe.com<br/>
 *         QQ:5382211
 */
@IocBean
@At("/admin/permission/category")
public class AdminPermissionCategoryAct {

	@Inject
	private PermissionCategoryService permissionCategoryService;

	@At
	@Ok("fm:template.admin.user.permission_category.list")
	@RequiresPermissions({ "system.permission:view" })
	public Pagination<PermissionCategory> list(@Param(value = "pageNumber", df = "1") int pageNumber) {
		return permissionCategoryService.getPagination(pageNumber, null);
	}

	@At
	@Ok("fm:template.admin.user.permission_category.edit")
	@RequiresPermissions({ "system.permission:edit" })
	public PermissionCategory edit(String id) {
		return permissionCategoryService.fetch(Cnd.where("id", "=", id));
	}

	@At
	@Ok("json")
	@RequiresPermissions({ "system.permission:edit" })
	public Message update(@Param("name") String name, @Param("id") String id, HttpServletRequest req) {
		permissionCategoryService.update(Chain.make("name", name), Cnd.where("id", "=", id));
		return Message.success("admin.common.success", req);
	}

	@At
	@Ok("fm:template.admin.user.permission_category.add")
	@RequiresPermissions({ "system.permission:add" })
	public void add() {
	}

	@At
	@Ok("json")
	@RequiresPermissions({ "system.permission:add" })
	public Message save(@Param("name") String name, HttpServletRequest req) {
		PermissionCategory pc = new PermissionCategory();
		pc.setName(name);
		permissionCategoryService.insert(pc);
		return Message.success("admin.common.success", req);
	}

	@At
	@Ok("json")
	@NutzRequiresPermissions(value = "system.permission:delete", name = "删除权限", tag = "权限管理", enable = true)
	public Message delete(@Param("id") String id, HttpServletRequest req) {
		PermissionCategory pc = edit(id);
		if (pc.isLocked()) {
			return Message.error("admin.permissionCategory.deleteLockedNotAllowed", req);
		}
		permissionCategoryService.remove(id);
		return Message.success("admin.common.success", req);
	}
}
