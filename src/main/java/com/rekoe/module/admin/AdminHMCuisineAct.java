package com.rekoe.module.admin;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.nutz.dao.Cnd;
import org.nutz.integration.shiro.annotation.NutzRequiresPermissions;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import com.rekoe.common.Message;
import com.rekoe.common.page.Pagination;
import com.rekoe.domain.HMCuisine;
import com.rekoe.service.HMCuisineService;

/**
 * 菜系
 * 
 * @author kouxian
 *http://www.runoob.com/jqueryui/example-autocomplete.html
 */
@IocBean
@At("/admin/hm/cuisine")
public class AdminHMCuisineAct {

	@Inject
	private HMCuisineService hmCuisineService;

	@At
	@Ok("fm:template.admin.hm_96789.cuisine.list")
	@NutzRequiresPermissions(value = "admin.hm:cuisine:view", name = "查看列表", tag = "惠民菜系", enable = true)
	public Pagination<HMCuisine> list(@Param(value = "pageNumber", df = "1") int page) {
		return hmCuisineService.getPagination(page, null);
	}

	@At
	@Ok("fm:template.admin.hm_96789.cuisine.add")
	@NutzRequiresPermissions(value = "admin.hm:cuisine:add", name = "添加", tag = "惠民菜系", enable = true)
	public void add() {
	}

	@At
	@Ok("json")
	@RequiresPermissions(value = "admin.hm:cuisine:add")
	public Message o_save(@Param("::cuisine.") HMCuisine cuisine, HttpServletRequest req) {
		if (StringUtils.isBlank(cuisine.getName())) {
			return Message.error("error.empty", req);
		}
		HMCuisine temp = hmCuisineService.fetch(Cnd.where("name", "=", cuisine.getName()));
		if (!Lang.isEmpty(temp)) {
			return Message.error("error.duplicate", req);
		}
		hmCuisineService.insert(cuisine);
		return Message.success("admin.message.success", req);
	}

	@At
	@Ok("fm:template.admin.hm_96789.cuisine:edit")
	@NutzRequiresPermissions(value = "admin.hm:cuisine.edit", name = "编辑", tag = "惠民菜系", enable = true)
	public HMCuisine edit(long id) {
		return hmCuisineService.fetch(id);
	}

	@At
	@Ok("json")
	@RequiresPermissions(value = "admin.hm:cuisine:edit")
	public Message update(@Param("::cuisine.") HMCuisine cuisine, HttpServletRequest req) {
		hmCuisineService.update(cuisine);
		return Message.success("admin.message.success", req);
	}
}
