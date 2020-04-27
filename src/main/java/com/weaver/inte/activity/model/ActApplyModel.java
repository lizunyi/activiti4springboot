package com.weaver.inte.activity.model;

/**
 * @description 
 * @author lzy
 * @date:2020年4月26日 上午11:10:29
 * @version v1.0
 */
public class ActApplyModel {
	private String flowDefId;// 流程定义id
	private String flowName;// 流程名称
	private String flowKey;// 流程key
	private String flowInstId;// 流程实例id
	private String owner;// 任务创建人
	private String startTime;// 流程开始时间
	private Long duration;// 流程耗时
	private String endTime;// 流程结束时间
	private String assignee;// 最后办理人
	private String businessId;// 业务id
	private String flowStatus;// 流程状态
	public String getFlowDefId() {
		return flowDefId;
	}
	public void setFlowDefId(String flowDefId) {
		this.flowDefId = flowDefId;
	}
	public String getFlowName() {
		return flowName;
	}
	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}
	public String getFlowKey() {
		return flowKey;
	}
	public void setFlowKey(String flowKey) {
		this.flowKey = flowKey;
	}
	public String getFlowInstId() {
		return flowInstId;
	}
	public void setFlowInstId(String flowInstId) {
		this.flowInstId = flowInstId;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public Long getDuration() {
		return duration;
	}
	public void setDuration(Long duration) {
		this.duration = duration;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getAssignee() {
		return assignee;
	}
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}
	public String getBusinessId() {
		return businessId;
	}
	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}
	public String getFlowStatus() {
		return flowStatus;
	}
	public void setFlowStatus(String flowStatus) {
		this.flowStatus = flowStatus;
	}
	
	 
	
}
