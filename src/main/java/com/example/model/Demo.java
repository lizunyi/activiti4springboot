package com.example.model;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "department")
public class Demo {
	@Id
	private long id;
	private String name;

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
}
