package com.rekoe.module;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Times;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import com.rekoe.domain.Article;

@IocBean
@At("/yvr/api/v1")
@Ok("json")
@Fail("http:500")
public class ApiModule {

	protected int pageSize = 15;

	@Inject
	protected Dao dao;

	@GET
	@At
	public List<NutMap> arts(@Param("page") int page, @Param("limit") int limit) {
		if (page < 1)
			page = 1;
		if (limit < 0 || limit > pageSize)
			limit = pageSize;
		Pager pager = dao.createPager(page, limit);
		List<Article> list = dao.query(Article.class, Cnd.orderBy().desc("create_date"), pager);
		List<NutMap> re = new ArrayList<>();
		for (Article art : list) {
			re.add(NutMap.NEW().addv("modify_date", _time(art.getModifyDate())).addv("id", art.getId()).addv("hits", art.getHits()).addv("title", art.getTitle()).addv("create_date", _time(art.getCreateDate())).addv("author", art.getAuthor()));
		}
		return re;
	}

	private List<NutMap> replies = new ArrayList<NutMap>();

	@GET
	@At({ "/art/?", "/art" })
	public NutMap topic(@Param("id") String id) {
		Article art = dao.fetch(Article.class, Cnd.where("id", "=", id));
		if (art != null) {
			dao.update(Article.class, Chain.makeSpecial("hits", "+1").add("modify_date", Times.now()), Cnd.where("id", "=", id));
			return NutMap.NEW().addv("replies", replies).addv("good", art.isRecommend()).addv("content", art.getContent()).addv("id", art.getId()).addv("hits", art.getHits() + 1).addv("title", art.getTitle()).addv("create_date", _time(art.getCreateDate())).addv("author", art.getAuthor());
		}
		return NutMap.NEW();
	}

	public String _time(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		return sdf.format(date);
	}
}
