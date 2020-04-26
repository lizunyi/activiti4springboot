select  
a.PROC_DEF_ID_ as flowDefId,
b.NAME_ as flowName,
b.KEY_ as flowKey,
a.PROC_INST_ID_ as flowInstId,
a.ID_ as taskId,
a.ASSIGNEE_ as assignee,
a.OWNER_ as owner,
c.START_TIME_ as createTime,
a.START_TIME_ as taskArriveTime,
a.END_TIME_ as taskEndTime,
a.DURATION_ as duration,
c.BUSINESS_KEY_ as businessKey,
if(c.END_ACT_ID_ is null,'执行中','已结束') as flowStatus
from act_hi_taskinst a
join act_re_procdef b on a.PROC_DEF_ID_ = b.ID_
join act_hi_procinst c on a.PROC_INST_ID_ = c.ID_
where 
a.ASSIGNEE_ = '${userId}'