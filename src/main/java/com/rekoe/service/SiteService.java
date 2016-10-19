package com.rekoe.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.sql.SqlCallback;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;

import com.rekoe.domain.Site;

@IocBean(fields = "dao")
public class SiteService extends BaseService<Site> {

	public List<Long> getAllIds() {
		Sql sql = Sqls.create("select id from $table");
		sql.vars().set("table", dao().getEntity(getEntityClass()).getTableName());
		sql.setCallback(new SqlCallback() {
			@Override
			public Object invoke(Connection conn, ResultSet rs, Sql sql) throws SQLException {
				List<Long> results = new ArrayList<Long>();
				while (rs.next()) {
					results.add(rs.getLong(1));
				}
				return results;
			}
		});
		dao().execute(sql);
		return sql.getList(Long.class);
	}

	public Site getSite(int id) {
		return dao().fetch(Site.class, Cnd.where("id", "=", id));
	}

	public List<Site> list() {
		return dao().query(getEntityClass(), null);
	}

	public List<Site> loadAllByIds(Long[] ids) {
		return dao().query(getEntityClass(), Cnd.where("id", "in", ids));
	}

	public boolean canAdd(int siteId) {
		return Lang.isEmpty(dao().fetch(getEntityClass(), Cnd.where("siteId", "=", siteId)));
	}

	public Site getByPid(int siteId) {
		return dao().fetch(getEntityClass(), Cnd.where("siteId", "=", siteId));
	}
}
