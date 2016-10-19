package com.rekoe.module;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.SessionException;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;

@IocBean
@At("/user")
public class IndexAct {

	private final static Log log = Logs.get();

	@At
	@Ok("fm:template.login.index")
	public void login() {
		try {
			SecurityUtils.getSubject().logout();
		} catch (SessionException ise) {
			log.debug("Encountered session exception during logout.  This can generally safely be ignored.", ise);
		} catch (Exception e) {
			log.debug("登出发生错误", e);
		}
	}
}
