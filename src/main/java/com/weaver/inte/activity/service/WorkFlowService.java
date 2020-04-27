package com.weaver.inte.activity.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.activiti.engine.repository.Model;

import com.weaver.inte.activity.model.ActApplyModel;
import com.weaver.inte.activity.model.ActDealModel;
import com.weaver.inte.activity.model.ActDoneModel;
import com.weaver.inte.activity.model.ActLog;

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

	List<ActDealModel> queryDeal(String userName, List<String> roles) throws Exception;
	
	List<ActDoneModel> queryDone(String userId) throws Exception;

	List<ActApplyModel> queryApply(String userId) throws Exception;
	
	List<ActLog> queryLog(String flowInstId) throws Exception;

	void syncActRuVariable(String executionId, Map map) throws Exception;

	List<Model> queryFlow(int start, int end);

	List<Map> queryDealData(String flowInstId) throws Exception;
	
	List<Map> queryDoneData(String flowInstId, String taskId);

	InputStream getDiagram(String processInstanceId);

	InputStream getDiagramByModelId(String deployId);
}
