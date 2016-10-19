package com.rekoe.module.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
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
import com.rekoe.domain.HMRestaurantInfo;
import com.rekoe.service.HMCuisineService;
import com.rekoe.service.HMRestaurantInfoService;

@IocBean
@At("admin/hm/resinf")
public class AdminHMRestaurantInfoAct {

	@Inject
	private HMRestaurantInfoService hmRestaurantInfoService;

	@Inject
	private HMCuisineService hmCuisineService;

	@At
	@Ok("fm:template.admin.hm_96789.resinf.list")
	@NutzRequiresPermissions(value = "admin.hm:resinf:view", name = "查看列表", tag = "惠民店铺", enable = true)
	public Pagination<HMRestaurantInfo> list(@Param(value = "pageNumber", df = "1") int page) {
		return hmRestaurantInfoService.getPagination(page, null);
	}

	@At
	@Ok("fm:template.admin.hm_96789.resinf.add")
	@NutzRequiresPermissions(value = "admin.hm:resinf:add", name = "添加", tag = "惠民菜系", enable = true)
	public List<HMCuisine> add() {
		return hmCuisineService.query();
	}

	@At
	@Ok("json")
	@RequiresPermissions(value = "admin.hm:resinf:add")
	public Message o_save(@Param("::resinf.") HMRestaurantInfo resinf, HttpServletRequest req) {
		if (StringUtils.isBlank(resinf.getName())) {
			return Message.error("error.empty", req);
		}
		HMRestaurantInfo temp = hmRestaurantInfoService.fetch(Cnd.where("name", "=", resinf.getName()));
		if (!Lang.isEmpty(temp)) {
			return Message.error("error.duplicate", req);
		}
		resinf = hmRestaurantInfoService.insert(resinf);
		hmRestaurantInfoService.insertRelation(resinf, "cuisines");
		return Message.success("admin.message.success", req);
	}

	@At
	@Ok("fm:template.admin.hm_96789.resinf.edit")
	@NutzRequiresPermissions(value = "admin.hm:resinf.edit", name = "编辑", tag = "惠民菜系", enable = true)
	public HMRestaurantInfo edit(long id, HttpServletRequest req) {
		req.setAttribute("cuisines", hmCuisineService.query());
		HMRestaurantInfo info = hmRestaurantInfoService.fetch(id);
		hmRestaurantInfoService.fetchLinks(info, "cuisines");
		return info;
	}

	@At
	@Ok("json")
	@RequiresPermissions(value = "admin.hm:resinf:edit")
	public Message o_update(@Param("::resinf.") HMRestaurantInfo resinf, HttpServletRequest req) {
		hmRestaurantInfoService.update(resinf);
		Sql sql = Sqls.create("delete from hm_restaurantinfo_cuisine $condition");
		sql.setCondition(Cnd.where("restaurantinfoid", "=", resinf.getId()));
		hmRestaurantInfoService.dao().execute(sql);
		hmRestaurantInfoService.insertRelation(resinf, "cuisines");
		return Message.success("admin.message.success", req);
	}
}
