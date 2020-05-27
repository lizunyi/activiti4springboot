package com.weaver.inte.business.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.weaver.inte.business.mapper.LeaveApplyMapper;
import com.weaver.inte.business.service.SaleService;

@Transactional
@Service("business.sale")
public class SaleServiceImpl implements SaleService {

	@Resource
	public LeaveApplyMapper leaveApplyMapper;

	@Override
	public long apply(Map<String, Object> formRecord) throws Exception {
		return 1;	
	}

	@Override
	public void applyCallback(int TASK_FLOW_TYPE,long instanceId,long id) throws Exception {
		System.out.println("回调");
	}
 

	@Override
	public void complete(Map<String, Object> formRecord) throws Exception {
		System.out.println("完成");
	}
}
