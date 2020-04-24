package com.weaver.inte.business.controller;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.weaver.inte.activity.service.WorkFlowService;
import com.weaver.inte.business.mapper.RoleMapper;
import com.weaver.inte.business.mapper.UserMapper;
import com.weaver.inte.business.model.User;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/***
 * 流程公共处理入口
 * 
 * @author saps.weaver
 *
 */
@Controller
@RequestMapping("/workflow")
public class WorkFlowController {

	@Resource
	private WorkFlowService workFlowService;
	@Resource
	private UserMapper userMapper;
	@Resource
	private RoleMapper roleMapper;

	@ResponseBody
	@RequestMapping("/query/deal/{userId}")
	public JSONObject queryDeal(HttpServletRequest request, @PathVariable String userId) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// 根据角色+用户
			User user = userMapper.selectByPrimaryKey(userId);
			List<String> roles = userMapper.getRoleNamesByUserId(userId);
			List<Task> tasks = workFlowService.queryDeal(user.getUsername(),roles);
			JSONArray array = new JSONArray();
			if (tasks != null) {
				for (int i = 0; i < tasks.size(); i++) {
					JSONObject o = new JSONObject();
					Task task = tasks.get(i);
					o.put("id", task.getId());
					o.put("apply_user", task.getOwner());
					o.put("create_time", sdf.format(task.getCreateTime()));
					o.put("task_name", task.getName());
					array.add(o);
				}
			}
			JSONObject result = new JSONObject();
			result.put("total", tasks.size());
			result.put("rows", array);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@ResponseBody
	@RequestMapping("/query/done/{userId}")
	public JSONObject queryDone(HttpServletRequest request, @PathVariable String userId) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			List<HistoricTaskInstance> tasks = workFlowService.queryDone(userId);
			JSONArray array = new JSONArray();
			if (tasks != null) {
				for (int i = 0; i < tasks.size(); i++) {
					JSONObject o = new JSONObject();
					HistoricTaskInstance task = tasks.get(i);
					o.put("id", task.getId());
					o.put("apply_user", task.getOwner());
					o.put("handle_user", task.getAssignee());
					o.put("create_time", sdf.format(task.getCreateTime()));
					o.put("task_name", task.getName());
					array.add(o);
				}
			}
			JSONObject result = new JSONObject();
			result.put("total", tasks.size());
			result.put("rows", array);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@ResponseBody
	@RequestMapping("/query/apply/{userId}")
	public JSONObject queryApply(HttpServletRequest request, @PathVariable String userId) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			List<HistoricTaskInstance> tasks = workFlowService.queryApply(userId);
			JSONArray array = new JSONArray();
			if (tasks != null) {
				for (int i = 0; i < tasks.size(); i++) {
					JSONObject o = new JSONObject();
					HistoricTaskInstance task = tasks.get(i);
					o.put("id", task.getId());
					o.put("apply_user", task.getOwner());
					o.put("handle_user", task.getAssignee());
					o.put("create_time", sdf.format(task.getCreateTime()));
					o.put("task_name", task.getName());
					array.add(o);
				}
			}
			JSONObject result = new JSONObject();
			result.put("total", tasks.size());
			result.put("rows", array);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
