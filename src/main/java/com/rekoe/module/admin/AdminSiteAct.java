package com.rekoe.module.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.nutz.integration.shiro.annotation.NutzRequiresPermissions;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Attr;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
import org.nutz.web.Webs;

import com.rekoe.common.Message;
import com.rekoe.common.page.Pagination;
import com.rekoe.domain.Site;
import com.rekoe.domain.User;
import com.rekoe.module.BaseAction;
import com.rekoe.service.SiteService;
import com.rekoe.service.UserService;

@IocBean
@At("/admin/site")
public class AdminSiteAct extends BaseAction{

	@Inject
	private SiteService siteService;

	@Inject
	private UserService userService;

	@At
	@Ok("fm:template.admin.site.list")
	@NutzRequiresPermissions(value = "site:view", name = "站点浏览", tag = "站点管理", enable = true)
	public Pagination<Site> list(@Param(value = "pageNumber", df = "1") int pageNumber) {
		return siteService.getPagination(pageNumber, null);
	}

	@At("/select/*")
	@Ok("json")
	@RequiresUser
	public Message select(@Param("id") long id, @Attr(Webs.ME) User user, HttpServletRequest req, HttpSession session) {
		Site site = user.getSites().get(id);
		if (!Lang.isEmpty(site)) {
			session.setAttribute("siteid", id);
			return Message.success("button.submit.success", req);
		}
		return Message.error("admin.message.error", req);
	}

	@At
	@Ok("fm:template.admin.site.add")
	@NutzRequiresPermissions(value = "site:add", name = "添加站点", tag = "站点管理", enable = true)
	public void add() {
	}

	@At
	@Ok("json")
	@RequiresPermissions("site:add")
	public Message o_save(@Param("::p.") Site site, HttpServletRequest req) {
		if (siteService.canAdd(site.getSiteId())) {
			siteService.insert(site);
			return Message.success("button.submit.success", req);
		}
		return Message.error("admin.message.duplicate", req);
	}

	@At
	@Ok("fm:template.admin.site.edit")
	@NutzRequiresPermissions(value = "site:edit", name = "编辑管理", tag = "站点管理", enable = true)
	public Site edit(@Param("id") long id, HttpServletRequest request) throws Throwable {
		Site site = siteService.fetch(id);
		if (Lang.isEmpty(site)) {
			throw new Throwable();
		}
		return site;
	}

	@At
	@Ok("json")
	@RequiresPermissions("site:edit")
	public Message update(@Param("::site.") Site site, HttpServletRequest req) {
		siteService.update(site);
		return Message.success("button.submit.success", req);
	}
}