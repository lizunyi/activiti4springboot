package com.example.service;

import java.util.List;
import java.util.Map;

import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Task;

public interface WorkFlowService {

	void apply(Map<String, Object> apply) throws Exception;

	void approve(Map<String, Object> apply, String taskid) throws Exception;

	List<Task> queryDeal(String roleName) throws Exception;

	List<HistoricTaskInstance> queryDone(String userId) throws Exception;

	List<HistoricTaskInstance> queryApply(String userId) throws Exception;

}
