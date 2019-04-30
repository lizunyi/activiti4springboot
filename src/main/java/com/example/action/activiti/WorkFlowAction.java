package com.example.action.activiti;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.mapper.RoleMapper;
import com.example.mapper.UserMapper;
import com.example.model.Role;
import com.example.model.User;
import com.example.service.activiti.WorkFlowService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/***
 * 流程公共处理入口
 * @author saps.weaver
 *
 */
@Controller
@RequestMapping("/workflow")
public class WorkFlowAction {

	@Resource
	private WorkFlowService workFlowService;
	@Resource
	private UserMapper userMapper;
	@Resource
	private RoleMapper roleMapper;

	@ResponseBody
	@RequestMapping("/apply")
	public String apply(HttpServletRequest request, @RequestParam Map<String,Object> apply) {
		try {
			workFlowService.apply(apply);
		} catch (Exception e) {
			e.printStackTrace();
			return "Error";
		}
		return "Ok";
	}

	@ResponseBody
	@RequestMapping("/approve/{taksId}")
	public String approve(HttpServletRequest request,@PathVariable String taksId, @RequestParam Map<String,Object> apply) {
		try {
			workFlowService.approve(apply,taksId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Ok";
	}
	
	@ResponseBody
	@RequestMapping("/query/deal/{userId}")
	public JSONObject queryDeal(HttpServletRequest request,@PathVariable String userId) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			List<Task> tasks = workFlowService.queryDeal(userId);
			JSONArray array  = new JSONArray();
			if(tasks !=null) {
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
	public JSONObject queryDone(HttpServletRequest request,@PathVariable String userId) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			List<HistoricTaskInstance> tasks = workFlowService.queryDone(userId);
			JSONArray array  = new JSONArray();
			if(tasks !=null) {
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
	public JSONObject queryApply(HttpServletRequest request,@PathVariable String userId) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			List<HistoricTaskInstance> tasks = workFlowService.queryApply(userId);
			JSONArray array  = new JSONArray();
			if(tasks !=null) {
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
	@RequestMapping("/query/users")
	public List<User> queryUsers(HttpServletRequest request) {
		try {
			return userMapper.selectAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping("/query/properties/{modelId}")
	public JSONArray queryUsers(HttpServletRequest request,@PathVariable String modelId) {
		try {
			JSONArray array = new JSONArray();
			String[] fields = new String[] {"leave_type","start_date","end_date","remark"};
			for (int i = 0; i < fields.length; i++) {
				JSONObject object = new JSONObject();
				object.put("id", fields[i]);
				object.put("name", fields[i]);
				object.put("required", false);
				object.put("readonly", false);
				object.put("hidden", false);
				array.add(object);
			}
			return array;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@ResponseBody
	@RequestMapping("/query/roles")
	public List<Role> queryRoles(HttpServletRequest request) {
		try {
			return roleMapper.selectAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
