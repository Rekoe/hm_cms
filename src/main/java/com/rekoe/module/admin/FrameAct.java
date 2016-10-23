package com.rekoe.module.admin;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;

@IocBean
@At("/admin/frame/")
public class FrameAct {

	/** 用户管理 **/
	@At("user/left")
	@Ok("fm:template.admin.user.frame.left")
	public void userLeft() {
	}

	@At({ "account/main", "pwd/main", "hm/main", "user/main" })
	@Ok("fm:template.admin.common.main")
	public void main() {

	}

	@At("account/left")
	@Ok("fm:template.admin.account.frame.left")
	public void accountPwdLeft() {
	}

	/*
	 * 修改密码
	 */
	@At("pwd/left")
	@Ok("fm:template.admin.account.frame.left")
	public void pwdLeft() {
	}

	@At("hm/left")
	@Ok("fm:template.admin.hm_96789.frame.left")
	public void hmLeft() {
	}
}
