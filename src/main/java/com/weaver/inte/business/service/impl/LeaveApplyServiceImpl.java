package com.weaver.inte.business.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.weaver.inte.business.mapper.LeaveApplyMapper;
import com.weaver.inte.business.model.LeaveApply;
import com.weaver.inte.business.service.LeaveApplyService;
import com.weaver.inte.utils.StringUtils;

@Transactional
@Service("business.leave")
public class LeaveApplyServiceImpl implements LeaveApplyService {

	@Resource
	public LeaveApplyMapper leaveApplyMapper;

	@Override
	public long apply(Map<String, Object> formRecord) throws Exception {
		LeaveApply apply = new LeaveApply();
		BeanUtils.populate(apply, formRecord);
		long flowHandleUserId = StringUtils.ifLongNull(formRecord.get("flowHandleUserId"));
		apply.setCreateId(flowHandleUserId);
		leaveApplyMapper.insertUseGeneratedKeys(apply);
		return apply.getId();
	}

	@Override
	public void applyCallback(int TASK_FLOW_TYPE, long instanceId, long id) throws Exception {
		LeaveApply apply = leaveApplyMapper.selectByPrimaryKey(id);
		apply.setStatus(TASK_FLOW_TYPE);// 0:添加审批中,1:修改审批中,2:删除审批中,3:完成
		apply.setFormrecid(instanceId);
		leaveApplyMapper.updateByPrimaryKey(apply);
	}

	@Override
	public void complete(Map<String, Object> formRecord) throws Exception {
		int TASK_FLOW_TYPE = StringUtils.ifIntNull(formRecord.get("TASK_FLOW_TYPE"), -1);
		Assert.isTrue(-1 != TASK_FLOW_TYPE, "业务类型不能为空!");

		Long id = StringUtils.ifLongNull(formRecord.get("id"));
		Assert.isTrue(0 != id, "业务id不能为空!");

		LeaveApply apply = leaveApplyMapper.selectByPrimaryKey(id);
		Assert.isTrue(null != apply, "业务实体不能为空!");

		if (2 == TASK_FLOW_TYPE) {
//			apply.setValid(0);//删除
		} else {
			BeanUtils.populate(apply, formRecord);
		}
		apply.setStatus(3);
		leaveApplyMapper.updateByPrimaryKeySelective(apply);
	}
}
