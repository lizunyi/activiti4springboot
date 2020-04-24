package com.weaver.inte.activity.model;

/**
 * @description 
 * @author lzy
 * @date:2020年4月24日 下午2:51:02
 * @version v1.0
 */
public class WorkflowReq {

	String flowHandleUser; //处理人
	
	int flowType; //流程类型,0:添加审批中,1:修改审批中,2:删除审批中,3:完成
	
	String businessServiceName; //业务处理类
	
	int result; //分支审批结果，1:同意,2:驳回

	public String getFlowHandleUser() {
		return flowHandleUser;
	}

	public void setFlowHandleUser(String flowHandleUser) {
		this.flowHandleUser = flowHandleUser;
	}

	public int getFlowType() {
		return flowType;
	}

	public void setFlowType(int flowType) {
		this.flowType = flowType;
	}

	public String getBusinessServiceName() {
		return businessServiceName;
	}

	public void setBusinessServiceName(String businessServiceName) {
		this.businessServiceName = businessServiceName;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}
 
 
}
