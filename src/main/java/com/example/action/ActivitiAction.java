package com.example.action;

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

import com.example.mapper.DemoMapper;
import com.example.mapper.RoleMapper;
import com.example.mapper.UserMapper;
import com.example.model.Role;
import com.example.model.User;
import com.example.service.LeavApplyService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/leave")
public class ActivitiAction {

	@Resource
	private DemoMapper demoMapper;

	@Resource
	private LeavApplyService leavApplyService;
	@Resource
	private UserMapper userMapper;
	@Resource
	private RoleMapper roleMapper;

	@ResponseBody
	@RequestMapping("/add")
	public String add(HttpServletRequest request, @RequestParam Map<String,Object> apply) {
		try {
			leavApplyService.add(apply);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Ok";
	}

	@ResponseBody
	@RequestMapping("/addApprove/{taksId}")
	public String addApprove(HttpServletRequest request,@PathVariable String taksId, @RequestParam Map<String,Object> apply) {
		try {
			leavApplyService.addApprove(apply,taksId);
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
			List<Task> tasks = leavApplyService.queryDeal(userId);
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
			List<HistoricTaskInstance> tasks = leavApplyService.queryDone(userId);
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
			List<HistoricTaskInstance> tasks = leavApplyService.queryApply(userId);
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
