package com.weaver.inte.activity.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.SelectProvider;

import com.weaver.inte.activity.model.ActApplyModel;
import com.weaver.inte.activity.model.ActRuTask;
import com.weaver.inte.activity.sqlprovider.SqlPrivoder;

import tk.mybatis.mapper.common.BaseMapper;

public interface ActApplyMapper extends BaseMapper<ActRuTask> {
	 
	 @SelectProvider(type = SqlPrivoder.class,method = "sql") 
	 public List<ActApplyModel> getApplyList(String sqlTemplete,Map map);
	 
}
