package com.rekoe.module.admin;

import javax.servlet.http.HttpServletRequest;

import org.nutz.dao.Cnd;
import org.nutz.integration.shiro.annotation.NutzRequiresPermissions;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import com.rekoe.common.page.Pagination;
import com.rekoe.domain.HMTradingStore;
import com.rekoe.service.HMTradingStoreService;

@IocBean
@At("/admin/hm/tradingstore")
public class AdminHMTradingStoreAct {

	@Inject
	private HMTradingStoreService hmTradingStoreService;

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
	public long add(@Param("id") long id) {
		return id;
	}

}
