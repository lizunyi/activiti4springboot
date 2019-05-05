/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50723
Source Host           : localhost:3306
Source Database       : act

Target Server Type    : MYSQL
Target Server Version : 50723
File Encoding         : 65001

Date: 2019-05-05 09:32:10
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for act_evt_log
-- ----------------------------
DROP TABLE IF EXISTS `act_evt_log`;
CREATE TABLE `act_evt_log` (
  `LOG_NR_` bigint(20) NOT NULL AUTO_INCREMENT,
  `TYPE_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TIME_STAMP_` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  `USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `DATA_` longblob,
  `LOCK_OWNER_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `LOCK_TIME_` timestamp(3) NULL DEFAULT NULL,
  `IS_PROCESSED_` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`LOG_NR_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for act_ge_bytearray
-- ----------------------------
DROP TABLE IF EXISTS `act_ge_bytearray`;
CREATE TABLE `act_ge_bytearray` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL COMMENT '版本号',
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '部署的文件名称',
  `DEPLOYMENT_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '来自于父表ACT_RE_DEPLOYMENT的主键',
  `BYTES_` longblob COMMENT '大文本类型，存储文本字节流',
  `GENERATED_` tinyint(4) DEFAULT NULL COMMENT '是否是引擎生成',
  PRIMARY KEY (`ID_`),
  KEY `ACT_FK_BYTEARR_DEPL` (`DEPLOYMENT_ID_`),
  CONSTRAINT `ACT_FK_BYTEARR_DEPL` FOREIGN KEY (`DEPLOYMENT_ID_`) REFERENCES `act_re_deployment` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='通用的流程定义和流程资源';

-- ----------------------------
-- Table structure for act_ge_property
-- ----------------------------
DROP TABLE IF EXISTS `act_ge_property`;
CREATE TABLE `act_ge_property` (
  `NAME_` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '属性名称',
  `VALUE_` varchar(300) COLLATE utf8_bin DEFAULT NULL COMMENT '属性值',
  `REV_` int(11) DEFAULT NULL COMMENT '版本号',
  PRIMARY KEY (`NAME_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='系统相关属性';

-- ----------------------------
-- Table structure for act_hi_actinst
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_actinst`;
CREATE TABLE `act_hi_actinst` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '流程定义ID',
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '流程实例ID',
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '流程执行ID',
  `ACT_ID_` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '活动ID',
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '任务ID',
  `CALL_PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '请求流程实例ID',
  `ACT_NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '活动名称',
  `ACT_TYPE_` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '活动类型',
  `ASSIGNEE_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '代理人员',
  `START_TIME_` datetime(3) NOT NULL COMMENT '开始时间',
  `END_TIME_` datetime(3) DEFAULT NULL COMMENT '结束时间',
  `DURATION_` bigint(20) DEFAULT NULL COMMENT '时长，耗时',
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  `DELETE_REASON_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_HI_ACT_INST_START` (`START_TIME_`),
  KEY `ACT_IDX_HI_ACT_INST_END` (`END_TIME_`),
  KEY `ACT_IDX_HI_ACT_INST_PROCINST` (`PROC_INST_ID_`,`ACT_ID_`),
  KEY `ACT_IDX_HI_ACT_INST_EXEC` (`EXECUTION_ID_`,`ACT_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='历史节点表';

-- ----------------------------
-- Table structure for act_hi_attachment
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_attachment`;
CREATE TABLE `act_hi_attachment` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL COMMENT '版本号',
  `USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '用户id',
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '附件名称',
  `DESCRIPTION_` varchar(4000) COLLATE utf8_bin DEFAULT NULL COMMENT '描述',
  `TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '附件类型',
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '节点实例ID',
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '流程实例ID',
  `URL_` varchar(4000) COLLATE utf8_bin DEFAULT NULL COMMENT '附件地址',
  `CONTENT_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '内容Id',
  `TIME_` datetime(3) DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='附件信息';

-- ----------------------------
-- Table structure for act_hi_comment
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_comment`;
CREATE TABLE `act_hi_comment` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '意见记录类型，为comment时，为处理意见',
  `TIME_` datetime(3) NOT NULL COMMENT '记录时间',
  `USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '用户Id',
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '任务Id',
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '流程实例ID',
  `ACTION_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '行为类型。为addcomment时，为处理意见',
  `MESSAGE_` varchar(4000) COLLATE utf8_bin DEFAULT NULL COMMENT '处理意见',
  `FULL_MSG_` longblob COMMENT '全部消息',
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='历史审批意见表';

-- ----------------------------
-- Table structure for act_hi_detail
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_detail`;
CREATE TABLE `act_hi_detail` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '数据类型',
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '流程实例ID',
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '执行实例ID',
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '任务实例ID',
  `ACT_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '活动实例Id,ACT_HI_ACTINST表的ID',
  `NAME_` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '名称',
  `VAR_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '参见VAR_TYPE_类型说明',
  `REV_` int(11) DEFAULT NULL COMMENT '版本',
  `TIME_` datetime(3) NOT NULL COMMENT '创建时间',
  `BYTEARRAY_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '字节数组Id,ACT_GE_BYTEARRAY表的ID',
  `DOUBLE_` double DEFAULT NULL COMMENT '存储变量类型为Double',
  `LONG_` bigint(20) DEFAULT NULL COMMENT '存储变量类型为long',
  `TEXT_` varchar(4000) COLLATE utf8_bin DEFAULT NULL COMMENT '存储变量值类型为String',
  `TEXT2_` varchar(4000) COLLATE utf8_bin DEFAULT NULL COMMENT '此处存储的是JPA持久化对象时，才会有值。此值为对象ID',
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_HI_DETAIL_PROC_INST` (`PROC_INST_ID_`),
  KEY `ACT_IDX_HI_DETAIL_ACT_INST` (`ACT_INST_ID_`),
  KEY `ACT_IDX_HI_DETAIL_TIME` (`TIME_`),
  KEY `ACT_IDX_HI_DETAIL_NAME` (`NAME_`),
  KEY `ACT_IDX_HI_DETAIL_TASK_ID` (`TASK_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='历史详细信息';

-- ----------------------------
-- Table structure for act_hi_identitylink
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_identitylink`;
CREATE TABLE `act_hi_identitylink` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `GROUP_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '用户组ID',
  `TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '用户组类型',
  `USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '用户ID',
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '任务Id',
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '流程实例Id',
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_HI_IDENT_LNK_USER` (`USER_ID_`),
  KEY `ACT_IDX_HI_IDENT_LNK_TASK` (`TASK_ID_`),
  KEY `ACT_IDX_HI_IDENT_LNK_PROCINST` (`PROC_INST_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='历史流程人员表';

-- ----------------------------
-- Table structure for act_hi_procinst
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_procinst`;
CREATE TABLE `act_hi_procinst` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '流程实例ID',
  `BUSINESS_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '业务Key',
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '流程定义Id',
  `START_TIME_` datetime(3) NOT NULL COMMENT '开始时间',
  `END_TIME_` datetime(3) DEFAULT NULL COMMENT '结束时间',
  `DURATION_` bigint(20) DEFAULT NULL COMMENT '时长',
  `START_USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '发起人员Id',
  `START_ACT_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '开始节点',
  `END_ACT_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '结束节点',
  `SUPER_PROCESS_INSTANCE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '超级流程实例Id',
  `DELETE_REASON_` varchar(4000) COLLATE utf8_bin DEFAULT NULL COMMENT '删除理由',
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  UNIQUE KEY `PROC_INST_ID_` (`PROC_INST_ID_`),
  KEY `ACT_IDX_HI_PRO_INST_END` (`END_TIME_`),
  KEY `ACT_IDX_HI_PRO_I_BUSKEY` (`BUSINESS_KEY_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='历史流程实例信息';

-- ----------------------------
-- Table structure for act_hi_taskinst
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_taskinst`;
CREATE TABLE `act_hi_taskinst` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '流程定义ID',
  `TASK_DEF_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '节点定义ID',
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '流程实例ID',
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '执行实例ID',
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '名称',
  `PARENT_TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '父节点实例ID',
  `DESCRIPTION_` varchar(4000) COLLATE utf8_bin DEFAULT NULL COMMENT '描述',
  `OWNER_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '实际签收人 任务的拥有者',
  `ASSIGNEE_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '代理人',
  `START_TIME_` datetime(3) NOT NULL COMMENT '开始时间',
  `CLAIM_TIME_` datetime(3) DEFAULT NULL COMMENT '提醒时间',
  `END_TIME_` datetime(3) DEFAULT NULL COMMENT '结束时间',
  `DURATION_` bigint(20) DEFAULT NULL COMMENT '时长,',
  `DELETE_REASON_` varchar(4000) COLLATE utf8_bin DEFAULT NULL COMMENT '删除理由',
  `PRIORITY_` int(11) DEFAULT NULL COMMENT '优先级',
  `DUE_DATE_` datetime(3) DEFAULT NULL COMMENT '应完成时间',
  `FORM_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '表单key',
  `CATEGORY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_HI_TASK_INST_PROCINST` (`PROC_INST_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='历史任务流程实例信息';

-- ----------------------------
-- Table structure for act_hi_varinst
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_varinst`;
CREATE TABLE `act_hi_varinst` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '流程实例ID',
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '执行实例ID',
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '任务实例ID',
  `NAME_` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '名称',
  `VAR_TYPE_` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '参见VAR_TYPE_类型说明',
  `REV_` int(11) DEFAULT NULL COMMENT 'Version,版本',
  `BYTEARRAY_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT 'ACT_GE_BYTEARRAY表的主键',
  `DOUBLE_` double DEFAULT NULL COMMENT '存储DoubleType类型的数据',
  `LONG_` bigint(20) DEFAULT NULL COMMENT '存储LongType类型的数据',
  `TEXT_` varchar(4000) COLLATE utf8_bin DEFAULT NULL COMMENT '存储变量值类型为String，如此处存储持久化对象时，值jpa对象的class',
  `TEXT2_` varchar(4000) COLLATE utf8_bin DEFAULT NULL COMMENT '此处存储的是JPA持久化对象时，才会有值。此值为对象ID',
  `CREATE_TIME_` datetime(3) DEFAULT NULL,
  `LAST_UPDATED_TIME_` datetime(3) DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_HI_PROCVAR_PROC_INST` (`PROC_INST_ID_`),
  KEY `ACT_IDX_HI_PROCVAR_NAME_TYPE` (`NAME_`,`VAR_TYPE_`),
  KEY `ACT_IDX_HI_PROCVAR_TASK_ID` (`TASK_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='历史变量信息';

-- ----------------------------
-- Table structure for act_id_group
-- ----------------------------
DROP TABLE IF EXISTS `act_id_group`;
CREATE TABLE `act_id_group` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL COMMENT '版本号',
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '用户组描述信息',
  `TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '用户组类型',
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户组表';

-- ----------------------------
-- Table structure for act_id_info
-- ----------------------------
DROP TABLE IF EXISTS `act_id_info`;
CREATE TABLE `act_id_info` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL COMMENT '版本号',
  `USER_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '用户ID',
  `TYPE_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '类型',
  `KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT 'formINPut名称',
  `VALUE_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '值',
  `PASSWORD_` longblob COMMENT '密码',
  `PARENT_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '父节点',
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户扩展信息表';

-- ----------------------------
-- Table structure for act_id_membership
-- ----------------------------
DROP TABLE IF EXISTS `act_id_membership`;
CREATE TABLE `act_id_membership` (
  `USER_ID_` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '用户Id',
  `GROUP_ID_` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '用户组Id',
  PRIMARY KEY (`USER_ID_`,`GROUP_ID_`),
  KEY `ACT_FK_MEMB_GROUP` (`GROUP_ID_`),
  CONSTRAINT `ACT_FK_MEMB_GROUP` FOREIGN KEY (`GROUP_ID_`) REFERENCES `act_id_group` (`ID_`),
  CONSTRAINT `ACT_FK_MEMB_USER` FOREIGN KEY (`USER_ID_`) REFERENCES `act_id_user` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户用户组关联表';

-- ----------------------------
-- Table structure for act_id_user
-- ----------------------------
DROP TABLE IF EXISTS `act_id_user`;
CREATE TABLE `act_id_user` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL COMMENT '版本号',
  `FIRST_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '	',
  `LAST_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '用户姓氏',
  `EMAIL_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '邮箱',
  `PWD_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '密码',
  `PICTURE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '头像Id',
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户信息表';

-- ----------------------------
-- Table structure for act_procdef_info
-- ----------------------------
DROP TABLE IF EXISTS `act_procdef_info`;
CREATE TABLE `act_procdef_info` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `INFO_JSON_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  UNIQUE KEY `ACT_UNIQ_INFO_PROCDEF` (`PROC_DEF_ID_`),
  KEY `ACT_IDX_INFO_PROCDEF` (`PROC_DEF_ID_`),
  KEY `ACT_FK_INFO_JSON_BA` (`INFO_JSON_ID_`),
  CONSTRAINT `ACT_FK_INFO_JSON_BA` FOREIGN KEY (`INFO_JSON_ID_`) REFERENCES `act_ge_bytearray` (`ID_`),
  CONSTRAINT `ACT_FK_INFO_PROCDEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for act_re_deployment
-- ----------------------------
DROP TABLE IF EXISTS `act_re_deployment`;
CREATE TABLE `act_re_deployment` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '部署包的名称',
  `CATEGORY_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '类型',
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  `DEPLOY_TIME_` timestamp(3) NULL DEFAULT NULL COMMENT '部署时间',
  `ENGINE_VERSION_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='部署信息表';

-- ----------------------------
-- Table structure for act_re_model
-- ----------------------------
DROP TABLE IF EXISTS `act_re_model`;
CREATE TABLE `act_re_model` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL COMMENT '数据更新次数',
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '模型的名称：比如：收文管理',
  `KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '模型的关键字，流程引擎用到。',
  `CATEGORY_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '类型，用户自己对流程模型的分类。',
  `CREATE_TIME_` timestamp(3) NULL DEFAULT NULL COMMENT '创建时间',
  `LAST_UPDATE_TIME_` timestamp(3) NULL DEFAULT NULL COMMENT '最后修改时间',
  `VERSION_` int(11) DEFAULT NULL COMMENT '版本，从1开始',
  `META_INFO_` varchar(4000) COLLATE utf8_bin DEFAULT NULL COMMENT '以json格式保存流程定义的信息;数据源信息，比如:{"name":"FTOA_SWGL","revision":1,"description":"丰台财政局OA，收文管理流程"}',
  `DEPLOYMENT_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '部署ID',
  `EDITOR_SOURCE_VALUE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '编辑源值ID,是 ACT_GE_BYTEARRAY 表中的ID_值。',
  `EDITOR_SOURCE_EXTRA_VALUE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '编辑源额外值ID（外键ACT_GE_BYTEARRAY ）',
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`),
  KEY `ACT_FK_MODEL_SOURCE` (`EDITOR_SOURCE_VALUE_ID_`),
  KEY `ACT_FK_MODEL_SOURCE_EXTRA` (`EDITOR_SOURCE_EXTRA_VALUE_ID_`),
  KEY `ACT_FK_MODEL_DEPLOYMENT` (`DEPLOYMENT_ID_`),
  CONSTRAINT `ACT_FK_MODEL_DEPLOYMENT` FOREIGN KEY (`DEPLOYMENT_ID_`) REFERENCES `act_re_deployment` (`ID_`),
  CONSTRAINT `ACT_FK_MODEL_SOURCE` FOREIGN KEY (`EDITOR_SOURCE_VALUE_ID_`) REFERENCES `act_ge_bytearray` (`ID_`),
  CONSTRAINT `ACT_FK_MODEL_SOURCE_EXTRA` FOREIGN KEY (`EDITOR_SOURCE_EXTRA_VALUE_ID_`) REFERENCES `act_ge_bytearray` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='流程设计模型表';

-- ----------------------------
-- Table structure for act_re_procdef
-- ----------------------------
DROP TABLE IF EXISTS `act_re_procdef`;
CREATE TABLE `act_re_procdef` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL COMMENT '版本号',
  `CATEGORY_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '流程命名空间（该编号就是流程文件targetNamespace的属性值）',
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '流程名称（该编号就是流程文件process元素的name属性值）',
  `KEY_` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '流程编号（该编号就是流程文件process元素的id属性值',
  `VERSION_` int(11) NOT NULL COMMENT '流程版本号（由程序控制，新增即为1，修改后依次加1来完成的）',
  `DEPLOYMENT_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '部署编号',
  `RESOURCE_NAME_` varchar(4000) COLLATE utf8_bin DEFAULT NULL COMMENT '资源文件名称',
  `DGRM_RESOURCE_NAME_` varchar(4000) COLLATE utf8_bin DEFAULT NULL COMMENT '图片资源文件名称',
  `DESCRIPTION_` varchar(4000) COLLATE utf8_bin DEFAULT NULL COMMENT '描述信息',
  `HAS_START_FORM_KEY_` tinyint(4) DEFAULT NULL COMMENT '是否从key启动;start节点是否存在formKey,0否  1是',
  `HAS_GRAPHICAL_NOTATION_` tinyint(4) DEFAULT NULL,
  `SUSPENSION_STATE_` int(11) DEFAULT NULL COMMENT '是否挂起1激活 2挂起',
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  `ENGINE_VERSION_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  UNIQUE KEY `ACT_UNIQ_PROCDEF` (`KEY_`,`VERSION_`,`TENANT_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='流程定义：解析表';

-- ----------------------------
-- Table structure for act_ru_deadletter_job
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_deadletter_job`;
CREATE TABLE `act_ru_deadletter_job` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `EXCLUSIVE_` tinyint(1) DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROCESS_INSTANCE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EXCEPTION_STACK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EXCEPTION_MSG_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `DUEDATE_` timestamp(3) NULL DEFAULT NULL,
  `REPEAT_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `HANDLER_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `HANDLER_CFG_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`),
  KEY `ACT_FK_DEADLETTER_JOB_EXECUTION` (`EXECUTION_ID_`),
  KEY `ACT_FK_DEADLETTER_JOB_PROCESS_INSTANCE` (`PROCESS_INSTANCE_ID_`),
  KEY `ACT_FK_DEADLETTER_JOB_PROC_DEF` (`PROC_DEF_ID_`),
  KEY `ACT_FK_DEADLETTER_JOB_EXCEPTION` (`EXCEPTION_STACK_ID_`),
  CONSTRAINT `ACT_FK_DEADLETTER_JOB_EXCEPTION` FOREIGN KEY (`EXCEPTION_STACK_ID_`) REFERENCES `act_ge_bytearray` (`ID_`),
  CONSTRAINT `ACT_FK_DEADLETTER_JOB_EXECUTION` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `act_ru_execution` (`ID_`),
  CONSTRAINT `ACT_FK_DEADLETTER_JOB_PROCESS_INSTANCE` FOREIGN KEY (`PROCESS_INSTANCE_ID_`) REFERENCES `act_ru_execution` (`ID_`),
  CONSTRAINT `ACT_FK_DEADLETTER_JOB_PROC_DEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for act_ru_event_subscr
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_event_subscr`;
CREATE TABLE `act_ru_event_subscr` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL COMMENT '版本号',
  `EVENT_TYPE_` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '事件类型',
  `EVENT_NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '事件名称',
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '流程执行ID',
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '流程实例ID',
  `ACTIVITY_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '活动ID',
  `CONFIGURATION_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '配置信息',
  `CREATED_` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '流程定义id',
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_EVENT_SUBSCR_CONFIG_` (`CONFIGURATION_`),
  KEY `ACT_FK_EVENT_EXEC` (`EXECUTION_ID_`),
  CONSTRAINT `ACT_FK_EVENT_EXEC` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `act_ru_execution` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='运行时事件';

-- ----------------------------
-- Table structure for act_ru_execution
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_execution`;
CREATE TABLE `act_ru_execution` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL COMMENT '版本号',
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '流程实例id',
  `BUSINESS_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '业务编号',
  `PARENT_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '父执行流程',
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '流程定义Id',
  `SUPER_EXEC_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ACT_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '实例id',
  `IS_ACTIVE_` tinyint(4) DEFAULT NULL COMMENT '激活状态',
  `IS_CONCURRENT_` tinyint(4) DEFAULT NULL COMMENT '并发状态',
  `IS_SCOPE_` tinyint(4) DEFAULT NULL,
  `IS_EVENT_SCOPE_` tinyint(4) DEFAULT NULL,
  `SUSPENSION_STATE_` int(11) DEFAULT NULL COMMENT '挂起状态   1激活 2挂起',
  `CACHED_ENT_STATE_` int(11) DEFAULT NULL COMMENT '缓存结束状态_',
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `LOCK_TIME_` timestamp(3) NULL DEFAULT NULL,
  `ROOT_PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `IS_MI_ROOT_` tinyint(4) DEFAULT NULL,
  `START_TIME_` datetime(3) DEFAULT NULL,
  `START_USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `IS_COUNT_ENABLED_` tinyint(4) DEFAULT NULL,
  `EVT_SUBSCR_COUNT_` int(11) DEFAULT NULL,
  `TASK_COUNT_` int(11) DEFAULT NULL,
  `JOB_COUNT_` int(11) DEFAULT NULL,
  `TIMER_JOB_COUNT_` int(11) DEFAULT NULL,
  `SUSP_JOB_COUNT_` int(11) DEFAULT NULL,
  `DEADLETTER_JOB_COUNT_` int(11) DEFAULT NULL,
  `VAR_COUNT_` int(11) DEFAULT NULL,
  `ID_LINK_COUNT_` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_EXEC_BUSKEY` (`BUSINESS_KEY_`),
  KEY `ACT_FK_EXE_PROCINST` (`PROC_INST_ID_`),
  KEY `ACT_FK_EXE_PROCDEF` (`PROC_DEF_ID_`),
  KEY `ACT_IDX_EXEC_ROOT` (`ROOT_PROC_INST_ID_`),
  KEY `ACT_FK_EXE_PARENT` (`PARENT_ID_`),
  KEY `ACT_FK_EXE_SUPER` (`SUPER_EXEC_`),
  CONSTRAINT `ACT_FK_EXE_PARENT` FOREIGN KEY (`PARENT_ID_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE CASCADE,
  CONSTRAINT `ACT_FK_EXE_PROCDEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`),
  CONSTRAINT `ACT_FK_EXE_PROCINST` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ACT_FK_EXE_SUPER` FOREIGN KEY (`SUPER_EXEC_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='运行时流程执行实例';

-- ----------------------------
-- Table structure for act_ru_identitylink
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_identitylink`;
CREATE TABLE `act_ru_identitylink` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL COMMENT '版本号',
  `GROUP_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '用户组ＩＤ',
  `TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '用户组类型;主要分为以下几种：assignee、candidate、',
  `USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '用户ID',
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '任务Id',
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '流程实例ID',
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '流程定义Id',
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_IDENT_LNK_USER` (`USER_ID_`),
  KEY `ACT_IDX_IDENT_LNK_GROUP` (`GROUP_ID_`),
  KEY `ACT_IDX_ATHRZ_PROCEDEF` (`PROC_DEF_ID_`),
  KEY `ACT_FK_TSKASS_TASK` (`TASK_ID_`),
  KEY `ACT_FK_IDL_PROCINST` (`PROC_INST_ID_`),
  CONSTRAINT `ACT_FK_ATHRZ_PROCEDEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`),
  CONSTRAINT `ACT_FK_IDL_PROCINST` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `act_ru_execution` (`ID_`),
  CONSTRAINT `ACT_FK_TSKASS_TASK` FOREIGN KEY (`TASK_ID_`) REFERENCES `act_ru_task` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='身份联系;主要存储当前节点参与者的信息,任务参与者数据表。';

-- ----------------------------
-- Table structure for act_ru_job
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_job`;
CREATE TABLE `act_ru_job` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL COMMENT '版本号',
  `TYPE_` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '类型',
  `LOCK_EXP_TIME_` timestamp(3) NULL DEFAULT NULL COMMENT '锁定释放时间',
  `LOCK_OWNER_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '挂起者',
  `EXCLUSIVE_` tinyint(1) DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '执行实例ID',
  `PROCESS_INSTANCE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '流程实例ID',
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '流程定义ID',
  `RETRIES_` int(11) DEFAULT NULL,
  `EXCEPTION_STACK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '异常信息ID',
  `EXCEPTION_MSG_` varchar(4000) COLLATE utf8_bin DEFAULT NULL COMMENT '异常信息',
  `DUEDATE_` timestamp(3) NULL DEFAULT NULL COMMENT '到期时间',
  `REPEAT_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '重复',
  `HANDLER_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '处理类型',
  `HANDLER_CFG_` varchar(4000) COLLATE utf8_bin DEFAULT NULL COMMENT '标识',
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`),
  KEY `ACT_FK_JOB_EXCEPTION` (`EXCEPTION_STACK_ID_`),
  KEY `ACT_FK_JOB_EXECUTION` (`EXECUTION_ID_`),
  KEY `ACT_FK_JOB_PROCESS_INSTANCE` (`PROCESS_INSTANCE_ID_`),
  KEY `ACT_FK_JOB_PROC_DEF` (`PROC_DEF_ID_`),
  CONSTRAINT `ACT_FK_JOB_EXCEPTION` FOREIGN KEY (`EXCEPTION_STACK_ID_`) REFERENCES `act_ge_bytearray` (`ID_`),
  CONSTRAINT `ACT_FK_JOB_EXECUTION` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `act_ru_execution` (`ID_`),
  CONSTRAINT `ACT_FK_JOB_PROCESS_INSTANCE` FOREIGN KEY (`PROCESS_INSTANCE_ID_`) REFERENCES `act_ru_execution` (`ID_`),
  CONSTRAINT `ACT_FK_JOB_PROC_DEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='运行中的任务;运行时定时任务数据表';

-- ----------------------------
-- Table structure for act_ru_suspended_job
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_suspended_job`;
CREATE TABLE `act_ru_suspended_job` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `EXCLUSIVE_` tinyint(1) DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROCESS_INSTANCE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `RETRIES_` int(11) DEFAULT NULL,
  `EXCEPTION_STACK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EXCEPTION_MSG_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `DUEDATE_` timestamp(3) NULL DEFAULT NULL,
  `REPEAT_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `HANDLER_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `HANDLER_CFG_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`),
  KEY `ACT_FK_SUSPENDED_JOB_EXECUTION` (`EXECUTION_ID_`),
  KEY `ACT_FK_SUSPENDED_JOB_PROCESS_INSTANCE` (`PROCESS_INSTANCE_ID_`),
  KEY `ACT_FK_SUSPENDED_JOB_PROC_DEF` (`PROC_DEF_ID_`),
  KEY `ACT_FK_SUSPENDED_JOB_EXCEPTION` (`EXCEPTION_STACK_ID_`),
  CONSTRAINT `ACT_FK_SUSPENDED_JOB_EXCEPTION` FOREIGN KEY (`EXCEPTION_STACK_ID_`) REFERENCES `act_ge_bytearray` (`ID_`),
  CONSTRAINT `ACT_FK_SUSPENDED_JOB_EXECUTION` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `act_ru_execution` (`ID_`),
  CONSTRAINT `ACT_FK_SUSPENDED_JOB_PROCESS_INSTANCE` FOREIGN KEY (`PROCESS_INSTANCE_ID_`) REFERENCES `act_ru_execution` (`ID_`),
  CONSTRAINT `ACT_FK_SUSPENDED_JOB_PROC_DEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for act_ru_task
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_task`;
CREATE TABLE `act_ru_task` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL COMMENT '版本号',
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '执行实例ID',
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '流程实例ID',
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '流程定义ID',
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '任务名称',
  `PARENT_TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '父节任务ID',
  `DESCRIPTION_` varchar(4000) COLLATE utf8_bin DEFAULT NULL COMMENT '任务描述',
  `TASK_DEF_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '任务定义key',
  `OWNER_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '所属人',
  `ASSIGNEE_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '代理人员(受让人)',
  `DELEGATION_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '代理团',
  `PRIORITY_` int(11) DEFAULT NULL COMMENT '优先权',
  `CREATE_TIME_` timestamp(3) NULL DEFAULT NULL COMMENT '创建时间',
  `DUE_DATE_` datetime(3) DEFAULT NULL COMMENT '执行时间',
  `CATEGORY_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '暂停状态,1代表激活 2代表挂起',
  `SUSPENSION_STATE_` int(11) DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  `FORM_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CLAIM_TIME_` datetime(3) DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_TASK_CREATE` (`CREATE_TIME_`),
  KEY `ACT_FK_TASK_EXE` (`EXECUTION_ID_`),
  KEY `ACT_FK_TASK_PROCINST` (`PROC_INST_ID_`),
  KEY `ACT_FK_TASK_PROCDEF` (`PROC_DEF_ID_`),
  CONSTRAINT `ACT_FK_TASK_EXE` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `act_ru_execution` (`ID_`),
  CONSTRAINT `ACT_FK_TASK_PROCDEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`),
  CONSTRAINT `ACT_FK_TASK_PROCINST` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `act_ru_execution` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='运行时任务数据表;（执行中实时任务）代办任务查询表';

-- ----------------------------
-- Table structure for act_ru_timer_job
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_timer_job`;
CREATE TABLE `act_ru_timer_job` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `LOCK_EXP_TIME_` timestamp(3) NULL DEFAULT NULL,
  `LOCK_OWNER_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `EXCLUSIVE_` tinyint(1) DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROCESS_INSTANCE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `RETRIES_` int(11) DEFAULT NULL,
  `EXCEPTION_STACK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EXCEPTION_MSG_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `DUEDATE_` timestamp(3) NULL DEFAULT NULL,
  `REPEAT_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `HANDLER_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `HANDLER_CFG_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`),
  KEY `ACT_FK_TIMER_JOB_EXECUTION` (`EXECUTION_ID_`),
  KEY `ACT_FK_TIMER_JOB_PROCESS_INSTANCE` (`PROCESS_INSTANCE_ID_`),
  KEY `ACT_FK_TIMER_JOB_PROC_DEF` (`PROC_DEF_ID_`),
  KEY `ACT_FK_TIMER_JOB_EXCEPTION` (`EXCEPTION_STACK_ID_`),
  CONSTRAINT `ACT_FK_TIMER_JOB_EXCEPTION` FOREIGN KEY (`EXCEPTION_STACK_ID_`) REFERENCES `act_ge_bytearray` (`ID_`),
  CONSTRAINT `ACT_FK_TIMER_JOB_EXECUTION` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `act_ru_execution` (`ID_`),
  CONSTRAINT `ACT_FK_TIMER_JOB_PROCESS_INSTANCE` FOREIGN KEY (`PROCESS_INSTANCE_ID_`) REFERENCES `act_ru_execution` (`ID_`),
  CONSTRAINT `ACT_FK_TIMER_JOB_PROC_DEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for act_ru_variable
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_variable`;
CREATE TABLE `act_ru_variable` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL COMMENT '版本号',
  `TYPE_` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '编码类型参见VAR_TYPE_类型说明',
  `NAME_` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '变量名称',
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '执行实例ID',
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '流程实例Id',
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '任务id',
  `BYTEARRAY_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '字节组ID',
  `DOUBLE_` double DEFAULT NULL COMMENT '存储变量类型为Double',
  `LONG_` bigint(20) DEFAULT NULL COMMENT '存储变量类型为long',
  `TEXT_` varchar(4000) COLLATE utf8_bin DEFAULT NULL COMMENT '存储变量值类型为String',
  `TEXT2_` varchar(4000) COLLATE utf8_bin DEFAULT NULL COMMENT '此处存储的是JPA持久化对象时，才会有值。此值为对象ID',
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_VARIABLE_TASK_ID` (`TASK_ID_`),
  KEY `ACT_FK_VAR_EXE` (`EXECUTION_ID_`),
  KEY `ACT_FK_VAR_PROCINST` (`PROC_INST_ID_`),
  KEY `ACT_FK_VAR_BYTEARRAY` (`BYTEARRAY_ID_`),
  CONSTRAINT `ACT_FK_VAR_BYTEARRAY` FOREIGN KEY (`BYTEARRAY_ID_`) REFERENCES `act_ge_bytearray` (`ID_`),
  CONSTRAINT `ACT_FK_VAR_EXE` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `act_ru_execution` (`ID_`),
  CONSTRAINT `ACT_FK_VAR_PROCINST` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `act_ru_execution` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='运行时流程变量数据表;';

