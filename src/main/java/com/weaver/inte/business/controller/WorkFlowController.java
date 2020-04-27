package com.weaver.inte.business.controller;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.repository.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.weaver.inte.activity.model.ActApplyModel;
import com.weaver.inte.activity.model.ActDealModel;
import com.weaver.inte.activity.model.ActDoneModel;
import com.weaver.inte.activity.model.ActLog;
import com.weaver.inte.activity.service.WorkFlowService;
import com.weaver.inte.activity.utils.StringUtils;
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
	@RequestMapping("/query/flow")
	public JSONObject queryFlow(HttpServletRequest request) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			List<Model> models = workFlowService.queryFlow(1,10);
			JSONArray array = new JSONArray();
			if (models != null) {
				for (int i = 0; i < models.size(); i++) {
					JSONObject o = new JSONObject();
					Model t = models.get(i);
					o.put("id",t.getId());
					o.put("flowName",t.getName());
					o.put("flowKey",t.getKey());
					o.put("status", StringUtils.isNull(t.getDeploymentId()) ? 0 : 1);
					o.put("deploymentId", t.getDeploymentId() );
					o.put("createTime",sdf.format(t.getCreateTime()));
					o.put("lastUpdateTime",sdf.format(t.getLastUpdateTime()));
					array.add(o);
				}
			}
			JSONObject result = new JSONObject();
			result.put("code", 0);
			result.put("count", models.size());
			result.put("data", array);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping("/query/deal/{userId}")
	public JSONObject queryDeal(HttpServletRequest request, @PathVariable String userId) {
		try {
			User user = userMapper.selectByPrimaryKey(userId);
			List<String> roles = userMapper.getRoleNamesByUserId(userId);
			List<ActDealModel> list = workFlowService.queryDeal(user.getUsername(),roles);
			JSONObject result = new JSONObject();
			result.put("code", 0);
			result.put("count", list.size());
			result.put("data", list);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	

	@ResponseBody
	@RequestMapping("/query/deal/data")
	public List<Map> queryDealData(HttpServletRequest request) {
		try {
		    String flowInstId = request.getParameter("flowInstId");
			List<Map> tasks = workFlowService.queryDealData(flowInstId);
			return tasks;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@ResponseBody
	@RequestMapping("/query/done/data")
	public List<Map> queryDoneData(HttpServletRequest request) {
		try {
		    String flowInstId = request.getParameter("flowInstId");
		    String taskId = request.getParameter("taskId");
			List<Map> tasks = workFlowService.queryDoneData(flowInstId,taskId);
			return tasks;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping("/query/done/{userId}")
	public JSONObject queryDone(HttpServletRequest request, @PathVariable String userId) {
		try {
			List<ActDoneModel> list = workFlowService.queryDone(userId);
			JSONObject result = new JSONObject();
			result.put("code", 0);
			result.put("count", list.size());
			result.put("data", list); 
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
			List<ActApplyModel> tasks = workFlowService.queryApply(userId);
			JSONObject result = new JSONObject();
			result.put("code", 0);
			result.put("count", tasks.size());
			result.put("data", tasks);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping("/query/log")
	public JSONObject queryLog(HttpServletRequest request,@RequestParam String flowInstId) {
		try {
			List<ActLog> logs = workFlowService.queryLog(flowInstId);
			JSONObject result = new JSONObject();
			result.put("code", 0);
			result.put("count", logs.size());
			result.put("data", logs);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
