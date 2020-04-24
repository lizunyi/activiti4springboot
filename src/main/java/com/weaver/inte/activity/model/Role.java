package com.weaver.inte.activity.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/***
 * 2019-04-18 12:56:33.0
 * 
 * @Author Saps.Weaver
 * 
 * @Deprecated
 */

@Table(name = "role")
public class Role {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "rid")
	private Integer rid; // rid
	@Column(name = "rolename")
	private String rolename; // rolename

	public Integer getRid() {
		return rid;
	}

	public void setRid(Integer $rid) {
		this.rid = $rid;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String $rolename) {
		this.rolename = $rolename;
	}
}
