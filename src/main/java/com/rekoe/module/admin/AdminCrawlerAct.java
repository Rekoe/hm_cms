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
import com.rekoe.domain.CrawlerRule;
import com.rekoe.service.AcquisitionTempService;
import com.rekoe.service.ArticleCategoryService;
import com.rekoe.service.CrawlerRuleService;

@IocBean
@At("/admin/crawler")
public class AdminCrawlerAct {

	@Inject
	private CrawlerRuleService crawlerRuleService;

	@Inject
	private ArticleCategoryService articleCategoryService;

	@Inject
	private AcquisitionTempService acquisitionTempService;

	@At
	@Ok("fm:template.admin.crawler.add")
	@NutzRequiresPermissions(value = "admin.crawler:add", name = "添加", tag = "采集器", enable = true)
	public List<ArticleCategory> add() {
		return articleCategoryService.getTopList();
	}

	@At
	@Ok("fm:template.admin.crawler.list")
	@NutzRequiresPermissions(value = "admin.crawler:view", name = "查看列表", tag = "采集器", enable = true)
	public Object list(@Param(value = "pageNumber", df = "1") int pageNumber) {
		return crawlerRuleService.getPagination(pageNumber, null);
	}

	@At
	@Ok("fm:template.admin.crawler.edit")
	@NutzRequiresPermissions(value = "admin.crawler:edit", name = "编辑", tag = "采集器", enable = true)
	public CrawlerRule edit(@Param("id") long id) {
		CrawlerRule crawlerRule = crawlerRuleService.fetch(id);
		return crawlerRule;
	}

	@At
	@Ok("json")
	@RequiresPermissions(value = "admin.crawler:add")
	public Message save(@Param("::crawler.") CrawlerRule crawler, HttpServletRequest req) {
		crawlerRuleService.insert(crawler);
		return Message.success("admin.message.success", req);
	}

	@At
	@Ok("json")
	@RequiresPermissions(value = "admin.crawler:edit")
	public Message update(@Param("::crawler.") CrawlerRule crawler, HttpServletRequest req) {
		crawlerRuleService.update(crawler);
		return Message.success("admin.message.success", req);
	}

	@At
	@Ok("json")
	@NutzRequiresPermissions(value = "admin.crawler:delete", name = "删除", tag = "采集器", enable = true)
	public Message delete(@Param("ids") String[] ids, HttpServletRequest req) {
		crawlerRuleService.clear(Cnd.where("id", "in", ids));
		return Message.success("admin.message.success", req);
	}

	@At
	@Ok("json")
	@RequiresUser
	public Message start(@Param("id") long id, HttpServletRequest req) {
		crawlerRuleService.start(id);
		return Message.success("admin.message.success", req);
	}

	@At
	@Ok("fm:template.admin.acquisition.progress_data")
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
	@Ok("fm:template.admin.acquisition.progress")
	@RequiresUser
	public void progress() {

	}
}
