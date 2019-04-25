package com.example.model.activiti;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/***
 * 2019-04-18 14:01:23.0
 * 
 * @Author Saps.Weaver
 * 
 * @Deprecated 历史变量信息
 */

@Table(name = "act_hi_varinst")
public class ActHiVarinst {
	@Id
	@Column(name = "ID_")
	private String id; // ID_
	@Column(name = "PROC_INST_ID_")
	private String procInstId; // 流程实例ID
	@Column(name = "EXECUTION_ID_")
	private String executionId; // 执行实例ID
	@Column(name = "TASK_ID_")
	private String taskId; // 任务实例ID
	@Column(name = "NAME_")
	private String name; // 名称
	@Column(name = "VAR_TYPE_")
	private String varType; // 参见VAR_TYPE_类型说明
	@Column(name = "REV_")
	private Integer rev; // Version,版本
	@Column(name = "BYTEARRAY_ID_")
	private String bytearrayId; // ACT_GE_BYTEARRAY表的主键
	@Column(name = "DOUBLE_")
	private Double doubleValue; // 存储DoubleType类型的数据
	@Column(name = "LONG_")
	private Long longValue; // 存储LongType类型的数据
	@Column(name = "TEXT_")
	private String text; // 存储变量值类型为String，如此处存储持久化对象时，值jpa对象的class
	@Column(name = "TEXT2_")
	private String text2; // 此处存储的是JPA持久化对象时，才会有值。此值为对象ID
	@Column(name = "CREATE_TIME_")
	private Date createTime; // CREATE_TIME_
	@Column(name = "LAST_UPDATED_TIME_")
	private Date lastUpdatedTime; // LAST_UPDATED_TIME_

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProcInstId() {
		return procInstId;
	}

	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}

	public String getExecutionId() {
		return executionId;
	}

	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVarType() {
		return varType;
	}

	public void setVarType(String varType) {
		this.varType = varType;
	}

	public Integer getRev() {
		return rev;
	}

	public void setRev(Integer rev) {
		this.rev = rev;
	}

	public String getBytearrayId() {
		return bytearrayId;
	}

	public void setBytearrayId(String bytearrayId) {
		this.bytearrayId = bytearrayId;
	}

	public Double getDoubleValue() {
		return doubleValue;
	}

	public void setDoubleValue(Double doubleValue) {
		this.doubleValue = doubleValue;
	}

	public Long getLongValue() {
		return longValue;
	}

	public void setLongValue(Long longValue) {
		this.longValue = longValue;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	public void setLastUpdatedTime(Date lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}

}