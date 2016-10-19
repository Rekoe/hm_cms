package com.rekoe.web.freemarker;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nutz.dao.Cnd;

import com.rekoe.domain.Article;
import com.rekoe.service.ArticleCategoryService;
import com.rekoe.service.ArticleService;
import com.rekoe.utils.DirectiveUtils;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * @author 科技㊣²º¹³ <br />
 *         2014年2月5日 下午6:16:26 <br />
 *         http://www.rekoe.com <br />
 *         QQ:5382211
 */
public class ArticleListDirective implements TemplateDirectiveModel {

	private ArticleService articleService;
	private ArticleCategoryService articleCategoryService;

	public ArticleListDirective(ArticleService articleService, ArticleCategoryService articleCategoryService) {
		this.articleService = articleService;
		this.articleCategoryService = articleCategoryService;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		Map<String, Object> localHashMap = new HashMap<String, Object>();
		String id = DirectiveUtils.getString("articleCategoryId", params);
		int count = DirectiveUtils.getInt("count", params);
		List<Article> list = articleService.getListByCnd(Cnd.where("articleCategoryId", "=", id).limit(1, count).desc("createDate"));
		for (Article atricle : list) {
			atricle.setArticleCategory(articleCategoryService.fetch(Cnd.where("id", "=", id)));
		}
		localHashMap.put("articles", list);
		DirectiveUtils.setVariables(localHashMap, env, body);
	}
}
