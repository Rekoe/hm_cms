package com.rekoe.service;

import org.apache.commons.lang3.math.NumberUtils;
import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.IocBean;

import com.rekoe.domain.WebMobileBind;

@IocBean(fields = { "dao" })
public class WebMobileBindService extends BaseService<WebMobileBind> {

	public WebMobileBind findByWebId(String webuid, String muid, int sid) {
		long uid = NumberUtils.toLong(muid) * 10000 + sid;
		return dao().fetch(getEntityClass(), Cnd.where("webUid", "=", webuid).or("mUid", "=", uid));
	}
}
