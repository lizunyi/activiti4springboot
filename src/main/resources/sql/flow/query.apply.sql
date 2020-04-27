select  
c.PROC_DEF_ID_ as flowDefId,
b.NAME_ as flowName,
b.KEY_ as flowKey,
c.PROC_INST_ID_ as flowInstId,
a.OWNER_ as owner,
c.START_TIME_ as startTime,
c.END_TIME_ as endTime,
c.DURATION_ as duration,
a.ASSIGNEE_ as assignee,
c.BUSINESS_KEY_ as businessId,
if(c.END_ACT_ID_ is null,'执行中','已结束') as flowStatus
from act_hi_taskinst a
join (select max(a.id_) id_ from act_hi_taskinst a 
group by a.PROC_INST_ID_) d on a.ID_ = d.ID_
join act_re_procdef b on a.PROC_DEF_ID_ = b.ID_
join act_hi_procinst c on a.PROC_INST_ID_ = c.ID_
where 
a.OWNER_ = '${userId}'