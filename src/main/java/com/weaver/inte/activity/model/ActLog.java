package com.weaver.inte.activity.model;

/**
 * @description 
 * @author lzy
 * @date:2020年4月26日 上午11:10:29
 * @version v1.0
 */
public class ActLog {
	private String taskId;// 任务id
	private String taskDefKey;// 节点key
	private String taskDefName;// 节点
	private String assignee;// 审批人
	private String startTime;// 任务到达时间
	private Long duration;// 任务耗时
	private String endTime;// 任务结束时间
	private String taskBranchCondition;// 审批意见
	
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
	public String getTaskDefKey() {
		return taskDefKey;
	}
	public void setTaskDefKey(String taskDefKey) {
		this.taskDefKey = taskDefKey;
	}
	public String getTaskDefName() {
		return taskDefName;
	}
	public void setTaskDefName(String taskDefName) {
		this.taskDefName = taskDefName;
	}
	public String getAssignee() {
		return assignee;
	}
	public void setAssignee(String assignee) {
		this.assignee = assignee;
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
	public String getTaskBranchCondition() {
		return taskBranchCondition;
	}
	public void setTaskBranchCondition(String taskBranchCondition) {
		this.taskBranchCondition = taskBranchCondition;
	}
 
}
