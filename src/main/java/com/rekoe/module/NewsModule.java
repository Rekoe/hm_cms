package com.rekoe.module;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import com.rekoe.common.page.Pagination;
import com.rekoe.domain.Article;
import com.rekoe.domain.ArticleCategory;
import com.rekoe.service.ArticleCategoryService;
import com.rekoe.service.ArticleService;
import com.rekoe.service.HtmlService;

@IocBean
public class NewsModule {

	@Inject
	private ArticleCategoryService articleCategoryService;
	@Inject
	private ArticleService articleService;

	@At({ "/news/list/?", "/news/list" })
	@Ok("fm:template.front.site.ts2.news.list")
	public void index() {
	}

	@At("/news/tags")
	@Ok("json")
	public List<ArticleCategory> tags() {
		return articleCategoryService.query();
	}

	@At({ "/news/view" })
	@Ok("fm:template.front.site.ts2.news.view")
	public Pagination<Article> view(@Param(value = "id", df = "") String id, @Param(value = "pageNumber", df = "1") int pageNumber, HttpServletRequest req) {
		req.setAttribute("tag", id);
		Pagination<Article> list = articleService.getPagination(pageNumber, StringUtils.isBlank(id) ? null : Cnd.where("articleCategoryId", "=", id).and("publication", "=", true));
		for (Article atricle : list.getList()) {
			atricle.setArticleCategory(articleCategoryService.fetch(Cnd.where("id", "=", atricle.getArticleCategoryId())));
		}
		return list;
	}

	@At("/news/content/*")
	@Ok("fm:template.front.site.ts2.news.content")
	public Article content(String id) {
		return articleService.fetchByID(id);
	}

	@At({ "/home", "index" })
	@Ok("fm:template.front.site.ts2.home")
	public void home() {

	}

	@At("/news/rk/*")
	@Ok("fm:template.front.site.ts2.news.tag")
	public List<Article> rk(String tag) {
		List<Article> list = articleService.query(Cnd.where("articleCategoryId", "=", tag).and("publication", "=", true).limit(1, 9).desc("modifyDate"));
		for (Article atricle : list) {
			atricle.setArticleCategory(articleCategoryService.fetch(Cnd.where("id", "=", tag)));
		}
		return list;
	}

	@At
	@Ok("fm:template.front.site.ts2.mobile")
	public void mobile() {

	}

	@Inject
	private HtmlService htmlService;

	@At
	@Ok("void")
	public void make() throws Exception {
		htmlService.makeIndex();
		htmlService.makeMobile();
	}
}
