package com.example.service;

import java.util.List;
import java.util.Map;

import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Task;

public interface LeavApplyService {
	void add(Map<String,Object> apply) throws Exception ;

	void addApprove(Map<String,Object> apply,String taskid) throws Exception ;

	void addDone(Map<String,Object> apply,String taskid) throws Exception ;

//	void update(Map<String,Object> apply) throws Exception ;
//
//	void updateApprove(Map<String,Object> apply) throws Exception ;
//
//	void updateDone(Map<String,Object> apply) throws Exception ;
//
//	void delete(Map<String,Object> apply) throws Exception ;
//
//	void deleteApprove(Map<String,Object> apply) throws Exception ;
//
//	void deleteDone(Map<String,Object> apply) throws Exception ;
	
	List<Task> queryDeal(String roleName) throws Exception;

	List<HistoricTaskInstance> queryDone(String userId) throws Exception;

	List<HistoricTaskInstance> queryApply(String userId) throws Exception;
	
}
