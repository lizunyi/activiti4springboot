package com.example.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Service;

import com.example.mapper.UserMapper;
import com.example.model.User;
import com.example.service.BusinessService;
import com.example.service.WorkFlowService;
import com.example.utils.SpringUtils;
import com.example.utils.StringUtils;

import tk.mybatis.mapper.util.StringUtil;

@Transactional
@Service
public class WorkFlowServiceImpl implements WorkFlowService {

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

	/***
	 * 流程开始
	 */
	@Override
	public void apply(Map<String, Object> map) throws Exception {
		String TASK_FORM_KEY = StringUtils.ifNull(map.get("TASK_FORM_KEY"));
		if (StringUtils.isNull(TASK_FORM_KEY)) {
			throw new Exception("TASK_FORM_KEY不能为空!");
		}
		BusinessService businessService = get(TASK_FORM_KEY);

		int TASK_FLOW_TYPE = StringUtils.ifIntNull(map.get("TASK_FLOW_TYPE"), -1);
		if (-1 == TASK_FLOW_TYPE) {
			throw new Exception("业务类型不能为空!");
		}
		String TASK_HANDLE_USER = map.get("TASK_HANDLE_USER").toString();
		// 1.添加内置属性
//		map.put("TASK_FLOW_TYPE", 0);//流程操作类型,0:添加,1:修改,2:删除
		map.put("TASK_HANDLE_USER", TASK_HANDLE_USER);// 处理人
		map.put("TASK_BRANCH_CONDITION", "1");// 默认,同意
		map.put("TASK_GLOBAL_CONDITION", "1");// 全局变量,是否同意
		map.put("TASK_GLOBAL_CREATE_USER", TASK_HANDLE_USER);// 全局变量,创建人
		// 2.添加业务
		long businessId = 0;
		if (0 == TASK_FLOW_TYPE) {
			businessId = businessService.add(map);
		} else {
			String id = StringUtils.ifNull(map.get("id"));
			if (StringUtil.isEmpty(id)) {
				throw new Exception("业务id不能为空!");
			}
		}
		// 3.开始任务,并生成流程实例id
		ProcessInstance instance = runtimeservice.startProcessInstanceByKey("myWorkFlow", String.valueOf(businessId),
				map);
		// 4.同步业务的流程实例信息与状态
		businessService.addCallback(TASK_FLOW_TYPE, Long.parseLong(instance.getId()), businessId);
		// 5.生成新的任务之后,需要同步任务的属性
		Task task = taskservice.createTaskQuery().processInstanceId(instance.getId()).singleResult();
		task.setOwner(TASK_HANDLE_USER);
		taskservice.saveTask(task);
	}

	/***
	 * 流程审批中
	 */
	@Override
	public void approve(Map<String, Object> map, String taskId) throws Exception {
		String TASK_FORM_KEY = StringUtils.ifNull(map.get("TASK_FORM_KEY"));
		if (StringUtils.isNull(TASK_FORM_KEY)) {
			throw new Exception("TASK_FORM_KEY不能为空!");
		}
		BusinessService businessService = get(TASK_FORM_KEY);
		// 1.获取到上一个节点任务信息
		Task task = taskservice.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		String oldOwner = task.getOwner();
		task.setAssignee(StringUtils.ifNull(map.get("TASK_HANDLE_USER")));
		taskservice.saveTask(task);
		// 2.提交任务并生成新的任务
		taskservice.setVariable(taskId, "TASK_GLOBAL_CONDITION",StringUtils.ifNull(map.get("TASK_BRANCH_CONDITION"), "1"));
		taskservice.setVariable(taskId, "TASK_GLOBAL_FORM_KEY",StringUtils.ifNull(map.get("TASK_FORM_KEY")));
		taskservice.setVariablesLocal(taskId, map);
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
}
