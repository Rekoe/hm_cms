package com.rekoe.module.admin;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresUser;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Attr;
import org.nutz.mvc.annotation.Ok;
import org.nutz.web.Webs;

import com.rekoe.domain.Site;
import com.rekoe.domain.User;
import com.rekoe.module.BaseAction;

@IocBean
@At("/admin")
public class WelcomeAct extends BaseAction {

	@At
	@Ok("fm:template.admin.main")
	@RequiresUser
	public void index_main() {
	}

	@At
	@Ok("fm:template.admin.top")
	@RequiresUser
	public boolean top(@Attr(Webs.ME) User user, HttpServletRequest req) {
		Collection<Site> servers = Lang.isEmpty(user.getSites()) ? (new ArrayList<Site>()) : user.getSites().values();
		req.setAttribute("servers", servers);
		return true;
	}

	@At
	@Ok("fm:template.admin.index")
	@RequiresUser
	public void main() {
	}

	@At
	@Ok("fm:template.admin.left")
	public void left() {
	}

	@At
	@Ok("fm:template.admin.right")
	public void right() {
	}
}
