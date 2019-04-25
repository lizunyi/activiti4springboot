package com.example.mapper.activiti;

import org.apache.ibatis.annotations.Update;

import com.example.model.activiti.ActHiVarinst;

import tk.mybatis.mapper.common.BaseMapper;

public interface ActHiVarinstMapper extends BaseMapper<ActHiVarinst> {
	 @Update("update act_hi_varinst set TASK_ID_ = #{param2} where EXECUTION_ID_ = #{param1}")
	 void updateTaskIdByExecutionId(String executionId,String taskId);
}
