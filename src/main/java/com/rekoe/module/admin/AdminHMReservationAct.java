package com.rekoe.module.admin;

import javax.servlet.http.HttpServletRequest;

import org.nutz.integration.shiro.annotation.NutzRequiresPermissions;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Times;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import com.rekoe.common.Message;
import com.rekoe.common.page.Pagination;
import com.rekoe.domain.HMReservation;
import com.rekoe.service.HMReservationService;
import com.rekoe.service.IdWorkerService;

@IocBean
@At("/admin/hm/reservation")
public class AdminHMReservationAct {

	@Inject
	private HMReservationService hmReservationService;

	@Inject
	private IdWorkerService idFactory;

	@At
	@Ok("fm:template.admin.hm_96789.reservation.list")
	@NutzRequiresPermissions(value = "admin.hm:reservation:view", name = "查看列表", tag = "订单", enable = true)
	public Pagination<HMReservation> list(@Param(value = "pageNumber", df = "1") int page) {
		return hmReservationService.getPagination(page, null);
	}

	@At
	@Ok("json")
	@NutzRequiresPermissions(value = "admin.hm:reservation:add", enable = true, name = "添加订单", tag = "订单")
	public Message o_save(@Param("::res.") HMReservation res, HttpServletRequest req) {
		res.setStatus(1);
		res.setCreateTim(Times.now());
		res.setOrderId(idFactory.nextId());
		hmReservationService.insert(res);
		return Message.success("ok", req);
	}
}
