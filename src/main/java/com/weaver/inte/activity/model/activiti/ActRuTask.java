package com.weaver.inte.activity.model.activiti;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/***
 * 2019-04-18 14:03:21.0
 * 
 * @Author Saps.Weaver
 * 
 * @Deprecated 运行时任务数据表;（执行中实时任务）代办任务查询表
 */

@Table(name = "act_ru_task")
public class ActRuTask {
	@Id
	@Column(name = "ID_")
	private String id; // ID_
	@Column(name = "REV_")
	private Integer rev; // 版本号
	@Column(name = "EXECUTION_ID_")
	private String executionId; // 执行实例ID
	@Column(name = "PROC_INST_ID_")
	private String procinstId; // 流程实例ID
	@Column(name = "PROC_DEF_ID_")
	private String procdefId; // 流程定义ID
	@Column(name = "NAME_")
	private String name; // 任务名称
	@Column(name = "PARENT_TASK_ID_")
	private String parenttaskId; // 父节任务ID
	@Column(name = "DESCRIPTION_")
	private String description; // 任务描述
	@Column(name = "TASK_DEF_KEY_")
	private String taskDefKey; // 任务定义key
	@Column(name = "OWNER_")
	private String owner; // 所属人
	@Column(name = "ASSIGNEE_")
	private String assignee; // 代理人员(受让人)
	@Column(name = "DELEGATION_")
	private String delegation; // 代理团
	@Column(name = "PRIORITY_")
	private Integer priority; // 优先权
	@Column(name = "CREATE_TIME_")
	private Date createTime; // 创建时间
	@Column(name = "DUE_DATE_")
	private Date duedate; // 执行时间
	@Column(name = "CATEGORY_")
	private String category; // 暂停状态,1代表激活 2代表挂起
	@Column(name = "SUSPENSION_STATE_")
	private Integer suspensionState; // SUSPENSION_STATE_
	@Column(name = "TENANT_ID_")
	private String tenantId; // TENANT_ID_
	@Column(name = "FORM_KEY_")
	private String formKey; // FORM_KEY_
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
	public String getExecutionId() {
		return executionId;
	}
	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}
	public String getProcinstId() {
		return procinstId;
	}
	public void setProcinstId(String procinstId) {
		this.procinstId = procinstId;
	}
	public String getProcdefId() {
		return procdefId;
	}
	public void setProcdefId(String procdefId) {
		this.procdefId = procdefId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParenttaskId() {
		return parenttaskId;
	}
	public void setParenttaskId(String parenttaskId) {
		this.parenttaskId = parenttaskId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTaskDefKey() {
		return taskDefKey;
	}
	public void setTaskDefKey(String taskDefKey) {
		this.taskDefKey = taskDefKey;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getAssignee() {
		return assignee;
	}
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}
	public String getDelegation() {
		return delegation;
	}
	public void setDelegation(String delegation) {
		this.delegation = delegation;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getDuedate() {
		return duedate;
	}
	public void setDuedate(Date duedate) {
		this.duedate = duedate;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Integer getSuspensionState() {
		return suspensionState;
	}
	public void setSuspensionState(Integer suspensionState) {
		this.suspensionState = suspensionState;
	}
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public String getFormKey() {
		return formKey;
	}
	public void setFormKey(String formKey) {
		this.formKey = formKey;
	}

}