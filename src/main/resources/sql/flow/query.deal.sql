select  
a.PROC_DEF_ID_ as flowDefId,
b.NAME_ as flowName,
b.KEY_ as flowKey,
a.PROC_INST_ID_ as flowInstId,
a.ID_ as taskId,
a.NAME_ as currentApprove,
a.OWNER_ as owner,
a.CREATE_TIME_ as createTime,
c.BUSINESS_KEY_ as businessKey
from act_ru_task a
join act_re_procdef b on a.PROC_DEF_ID_ = b.ID_
join act_hi_procinst c on a.PROC_INST_ID_ = c.ID_
where 
a.NAME_ IN ${approveList}