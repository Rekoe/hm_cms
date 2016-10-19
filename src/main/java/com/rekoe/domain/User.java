package com.rekoe.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.ColType;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Index;
import org.nutz.dao.entity.annotation.ManyMany;
import org.nutz.dao.entity.annotation.Table;
import org.nutz.dao.entity.annotation.TableIndexes;

@Table("system_user")
@TableIndexes({ @Index(name = "user_name", fields = { "name" }, unique = true) })
public class User implements Serializable {

	private static final long serialVersionUID = -965829144356813385L;

	@Id
	private long id;

	@Column
	@ColDefine(type = ColType.VARCHAR, width = 20)
	private String name;

	@Column
	@ColDefine(type = ColType.CHAR, width = 44)
	private String password;

	@Column
	@ColDefine(type = ColType.CHAR, width = 24)
	private String salt;

	@Column("is_locked")
	@ColDefine(type = ColType.BOOLEAN)
	private boolean locked;

	@Column(hump = true)
	@ColDefine(type = ColType.TIMESTAMP)
	private Date createDate;

	@ManyMany(target = Role.class, relation = "system_user_role", from = "userid", to = "roleid")
	private List<Role> roles;

	@Column("is_sys")
	@ColDefine(type = ColType.BOOLEAN)
	private boolean system;

	@ManyMany(target = Site.class, relation = "system_user_site", from = "user_id", to = "site_id", key = "id")
	private Map<Long, Site> sites;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public boolean isSystem() {
		return system;
	}

	public void setSystem(boolean system) {
		this.system = system;
	}

	public Map<Long, Site> getSites() {
		return sites;
	}

	public void setSites(Map<Long, Site> sites) {
		this.sites = sites;
	}
}