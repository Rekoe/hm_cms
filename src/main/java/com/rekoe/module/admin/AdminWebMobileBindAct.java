package com.rekoe.module.admin;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.nutz.dao.Cnd;
import org.nutz.integration.shiro.annotation.NutzRequiresPermissions;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import com.rekoe.common.Message;
import com.rekoe.common.page.Pagination;
import com.rekoe.domain.WebMobileBind;
import com.rekoe.service.WebMobileBindService;

@IocBean
@At("/admin/bind")
public class AdminWebMobileBindAct {

	@Inject
	private WebMobileBindService webMobileBindService;

	@At
	@Ok("fm:template.admin.bind.list")
	@NutzRequiresPermissions(value = "admin.bind.uid:view", name = "查看列表", tag = "账号绑定", enable = true)
	public Pagination<WebMobileBind> list(@Param(value = "pageNumber", df = "1") int pageNumber, @Param("uid") String uid, HttpServletRequest req) {
		req.setAttribute("uid", uid);
		return webMobileBindService.getPagination(pageNumber, StringUtils.isBlank(uid) ? null : Cnd.where("webUid", "=", uid).or("mUid", "=", uid));
	}

	@At
	@Ok("json")
	@NutzRequiresPermissions(value = "admin.bind.uid:delete", name = "删除", tag = "账号绑定", enable = true)
	public Message delete(@Param("id") String id, HttpServletRequest req) {
		webMobileBindService.clear(Cnd.where("id", "=", id));
		return Message.success("admin.message.success", req);
	}

}
