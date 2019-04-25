package com.example.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mapper.DemoMapper;
import com.example.model.Demo;

import net.sf.json.JSONArray;

@RestController
public class DemoAction {

	@Autowired
	private DemoMapper demoMapper;

	@RequestMapping("/mapper")
	public JSONArray mapper(HttpServletRequest request) {
		List<Demo> demo1 = demoMapper.getByName();
		return JSONArray.fromObject(demo1);
	}
}
