package com.example.service.business.impl;

import java.util.Map;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.mapper.LeaveApplyMapper;
import com.example.service.business.SaleService;

@Transactional
@Service("sale")
public class SaleServiceImpl implements SaleService {

	@Resource
	public LeaveApplyMapper leaveApplyMapper;

	@Override
	public long add(Map<String, Object> formRecord) throws Exception {
		return 1;
	}

	@Override
	public void addCallback(int TASK_FLOW_TYPE,long instanceId,long id) throws Exception {
		System.out.println("回调");
	}
 

	@Override
	public void complete(Map<String, Object> formRecord) throws Exception {
		System.out.println("完成");
	}
}
