package com.weaver.inte.activity.model;

/**
 * @description 
 * @author lzy
 * @date:2020年4月26日 上午11:10:29
 * @version v1.0
 */
public class ActDealModel {
	private String flowDefId;// 流程定义id
	private String flowName;// 流程名称
	private String flowKey;// 流程key
	private String flowInstId;// 流程实例id
	private String taskId;// 任务id 
	private String currentApprove;// 当前审批人/角色
	private String owner;// 任务创建人
	private String createTime;// 任务创建时间
	private String businessKey;// 业务id
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
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getCurrentApprove() {
		return currentApprove;
	}
	public void setCurrentApprove(String currentApprove) {
		this.currentApprove = currentApprove;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getBusinessKey() {
		return businessKey;
	}
	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}
	
	
}
