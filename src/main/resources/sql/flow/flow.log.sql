select 
a.ID_ as taskId,
TASK_DEF_KEY_ as taskDefKey,
NAME_ as taskDefName,
ASSIGNEE_ as assignee,
START_TIME_ as startTime,
END_TIME_ as endTime,
DURATION_ as duration,
if(CATEGORY_ = '1' ,'同意','驳回') as TASK_BRANCH_CONDITION
from act_hi_taskinst a
where 
a.PROC_INST_ID_ = '${flowInstId}'