package com.rekoe.domain;

import java.io.Serializable;
import java.util.List;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Index;
import org.nutz.dao.entity.annotation.ManyMany;
import org.nutz.dao.entity.annotation.Table;
import org.nutz.dao.entity.annotation.TableIndexes;

@Table("system_site")
@TableIndexes({ @Index(name = "site_id_index", fields = { "siteId" }, unique = true) })
public class Site implements Serializable {

	private static final long serialVersionUID = 4391955362256919755L;

	@Id
	private long id;

	@Column(hump = true)
	private String name;

	@Column(hump = true)
	private String domain;

	@Column(hump = true)
	private int siteId;

	@ManyMany(target = User.class, relation = "system_user_site", from = "site_id", to = "user_id")
	private List<User> users;

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSiteId() {
		return siteId;
	}

	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
}