package com.weaver.inte.activity.service.activiti.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.persistence.entity.VariableInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.weaver.inte.activity.mapper.UserMapper;
import com.weaver.inte.activity.mapper.activiti.ActRuVariableMapper;
import com.weaver.inte.activity.model.User;
import com.weaver.inte.activity.service.activiti.BusinessService;
import com.weaver.inte.activity.service.activiti.WorkFlowService;
import com.weaver.inte.activity.utils.SpringUtils;
import com.weaver.inte.activity.utils.StringUtils;

/***
 * 工作流实现类
 * @author saps.weaver
 *
 */
@Transactional
@Service
public class WorkFlowServiceImpl implements WorkFlowService {

	/***
	 * 根据前端传来的表单key获取具体的表单Service实现
	 * @param formkey 表单/Service 标识
	 * @return
	 * @throws Exception
	 */
	private BusinessService get(String formkey) throws Exception {
		return (BusinessService) SpringUtils.getBean(formkey);
	}

	@Resource
	private RuntimeService runtimeservice;
	@Resource
	private TaskService taskservice;
	@Resource
	private HistoryService historyService;
	@Resource
	private UserMapper userMapper;
	@Resource
	private ActRuVariableMapper actRuVariableMapper;

	/***
	 * 申请流程
	 */
	@Override
	public void apply(Map<String, Object> map) throws Exception {
		String TASK_GLOBAL_FORM_KEY = StringUtils.ifNull(map.get("TASK_GLOBAL_FORM_KEY"));
		Assert.hasLength(TASK_GLOBAL_FORM_KEY, "TASK_GLOBAL_FORM_KEY不能为空!");
		
		BusinessService businessService = get(TASK_GLOBAL_FORM_KEY);

		int TASK_FLOW_TYPE = StringUtils.ifIntNull(map.get("TASK_FLOW_TYPE"), -1);
		Assert.isTrue(-1 != TASK_FLOW_TYPE,"业务类型不能为空!");
		
		Assert.hasLength(StringUtils.ifNull(map.get("TASK_HANDLE_USER")), "办理人不能为空!");
		String TASK_HANDLE_USER = map.get("TASK_HANDLE_USER").toString();
		// 1.添加内置属性
//		map.put("TASK_FLOW_TYPE", 0);//流程操作类型,0:添加,1:修改,2:删除
		map.put("TASK_HANDLE_USER", TASK_HANDLE_USER);// 处理人
		map.put("TASK_BRANCH_CONDITION", "1");// 默认,同意
		map.put("TASK_GLOBAL_CONDITION", "1");// 全局变量,是否同意
		map.put("TASK_GLOBAL_CREATE_USER", TASK_HANDLE_USER);// 全局变量,创建人
//		map.put("TASK_GLOBAL_FORM_KEY", "");// 全局保单key
		// 2.添加业务
		long businessId = 0;
		if (0 == TASK_FLOW_TYPE) {
			businessId = businessService.add(map);
		} else {
			businessId = StringUtils.ifLongNull(map.get("id"));
			Assert.isTrue(businessId != 0, "业务id不能为空!");
		}
		// 3.开始任务,并生成流程实例id
		ProcessInstance instance = runtimeservice.startProcessInstanceByKey("myWorkFlow", String.valueOf(businessId),
				map);
		// 4.同步业务的流程实例信息与状态
		businessService.applyCallback(TASK_FLOW_TYPE, Long.parseLong(instance.getId()), businessId);
		// 5.生成新的任务之后,需要同步任务的属性
		Task task = taskservice.createTaskQuery().processInstanceId(instance.getId()).singleResult();
		task.setOwner(TASK_HANDLE_USER);
		taskservice.saveTask(task);
	}

	/***
	 * 审批流程
	 */
	@Override
	public void approve(Map<String, Object> map, String taskId) throws Exception {
		String TASK_GLOBAL_FORM_KEY = StringUtils.ifNull(map.get("TASK_GLOBAL_FORM_KEY"));
		Assert.hasLength(TASK_GLOBAL_FORM_KEY, "TASK_GLOBAL_FORM_KEY不能为空!");
		
		BusinessService businessService = get(TASK_GLOBAL_FORM_KEY);
		// 1.获取到上一个节点任务信息
		Task task = taskservice.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		String oldOwner = task.getOwner();
		task.setAssignee(StringUtils.ifNull(map.get("TASK_HANDLE_USER")));
		taskservice.saveTask(task);
		// 2.提交任务并生成新的任务
		taskservice.setVariable(taskId, "TASK_GLOBAL_CONDITION",StringUtils.ifNull(map.get("TASK_BRANCH_CONDITION"), "0"));
		taskservice.setVariablesLocal(taskId, map);
		// activiti 的bug,理论上act_ru_variable 的数据应该随着每次审批都会修改成最新的,但是activiti目前没有解决,只有通过手动处理
		syncActRuVariable(task.getExecutionId(), map);
		taskservice.complete(taskId);
		// 3.查询到最新的任务,并同步任务的属性 && 判断流程是否结束
		task = taskservice.createTaskQuery().processInstanceId(processInstanceId).singleResult();
		if (task != null) {
			task.setParentTaskId(taskId);
			task.setOwner(oldOwner);
			taskservice.saveTask(task);
		} else {
			businessService.complete(map);
		}
	}

	/***
	 * 查询我的待办任务
	 */
	@Override
	public List<Task> queryDeal(String userId) throws Exception {
		// 根据角色+用户
		User user = userMapper.selectByPrimaryKey(userId);
		List<String> roles = userMapper.getRoleNamesByUserId(userId);
		return taskservice.createTaskQuery().or().taskCandidateGroupIn(roles)
				.taskCandidateOrAssigned("USER(" + user.getUsername() + ")").endOr().list();
	}

	/***
	 * 查询我处理过的任务
	 */
	@Override
	public List<HistoricTaskInstance> queryDone(String userId) throws Exception {
		return historyService.createHistoricTaskInstanceQuery().taskAssignee(userId).list();
	}

	/***
	 * 查询我申请的任务
	 */
	@Override
	public List<HistoricTaskInstance> queryApply(String userId) throws Exception {
		return historyService.createNativeHistoricTaskInstanceQuery()
				.sql("select * from act_hi_taskinst where OWNER_ = '" + userId + "' and PARENT_TASK_ID_ IS NULL ")
				.list();
	}

	@Override
	public void syncActRuVariable(String executionId, Map map) throws Exception {
		Map<String, VariableInstance> vars = runtimeservice.getVariableInstances(executionId);
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
}
