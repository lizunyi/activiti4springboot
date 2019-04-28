package com.example.service;

import java.util.Map;

public interface BusinessService {
	
	long add(Map<String, Object> formRecord) throws Exception;

	void addCallback(int TASK_FLOW_TYPE, long instanceId, long id) throws Exception;

	void complete(Map<String, Object> formRecord) throws Exception;

}
