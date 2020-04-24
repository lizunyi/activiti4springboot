package com.weaver.inte.activity.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/***
 * 2019-04-18 14:03:39.0
 * 
 * @Author Saps.Weaver
 * 
 * @Deprecated 运行时流程变量数据表;
 */

@Table(name = "act_ru_variable")
public class ActRuVariable {
	@Id
	@Column(name = "ID_")
	private String id; // ID_
	@Column(name = "REV_")
	private Integer rev; // 版本号
	@Column(name = "TYPE_")
	private String type; // 编码类型参见VAR_TYPE_类型说明
	@Column(name = "NAME_")
	private String name; // 变量名称
	@Column(name = "EXECUTION_ID_")
	private String executionId; // 执行实例ID
	@Column(name = "PROC_INST_ID_")
	private String procInstId; // 流程实例Id
	@Column(name = "TASK_ID_")
	private String taskId; // 任务id
	@Column(name = "BYTEARRAY_ID_")
	private String bytearrayId; // 字节组ID
	@Column(name = "DOUBLE_")
	private Double doubleChar; // 存储变量类型为Double
	@Column(name = "LONG_")
	private Long longChar; // 存储变量类型为long
	@Column(name = "TEXT_")
	private String text; // 存储变量值类型为String
	@Column(name = "TEXT2_")
	private String text2; // 此处存储的是JPA持久化对象时，才会有值。此值为对象ID
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getRev() {
		return rev;
	}
	public void setRev(Integer rev) {
		this.rev = rev;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getExecutionId() {
		return executionId;
	}
	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}
	public String getProcInstId() {
		return procInstId;
	}
	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getBytearrayId() {
		return bytearrayId;
	}
	public void setBytearrayId(String bytearrayId) {
		this.bytearrayId = bytearrayId;
	}
	public Double getDoubleChar() {
		return doubleChar;
	}
	public void setDoubleChar(Double doubleChar) {
		this.doubleChar = doubleChar;
	}
	public Long getLongChar() {
		return longChar;
	}
	public void setLongChar(Long longChar) {
		this.longChar = longChar;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getText2() {
		return text2;
	}
	public void setText2(String text2) {
		this.text2 = text2;
	}
	
	
}