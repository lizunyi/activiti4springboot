package com.weaver.inte.business.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/***
 * 2019-04-18 12:56:34.0
 * 
 * @Author Saps.Weaver
 * 
 * @Deprecated
 */

@Table(name = "user")
public class User {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "uid")
	private Integer uid; // uid
	@Column(name = "username")
	private String username; // username
	@Column(name = "password")
	private String password; // password
	 
	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer $uid) {
		this.uid = $uid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String $username) {
		this.username = $username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String $password) {
		this.password = $password;
	}
}
