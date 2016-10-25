package com.rekoe.module.admin;

import javax.servlet.http.HttpServletRequest;

import org.nutz.integration.shiro.annotation.NutzRequiresPermissions;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import com.rekoe.common.Message;
import com.rekoe.domain.HMReservation;

@IocBean
@At("/admin/hm/reservation")
public class AdminHMReservationAct {

	@At
	@Ok("json")
	@NutzRequiresPermissions(value = "admin.hm:reservation:add", enable = false, name = "添加订单", tag = "订单")
	public Message o_save(@Param("res") HMReservation res, HttpServletRequest req) {
		return Message.success("ok", req);
	}
}
