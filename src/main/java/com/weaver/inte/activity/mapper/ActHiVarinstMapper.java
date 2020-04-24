package com.weaver.inte.activity.mapper;

import org.apache.ibatis.annotations.Update;

import com.weaver.inte.activity.model.ActHiVarinst;

import tk.mybatis.mapper.common.BaseMapper;

public interface ActHiVarinstMapper extends BaseMapper<ActHiVarinst> {
	 @Update("update act_hi_varinst set TASK_ID_ = #{param2} where EXECUTION_ID_ = #{param1}")
	 void updateTaskIdByExecutionId(String executionId,String taskId);
}
