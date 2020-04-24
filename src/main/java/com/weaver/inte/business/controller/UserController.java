package com.weaver.inte.business.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.weaver.inte.business.mapper.RoleMapper;
import com.weaver.inte.business.mapper.UserMapper;
import com.weaver.inte.business.model.Role;
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
public class UserController {

	@Resource
	private UserMapper userMapper;
	@Resource
	private RoleMapper roleMapper;

	
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
	public JSONArray queryUsers(HttpServletRequest request, @PathVariable String modelId) {
		try {
			JSONArray array = new JSONArray();
			String[] fields = new String[] { "leave_type", "start_date", "end_date", "remark" };
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
