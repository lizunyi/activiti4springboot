package com.example.service.activiti;

import java.util.Map;

/***
 * 业务类流程处理接口
 * @author saps.weaver
 *
 */
public interface BusinessService {
	
	/***
	 * 申请流程
	 * @param formRecord 流程表单记录
	 * @return 返回业务实体对象id
	 * @throws Exception
	 */
	long add(Map<String, Object> formRecord) throws Exception;

	/***
	 * 申请流程回调
	 * @param TASK_FLOW_TYPE 流程类型,0:添加审批中,1:修改审批中,2:删除审批中,3:完成
	 * @param instanceId 流程实例id
	 * @param id 业务实体对象id
	 * @throws Exception
	 */
	void applyCallback(int TASK_FLOW_TYPE, long instanceId, long id) throws Exception;

	/***
	 * 流程结束
	 * @param formRecord 流程表单记录
	 * @throws Exception
	 */
	void complete(Map<String, Object> formRecord) throws Exception;

}
