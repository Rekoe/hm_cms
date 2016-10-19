package com.rekoe.module.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.lang.Times;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import com.rekoe.common.Message;
import com.rekoe.domain.ArticleCategory;
import com.rekoe.service.ArticleCategoryService;

@IocBean
@At("/admin/article_category")
public class AdminArticleCategoryAct {

	@Inject
	private ArticleCategoryService articleCategoryService;

	@At
	@Ok("fm:template.admin.article_category.list")
	@RequiresPermissions(value = "admin.article:view")
	public List<ArticleCategory> list() {
		return articleCategoryService.getList();
	}

	@At
	@Ok("fm:template.admin.article_category.add")
	@RequiresPermissions(value = "admin.article:add")
	public List<ArticleCategory> add() {
		return articleCategoryService.getList();
	}

	@At
	@Ok("fm:template.admin.article_category.edit")
	@RequiresPermissions(value = "admin.article:edit")
	public ArticleCategory edit(@Param("id") String id, HttpServletRequest req) {
		ArticleCategory articleCategory = articleCategoryService.fetch(id);
		req.setAttribute("articleCategoryTree", list());
		req.setAttribute("children", articleCategoryService.findChildren(articleCategory));
		return articleCategory;
	}

	@At
	@Ok("json")
	@RequiresPermissions(value = "admin.article:edit")
	public Message update(@Param("name") String name, @Param("order") int order, final @Param("::category.") ArticleCategory ac, HttpServletRequest req) {
		ArticleCategory temp = articleCategoryService.fetch(Cnd.where("name", "=", name));
		if (Lang.isEmpty(temp)) {
			ParentCallBack callBack = new ParentCallBack() {
				int i = 0;

				@Override
				public void invoke() {
					i++;
				}

				@Override
				public int getCount() {
					return i;
				}
			};
			checkCategoryGread(ac.getParentId(), callBack);
			ac.setGrade(callBack.getCount());
			ac.setCreateDate(Times.now());
			ac.setModifyDate(Times.now());
			ac.setName(name);
			ac.setOrder(order);
			articleCategoryService.update(ac);
		}
		return Message.success("admin.message.success", req);
	}

	@At
	@Ok("json")
	@RequiresPermissions(value = "admin.article:add")
	public Message save(@Param("name") String name, @Param("order") int order, final @Param("::ac.") ArticleCategory ac, HttpServletRequest req) {
		ParentCallBack callBack = new ParentCallBack() {
			int i = 0;

			@Override
			public void invoke() {
				i++;
			}

			@Override
			public int getCount() {
				return i;
			}
		};
		checkCategoryGread(ac.getParentId(), callBack);
		ac.setGrade(callBack.getCount());
		ac.setCreateDate(Times.now());
		ac.setModifyDate(Times.now());
		ac.setName(name);
		ac.setOrder(order);
		articleCategoryService.insert(ac);
		return Message.success("admin.message.success", req);
	}

	private String checkCategoryGread(String parentid, ParentCallBack callBack) {
		boolean haveParent = StringUtils.isNotBlank(parentid);
		if (haveParent) {
			callBack.invoke();
			ArticleCategory articleCategory = articleCategoryService.fetch(parentid);
			if (!Lang.isEmpty(articleCategory)) {
				String tempParentid = checkCategoryGread(articleCategory.getParentId(), callBack);
				if (StringUtils.isBlank(tempParentid)) {
					return "";
				} else {
					checkCategoryGread(tempParentid, callBack);
				}
			}
		}
		return "";
	}

	public abstract interface ParentCallBack {
		public void invoke();

		public int getCount();
	}

	@At
	@Ok("json")
	@RequiresPermissions(value = "admin.article:delete")
	public Message delete(@Param("id") String id, HttpServletRequest req) {
		ArticleCategory ac = articleCategoryService.fetch(id);
		if (!Lang.isEmpty(ac.getArticleSet())) {
			return Message.error("admin.articleCategory.deleteExistArticleNotAllowed", req);
		}
		if (!Lang.isEmpty(ac.getChildren())) {
			return Message.error("admin.articleCategory.deleteExistChildrenNotAllowed", req);
		}
		articleCategoryService.delete(id);
		return Message.success("admin.message.success", req);
	}

	@At
	@Ok("json")
	@RequiresUser
	public List<ArticleCategory> by_channel(@Param("channelId") String id) {
		return articleCategoryService.getChildrenList(id);
	}
}