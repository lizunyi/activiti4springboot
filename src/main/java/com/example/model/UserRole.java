package com.example.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/***
 * 2019-04-18 12:56:35.0
 * 
 * @Author Saps.Weaver
 * 
 * @Deprecated
 */

@Table(name = "user_role")
public class UserRole {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "urid")
	private Integer urid; // urid
	@Column(name = "userid")
	private Integer userid; // userid
	@Column(name = "roleid")
	private Integer roleid; // roleid

	public Integer getUrid() {
		return urid;
	}

	public void setUrid(Integer $urid) {
		this.urid = $urid;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer $userid) {
		this.userid = $userid;
	}

	public Integer getRoleid() {
		return roleid;
	}

	public void setRoleid(Integer $roleid) {
		this.roleid = $roleid;
	}
}
