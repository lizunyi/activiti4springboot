package com.weaver.inte.business.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.weaver.inte.activity.service.WorkFlowService;

/**
 * @description 
 * @author lzy
 * @date:2020年4月24日 下午3:18:34
 * @version v1.0
 */
 @RequestMapping("/leave")
 @Controller
public class LeaveApplyController {

	@Resource
	private WorkFlowService workFlowService;
	
	@ResponseBody
	@RequestMapping("/apply")
	public String apply(HttpServletRequest request, @RequestParam Map<String, Object> apply) {
		try {
//			apply.put("TASK_HANDLE_USER", "1");
			apply.put("TASK_FLOW_TYPE", 0);
			apply.put("BUSINESS_SERVICE_NAME", "business.leave");
			workFlowService.apply(apply);
		} catch (Exception e) {
			e.printStackTrace();
			return "Error";
		}
		return "Ok";
	}

	@ResponseBody
	@RequestMapping("/approve/{taksId}")
	public String approve(HttpServletRequest request, @PathVariable String taksId,
			@RequestParam Map<String, Object> apply) {
		try {
//			apply.put("TASK_HANDLE_USER", "1");
			apply.put("TASK_FLOW_TYPE", 0);
			apply.put("BUSINESS_SERVICE_NAME", "business.leave");
			workFlowService.approve(apply, taksId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Ok";
	}
}
