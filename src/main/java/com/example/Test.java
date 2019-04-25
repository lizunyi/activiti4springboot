package com.example;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.TaskService;
import org.activiti.engine.impl.javax.el.ExpressionFactory;
import org.activiti.engine.impl.javax.el.ValueExpression;
import org.activiti.engine.impl.juel.ExpressionFactoryImpl;
import org.activiti.engine.impl.juel.SimpleContext;
import org.apache.commons.beanutils.BeanUtils;

import com.example.model.LeaveApply;

public class Test {

	public static Boolean checkFormDataByRuleEl(String el, Map<String, Object> formData) throws Exception {

		ExpressionFactory factory = new ExpressionFactoryImpl();
		SimpleContext context = new SimpleContext();
		for (Object k : formData.keySet()) {
			if (formData.get(k) != null) {
				context.setVariable(k.toString(),
						factory.createValueExpression(formData.get(k), formData.get(k).getClass()));
			}
		}

		ValueExpression e = factory.createValueExpression(context, el, Boolean.class);
		return (Boolean) e.getValue(context);
	}

	public static void main(String[] args) throws Exception {
		String el = "${test== '1'}";
		Map<String, Object> formData = new HashMap<>();
		formData.put("test", 1);
//		System.out.println(checkFormDataByRuleEl(el, formData));

		LeaveApply apply = new LeaveApply();
		apply.setCreateId(2L);
		apply.setStatus(7);
		Map map = new HashMap();
		map.put("id", 3);
		map.put("createId", "5");
		map.put("createIdaa", "5");
		BeanUtils.populate(apply, map);
		System.out.println(apply);
	}

}
