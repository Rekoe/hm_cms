package com.rekoe.module.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.nutz.dao.Cnd;
import org.nutz.integration.shiro.annotation.NutzRequiresPermissions;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import com.rekoe.common.Message;
import com.rekoe.domain.AcquisitionTemp;
import com.rekoe.domain.ArticleCategory;
import com.rekoe.domain.CmsAcquisition;
import com.rekoe.service.AcquisitionService;
import com.rekoe.service.AcquisitionTempService;
import com.rekoe.service.ArticleCategoryService;

@IocBean
@At("/admin/acquisition")
public class AcquisitionAct {

	@Inject
	private AcquisitionService acquisitionService;

	@Inject
	private ArticleCategoryService articleCategoryService;

	@Inject
	private AcquisitionTempService acquisitionTempService;

	@At
	@Ok("fm:template.admin.article_acquisition.list")
	@NutzRequiresPermissions(value = "admin.acquisition:view", name = "查看列表", tag = "资源采集", enable = true)
	public Object list(@Param(value = "pageNumber", df = "1") int pageNumber) {
		return acquisitionService.getPagination(pageNumber, null);
	}

	@At
	@Ok("fm:template.admin.article_acquisition.add")
	@NutzRequiresPermissions(value = "admin.acquisition:add", name = "添加规则", tag = "资源采集", enable = true)
	public List<ArticleCategory> add() {
		List<ArticleCategory> list = articleCategoryService.query(null, null);
		return list;
	}

	@At
	@Ok("json")
	@RequiresPermissions(value = "admin.acquisition:add")
	public Message save(@Param("::acqu.") CmsAcquisition acquisition, HttpServletRequest req) {
		acquisitionService.insert(acquisition);
		return Message.success("admin.message.success", req);
	}

	@At
	@Ok("json")
	@NutzRequiresPermissions(value = "admin.acquisition:start", name = "启动采集", tag = "资源采集", enable = true)
	public Message start(@Param("id") int id, HttpServletRequest req) {
		acquisitionService.start(id);
		return Message.success("admin.message.success", req);
	}

	@At
	@Ok("fm:template.admin.article_acquisition.edit")
	@NutzRequiresPermissions(value = "admin.acquisition:edit", name = "编辑采集规则", tag = "资源采集", enable = true)
	public CmsAcquisition edit(@Param("id") long id) {
		return acquisitionService.fetch(id);
	}

	@At
	@Ok("json")
	@RequiresPermissions(value = "admin.acquisition:edit")
	public Message update(@Param("::acqu.") CmsAcquisition acquisition, HttpServletRequest req) {
		acquisitionService.update(acquisition);
		return Message.success("admin.message.success", req);
	}

	@At
	@Ok("fm:template.admin.article_acquisition.progress_data")
	@RequiresUser
	public int v_progress_data(HttpServletRequest req) {
		List<AcquisitionTemp> list = acquisitionTempService.getList();
		req.setAttribute("list", list);
		return acquisitionTempService.getPercent();
	}

	@At
	@Ok("json")
	@RequiresUser
	public Message v_check_complete(HttpServletRequest req) {
		List<AcquisitionTemp> list = acquisitionTempService.getList();
		if (Lang.isEmpty(list)) {
			return Message.success("admin.message.success", req);
		}
		return Message.error("admin.message.success", req);
	}

	@At
	@Ok("fm:template.admin.article_acquisition.progress")
	@RequiresUser
	public void progress() {

	}

	@At
	@Ok("json")
	@NutzRequiresPermissions(value = "admin.acquisition:delete", name = "删除采集规则", tag = "资源采集", enable = true)
	public Message delete(@Param("ids") String[] ids, HttpServletRequest req) {
		acquisitionService.clear(Cnd.where("id", "in", ids));
		return Message.success("admin.message.success", req);
	}
}
