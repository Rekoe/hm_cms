package com.rekoe.module.admin;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.nutz.dao.Cnd;
import org.nutz.integration.shiro.annotation.NutzRequiresPermissions;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Times;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import com.rekoe.common.Message;
import com.rekoe.common.page.Pagination;
import com.rekoe.domain.Article;
import com.rekoe.domain.ArticleCategory;
import com.rekoe.service.ArticleCategoryService;
import com.rekoe.service.ArticleService;

@IocBean
@At("/admin/article")
public class AdminArticleAct {

	@Inject
	private ArticleService articleService;

	@Inject
	private ArticleCategoryService articleCategoryService;

	@At
	@Ok("fm:template.admin.article.list")
	@NutzRequiresPermissions(value = "admin.article:view", name = "查看列表", tag = "文章", enable = true)
	public Pagination<Article> list(@Param(value = "pageNumber", df = "1") int pageNumber) {
		return articleService.getArticleListByPager(pageNumber);
	}

	@At
	@Ok("fm:template.admin.article.add")
	@NutzRequiresPermissions(value = "admin.article:add", name = "添加", tag = "文章", enable = true)
	public List<ArticleCategory> add() {
		List<ArticleCategory> list = articleCategoryService.query(null, null);
		return list;
	}

	@At
	@Ok("json")
	@RequiresPermissions(value = "admin.article:add")
	public Message save(@Param("::art.") Article article, HttpServletRequest req) {
		Date now = Times.now();
		article.setCreateDate(now);
		article.setModifyDate(now);
		articleService.insert(article);
		return Message.success("admin.message.success", req);
	}

	@At
	@Ok("fm:template.admin.article.edit")
	@NutzRequiresPermissions(value = "admin.article:edit", name = "编辑", tag = "文章", enable = true)
	public List<ArticleCategory> edit(String id, HttpServletRequest req) {
		Article art = articleService.fetchByID(id);
		art = articleService.dao().fetchLinks(art, "articleCategory");
		List<ArticleCategory> list = articleCategoryService.query(null, null);
		req.setAttribute("article", art);
		return list;
	}

	@At
	@Ok("json")
	@RequiresPermissions(value = "admin.article:update")
	public Message update(@Param("::art.") Article article, HttpServletRequest req) {
		article.setModifyDate(Times.now());
		articleService.update(article);
		return Message.success("admin.message.success", req);
	}

	@At
	@Ok("json")
	@NutzRequiresPermissions(value = "admin.article:delete", name = "删除", tag = "文章", enable = true)
	public Message delete(@Param("id") String id, HttpServletRequest req) {
		articleService.clear(Cnd.where("id", "=", id));
		return Message.success("admin.message.success", req);
	}
}
