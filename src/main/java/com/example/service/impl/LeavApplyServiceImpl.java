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
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mapper.LeaveApplyMapper;
import com.example.mapper.UserMapper;
import com.example.model.LeaveApply;
import com.example.model.User;
import com.example.service.LeavApplyService;
import com.example.utils.StringUtils;

import tk.mybatis.mapper.util.StringUtil;

@Transactional
@Service
public class LeavApplyServiceImpl implements LeavApplyService {

	@Resource
	private LeaveApplyMapper leaveApplyMapper;

	@Resource
	private RuntimeService runtimeservice;
	@Autowired
	private TaskService taskservice;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private UserMapper userMapper;

	/***
	 * 流程开始
	 */
	@Override
	public void add(Map<String, Object> map) throws Exception {
		int TASK_FLOW_TYPE = StringUtils.ifIntNull(map.get("TASK_FLOW_TYPE"), -1);
		if(-1 == TASK_FLOW_TYPE) {
			throw new Exception("业务类型不能为空!");
		}
		String TASK_HANDLE_USER = map.get("TASK_HANDLE_USER").toString();
		// 1.添加内置属性
//		map.put("TASK_FLOW_TYPE", 0);//流程操作类型,0:添加,1:修改,2:删除
		map.put("TASK_HANDLE_USER", TASK_HANDLE_USER);//处理人
		map.put("TASK_BRANCH_CONDITION", "1");//默认,同意
		map.put("TASK_GLOBAL_CONDITION", "1");//全局变量,是否同意
		map.put("TASK_GLOBAL_CREATE_USER", TASK_HANDLE_USER);//全局变量,创建人
		// 2.添加业务
		LeaveApply apply = null;
		if(0 == TASK_FLOW_TYPE) {
			apply = new LeaveApply();
			BeanUtils.populate(apply, map);
			String taskCreateUser = apply.getModifyUserId();
			apply.setCreateId(Long.parseLong(taskCreateUser));
			leaveApplyMapper.insert(apply);
		}else {
			String id = StringUtils.ifNull(map.get("id"));
			if(StringUtil.isEmpty(id)) {
				throw new Exception("业务id不能为空!");
			}
			apply = leaveApplyMapper.selectByPrimaryKey(id);
			if(apply == null) {
				throw new Exception("业务实体不能为空!");
			}
		}
		// 3.开始任务,并生成流程实例id
		ProcessInstance instance = runtimeservice.startProcessInstanceByKey("myWorkFlow", String.valueOf(apply.getId()),map);
		// 4.同步业务的流程实例信息与状态
		apply.setStatus(TASK_FLOW_TYPE);// 0:添加审批中,1:修改审批中,2:删除审批中,3:完成
		apply.setFormrecid(Long.parseLong(instance.getId()));
		leaveApplyMapper.updateByPrimaryKey(apply);
		// 5.生成新的任务之后,需要同步任务的属性
		Task task = taskservice.createTaskQuery().processInstanceId(instance.getId()).singleResult();
		task.setOwner(TASK_HANDLE_USER);
		taskservice.saveTask(task);
	}

	/***
	 * 流程审批中
	 */
	@Override
	public void addApprove(Map<String, Object> map, String taskId) throws Exception {
		// 1.获取到上一个节点任务信息
		Task task = taskservice.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		String oldOwner = task.getOwner();
		task.setAssignee(StringUtils.ifNull(map.get("TASK_HANDLE_USER")));
		taskservice.saveTask(task);
		// 2.提交任务并生成新的任务
		taskservice.setVariable(taskId, "TASK_GLOBAL_CONDITION",StringUtils.ifNull(map.get("TASK_BRANCH_CONDITION"),"1"));
		taskservice.setVariablesLocal(taskId, map);
		taskservice.complete(taskId);
		// 3.查询到最新的任务,并同步任务的属性 && 判断流程是否结束
		task = taskservice.createTaskQuery().processInstanceId(processInstanceId).singleResult();
		if(task!=null) {
			task.setParentTaskId(taskId);
			task.setOwner(oldOwner);
			taskservice.saveTask(task);
		}else {
			addDone(map, taskId);
		}
	}

	/***
	 * 流程结束
	 */
	@Override
	public void addDone(Map<String, Object> map, String taskId) throws Exception {
		int TASK_FLOW_TYPE = StringUtils.ifIntNull(map.get("TASK_FLOW_TYPE"), -1);
		if(-1 == TASK_FLOW_TYPE) {
			throw new Exception("业务类型不能为空!");
		}
		Long id =StringUtils.ifLongNull(map.get("id"));
		if(0 == id) {
			throw new Exception("业务id不能为空!");
		}
		LeaveApply apply = leaveApplyMapper.selectByPrimaryKey(id);
		if(apply == null) {
			throw new Exception("业务实体不能为空!");
		}
		if(2 == TASK_FLOW_TYPE) {
//			apply.setValid(0);//删除
		}else {
			BeanUtils.populate(apply, map);
		}
		apply.setStatus(3);
		leaveApplyMapper.updateByPrimaryKeySelective(apply);
	}

	/***
	 * 查询我的待办任务
	 */
	@Override
	public List<Task> queryDeal(String userId) throws Exception {
		//根据角色+用户
		User user = userMapper.selectByPrimaryKey(userId);
		List<String> roles = userMapper.getRoleNamesByUserId(userId);
		return taskservice.createTaskQuery()
			.or()
				.taskCandidateGroupIn(roles)
				.taskCandidateOrAssigned("USER("+user.getUsername()+")")
			.endOr().list();
	}

	/***
	 * 查询我处理过的任务
	 * TODO: 这个处理存在问题.可解决方案有:
	 * 1.利用原生api查询,自己组织sql
	 * 2.利用 act_hi_taskinst 的 ASSIGNEE_ 查询
	 * 注意:
	 * 利用taskVariableValueEquals 与 taskVariableValueNotEquals 结合的查询存在一定的权限关系.后续再研究
	 */
	@Override
	public List<HistoricTaskInstance> queryDone(String userId) throws Exception {
		return historyService.createHistoricTaskInstanceQuery()
				.taskVariableValueEquals("TASK_HANDLE_USER", userId)
				.taskVariableValueNotEquals("TASK_GLOBAL_CREATE_USER", userId)
				.list();
	}

	/***
	 * 查询我申请的任务
	 * TODO: 这个处理存在问题.可解决方案有:
	 * 1.利用原生api查询,自己组织sql
	 */
	@Override
	public List<HistoricTaskInstance> queryApply(String userId) throws Exception {
		return historyService.createHistoricTaskInstanceQuery()
				.taskOwner(userId)
				.list();
	}
}
