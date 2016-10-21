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
import com.rekoe.domain.HMRestaurantInfo;
import com.rekoe.domain.HMTradingStore;
import com.rekoe.service.HMRestaurantInfoService;
import com.rekoe.service.HMTradingStoreService;

@IocBean
@At("/admin/hm/tradingstore")
public class AdminHMTradingStoreAct {

	@Inject
	private HMTradingStoreService hmTradingStoreService;

	@Inject
	private HMRestaurantInfoService hmRestaurantInfoService;

	@At
	@Ok("fm:template.admin.hm_96789.tradingstore.list")
	@NutzRequiresPermissions(value = "admin.hm:tradingstore:view", name = "查看列表", tag = "商户商圈", enable = true)
	public Pagination<HMTradingStore> list(@Param("id") long id, @Param(value = "pageNumber", df = "1") int page, HttpServletRequest req) {
		req.setAttribute("id", id);
		return hmTradingStoreService.getPagination(page, Cnd.where("restaurantInfoId", "=", id));
	}

	@At
	@Ok("fm:template.admin.hm_96789.tradingstore.add")
	@NutzRequiresPermissions(value = "admin.hm:tradingstore:add", name = "查看列表", tag = "商户商圈", enable = true)
	public HMRestaurantInfo add(@Param("id") long id, HttpServletRequest req) {
		req.setAttribute("id", id);
		HMRestaurantInfo restaurantInfo = hmRestaurantInfoService.fetch(id);
		return hmRestaurantInfoService.fetchLinks(restaurantInfo, "cuisines");
	}

	@At
	@Ok("json")
	@RequiresPermissions(value = "admin.hm:tradingstore:add")
	public Message o_save(@Param("::tradingstore.") HMTradingStore tradingstore, HttpServletRequest req) {
		if (StringUtils.isBlank(tradingstore.getName())) {
			return Message.error("error.empty", req);
		}
		HMTradingStore temp = hmTradingStoreService.fetch(Cnd.where("name", "=", tradingstore.getName()));
		if (!Lang.isEmpty(temp)) {
			return Message.error("error.duplicate", req);
		}
		if (Lang.isEmpty(tradingstore.getCuisines())) {
			return Message.error("error.empty.cuisines", req);
		}
		tradingstore = hmTradingStoreService.insert(tradingstore);
		hmTradingStoreService.insertRelation(tradingstore, "cuisines");
		return Message.success("admin.message.success", req);
	}

	@At
	@Ok("json")
	@NutzRequiresPermissions(value = "admin.hm:tradingstore:delete", name = "删除商圈", tag = "商户商圈", enable = true)
	public Message deleted(@Param("id") long id, HttpServletRequest req) {
		hmTradingStoreService.delete(id);
		return Message.success("ok", req);
	}
}
