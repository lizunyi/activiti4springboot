package com.weaver.inte.activity.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import com.weaver.inte.activity.model.ActDoneModel;
import com.weaver.inte.activity.model.ActRuTask;
import com.weaver.inte.activity.sqlprovider.SqlPrivoder;

import tk.mybatis.mapper.common.BaseMapper;

public interface ActDoneMapper extends BaseMapper<ActRuTask> {
	 
	 @SelectProvider(type = SqlPrivoder.class,method = "sql") 
	 public List<ActDoneModel> getDoneList(String sqlTemplete,Map map);
	 
	 @Select({
	 	"<script>",
	 		"select NAME_ AS fieldid,TEXT_ as fieldvalue from act_hi_varinst where PROC_INST_ID_=#{procInstId}",
	 	"<if test=\"taskId != null and taskId != '' \">",
	 		" and TASK_ID_=${taskId}",
	 	"</if>",
	 	"<if test=\"taskId == null or taskId == '' \">",
	 		" and TASK_ID_ is null",
	 	"</if>",
	 	"</script>"
	 })
	 public List<Map> getDoneData(@Param("procInstId") String procInstId,@Param("taskId") String taskId);
}
