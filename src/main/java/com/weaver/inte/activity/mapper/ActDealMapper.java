package com.weaver.inte.activity.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import com.weaver.inte.activity.model.ActDealModel;
import com.weaver.inte.activity.model.ActRuTask;
import com.weaver.inte.activity.sqlprovider.SqlPrivoder;

import tk.mybatis.mapper.common.BaseMapper;

public interface ActDealMapper extends BaseMapper<ActRuTask> {
	 
	 @SelectProvider(type = SqlPrivoder.class,method = "sql") 
	 public List<ActDealModel> getDealList(String sqlTemplete,Map map);
	 
	 @Select("select NAME_ AS fieldid,TEXT_ as fieldvalue from act_ru_variable where PROC_INST_ID_=#{procInstId}")
	 public List<Map> getDealData(@Param("procInstId") String procInstId);
}
