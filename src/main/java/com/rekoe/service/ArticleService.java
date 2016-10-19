package com.rekoe.service;

import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.FieldFilter;
import org.nutz.dao.util.Daos;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import com.rekoe.common.page.Pagination;
import com.rekoe.domain.Article;
import com.rekoe.domain.ArticleCategory;

@IocBean(fields = { "dao" })
public class ArticleService extends BaseService<Article> {

	@Inject
	private ArticleCategoryService articleCategoryService;

	public List<Article> list() {
		return query(null, null);
	}

	public List<Article> getIndexNewList(int limit, String desc) {
		return getListByCnd(Cnd.NEW().limit(1, limit).desc(desc));
	}

	public List<Article> getListByCnd(Condition cnd) {
		return dao().query(getEntityClass(), cnd);
	}

	public int update(final Article art) {
		return Daos.ext(dao(), FieldFilter.create(Article.class, null, "^(createDate|hits)$", true)).update(art);
	}

	public Article fetchByID(String id) {
		Article art = fetch(Cnd.where("id", "=", id));
		art.setArticleCategory(articleCategoryService.fetch(Cnd.where("id", "=", art.getArticleCategoryId())));
		return art;
	}

	public Pagination<Article> getArticleListByPager(int pageNumber) {
		Pagination<Article> pagination = super.getPagination(pageNumber, null);
		List<Article> list = pagination.getList();
		for (Article atricle : list) {
			atricle.setArticleCategory(dao().fetch(ArticleCategory.class, Cnd.where("id", "=", atricle.getArticleCategoryId())));
		}
		return pagination;
	}
}
