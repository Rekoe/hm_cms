package com.rekoe.domain;

import java.io.Serializable;
import java.util.Date;

import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.ColType;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.EL;
import org.nutz.dao.entity.annotation.Index;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Prev;
import org.nutz.dao.entity.annotation.Table;
import org.nutz.dao.entity.annotation.TableIndexes;
import org.nutz.lang.Times;

@Table("web_mobile_bind")
@TableIndexes({ @Index(name = "pid_bind_index1", fields = { "webUid" }, unique = true), @Index(name = "pid_bind_index2", fields = { "mUid" }, unique = true) })
public class WebMobileBind implements Serializable {

	private static final long serialVersionUID = -3272852169000024195L;

	@Name
	@Prev(els = { @EL("uuid()") })
	private String id;

	@Column(hump = true)
	private String webUid;

	@Column("w_sid")
	private String webSid;

	@Column(hump = true)
	private String mPid;

	@Column(hump = true)
	private String mUid;

	@Column(hump = true)
	private int mSid;

	@Column("is_bind")
	@ColDefine(type = ColType.BOOLEAN)
	private boolean bind;

	@Column
	private String addr;

	@Column
	@ColDefine(type = ColType.TIMESTAMP)
	private Date time;

	public WebMobileBind() {
		super();
	}

	public WebMobileBind(String webUid, String webSid, String mPid, String mUid, int mSid, boolean bind, String addr) {
		super();
		this.webUid = webUid;
		this.webSid = webSid;
		this.mPid = mPid;
		this.mUid = mUid;
		this.mSid = mSid;
		this.bind = bind;
		this.addr = addr;
		this.time = Times.now();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWebUid() {
		return webUid;
	}

	public void setWebUid(String webUid) {
		this.webUid = webUid;
	}

	public String getWebSid() {
		return webSid;
	}

	public void setWebSid(String webSid) {
		this.webSid = webSid;
	}

	public String getmPid() {
		return mPid;
	}

	public void setmPid(String mPid) {
		this.mPid = mPid;
	}

	public String getmUid() {
		return mUid;
	}

	public void setmUid(String mUid) {
		this.mUid = mUid;
	}

	public int getmSid() {
		return mSid;
	}

	public void setmSid(int mSid) {
		this.mSid = mSid;
	}

	public boolean isBind() {
		return bind;
	}

	public void setBind(boolean bind) {
		this.bind = bind;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

}