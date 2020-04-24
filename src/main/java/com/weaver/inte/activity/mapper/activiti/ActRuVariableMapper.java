package com.weaver.inte.activity.mapper.activiti;

import org.apache.ibatis.annotations.Update;

import com.weaver.inte.activity.model.activiti.ActRuVariable;

import tk.mybatis.mapper.common.BaseMapper;

public interface ActRuVariableMapper extends BaseMapper<ActRuVariable> {
	 @Update("update act_ru_variable set TEXT_ = #{param2} where ID_ = #{param1}")
	 void updateById(String id,String value);
}
