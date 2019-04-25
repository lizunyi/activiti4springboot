package com.example.mapper.activiti;

import org.apache.ibatis.annotations.Update;

import com.example.model.activiti.ActRuVariable;

import tk.mybatis.mapper.common.BaseMapper;

public interface ActRuVariableMapper extends BaseMapper<ActRuVariable> {
	 @Update("update act_ru_variable set TASK_ID_ = #{param2} where EXECUTION_ID_ = #{param1}")
	 void updateTaskIdByExecutionId(String executionId,String taskId);
}
