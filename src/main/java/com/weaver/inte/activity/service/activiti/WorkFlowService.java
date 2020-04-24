package com.weaver.inte.activity.service.activiti;

import java.util.List;
import java.util.Map;

import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Task;

/***
 * 工作流Service
 * :申请
 * :审核
 * :查询待办
 * :查询已办
 * :查询已申请
 * @author saps.weaver
 *
 */
public interface WorkFlowService {

	void apply(Map<String, Object> apply) throws Exception;

	void approve(Map<String, Object> apply, String taskid) throws Exception;

	List<Task> queryDeal(String roleName) throws Exception;

	List<HistoricTaskInstance> queryDone(String userId) throws Exception;

	List<HistoricTaskInstance> queryApply(String userId) throws Exception;

	void syncActRuVariable(String executionId, Map map) throws Exception;

}
