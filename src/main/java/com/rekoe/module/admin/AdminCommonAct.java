package com.rekoe.module.admin;

import org.apache.shiro.authz.annotation.RequiresUser;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.Mvcs;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;

import com.rekoe.common.Message;

@IocBean
@At("/admin/common")
public class AdminCommonAct {

	protected static final String ERROR = "/admin/common/error";
	protected static final Message MESSAGE_ERROR = Message.error("admin.message.error", Mvcs.getReq());
	protected static final Message MESSAGE_ERROR_FORBIT = Message.error("admin.error.message.forbit", Mvcs.getReq());
	protected static final Message MESSAGE_SUCCESS = Message.success("admin.message.success", Mvcs.getReq());

	@At
	@Ok("fm:template.admin.common.error")
	public Message unauthorized() {
		return MESSAGE_ERROR;
	}

	@At
	@Ok("fm:template.admin.common.error")
	public Message forbit() {
		return MESSAGE_ERROR_FORBIT;
	}

	@At
	@Ok("fm:template.admin.common.error")
	public Message success() {
		return MESSAGE_SUCCESS;
	}

	@At
	@Ok("fm:template.admin.common.right")
	@RequiresUser
	public void right() {

	}
	

	@At
	@Ok("fm:template.admin.common.main")
	@RequiresUser
	public void main() {

	}
}
