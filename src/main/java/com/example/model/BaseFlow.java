package com.example.model;

import javax.persistence.Transient;

public class BaseFlow {
	@Transient
	private  String modifyUserId;
	@Transient
	private  String modifyTime;
	public String getModifyUserId() {
		return modifyUserId;
	}
	public void setModifyUserId(String modifyUserId) {
		this.modifyUserId = modifyUserId;
	}
	public String getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}
	
	
}
