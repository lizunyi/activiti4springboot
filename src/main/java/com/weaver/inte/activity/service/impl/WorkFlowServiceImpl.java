package com.weaver.inte.activity.service.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.persistence.entity.VariableInstance;
import org.activiti.engine.repository.Model;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.image.ProcessDiagramGenerator;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.weaver.inte.activity.enums.BpmsActivityTypeEnum;
import com.weaver.inte.activity.mapper.ActApplyMapper;
import com.weaver.inte.activity.mapper.ActDealMapper;
import com.weaver.inte.activity.mapper.ActDoneMapper;
import com.weaver.inte.activity.mapper.ActLogMapper;
import com.weaver.inte.activity.mapper.ActRuVariableMapper;
import com.weaver.inte.activity.model.ActApplyModel;
import com.weaver.inte.activity.model.ActDealModel;
import com.weaver.inte.activity.model.ActDoneModel;
import com.weaver.inte.activity.model.ActLog;
import com.weaver.inte.activity.service.BusinessService;
import com.weaver.inte.activity.service.WorkFlowService;
import com.weaver.inte.activity.utils.SpringUtils;
import com.weaver.inte.activity.utils.StringUtils;
import com.weaver.inte.activity.utils.UtilMisc;

/***
 * 工作流实现类
 * @author saps.weaver
 *
 */
@Service
public class WorkFlowServiceImpl implements WorkFlowService {

	/***
	 * 根据前端传来的表单key获取具体的表单Service实现
	 * @param formkey 表单/Service 标识
	 * @return
	 * @throws Exception
	 */
	private BusinessService get(String businessServiceName) throws Exception {
		return (BusinessService) SpringUtils.getBean(businessServiceName);
	}

	@Resource
	private RuntimeService runtimeService;
	@Resource
	private HistoryService historyService;
	@Resource
	private ProcessEngine processEngine;
	@Resource
	private TaskService taskService;
	@Resource
	private ActRuVariableMapper actRuVariableMapper;
	@Resource
	private RepositoryService repositoryService;
	@Resource
	private ActDealMapper dealMapper;
	@Resource
	private ActDoneMapper doneMapper;
	@Resource
	private ActLogMapper logMapper;
	@Resource
	private ActApplyMapper applyMapper;

	/***
	 * 申请流程
	 */
	@Override
	@Transactional
	public void apply(Map<String, Object> map) throws Exception {
		String FLOW_KEY = StringUtils.ifNull(map.get("FLOW_KEY"));
		Assert.hasLength(FLOW_KEY, "FLOW_KEY不能为空!");
		
		String BUSINESS_SERVICE_NAME = StringUtils.ifNull(map.get("BUSINESS_SERVICE_NAME"));
		Assert.hasLength(BUSINESS_SERVICE_NAME, "BUSINESS_SERVICE_NAME不能为空!");
		
		BusinessService businessService = get(BUSINESS_SERVICE_NAME);

		int TASK_FLOW_TYPE = StringUtils.ifIntNull(map.get("TASK_FLOW_TYPE"), -1);
		Assert.isTrue(-1 != TASK_FLOW_TYPE,"业务类型不能为空!");
		
		Assert.hasLength(StringUtils.ifNull(map.get("TASK_HANDLE_USER_ID")), "TASK_HANDLE_USER_ID不能为空!");
		String TASK_HANDLE_USER_ID = map.get("TASK_HANDLE_USER_ID").toString();
		Assert.hasLength(StringUtils.ifNull(map.get("TASK_HANDLE_USER_NAME")), "TASK_HANDLE_USER_NAME不能为空!");
		String TASK_HANDLE_USER_NAME = map.get("TASK_HANDLE_USER_NAME").toString();
		// 1.添加内置属性
//		map.put("TASK_FLOW_TYPE", 0);//流程操作类型,0:添加,1:修改,2:删除
		map.put("TASK_HANDLE_USER_ID", TASK_HANDLE_USER_ID);// 处理人
		map.put("TASK_HANDLE_USER_NAME", TASK_HANDLE_USER_NAME);// 处理人
		map.put("TASK_BRANCH_CONDITION", "1");// 默认,同意
		map.put("TASK_GLOBAL_CONDITION", "1");// 全局变量,是否同意
		map.put("TASK_GLOBAL_CREATE_USER_ID", TASK_HANDLE_USER_ID);// 全局变量,创建人
		map.put("TASK_GLOBAL_CREATE_USER_NAME", TASK_HANDLE_USER_NAME);// 全局变量,创建人
//		map.put("TASK_GLOBAL_FORM_KEY", "");// 全局保单key
		// 2.添加业务
		long businessId = 0;
		if (0 == TASK_FLOW_TYPE) {
			businessId = businessService.apply(map);
		} else {
			businessId = StringUtils.ifLongNull(map.get("id"));
			Assert.isTrue(businessId != 0, "业务id不能为空!");
		}
		// 3.开始任务,并生成流程实例id
		ProcessInstance instance = runtimeService.startProcessInstanceByKey(FLOW_KEY, String.valueOf(businessId),map);
		// 4.同步业务的流程实例信息与状态
		businessService.applyCallback(TASK_FLOW_TYPE, Long.parseLong(instance.getId()), businessId);
		// 5.生成新的任务之后,需要同步任务的属性
		Task task = taskService.createTaskQuery().processInstanceId(instance.getId()).singleResult();
		task.setOwner(TASK_HANDLE_USER_ID);
		task.setCategory("1");
		taskService.saveTask(task);
		// 如果下一个审批人，即是当前申请人，则自动审批
		defaultApprove(businessService,task,map);
	}

	private void defaultApprove(BusinessService businessService,Task task,Map<String, Object> map) throws Exception{
		String TASK_HANDLE_USER_ID = map.get("TASK_HANDLE_USER_ID").toString();
		String applyUserName = map.get("TASK_HANDLE_USER_NAME").toString();
		BpmnModel model = repositoryService.getBpmnModel(task.getProcessDefinitionId());
		List<FlowElement> flows = (List<FlowElement>) model.getMainProcess().getFlowElements();
		String parentTaskId = task.getId();
		for (int i = 0; i < flows.size(); i++) {
			FlowElement el = flows.get(i);
			if(el instanceof UserTask && task.getTaskDefinitionKey().contentEquals(el.getId()) && el.getName().contentEquals("USER("+applyUserName + ")")){
				taskService.complete(task.getId());
				task = taskService.createTaskQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
				if (task == null) {
					businessService.complete(map);
				}else{
					task.setParentTaskId(parentTaskId);
					task.setOwner(TASK_HANDLE_USER_ID);
					task.setCategory("1");
					taskService.saveTask(task);
				}
				break;
			}
		}
	}
	
	/***
	 * 审批流程
	 */
	@Override
	@Transactional
	public void approve(Map<String, Object> map, String taskId) throws Exception {
		String BUSINESS_SERVICE_NAME = StringUtils.ifNull(map.get("BUSINESS_SERVICE_NAME"));
		Assert.hasLength(BUSINESS_SERVICE_NAME, "BUSINESS_SERVICE_NAME不能为空!");
		
		int TASK_BRANCH_CONDITION = StringUtils.ifIntNull(map.get("TASK_BRANCH_CONDITION"), 0);
		
		BusinessService businessService = get(BUSINESS_SERVICE_NAME);
		// 1.获取到上一个节点任务信息
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		String oldOwner = task.getOwner();
		task.setAssignee(StringUtils.ifNull(map.get("TASK_HANDLE_USER_ID")));
		task.setCategory(String.valueOf(TASK_BRANCH_CONDITION));
		taskService.saveTask(task);
		// 2.提交任务并生成新的任务
		taskService.setVariable(taskId, "TASK_BRANCH_CONDITION",String.valueOf(TASK_BRANCH_CONDITION));
		taskService.setVariablesLocal(taskId, map);
		// activiti 的bug,理论上act_ru_variable 的数据应该随着每次审批都会修改成最新的,但是activiti目前没有解决,只有通过手动处理
		syncActRuVariable(task.getExecutionId(), map);
		taskService.complete(taskId);
		// 3.查询到最新的任务,并同步任务的属性 && 判断流程是否结束
		task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
		if (task != null) {
			task.setParentTaskId(taskId);
			task.setOwner(oldOwner);
			task.setCategory(String.valueOf(TASK_BRANCH_CONDITION));
			taskService.saveTask(task);
		} else {
			businessService.complete(map);
		}
	}

	/***
	 * 查询我的待办任务
	 */
	@Override
	public List<ActDealModel> queryDeal(String userName,List<String> roles) throws Exception {
		List<String> approveList = new ArrayList();
		approveList.add("USER("+userName+")");
		approveList.addAll(roles);
		Map params = new HashMap();
		params.put("approveList", approveList);
		return dealMapper.getDealList("query.deal",params);
	}

	/***
	 * 查询我处理过的任务
	 */
	@Override
	public List<ActDoneModel> queryDone(String userId) throws Exception {
		Map params = new HashMap();
		params.put("userId", userId);
		return doneMapper.getDoneList("query.done", params);
	}

	/***
	 * 查询我申请的任务
	 */
	@Override
	public List<ActApplyModel> queryApply(String userId) throws Exception {
		Map params = new HashMap();
		params.put("userId", userId);
		return  applyMapper.getApplyList("query.apply", params);
	}

	@Override
	public void syncActRuVariable(String executionId, Map map) throws Exception {
		Map<String, VariableInstance> vars = runtimeService.getVariableInstances(executionId);
		Iterator<String> keyiterator = vars.keySet().iterator();
		while(keyiterator.hasNext()) {
			String key = keyiterator.next();
			VariableInstance ins = vars.get(key);
			String fieldName = ins.getName();
			if(map.containsKey(fieldName)) {
				actRuVariableMapper.updateById(ins.getId(), StringUtils.ifNull(map.get(key)));
			}
		}
	}

	@Override
	public List<Model> queryFlow(int start,int end) {
		return repositoryService.createModelQuery().list();
	}

	@Override
	public List<Map> queryDealData(String flowInstId) throws Exception {
		return dealMapper.getDealData(flowInstId);
	}

	@Override
	public List<Map> queryDoneData(String flowInstId, String taskId) {
		return doneMapper.getDoneData(flowInstId,taskId);
	}

	@Override
	public List<ActLog> queryLog(String flowInstId) throws Exception {
		Map params = new HashMap();
		params.put("flowInstId", flowInstId);
		return logMapper.getLogList("flow.log",params);
	}
	
//	@Override
// 	public InputStream getDiagram(String processInstanceId) {
// 	    //获得流程实例
// 	    ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
// 	    String processDefinitionId = "";
// 	    if (processInstance == null) {
// 	        HistoricProcessInstance processInstanceHistory = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
// 	        processDefinitionId = processInstanceHistory.getProcessDefinitionId();
// 	    } else {
// 	        processDefinitionId = processInstance.getProcessDefinitionId();
// 	    }
// 	    BpmnModel model = repositoryService.getBpmnModel(processDefinitionId);
// 	    List<String> currentActs = null;
//     	if (processInstance == null) {
// 	    	currentActs = historyService
//            	.createHistoricActivityInstanceQuery()
//            	.processInstanceId(processInstanceId)
//            	.list()
//            	.stream()
//            	.map(r-> r.getActivityId()).collect(Collectors.toList());
//     	}else{
// 	    	currentActs = runtimeService.getActiveActivityIds(processInstanceId);
//     	}
// 	    return processEngine.getProcessEngineConfiguration()
// 	            .getProcessDiagramGenerator()
// 	            .generateDiagram(model, "png", currentActs, new ArrayList<String>(), "宋体", "微软雅黑", "黑体", null, 2.0);
// 	}
 	
	@Override
 	public InputStream getDiagramByModelId(String modelId) {
		try {
			byte[] result = repositoryService.getModelEditorSourceExtra(modelId);
			return new ByteArrayInputStream(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
 	public InputStream getDiagram(String processInstanceId) {
        try {
            HistoricProcessInstance historicProcessInstance = historyService
	           	 .createHistoricProcessInstanceQuery()
	           	 .processInstanceId(processInstanceId).singleResult();
            
            List<HistoricActivityInstance> historicActivityInstanceList = historyService
            	.createHistoricActivityInstanceQuery()
            	.processInstanceId(processInstanceId)
            	.orderByHistoricActivityInstanceId().asc().list();
          
            List<String> executedActivityIdList = new ArrayList<String>();
            for (HistoricActivityInstance activityInstance : historicActivityInstanceList) {
                executedActivityIdList.add(activityInstance.getActivityId());
            }
            BpmnModel bpmnModel = repositoryService.getBpmnModel(historicProcessInstance.getProcessDefinitionId());
            List<String> flowIds = this.getExecutedFlows(bpmnModel, historicActivityInstanceList);
            ProcessDiagramGenerator processDiagramGenerator = processEngine.getProcessEngineConfiguration().getProcessDiagramGenerator();
            InputStream imageStream = processDiagramGenerator.generateDiagram(bpmnModel, "png", executedActivityIdList, flowIds, "宋体", "微软雅黑", "黑体", null, 2.0);
            return imageStream;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
	}
	
	private List<String> getExecutedFlows(BpmnModel bpmnModel, List<HistoricActivityInstance> historicActivityInstances) {
        // 流转线ID集合
        List<String> flowIdList = new ArrayList<String>();
        // 全部活动实例
        List<FlowNode> historicFlowNodeList = new LinkedList<FlowNode>();
        // 已完成的历史活动节点
        List<HistoricActivityInstance> finishedActivityInstanceList = new LinkedList<HistoricActivityInstance>();
        for (HistoricActivityInstance historicActivityInstance : historicActivityInstances) {
            historicFlowNodeList.add((FlowNode) bpmnModel.getMainProcess().getFlowElement(historicActivityInstance.getActivityId(), true));
            if (historicActivityInstance.getEndTime() != null) {
                finishedActivityInstanceList.add(historicActivityInstance);
            }
        }
        FlowNode currentFlowNode = null;
        for (HistoricActivityInstance currentActivityInstance : finishedActivityInstanceList) {
            currentFlowNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(currentActivityInstance.getActivityId(), true);
            List<SequenceFlow> sequenceFlowList = currentFlowNode.getOutgoingFlows();
            FlowNode targetFlowNode = null;
            if (BpmsActivityTypeEnum.PARALLEL_GATEWAY.getType().equals(currentActivityInstance.getActivityType())
                    || BpmsActivityTypeEnum.INCLUSIVE_GATEWAY.getType().equals(currentActivityInstance.getActivityType())) {
                // 遍历历史活动节点，找到匹配Flow目标节点的
                for (SequenceFlow sequenceFlow : sequenceFlowList) {
                    targetFlowNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(sequenceFlow.getTargetRef(), true);
                    if (historicFlowNodeList.contains(targetFlowNode)) {
                        flowIdList.add(sequenceFlow.getId());
                    }
                }
            } else {
                List<Map<String, String>> tempMapList = new LinkedList<Map<String,String>>();
                // 遍历历史活动节点，找到匹配Flow目标节点的
                for (SequenceFlow sequenceFlow : sequenceFlowList) {
                    for (HistoricActivityInstance historicActivityInstance : historicActivityInstances) {
                        if (historicActivityInstance.getActivityId().equals(sequenceFlow.getTargetRef())) {
                            tempMapList.add(UtilMisc.toMap("flowId", sequenceFlow.getId(), "activityStartTime", String.valueOf(historicActivityInstance.getStartTime().getTime())));
                        }
                    }		
                }
                // 遍历匹配的集合，取得开始时间最早的一个
                long earliestStamp = 0L;
                String flowId = null;
                for (Map<String, String> map : tempMapList) {
                    long activityStartTime = Long.valueOf(map.get("activityStartTime"));
                    if (earliestStamp == 0 || earliestStamp >= activityStartTime) {
                        earliestStamp = activityStartTime;
                        flowId = map.get("flowId");
                    }
                }
                flowIdList.add(flowId);
            }
        }
        return flowIdList;
    }
}
