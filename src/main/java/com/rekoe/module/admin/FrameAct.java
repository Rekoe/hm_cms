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

	@At({ "account/main", "article/main", "crawler/main", "bind/main", "pwd/main" , "hm/main"})
	@Ok("fm:template.admin.common.main")
	public void main() {

	}

	@At("account/left")
	@Ok("fm:template.admin.account.frame.left")
	public void accountPwdLeft() {
	}

	@At("article/left")
	@Ok("fm:template.admin.article.frame.left")
	public void article_left() {
	}

	@At("crawler/left")
	@Ok("fm:template.admin.crawler.frame.left")
	public void crawler_left() {
	}

	/**
	 * 账号绑定
	 * 
	 */
	@At("bind/left")
	@Ok("fm:template.admin.bind.frame.left")
	public void bind_left() {
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
