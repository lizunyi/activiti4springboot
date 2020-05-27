package com.weaver.inte.event;

import org.springframework.context.ApplicationEvent;

/**
 * @description 
 * @author lzy
 * @date:2020年5月27日 上午9:27:25
 * @version v1.0
 */
public class DemoEvent extends ApplicationEvent{
 	private String msg;

	public DemoEvent(Object source, String msg) {
		super(source);
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
