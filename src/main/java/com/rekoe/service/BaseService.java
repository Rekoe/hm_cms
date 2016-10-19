package com.rekoe.service;

import org.nutz.dao.Condition;
import org.nutz.dao.pager.Pager;
import org.nutz.service.IdEntityService;

import com.rekoe.common.page.Pagination;

/**
 * @author 科技㊣²º¹³<br />
 *         http://www.rekoe.com<br />
 *         QQ:5382211
 */
public class BaseService<T> extends IdEntityService<T> {

	protected final static int DEFAULT_PAGE_NUMBER = 10;

	/*public Pagination<T> getPagination(int pageNumber, Condition cnd) {
		Sql sql = Sqls.create("select * from $table $condition");
		sql.getContext().attr("dao-cache-skip", "false");
		sql.setEntity(dao().getEntity(getEntityClass()));
		sql.vars().set("table", dao().getEntity(getEntityClass()).getTableName());
		sql.setCondition(cnd);
		sql.setCallback(Sqls.callback.entities());
		Pager pager = dao().createPager(pageNumber, DEFAULT_PAGE_NUMBER);
		pager.setRecordCount(dao().count(getEntityClass(), cnd));
		sql.setPager(pager);
		dao().execute(sql);
		List<T> list = sql.getList(getEntityClass());
		return new Pagination<T>(pageNumber, DEFAULT_PAGE_NUMBER, pager.getRecordCount(), list);
	}*/
	
	public Pagination<T> getPagination(int pageNumber, Condition cnd) {
		Pager pager = dao().createPager(pageNumber, DEFAULT_PAGE_NUMBER);
		pager.setRecordCount(dao().count(getEntityClass(), cnd));
		return new Pagination<T>(pageNumber, DEFAULT_PAGE_NUMBER, pager.getRecordCount(), dao().query(getEntityClass(), cnd, pager));
	}
}
