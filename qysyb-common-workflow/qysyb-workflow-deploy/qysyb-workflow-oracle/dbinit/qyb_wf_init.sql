/*
Navicat Oracle Data Transfer
Oracle Client Version : 10.2.0.5.0

Source Server         : qyb_workflow
Source Server Version : 110200
Source Host           : 172.16.81.110:1521
Source Schema         : QYB_WORKFLOW

Target Server Type    : ORACLE
Target Server Version : 110200
File Encoding         : 65001

Date: 2016-07-24 10:45:46
*/


-- ----------------------------
-- Table structure for REL_TAB
-- ----------------------------
DROP TABLE "QYB_WORKFLOW"."REL_TAB";
CREATE TABLE "QYB_WORKFLOW"."REL_TAB" (
"REL_ID" VARCHAR2(50 BYTE) NOT NULL ,
"TAB" VARCHAR2(50 BYTE) NULL ,
"REMARK1" VARCHAR2(50 BYTE) NULL ,
"REMARK2" LONG NULL ,
"BUSINESS_KEY" VARCHAR2(50 BYTE) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;
COMMENT ON TABLE "QYB_WORKFLOW"."REL_TAB" IS '业务与动态tab页关联表';
COMMENT ON COLUMN "QYB_WORKFLOW"."REL_TAB"."REL_ID" IS '主键';
COMMENT ON COLUMN "QYB_WORKFLOW"."REL_TAB"."TAB" IS '动态tab 页签ID';
COMMENT ON COLUMN "QYB_WORKFLOW"."REL_TAB"."BUSINESS_KEY" IS '关联动态tab页的ID';

-- ----------------------------
-- Records of REL_TAB
-- ----------------------------

-- ----------------------------
-- Table structure for SERVICE_AGREEMENT
-- ----------------------------
DROP TABLE "QYB_WORKFLOW"."SERVICE_AGREEMENT";
CREATE TABLE "QYB_WORKFLOW"."SERVICE_AGREEMENT" (
"SERVICE_AGREEMENT_ID" NVARCHAR2(50) NOT NULL ,
"SERVICE_AGREEMENT_NAME" NVARCHAR2(80) NULL ,
"SERVICE_LEVEL_ORG_ID" NVARCHAR2(20) NULL ,
"START_DATE" DATE NULL ,
"END_DATE" DATE NULL ,
"PRI_LEVEL" NVARCHAR2(20) NULL ,
"STATE" NUMBER(4) NULL ,
"RAMARK" NVARCHAR2(500) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;
COMMENT ON TABLE "QYB_WORKFLOW"."SERVICE_AGREEMENT" IS 'SLA���';
COMMENT ON COLUMN "QYB_WORKFLOW"."SERVICE_AGREEMENT"."SERVICE_AGREEMENT_ID" IS 'ID';
COMMENT ON COLUMN "QYB_WORKFLOW"."SERVICE_AGREEMENT"."SERVICE_AGREEMENT_NAME" IS 'NAME';
COMMENT ON COLUMN "QYB_WORKFLOW"."SERVICE_AGREEMENT"."SERVICE_LEVEL_ORG_ID" IS '�������ID';
COMMENT ON COLUMN "QYB_WORKFLOW"."SERVICE_AGREEMENT"."START_DATE" IS '开始时间';
COMMENT ON COLUMN "QYB_WORKFLOW"."SERVICE_AGREEMENT"."END_DATE" IS '结束时间';
COMMENT ON COLUMN "QYB_WORKFLOW"."SERVICE_AGREEMENT"."PRI_LEVEL" IS '优先级';
COMMENT ON COLUMN "QYB_WORKFLOW"."SERVICE_AGREEMENT"."STATE" IS '状态';
COMMENT ON COLUMN "QYB_WORKFLOW"."SERVICE_AGREEMENT"."RAMARK" IS '备注';

-- ----------------------------
-- Records of SERVICE_AGREEMENT
-- ----------------------------

-- ----------------------------
-- Table structure for SERVICE_SERVICEPRODUCT
-- ----------------------------
DROP TABLE "QYB_WORKFLOW"."SERVICE_SERVICEPRODUCT";
CREATE TABLE "QYB_WORKFLOW"."SERVICE_SERVICEPRODUCT" (
"PRODUCT_ID" NVARCHAR2(50) NOT NULL ,
"PRODUCT_NO" NVARCHAR2(50) NULL ,
"PRODUCT_NAME" NVARCHAR2(100) NULL ,
"PRODUCT_DESCRIBE" NVARCHAR2(500) NULL ,
"SERVICE_TYPE_ID" NVARCHAR2(50) NULL ,
"GROUP_ID" NVARCHAR2(50) NULL ,
"WF_PROCESSDEFID" NVARCHAR2(50) NULL ,
"WF_PROCESSSTART_URL" NVARCHAR2(200) NULL ,
"FORM_ID" NVARCHAR2(50) NULL ,
"ICO" NVARCHAR2(200) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;
COMMENT ON COLUMN "QYB_WORKFLOW"."SERVICE_SERVICEPRODUCT"."PRODUCT_NO" IS '产品编码';
COMMENT ON COLUMN "QYB_WORKFLOW"."SERVICE_SERVICEPRODUCT"."PRODUCT_NAME" IS '产品名称';
COMMENT ON COLUMN "QYB_WORKFLOW"."SERVICE_SERVICEPRODUCT"."PRODUCT_DESCRIBE" IS '描述';
COMMENT ON COLUMN "QYB_WORKFLOW"."SERVICE_SERVICEPRODUCT"."SERVICE_TYPE_ID" IS '服务类型';
COMMENT ON COLUMN "QYB_WORKFLOW"."SERVICE_SERVICEPRODUCT"."GROUP_ID" IS '分组';
COMMENT ON COLUMN "QYB_WORKFLOW"."SERVICE_SERVICEPRODUCT"."WF_PROCESSDEFID" IS '关联流程';
COMMENT ON COLUMN "QYB_WORKFLOW"."SERVICE_SERVICEPRODUCT"."WF_PROCESSSTART_URL" IS '流程启动url';
COMMENT ON COLUMN "QYB_WORKFLOW"."SERVICE_SERVICEPRODUCT"."FORM_ID" IS '表单';
COMMENT ON COLUMN "QYB_WORKFLOW"."SERVICE_SERVICEPRODUCT"."ICO" IS '图标';

-- ----------------------------
-- Records of SERVICE_SERVICEPRODUCT
-- ----------------------------
INSERT INTO "QYB_WORKFLOW"."SERVICE_SERVICEPRODUCT" VALUES ('I9HScn2KTuix8jDlLK8aeg', 'HYSQ', '用车申请', null, 'CL', '服务流程', null, null, '4B_QUPJ5TkK6u0l-qnB7vg', '/assets/images/serviceproducticon\event2.png');

-- ----------------------------
-- Table structure for SERVICE_SLA_KPI
-- ----------------------------
DROP TABLE "QYB_WORKFLOW"."SERVICE_SLA_KPI";
CREATE TABLE "QYB_WORKFLOW"."SERVICE_SLA_KPI" (
"KPI_ID" NVARCHAR2(50) NOT NULL ,
"KPI_NO" NVARCHAR2(50) NULL ,
"KPI_NAME" NVARCHAR2(80) NULL ,
"REMARK" NVARCHAR2(500) NULL ,
"DEFAULT_VALUE" NVARCHAR2(100) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;
COMMENT ON TABLE "QYB_WORKFLOW"."SERVICE_SLA_KPI" IS 'KPIָ����Ϣ��';
COMMENT ON COLUMN "QYB_WORKFLOW"."SERVICE_SLA_KPI"."KPI_ID" IS 'ID';
COMMENT ON COLUMN "QYB_WORKFLOW"."SERVICE_SLA_KPI"."KPI_NO" IS 'kpi编码';
COMMENT ON COLUMN "QYB_WORKFLOW"."SERVICE_SLA_KPI"."KPI_NAME" IS 'ָName';
COMMENT ON COLUMN "QYB_WORKFLOW"."SERVICE_SLA_KPI"."REMARK" IS '备注';
COMMENT ON COLUMN "QYB_WORKFLOW"."SERVICE_SLA_KPI"."DEFAULT_VALUE" IS '默认值';

-- ----------------------------
-- Records of SERVICE_SLA_KPI
-- ----------------------------
INSERT INTO "QYB_WORKFLOW"."SERVICE_SLA_KPI" VALUES ('-M6NTVZ6RlmhI9982Dd3gg', 'FWZDSJ', '服务中断时间(分钟)', '服务中断时间', '180');
INSERT INTO "QYB_WORKFLOW"."SERVICE_SLA_KPI" VALUES ('3vk7M9aBRlOTg3gxToZKog', 'FWJJSJ', '服务解决时间（分钟）', '服务解决时间', '120');
INSERT INTO "QYB_WORKFLOW"."SERVICE_SLA_KPI" VALUES ('kqnSPh2XTYCeLOYI87Ym0w', null, '可用性（%）', '可用性范围为1%~100%', '90');
INSERT INTO "QYB_WORKFLOW"."SERVICE_SLA_KPI" VALUES ('lNLchGYpR6i1ge7q_yl1Sw', 'FWXYSJ', '服务响应时间(分钟)', '服务响应时间', '70');
INSERT INTO "QYB_WORKFLOW"."SERVICE_SLA_KPI" VALUES ('m84eYMgERwKc8enazQ7i1A', null, '连续性-服务运行最短持续时间（小时）', '连续性默认为7*24小时', '168');

-- ----------------------------
-- Table structure for SERVICE_SLA_KPI_REL
-- ----------------------------
DROP TABLE "QYB_WORKFLOW"."SERVICE_SLA_KPI_REL";
CREATE TABLE "QYB_WORKFLOW"."SERVICE_SLA_KPI_REL" (
"REL_ID" NVARCHAR2(50) NOT NULL ,
"SERVICE_AGREEMENT_ID" NVARCHAR2(80) NULL ,
"KPI_ID" NVARCHAR2(50) NULL ,
"VLAUE" NVARCHAR2(80) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;
COMMENT ON TABLE "QYB_WORKFLOW"."SERVICE_SLA_KPI_REL" IS 'SLA����KPI����';
COMMENT ON COLUMN "QYB_WORKFLOW"."SERVICE_SLA_KPI_REL"."REL_ID" IS '����ID';
COMMENT ON COLUMN "QYB_WORKFLOW"."SERVICE_SLA_KPI_REL"."SERVICE_AGREEMENT_ID" IS 'SLA ID';
COMMENT ON COLUMN "QYB_WORKFLOW"."SERVICE_SLA_KPI_REL"."KPI_ID" IS 'KPI项ID';
COMMENT ON COLUMN "QYB_WORKFLOW"."SERVICE_SLA_KPI_REL"."VLAUE" IS 'KPI值';

-- ----------------------------
-- Records of SERVICE_SLA_KPI_REL
-- ----------------------------

-- ----------------------------
-- Table structure for SERVICE_SLA_SP_REL
-- ----------------------------
DROP TABLE "QYB_WORKFLOW"."SERVICE_SLA_SP_REL";
CREATE TABLE "QYB_WORKFLOW"."SERVICE_SLA_SP_REL" (
"REL_ID" NVARCHAR2(50) NOT NULL ,
"PRODUCT_ID" NVARCHAR2(50) NULL ,
"SLA_ID" NVARCHAR2(100) NULL ,
"PRODUCT_DESCRIBE" NVARCHAR2(500) NULL ,
"SERVICE_TYPE_ID" NVARCHAR2(50) NULL ,
"GROUP_ID" NVARCHAR2(50) NULL ,
"WF_PROCESSDEFID" NVARCHAR2(50) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;
COMMENT ON COLUMN "QYB_WORKFLOW"."SERVICE_SLA_SP_REL"."PRODUCT_ID" IS '产品ID';
COMMENT ON COLUMN "QYB_WORKFLOW"."SERVICE_SLA_SP_REL"."SLA_ID" IS 'SLA_ID';
COMMENT ON COLUMN "QYB_WORKFLOW"."SERVICE_SLA_SP_REL"."PRODUCT_DESCRIBE" IS '描述';
COMMENT ON COLUMN "QYB_WORKFLOW"."SERVICE_SLA_SP_REL"."SERVICE_TYPE_ID" IS '服务类型';
COMMENT ON COLUMN "QYB_WORKFLOW"."SERVICE_SLA_SP_REL"."GROUP_ID" IS '分组';
COMMENT ON COLUMN "QYB_WORKFLOW"."SERVICE_SLA_SP_REL"."WF_PROCESSDEFID" IS '关联流程';

-- ----------------------------
-- Records of SERVICE_SLA_SP_REL
-- ----------------------------

-- ----------------------------
-- Table structure for SERVICE_TAB
-- ----------------------------
DROP TABLE "QYB_WORKFLOW"."SERVICE_TAB";
CREATE TABLE "QYB_WORKFLOW"."SERVICE_TAB" (
"TITLE" VARCHAR2(100 BYTE) NULL ,
"TAB_ID" VARCHAR2(100 BYTE) NOT NULL ,
"ICON" VARCHAR2(100 BYTE) NULL ,
"URL" VARCHAR2(100 BYTE) NULL ,
"CONTENT" VARCHAR2(500 BYTE) NULL ,
"DESCRIBE" VARCHAR2(200 BYTE) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Records of SERVICE_TAB
-- ----------------------------
INSERT INTO "QYB_WORKFLOW"."SERVICE_TAB" VALUES ('关联资源', 'DjrtPPzCTWGN5VJ5gno9ow', null, 'f/workrelasset', null, null);
INSERT INTO "QYB_WORKFLOW"."SERVICE_TAB" VALUES ('关联知识', 'fJr2agaBR8utAoAwu2LYpw', null, 'f/knowledgerel', null, null);
INSERT INTO "QYB_WORKFLOW"."SERVICE_TAB" VALUES ('关联工单', 'CXJqfHtAT6WdzKoxRnslDQ', null, 'f/relworktab', null, null);
INSERT INTO "QYB_WORKFLOW"."SERVICE_TAB" VALUES ('文档', '8OOADr9bTsupCA1R3dgCIg', null, 'f/opentempandsave', null, '111');
INSERT INTO "QYB_WORKFLOW"."SERVICE_TAB" VALUES ('Word套红', 'EOMgkJ7qS7yAHLKSeVxYRg', null, 'f/taohong', null, null);
INSERT INTO "QYB_WORKFLOW"."SERVICE_TAB" VALUES ('服务承诺', 'kbHLcE-WR_mY-vPWzxCyhA', null, 'f/servicepromisetab', null, null);
INSERT INTO "QYB_WORKFLOW"."SERVICE_TAB" VALUES ('日志信息', 'Bzg2FVypRMuvebFznWGPPg', null, 'f/workloginfo', null, null);
INSERT INTO "QYB_WORKFLOW"."SERVICE_TAB" VALUES ('word电子盖章', 'gDyZrV2yRNCcccrzOpZUDw', null, 'f/insertseal', null, null);
INSERT INTO "QYB_WORKFLOW"."SERVICE_TAB" VALUES ('内容', 'MkF7JpRIQe6uzqj7_4vnYg', null, 'f/ueditortab', null, '通知公告 内容 富文本框');

-- ----------------------------
-- Table structure for SERVICE_UC_SP_REL
-- ----------------------------
DROP TABLE "QYB_WORKFLOW"."SERVICE_UC_SP_REL";
CREATE TABLE "QYB_WORKFLOW"."SERVICE_UC_SP_REL" (
"REL_ID" NVARCHAR2(50) NOT NULL ,
"PRODUCT_ID" NVARCHAR2(50) NULL ,
"UC_ID" NVARCHAR2(100) NULL ,
"REL_DESCRIBE" NVARCHAR2(500) NULL ,
"REL_TYPE_ID" NVARCHAR2(50) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;
COMMENT ON COLUMN "QYB_WORKFLOW"."SERVICE_UC_SP_REL"."PRODUCT_ID" IS '产品ID';
COMMENT ON COLUMN "QYB_WORKFLOW"."SERVICE_UC_SP_REL"."UC_ID" IS 'UC_ID';
COMMENT ON COLUMN "QYB_WORKFLOW"."SERVICE_UC_SP_REL"."REL_DESCRIBE" IS '描述';
COMMENT ON COLUMN "QYB_WORKFLOW"."SERVICE_UC_SP_REL"."REL_TYPE_ID" IS '服务类型';

-- ----------------------------
-- Records of SERVICE_UC_SP_REL
-- ----------------------------

-- ----------------------------
-- Table structure for SERVICE_UPDERPIN_CONTRACT
-- ----------------------------
DROP TABLE "QYB_WORKFLOW"."SERVICE_UPDERPIN_CONTRACT";
CREATE TABLE "QYB_WORKFLOW"."SERVICE_UPDERPIN_CONTRACT" (
"UC_ID" NVARCHAR2(50) NOT NULL ,
"UC_NAME" NVARCHAR2(50) NULL ,
"CONTRACT_NAME" NVARCHAR2(50) NULL ,
"SUPPLIER_NAME" NVARCHAR2(500) NULL ,
"SERVICE_VALID_STARTTIME" DATE NULL ,
"SERVICE_VALID_ENDTIME" DATE NULL ,
"SERVICE_TIME_DAY_OF_WEEK" NVARCHAR2(50) NULL ,
"SERVICE_TIME_TIME_OF_DAY" NVARCHAR2(50) NULL ,
"SERVICE_TYPE" NVARCHAR2(50) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;
COMMENT ON COLUMN "QYB_WORKFLOW"."SERVICE_UPDERPIN_CONTRACT"."UC_ID" IS 'ID';
COMMENT ON COLUMN "QYB_WORKFLOW"."SERVICE_UPDERPIN_CONTRACT"."UC_NAME" IS 'UC名称';
COMMENT ON COLUMN "QYB_WORKFLOW"."SERVICE_UPDERPIN_CONTRACT"."CONTRACT_NAME" IS '合同名称';
COMMENT ON COLUMN "QYB_WORKFLOW"."SERVICE_UPDERPIN_CONTRACT"."SUPPLIER_NAME" IS '供应商名称';
COMMENT ON COLUMN "QYB_WORKFLOW"."SERVICE_UPDERPIN_CONTRACT"."SERVICE_VALID_STARTTIME" IS '服务有效期开始时间';
COMMENT ON COLUMN "QYB_WORKFLOW"."SERVICE_UPDERPIN_CONTRACT"."SERVICE_VALID_ENDTIME" IS '服务有效期结束时间';
COMMENT ON COLUMN "QYB_WORKFLOW"."SERVICE_UPDERPIN_CONTRACT"."SERVICE_TIME_DAY_OF_WEEK" IS '服务时间一周几天';
COMMENT ON COLUMN "QYB_WORKFLOW"."SERVICE_UPDERPIN_CONTRACT"."SERVICE_TIME_TIME_OF_DAY" IS '服务时间一天几小时';
COMMENT ON COLUMN "QYB_WORKFLOW"."SERVICE_UPDERPIN_CONTRACT"."SERVICE_TYPE" IS '方式';

-- ----------------------------
-- Records of SERVICE_UPDERPIN_CONTRACT
-- ----------------------------

-- ----------------------------
-- Table structure for SERVICE_WORK_KLG_REL
-- ----------------------------
DROP TABLE "QYB_WORKFLOW"."SERVICE_WORK_KLG_REL";
CREATE TABLE "QYB_WORKFLOW"."SERVICE_WORK_KLG_REL" (
"REL_ID" NVARCHAR2(50) NOT NULL ,
"WORK_ID" NVARCHAR2(50) NOT NULL ,
"KNOWLEDGE_ID" NVARCHAR2(50) NOT NULL ,
"REL_DESCRIBE" NVARCHAR2(500) NULL ,
"REL_TYPE_ID" NVARCHAR2(50) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;
COMMENT ON COLUMN "QYB_WORKFLOW"."SERVICE_WORK_KLG_REL"."REL_ID" IS 'ID';
COMMENT ON COLUMN "QYB_WORKFLOW"."SERVICE_WORK_KLG_REL"."WORK_ID" IS 'work ID';
COMMENT ON COLUMN "QYB_WORKFLOW"."SERVICE_WORK_KLG_REL"."KNOWLEDGE_ID" IS 'work ID';
COMMENT ON COLUMN "QYB_WORKFLOW"."SERVICE_WORK_KLG_REL"."REL_DESCRIBE" IS '描述';
COMMENT ON COLUMN "QYB_WORKFLOW"."SERVICE_WORK_KLG_REL"."REL_TYPE_ID" IS '关联类型id';

-- ----------------------------
-- Records of SERVICE_WORK_KLG_REL
-- ----------------------------

-- ----------------------------
-- Table structure for SERVICE_WORK_REL
-- ----------------------------
DROP TABLE "QYB_WORKFLOW"."SERVICE_WORK_REL";
CREATE TABLE "QYB_WORKFLOW"."SERVICE_WORK_REL" (
"REL_ID" NVARCHAR2(50) NOT NULL ,
"WORK_ID" NVARCHAR2(50) NOT NULL ,
"WWORK_ID" NVARCHAR2(50) NOT NULL ,
"REL_DESCRIBE" NVARCHAR2(500) NULL ,
"REL_TYPE_ID" NVARCHAR2(50) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;
COMMENT ON TABLE "QYB_WORKFLOW"."SERVICE_WORK_REL" IS '工单关联';
COMMENT ON COLUMN "QYB_WORKFLOW"."SERVICE_WORK_REL"."REL_ID" IS 'ID';
COMMENT ON COLUMN "QYB_WORKFLOW"."SERVICE_WORK_REL"."WORK_ID" IS 'work ID';
COMMENT ON COLUMN "QYB_WORKFLOW"."SERVICE_WORK_REL"."WWORK_ID" IS 'work ID';
COMMENT ON COLUMN "QYB_WORKFLOW"."SERVICE_WORK_REL"."REL_DESCRIBE" IS '描述';
COMMENT ON COLUMN "QYB_WORKFLOW"."SERVICE_WORK_REL"."REL_TYPE_ID" IS '关联类型id';

-- ----------------------------
-- Records of SERVICE_WORK_REL
-- ----------------------------

-- ----------------------------
-- Table structure for TA_OFFICE_ARCHHANDLE
-- ----------------------------
DROP TABLE "QYB_WORKFLOW"."TA_OFFICE_ARCHHANDLE";
CREATE TABLE "QYB_WORKFLOW"."TA_OFFICE_ARCHHANDLE" (
"OID" VARCHAR2(40 BYTE) NOT NULL ,
"CC_FORM_INSTANCEID" VARCHAR2(50 BYTE) NULL ,
"AUDITER_ID" VARCHAR2(40 BYTE) NULL ,
"AUDIT_TIME" TIMESTAMP(6)  NULL ,
"OPINION_CONTENT" VARCHAR2(4000 BYTE) NULL ,
"LOGICAL_DELETE" NUMBER NULL ,
"ACTIVITY_NAME" VARCHAR2(40 BYTE) NULL ,
"PROC_INS_ID" VARCHAR2(100 BYTE) NULL ,
"ACTIVITY_ID" VARCHAR2(100 BYTE) NULL ,
"AUDIT_ORG_ID" VARCHAR2(100 BYTE) NULL ,
"BUSINESS_KEY" NVARCHAR2(40) NULL ,
"AUDIT_STATE" VARCHAR2(40 BYTE) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;
COMMENT ON COLUMN "QYB_WORKFLOW"."TA_OFFICE_ARCHHANDLE"."OID" IS 'OID';
COMMENT ON COLUMN "QYB_WORKFLOW"."TA_OFFICE_ARCHHANDLE"."CC_FORM_INSTANCEID" IS '流程主键OID';
COMMENT ON COLUMN "QYB_WORKFLOW"."TA_OFFICE_ARCHHANDLE"."AUDITER_ID" IS '意见填写人ID';
COMMENT ON COLUMN "QYB_WORKFLOW"."TA_OFFICE_ARCHHANDLE"."AUDIT_TIME" IS '意见填写时间';
COMMENT ON COLUMN "QYB_WORKFLOW"."TA_OFFICE_ARCHHANDLE"."OPINION_CONTENT" IS '意见内容';
COMMENT ON COLUMN "QYB_WORKFLOW"."TA_OFFICE_ARCHHANDLE"."LOGICAL_DELETE" IS '逻辑删除(1:已删除;0:未删除)';
COMMENT ON COLUMN "QYB_WORKFLOW"."TA_OFFICE_ARCHHANDLE"."ACTIVITY_NAME" IS '意见填写环节名称';
COMMENT ON COLUMN "QYB_WORKFLOW"."TA_OFFICE_ARCHHANDLE"."PROC_INS_ID" IS '流程定义ID或者module_id';
COMMENT ON COLUMN "QYB_WORKFLOW"."TA_OFFICE_ARCHHANDLE"."ACTIVITY_ID" IS '意见填写环节ID';
COMMENT ON COLUMN "QYB_WORKFLOW"."TA_OFFICE_ARCHHANDLE"."AUDIT_ORG_ID" IS '审核人部门ID';
COMMENT ON COLUMN "QYB_WORKFLOW"."TA_OFFICE_ARCHHANDLE"."BUSINESS_KEY" IS '业务id';
COMMENT ON COLUMN "QYB_WORKFLOW"."TA_OFFICE_ARCHHANDLE"."AUDIT_STATE" IS '审核状态';

-- ----------------------------
-- Records of TA_OFFICE_ARCHHANDLE
-- ----------------------------


-- ----------------------------
-- Table structure for WORKFLOW_ACTIVITY_SETTING
-- ----------------------------
DROP TABLE "QYB_WORKFLOW"."WORKFLOW_ACTIVITY_SETTING";
CREATE TABLE "QYB_WORKFLOW"."WORKFLOW_ACTIVITY_SETTING" (
"ID" NVARCHAR2(50) NOT NULL ,
"MODULE_ID" NVARCHAR2(50) NULL ,
"TASK_DEF_ID" NVARCHAR2(100) NULL ,
"ACTION" NVARCHAR2(100) NULL ,
"INFORM_TYPE" NVARCHAR2(100) NULL ,
"NEEDCLAIM" NUMBER(4) NULL ,
"IS_SLA_TIME" NUMBER(4) NULL ,
"INCLUDE_TABS" NVARCHAR2(1000) NULL ,
"INCLUDE_ACTIONS" NVARCHAR2(100) NULL ,
"RETURN_ACTIVITY" NVARCHAR2(100) NULL ,
"FREECHOOSE_URL" NVARCHAR2(100) NULL ,
"REMARK1" NVARCHAR2(500) NULL ,
"REMARK2" NVARCHAR2(500) NULL ,
"REMARK3" NUMBER(4) NULL ,
"REMARK4" NUMBER(4) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;
COMMENT ON TABLE "QYB_WORKFLOW"."WORKFLOW_ACTIVITY_SETTING" IS '环节配置表';
COMMENT ON COLUMN "QYB_WORKFLOW"."WORKFLOW_ACTIVITY_SETTING"."MODULE_ID" IS '事项Id(服务产品Id)';
COMMENT ON COLUMN "QYB_WORKFLOW"."WORKFLOW_ACTIVITY_SETTING"."TASK_DEF_ID" IS '环节';
COMMENT ON COLUMN "QYB_WORKFLOW"."WORKFLOW_ACTIVITY_SETTING"."INFORM_TYPE" IS '通知类型';
COMMENT ON COLUMN "QYB_WORKFLOW"."WORKFLOW_ACTIVITY_SETTING"."NEEDCLAIM" IS '需要签收';
COMMENT ON COLUMN "QYB_WORKFLOW"."WORKFLOW_ACTIVITY_SETTING"."IS_SLA_TIME" IS '是否计入处理时间';
COMMENT ON COLUMN "QYB_WORKFLOW"."WORKFLOW_ACTIVITY_SETTING"."INCLUDE_TABS" IS '用来设置节点包含哪些tab页';
COMMENT ON COLUMN "QYB_WORKFLOW"."WORKFLOW_ACTIVITY_SETTING"."INCLUDE_ACTIONS" IS '节点进行的操作';
COMMENT ON COLUMN "QYB_WORKFLOW"."WORKFLOW_ACTIVITY_SETTING"."RETURN_ACTIVITY" IS '退回操作退回到环节的ID';
COMMENT ON COLUMN "QYB_WORKFLOW"."WORKFLOW_ACTIVITY_SETTING"."FREECHOOSE_URL" IS '自由选择的url';


-- ----------------------------
-- Table structure for WORKFLOW_FORM
-- ----------------------------
DROP TABLE "QYB_WORKFLOW"."WORKFLOW_FORM";
CREATE TABLE "QYB_WORKFLOW"."WORKFLOW_FORM" (
"FORM_ID" NVARCHAR2(50) NOT NULL ,
"FORM_NO" NVARCHAR2(50) NULL ,
"FORM_NAME" NVARCHAR2(100) NULL ,
"FORM_DESCRIBE" NVARCHAR2(500) NULL ,
"IS_TABLE_STORAGE" NUMBER NULL ,
"TABLE_NAME" VARCHAR2(50 BYTE) NULL ,
"REMARK1" NUMBER NULL ,
"REMARK2" VARCHAR2(200 CHAR) NULL ,
"REMARK3" VARCHAR2(200 BYTE) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;
COMMENT ON COLUMN "QYB_WORKFLOW"."WORKFLOW_FORM"."FORM_NO" IS '字段标号';
COMMENT ON COLUMN "QYB_WORKFLOW"."WORKFLOW_FORM"."FORM_NAME" IS '表单名称';
COMMENT ON COLUMN "QYB_WORKFLOW"."WORKFLOW_FORM"."FORM_DESCRIBE" IS '描述';
COMMENT ON COLUMN "QYB_WORKFLOW"."WORKFLOW_FORM"."IS_TABLE_STORAGE" IS '是否有实体数据库表';
COMMENT ON COLUMN "QYB_WORKFLOW"."WORKFLOW_FORM"."TABLE_NAME" IS '数据库表名';
COMMENT ON COLUMN "QYB_WORKFLOW"."WORKFLOW_FORM"."REMARK2" IS '业务数据操作bean名称';


-- ----------------------------
-- Table structure for WORKFLOW_FORM_FIELD
-- ----------------------------
DROP TABLE "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD";
CREATE TABLE "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" (
"FIELD_ID" NVARCHAR2(50) NOT NULL ,
"FIELD_NO" NVARCHAR2(50) NULL ,
"FIELD_NAME" NVARCHAR2(100) NULL ,
"FIELD_TYPE" NVARCHAR2(20) NULL ,
"IS_OBJECT" NUMBER(4) NULL ,
"OBJECT_CLASS" NVARCHAR2(200) NULL ,
"FIELD_DESCRIBE" NVARCHAR2(500) NULL ,
"CATEGORY_ID" NVARCHAR2(50) NULL ,
"WEB_DISPLAY_TYPE_ID" NVARCHAR2(255) NULL ,
"FORDER" NUMBER(11) NULL ,
"REMARK1" NVARCHAR2(100) NULL ,
"REMARK2" NVARCHAR2(100) NULL ,
"REMARK3" NVARCHAR2(100) NULL ,
"REMARK4" NVARCHAR2(100) NULL ,
"REMARK5" NVARCHAR2(100) NULL ,
"REMARK6" NVARCHAR2(100) NULL ,
"RAMARK7" NVARCHAR2(255) NULL ,
"REMARK8" NVARCHAR2(255) NULL ,
"REMARK9" NUMBER(11) NULL ,
"REMARK10" NUMBER(11) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;
COMMENT ON COLUMN "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD"."FIELD_NO" IS '字段编码';
COMMENT ON COLUMN "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD"."FIELD_NAME" IS '字段名称';
COMMENT ON COLUMN "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD"."FIELD_TYPE" IS '持久数据类型';
COMMENT ON COLUMN "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD"."IS_OBJECT" IS '是否为内嵌表单';
COMMENT ON COLUMN "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD"."OBJECT_CLASS" IS '内嵌表单ID';
COMMENT ON COLUMN "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD"."FIELD_DESCRIBE" IS '描述';
COMMENT ON COLUMN "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD"."CATEGORY_ID" IS '分类Id 基本信息 事件信息等';
COMMENT ON COLUMN "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD"."WEB_DISPLAY_TYPE_ID" IS '前端控件类型';
COMMENT ON COLUMN "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD"."REMARK1" IS '字典名 字典下拉框等生效';
COMMENT ON COLUMN "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD"."REMARK2" IS 'list数据源 给字段展示控件使用';
COMMENT ON COLUMN "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD"."REMARK3" IS '名属性 前端控件 使用';
COMMENT ON COLUMN "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD"."REMARK4" IS '值属性 前端控件使用';
COMMENT ON COLUMN "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD"."REMARK5" IS '数据库表字段名 字段以单独业务表组织时使用';
COMMENT ON COLUMN "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD"."REMARK6" IS '数据库表名 字段以单独业务表组织时使用';
COMMENT ON COLUMN "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD"."REMARK9" IS '是否存入流程变量保存';
COMMENT ON COLUMN "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD"."REMARK10" IS '控件占位数';

-- ----------------------------
-- Records of WORKFLOW_FORM_FIELD
-- ----------------------------
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('RduIZbt4QfOIvb_zFfXBcw', 'partleader', '部门领导审核', 'STR', null, null, null, null, 'BMLD', null, null, null, null, null, null, null, null, null, null, '4');
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('jsgUTANeQPioDO07VFy_ag', 'departLeaderid', '部门领导', 'STR', null, null, null, null, 'BMLD', null, null, null, null, null, 'DEPART_LEADERID', null, null, null, null, '4');
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('UWyALjrsRoG5KW2BY8Et9g', 'chargeLeaderid', '分管领导', 'STR', null, null, null, null, 'FGLDSH', null, null, null, null, null, 'CHARGE_LEADERID', null, null, null, null, '4');
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('fw-DVlyqQM6O7RcjmJop_Q', 'secret', '密级', 'STR', null, null, null, null, 'ZDXLK', null, '公文管理_密级', null, null, null, 'SECRET', 'TA_OFFICE_RECEIVED', null, null, null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('nfZjaPwjSTqo7SOlHzdoVw', 'sendOrg', '主送单位', 'STR', null, null, null, null, 'WBY', null, '主送单位', null, null, null, 'SEND_ORG', 'TA_OFFICE_DISPATCH', null, null, null, '12');
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('WIfjad8yR9CjVVZ8Gi-4-g', 'docYear', '文号年', 'STR', null, null, null, null, 'WBK', null, null, null, null, null, 'DOC_YEAR', null, null, null, null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('JQAURzXYQa6qNiGerCE0-A', 'openedPersonId', '登记人', 'STR', null, null, null, null, 'DJR', null, '登记人', null, null, null, 'OPENED_PERSON_ID', 'TA_OFFICE_RECEIVED', null, null, null, '4');
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('A79InQKrSOS2Dj4EYdB5zQ', 'draftOpinion', '拟办意见', 'STR', null, null, null, null, 'AUDIT_OPINION', null, null, null, null, null, null, null, null, null, null, '12');
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('S-DX1UxpTkG34eKsqahCVw', 'deptOpinion', '承办部门意见', 'STR', null, null, null, null, 'AUDIT_OPINION', null, null, null, null, null, null, null, null, null, null, '12');
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('KOS19tqmQ_We0S_r_QLGUA', 'pubDate', '发布时间', 'TIME', null, null, null, null, 'RQXZK', null, null, null, null, null, 'PUB_DATE', null, null, null, null, '6');
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('qHVbonwASlmFeQYuWOBe5g', 'createDate', '发起时间', 'TIME', null, null, null, null, 'RQXZK', null, null, null, null, null, 'CREATE_DATE', null, null, null, null, '4');
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('cFCxfIJXQIex2ka20F4GPw', 'usecarHours', '用车时长', 'INT', null, null, null, null, 'SZSR', null, null, null, null, null, 'USECAR_HOURS', null, null, null, null, '6');
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('tqGwEUKqR26h91oTrAN19Q', 'applyUserId', '申请人', 'STR', null, null, null, null, 'YHXZS', null, null, null, null, null, 'APPLY_USER_ID', null, null, null, null, '6');
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('q2fss-w5Rqu1ToQjs3xG3g', 'content', '会议内容', 'STR', null, null, null, null, 'WBY', null, null, null, null, null, null, null, null, null, null, '12');
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('pIpccvLvQz2yfoz1Vs6ceg', 'giftsKey', '礼物名称', 'STR', null, null, null, null, 'LWXZ', null, null, null, null, null, null, null, null, null, null, '12');
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('p3HmuED-RTmwWLRIYVYcUQ', 'principalAudit', '分管领导审核', 'STR', null, null, null, null, 'AUDIT_OPINION', null, null, null, null, null, null, null, null, null, null, '12');
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('hnyXWFSuRx2OM7ERFv_GFQ', 'applyId', '申请人', 'STR', null, null, null, null, 'DJR', null, null, null, null, null, 'APPLY_ID', null, null, null, null, '4');
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('WiX35QfdSdu6AhzVKrureQ', 'sumUserId', '制表人', 'STR', null, null, null, null, 'DJR', null, null, null, null, null, 'SUM_USERID', null, null, null, null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('gvnnLcTGT3WuNtGREX902g', 'downloadFile', '文件下载', 'STR', null, null, null, null, 'XZWJ', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('gPBAVIP_SDWL1VEcdcCBlA', 'leader', '分管领导审核', 'STR', null, null, null, null, 'FGLDSH', null, null, null, null, null, null, null, null, null, null, '4');
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('-TNZtOJ_SN-xJrmgwPNDbQ', 'priId', '优先级', null, null, null, null, null, 'ZDXLK', null, '优先级', null, null, null, null, null, null, null, null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('-_TrHZiIQE6AgFEw893htA', 'assignDate', '分派时间', null, null, null, null, null, 'SJRQXZ', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('2QBfHVo1QMGHw2RysNnBiQ', 'businessSystem', '业务系统', 'STR', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('4113uuzcQC2I4hfSR0Xsxg', 'finishDate', '结束时间', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('4Z0iEpa-TIiYaR6o-fntOA', 'acceptDate', '接收时间', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('5BNZr0ubQQGaNcNS42FyPg', 'collapseTime', '故障时间', 'TIME', null, null, null, null, 'RQXZK', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('IYc_2zHSQSm2DAE0kzQEEw', 'requireArrivedDate', '到达时间', 'TIME', null, null, null, null, 'SJRQXZ', null, null, null, null, null, 'REQUIRE_ARRIVED_DATE', null, null, null, null, '6');
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('c9FM-krPR-GWnO3go4vrjg', 'applyDate', '申请时间', 'TIME', null, null, null, null, 'SJRQXZ', null, null, null, null, null, 'APPLY_DATE', null, null, null, null, '6');
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('3QfajdSSSI6fGDYX336jQw', 'startDate', '出发时间', 'TIME', null, null, null, null, 'SJRQXZ', null, null, null, null, null, 'START_DATE', null, null, null, null, '6');
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('8rdRpeTMTBG48S3PF2xv4w', 'startTime', '开始时间', 'TIME', null, null, null, null, 'SJRQXZ', null, null, null, null, null, null, null, null, null, null, '6');
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('9b9MkyB2TvOgBwbmwtWbdw', 'returnPlan', '回退方案', null, null, null, null, null, 'WBY', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('adpVtGFSSxiifGYypi_rbA', 'applicantId', '申请人', null, null, null, null, null, 'YHXZS', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('AJNDA-QTSSWTHUwd5Ok1HA', 'configSchedule', '配置方案', 'STR', null, null, null, null, 'WBY', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('B0l3uFPtT6qvL-TM1oJTLA', 'affectScopeId', '影响范围', null, null, null, null, null, 'ZDXLK', null, '影响范围', null, null, null, null, null, null, null, null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('bp6rAeqMSb2hL99tblCeMg', 'workTitle', '工单标题', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('ByEpZePtQ4eISuPNPKERVQ', 'applicantName', '申请人名称', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('DrXI0r8sT-2eDwgPIzbZNA', 'backTime', '返回时间', 'TIME', null, null, null, null, 'SJRQXZ', null, null, null, null, null, 'BACK_TIME', null, null, null, null, '6');
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('EDm0Pad9T26-MtJgl8GAMA', 'urgentLevelId', '紧急程度', null, null, null, null, null, 'ZDXLK', null, '紧急程度', null, null, null, null, null, null, null, null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('3AfpK5xwSpmXA5TkYk67Mw', 'backKm', '返回里程', 'INT', null, null, null, null, 'SZSR', null, null, null, null, null, 'BACK_KM', null, null, null, null, '6');
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('W2DDyV6-Tm2R7QZaWO63Zw', 'applyDeptId', '申请部门', 'STR', null, null, null, null, 'JGXZS', null, null, null, null, null, 'APPLY_DEPTID', null, null, null, null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('_semXvShQl69dJpA8kvLgg', 'newsPic', '新闻配图', null, null, null, null, null, 'NEWSPIC', null, null, null, null, null, null, null, null, null, null, '12');
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('0CC9aC-aRBm_zW7pbZIUmQ', 'filePath', '通知公告文件上传', 'STR', null, null, null, null, 'TZGGWJSC', null, null, null, null, null, 'FILEPATH', null, null, null, null, '12');
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('fUoaH73eST2ybAz0DA6W_A', 'applicantContactWay', '联系方式', null, null, null, null, null, 'DHHM', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('FWUMC28vSjK4Y76WH-112A', 'applyDeptId', '申请部门', 'STR', null, null, null, null, 'JGXZS', null, null, null, null, null, 'APPLY_DEPTID', null, null, null, null, '4');
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('h0WTm1IcTQmgt-3z3hUKzA', 'endTime', '结束时间', 'TIME', null, null, null, null, 'SJRQXZ', null, null, null, null, null, null, null, null, null, null, '6');
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('HEsWeUJuRDiMlcy0cyzqNg', 'rescueResult', '补救结果', null, null, null, null, null, 'WBY', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('Oyay6wFnTzKYDdRDyWzcmg', 'collector', '汇总人', 'STR', null, null, null, null, 'HZR', null, null, null, null, null, null, null, null, null, null, '4');
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('lI7rUMJNTTab4xUhF4kqVA', 'happenDate', '登记时间', 'TIME', null, null, null, null, 'RQXZK', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('LYzJSSUgS6-LebkcCTqG6Q', 'businessKey', '业务key', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('OjZ_fccMQAetp_NNxvXAgA', 'workStage', '任务环节', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('S6XMmU8wRtin7CKTlyH0bA', 'workDescribe', '工单描述', null, null, null, null, null, 'WBY', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('tHjunL2_STyyRqtLJasj1Q', 'moduleId', 'moduleId', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('ue1SxIoxSOizCp_8lJUq5Q', 'incidentLevelId', '事件级别', null, null, null, null, null, 'ZDXLK', null, '事件级别', null, null, null, null, null, null, null, null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('VC2a0_gLTMCkgkfEGdMFGA', 'workNo', '工单编号', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '1', null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('VwS8r4SmS1ugiGJUK8khTg', 'workId', '工单Id', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('whnGZsRZQpeynRqneZA_UQ', 'releaseOpinion', '发布意见', 'STR', null, null, null, null, 'WBY', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('wMURAdX7RL-essxS6bwb5g', 'applyBranchName', '申请部门名称', null, null, null, null, null, 'WBK', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('x0fIkvWnTr-WoVfWLbvFsw', 'draftDeptId', '拟稿部门', 'STR', null, null, null, null, 'NGBM', null, '拟稿部门', null, null, null, 'DRAFT_DEPT_ID', 'TA_OFFICE_DISPATCH', null, null, null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('Du_b7SZ1TuOU81SS1-jKeQ', 'draftDate', '拟稿日期', 'TIME', null, null, null, null, 'RQXZK', null, '拟稿日期', null, null, null, 'DRAFT_DATE', 'TA_OFFICE_DISPATCH', null, null, null, '4');
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('S9m860aJS6WrxbpRbtD-wQ', 'docType', '文档类型', 'STR', null, null, null, null, 'XLK', null, '文档类型', null, null, null, 'DOC_TYPE', 'TA_OFFICE_DISPATCH', null, null, null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('AEf8Va8RSLSTCZtTZkYNDg', 'ccOrg', '抄送单位', 'STR', null, null, null, null, 'WBY', null, '抄送单位', null, null, null, 'CC_ORG', 'TA_OFFICE_DISPATCH', null, null, null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('YoFK-so9QompNgWY99rCtg', 'signUser', '签发人', 'STR', null, null, null, null, 'YHXZS', null, '签发人', null, null, null, 'SIGN_USER', 'TA_OFFICE_DISPATCH', null, null, null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('YauDtuuPT0WWdRuJbEsd8w', 'productNo', '产品名称', null, null, null, '这个一般是给查询时筛选用的', null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('sjYYNtQVTHWNXu7GtnL20Q', 'receivedNumber', '来文号', 'STR', null, null, null, null, 'LWH', null, '来文号', null, null, null, 'RECEIVED_NUMBER', 'TA_OFFICE_RECEIVED', null, null, null, '4');
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('oSMNR0JaRGKQORTgk_Osrw', 'draftTitle', '稿纸标题', 'STR', null, null, null, null, 'GZBT', null, null, null, null, null, null, null, null, null, '1', '12');
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('HJnSSyugQMCtu34xmdpHfg', 'receivedNumeric', '收文号', 'STR', null, null, null, null, 'LWH', null, '收文号', null, null, null, 'RECEIVED_NUMERIC', 'TA_OFFICE_RECEIVED', null, null, null, '4');
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('OPBx-7pOQJWp1MDdOlsrFQ', 'moderatorSign', '会议主持人签发', null, null, null, null, null, 'AUDIT_OPINION', null, null, null, null, null, null, null, null, null, null, '12');
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('SITrYmYySbe4VFzG8d4BRg', 'Symbol', '文号', 'STR', null, null, null, null, 'WH', null, '文号', null, null, null, null, null, null, null, null, '4');
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('9fvl8urCReuYYXgL8v2JFg', 'draftPersonId', '拟稿人', 'STR', null, null, null, null, 'NGR', null, '拟稿人', null, null, null, 'DRAFT_PERSON_ID', 'TA_OFFICE_DISPATCH', null, null, '0', null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('7xHQ-nfhRBGAhC8mdYDWlw', 'title', '标题', 'STR', null, null, null, null, 'WJMC', null, '标题', null, null, null, 'TITLE', 'TA_OFFICE_DISPATCH', null, null, null, '12');
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('E55w9EaFRoa9RumUH1WSxw', 'signDate', '签发日期', 'TIME', null, null, null, null, 'RQXZK', null, '签发日期', null, null, null, 'SIGN_DATE', 'TA_OFFICE_DISPATCH', null, null, null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('j5N1HBv0SD-wF6S1naSh3g', 'sendOrgId', '发文部门Id', null, null, null, null, null, null, null, null, null, null, null, 'SEND_ORG_ID', null, null, null, null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('iVrP4684Q-yezOJ5mcLkww', 'fileName', '文件名称', 'STR', null, null, null, null, 'WJMC', null, '文件名称', null, null, null, 'FILE_NAME', 'TA_OFFICE_RECEIVED', null, null, null, '12');
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('TBBhKweLRcufX0EfSTW2kw', 'receivedOrgId', '来文单位', 'STR', null, null, null, null, 'LWDW', null, '来文单位', null, null, null, 'RECEIVED_ORG_ID', 'TA_OFFICE_RECEIVED', null, null, null, '6');
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('0cTKZeOkTUymnW4i0ogMAA', 'fileUpload', '上传附件', 'STR', null, null, null, null, 'SCFJ', null, null, null, null, null, null, null, null, null, null, '12');
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('sL24tVWGRV2etjBfr5gcrg', 'partyChiefSign', '党总支书记签发', null, null, null, null, null, 'AUDIT_OPINION', null, null, null, null, null, null, null, null, null, null, '12');
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('LaHQWKlGTL2THS3Bw9vydQ', 'unionChiefSign', '工会主席签发', null, null, null, null, null, 'AUDIT_OPINION', null, null, null, null, null, null, null, null, null, null, '12');
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('JI1e_t7uSFag8f2qjWcQPw', 'pubDeptoId', '发布单位', null, null, null, null, null, 'JGXZS', null, null, null, null, null, 'PUB_DEPTO_ID', null, null, null, null, '6');
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('IFD3-SnaSUiCiP_1pcSjGw', 'passengerNumber', '乘客数量', 'INT', null, null, null, null, 'SZSR', null, null, null, null, null, 'PASSENGER_NUMBER', null, null, null, null, '6');
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('Kg2AOAkyRGSDDiBk7hWdbg', 'involvedUsersId', '与会人员', 'STR', null, null, null, null, 'YHDX', null, null, null, null, null, null, null, null, null, null, '12');
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('8_juQLuQRbmjKKrQBhaeBw', 'arrivedPlace', '目的地点', 'STR', null, null, null, null, 'WBK', null, null, null, null, null, 'ARRIVED_PLACE', null, null, null, null, '6');
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('6a6h1Rv4QbW7AlRmjg33Iw', 'contactName', '联系人', 'STR', null, null, null, null, 'WBK', null, null, null, null, null, 'CONTACT_NAME', null, null, null, null, '6');
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('0oyFRl-4QSOUcBnKWYsVqA', 'applyReason', '申请事由', 'STR', null, null, null, null, 'WBY', null, null, null, null, null, 'APPLY_REASON', null, null, null, null, '12');
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('Yzkdi6M3T3yuKlkb0v_4VQ', 'startKm', '出发里程', 'INT', null, null, null, null, 'SZSR', null, null, null, null, null, 'START_KM', null, null, null, null, '6');
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('KqLNetLwTfuXuUkoDcuAsg', 'driverId', '驾驶员', 'STR', null, null, null, null, 'SJXZSR', null, null, null, null, null, null, null, null, null, null, '6');
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('BuQAueZHSLyijkXc7x9p6A', 'applyDate', '申请时间', 'TIME', null, null, null, null, 'RQXZK', null, null, null, null, null, 'APPLY_DATE', null, null, null, null, '4');
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('GnpYaiutSWKzoKul_DI2LQ', 'manageInstructions', '总经理批示', 'STR', null, null, null, null, 'AUDIT_OPINION', null, '总经理批示', null, null, null, null, null, null, null, null, '12');
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('9Z2yeMpKRSWOBD22CM0row', 'headAudit', '承办部门负责人审核', 'STR', null, null, null, null, 'WBY', null, '承办部门负责人审核', null, null, null, null, null, null, null, null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('_uSjRrBhSIaN7-Di429dAQ', 'signView', '签发意见', 'STR', null, null, null, null, 'WBY', null, '签发一件', null, null, null, 'SIGN_VIEW', 'TA_OFFICE_DISPATCH', null, null, null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('VxaCPSsWRlK1W8BrosQ2UQ', 'docNumber', '文号号', 'STR', null, null, null, null, 'WBK', null, null, null, null, null, 'DOC_NUMBER', null, null, null, null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('UgWP-zpGSfSicpt2ADn2Jg', 'receivedDate', '收文日期', 'TIME', null, null, null, null, 'RQXZK', null, '收文日期', null, null, null, 'RECEIVED_DATE', 'TA_OFFICE_RECEIVED', null, null, null, '4');
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('5fjRB2kASGuhGhy11sMQcg', 'leaderComment', '公司领导批示', 'STR', null, null, null, null, 'AUDIT_OPINION', null, null, null, null, null, null, null, null, null, null, '12');
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('eq8YCLSnQWyeKIeU6kpg_g', 'webRevision', '手写签章', 'STR', null, null, null, null, 'SXQZ', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('NaLhJc73SamSneehqrIl8w', 'wordType', '正文编辑类型', null, null, null, '正文编辑类型参数', null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('V87ZUh4tSVieQDIl9EKQpQ', 'secretaryAuditOpinion', '办公室秘书审核', 'STR', null, null, null, null, 'AUDIT_OPINION', null, null, null, null, null, null, null, null, null, null, '12');
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('fhSRZPN7TaqajicfG0QU9g', 'docCode', '发文文号', 'STR', null, null, null, null, 'FWWH', null, '发文文号', null, null, null, 'DOC_CODE', null, null, null, null, '12');
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('LaIZSe0kRyCgKxhuTrEIBQ', 'remark', '备注', 'STR', null, null, null, null, 'WBY', null, null, null, null, null, null, null, null, null, null, '12');
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('k_uiiZM8SHethyeujjLKgg', 'passengersName', '乘客姓名', 'STR', null, null, null, null, 'WBK', null, null, null, null, null, 'PASSENGERS_NAME', null, null, null, null, '6');
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('WZ2TO7ZqSKi11nTUW75w1Q', 'contactPhone', '联系电话', 'STR', null, null, null, null, 'DHHM', null, null, null, null, null, 'CONTACT_PHONE', null, null, null, null, '6');
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('M5Kz-EW4R_mb_fU3tSX09Q', 'refuelMoney', '加油金额', 'INT', null, null, null, null, 'SZSR', null, null, null, null, null, 'REFUEL_MONEY', null, null, null, null, '6');
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('Dqb8K_2QRUOsUv9nEsEfdQ', 'carId', '安排车辆', 'STR', null, null, null, null, 'CLSR', null, null, null, null, null, null, null, null, null, null, '6');
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('AblK6BjeQmmh33yDkC4Zjg', 'serviceTypeId', '服务分类', 'STR', null, null, null, null, 'ZDXLK', null, '服务分类', null, null, null, null, null, null, null, null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('OhhAmgThQlmbWIbjlcUd5w', 'distributeUsers', '分发人员', null, null, null, null, null, 'YHDX', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('Y_tbmr7NS8i0IOx_aUFw-g', 'urgency', '急缓', 'STR', null, null, null, null, 'ZDXLK', null, '公文管理_紧急程度', null, null, null, 'URGENCY', 'TA_OFFICE_DISPATCH', null, null, null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('LvGkaV7lRuOx_2wTdkA78A', 'ccOrgId', '分发部门', null, null, null, null, null, 'JGDX', null, null, null, null, null, null, null, null, null, null, '12');
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('QR3pg4ACRzWxNllu3lo9_g', 'auditOpinion', '部门负责人审核', 'STR', null, null, null, null, 'AUDIT_OPINION', null, null, null, null, null, null, null, null, null, '0', '12');
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('BtBHQgx9TpK2h-qRMUCLaw', 'manageComment', '总经理签发', 'STR', null, null, null, null, 'AUDIT_OPINION', null, null, null, null, null, null, null, null, null, null, '12');
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('_A5Pm1NySdqVY9RTc0EN0w', 'bossCountersign', '分管领导会签', 'STR', null, null, null, null, 'AUDIT_OPINION', null, null, null, null, null, null, null, null, null, null, '12');
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('ieecSrPzT_K_uPn3nCF5HQ', 'officePrincipalComment', '办公室主任批示', 'STR', null, null, null, null, 'AUDIT_OPINION', null, null, null, null, null, null, null, null, null, null, '12');
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('74M-K87iQcKTFXBvnz_09w', 'content', '正文', null, null, null, null, null, 'FWB', null, null, null, null, null, null, null, null, null, null, '12');
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('dP9e-rrHSKaiNehqG4h2yw', 'deadline', '有效时间', 'TIME', null, null, null, null, 'RQXZK', null, null, null, '6', null, 'DEADLINE', null, null, null, null, '6');
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('3289CQJcSauMaZgL0_-cGA', 'pubTargetDeptId', '发布对象', 'STR', null, null, null, null, 'JGDX', null, null, null, null, null, 'PUB_TARGET_DEPT_ID', null, null, null, null, '12');
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('9xE8Liz0Q7uH5ai9_7IX1w', 'pubUserId', '发布人', null, null, null, null, null, 'YHXZS', null, null, null, null, null, 'PUB_USER_ID', null, null, null, null, '6');
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('yJzaK8qnShKK7l494WGs-Q', 'finishState', '流程状态', 'STR', null, null, null, null, 'ZDXLK', null, '流程状态', null, null, null, 'FINISH_STATE', null, null, null, null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('f--9OzgNQAiDlGVxeXQUfA', 'applyDeptId', '申请部门', 'STR', null, null, null, null, 'JGXZS', null, null, null, null, null, null, null, null, null, null, '6');
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('WINg7qJNSRWIMytkuBye5w', 'roomId', '会议室', 'STR', null, null, null, null, 'HYSXZ', null, null, null, null, null, 'ROOM_ID', null, null, null, null, '6');
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('nhDt_6SxSLKqW8ZvhWzyFg', 'startPlace', '出发地点', 'STR', null, null, null, null, 'WBK', null, null, null, null, null, 'START_PLACE', null, null, null, null, '6');
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('3Eri826DS3y_KNAZB_x3lw', 'giftsNumber', '礼物数量', 'INT', null, null, null, null, 'SZSR', null, null, null, null, null, 'GIFTS_NUMBER', null, null, null, null, '6');
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('MOMn7CP4QfWZuzjj4tPPoQ', 'applyUsername', '申请人姓名', 'STR', null, null, null, null, null, null, null, null, null, null, 'APPLY_USERNAME', null, null, null, null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" VALUES ('SgL6cSK7QjOUZYMFV0AXww', 'collect', '汇总审核', 'STR', null, null, null, null, 'HZ', null, null, null, null, null, null, null, null, null, null, '8');

-- ----------------------------
-- Table structure for WORKFLOW_FORM_FIELD_PERMISSON
-- ----------------------------
DROP TABLE "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD_PERMISSON";
CREATE TABLE "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD_PERMISSON" (
"ID" NVARCHAR2(50) NOT NULL ,
"FIELD_ID" NVARCHAR2(50) NULL ,
"MODULE_ID" NVARCHAR2(100) NULL ,
"BUSINESS_KEY" NVARCHAR2(100) NULL ,
"BUSINESS_KEY2" NVARCHAR2(255) NULL ,
"READ_PERMISSION" NUMBER(4) NULL ,
"WRITE_PERMISSION" NUMBER(4) NULL ,
"FILL_NECESSARY" NUMBER(4) NULL ,
"VISIBLE" NUMBER(4) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;
COMMENT ON COLUMN "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD_PERMISSON"."FIELD_ID" IS '字段ID';
COMMENT ON COLUMN "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD_PERMISSON"."MODULE_ID" IS '事项ID';
COMMENT ON COLUMN "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD_PERMISSON"."BUSINESS_KEY" IS '业务key';
COMMENT ON COLUMN "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD_PERMISSON"."READ_PERMISSION" IS '读权限';
COMMENT ON COLUMN "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD_PERMISSON"."WRITE_PERMISSION" IS '写权限';
COMMENT ON COLUMN "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD_PERMISSON"."FILL_NECESSARY" IS '是否必填项';
COMMENT ON COLUMN "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD_PERMISSON"."VISIBLE" IS '是否可见';

-- ----------------------------
-- Records of WORKFLOW_FORM_FIELD_PERMISSON
-- ----------------------------

-- ----------------------------
-- Table structure for WORKFLOW_FORM_FIELD_REL
-- ----------------------------
DROP TABLE "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD_REL";
CREATE TABLE "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD_REL" (
"REL_ID" NVARCHAR2(50) NOT NULL ,
"FORM_ID" NVARCHAR2(50) NULL ,
"FIELD_ID" NVARCHAR2(50) NULL ,
"CATEGOTY_ID" NVARCHAR2(50) NULL ,
"RORDER" NUMBER(11) NULL ,
"REL_DESCRIBE" NVARCHAR2(500) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;
COMMENT ON COLUMN "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD_REL"."REL_ID" IS 'ID';
COMMENT ON COLUMN "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD_REL"."FORM_ID" IS 'form ID';
COMMENT ON COLUMN "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD_REL"."FIELD_ID" IS 'field ID';
COMMENT ON COLUMN "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD_REL"."CATEGOTY_ID" IS '字段类别 问题信息 基本信息 申请人信息等';
COMMENT ON COLUMN "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD_REL"."REL_DESCRIBE" IS '描述';

-- ----------------------------
-- Records of WORKFLOW_FORM_FIELD_REL
-- ----------------------------

-- ----------------------------
-- Table structure for WORKFLOW_FORM_FIELD_VALUE
-- ----------------------------
DROP TABLE "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD_VALUE";
CREATE TABLE "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD_VALUE" (
"ID" NVARCHAR2(50) NOT NULL ,
"FORM_ID" NVARCHAR2(50) NULL ,
"FORM_BUSINESS_KEY" NVARCHAR2(100) NULL ,
"FIELD_ID" NVARCHAR2(500) NULL ,
"FIELD_VALUE" NVARCHAR2(1000) NULL ,
"FIELD_VALUE_INT" NUMBER(11) NULL ,
"FIELD_VALUE_TIMESTAMP" DATE NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;
COMMENT ON COLUMN "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD_VALUE"."FORM_ID" IS '表单';
COMMENT ON COLUMN "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD_VALUE"."FORM_BUSINESS_KEY" IS '表单业务Key';
COMMENT ON COLUMN "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD_VALUE"."FIELD_ID" IS '字段';
COMMENT ON COLUMN "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD_VALUE"."FIELD_VALUE" IS '字段值';

-- ----------------------------
-- Records of WORKFLOW_FORM_FIELD_VALUE
-- ----------------------------

-- ----------------------------
-- Table structure for WORKFLOW_RETRIEVAL_ITEM
-- ----------------------------
DROP TABLE "QYB_WORKFLOW"."WORKFLOW_RETRIEVAL_ITEM";
CREATE TABLE "QYB_WORKFLOW"."WORKFLOW_RETRIEVAL_ITEM" (
"ITEM_ID" NVARCHAR2(50) NOT NULL ,
"RETRIEVE_KEY" NVARCHAR2(100) NULL ,
"FIELD_ID" NVARCHAR2(50) NULL ,
"IS_LIKE" NUMBER(4) NULL ,
"RIGHT_OPERATE" NVARCHAR2(100) NULL ,
"LEFT_OPERATE" NVARCHAR2(100) NULL ,
"REMARK1" NVARCHAR2(100) NULL ,
"REMARK2" NVARCHAR2(100) NULL ,
"REMARK3" NVARCHAR2(1000) NULL ,
"REMARK4" NUMBER(4) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;
COMMENT ON COLUMN "QYB_WORKFLOW"."WORKFLOW_RETRIEVAL_ITEM"."RETRIEVE_KEY" IS '检索器id';
COMMENT ON COLUMN "QYB_WORKFLOW"."WORKFLOW_RETRIEVAL_ITEM"."FIELD_ID" IS '字段id';
COMMENT ON COLUMN "QYB_WORKFLOW"."WORKFLOW_RETRIEVAL_ITEM"."IS_LIKE" IS '是否模糊检索';
COMMENT ON COLUMN "QYB_WORKFLOW"."WORKFLOW_RETRIEVAL_ITEM"."RIGHT_OPERATE" IS '左边操作符';
COMMENT ON COLUMN "QYB_WORKFLOW"."WORKFLOW_RETRIEVAL_ITEM"."LEFT_OPERATE" IS '右边操作符';
COMMENT ON COLUMN "QYB_WORKFLOW"."WORKFLOW_RETRIEVAL_ITEM"."REMARK1" IS '1';
COMMENT ON COLUMN "QYB_WORKFLOW"."WORKFLOW_RETRIEVAL_ITEM"."REMARK2" IS '2';
COMMENT ON COLUMN "QYB_WORKFLOW"."WORKFLOW_RETRIEVAL_ITEM"."REMARK3" IS '3';
COMMENT ON COLUMN "QYB_WORKFLOW"."WORKFLOW_RETRIEVAL_ITEM"."REMARK4" IS '4';

-- ----------------------------
-- Records of WORKFLOW_RETRIEVAL_ITEM
-- ----------------------------

-- ----------------------------
-- Table structure for WORKFLOW_USERCONCERNED_SETTING
-- ----------------------------
DROP TABLE "QYB_WORKFLOW"."WORKFLOW_USERCONCERNED_SETTING";
CREATE TABLE "QYB_WORKFLOW"."WORKFLOW_USERCONCERNED_SETTING" (
"ID" NVARCHAR2(50) NOT NULL ,
"OBSERVER_ID" NVARCHAR2(500) NULL ,
"CATOGORY" NVARCHAR2(50) NULL ,
"PROCESS_INS_ID" NVARCHAR2(100) NULL ,
"PROCESS_DEF_ID" NVARCHAR2(50) NULL ,
"TASK_DEF_ID" NVARCHAR2(100) NULL ,
"ACTION" NVARCHAR2(100) NULL ,
"INFORM_TYPE" NVARCHAR2(200) NULL ,
"REMARK1" NVARCHAR2(100) NULL ,
"REMARK2" NVARCHAR2(100) NULL ,
"REMARK3" NVARCHAR2(100) NULL ,
"REMARK4" NVARCHAR2(100) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;
COMMENT ON TABLE "QYB_WORKFLOW"."WORKFLOW_USERCONCERNED_SETTING" IS '用户关注工单配置表';
COMMENT ON COLUMN "QYB_WORKFLOW"."WORKFLOW_USERCONCERNED_SETTING"."OBSERVER_ID" IS '观察者ID';
COMMENT ON COLUMN "QYB_WORKFLOW"."WORKFLOW_USERCONCERNED_SETTING"."CATOGORY" IS '观察者类别 用户 或组';
COMMENT ON COLUMN "QYB_WORKFLOW"."WORKFLOW_USERCONCERNED_SETTING"."PROCESS_INS_ID" IS '流程实例';
COMMENT ON COLUMN "QYB_WORKFLOW"."WORKFLOW_USERCONCERNED_SETTING"."PROCESS_DEF_ID" IS '流程定义id';
COMMENT ON COLUMN "QYB_WORKFLOW"."WORKFLOW_USERCONCERNED_SETTING"."TASK_DEF_ID" IS '字段分类名称';
COMMENT ON COLUMN "QYB_WORKFLOW"."WORKFLOW_USERCONCERNED_SETTING"."ACTION" IS '事件名称';
COMMENT ON COLUMN "QYB_WORKFLOW"."WORKFLOW_USERCONCERNED_SETTING"."INFORM_TYPE" IS '通知类型';
COMMENT ON COLUMN "QYB_WORKFLOW"."WORKFLOW_USERCONCERNED_SETTING"."REMARK1" IS '1';
COMMENT ON COLUMN "QYB_WORKFLOW"."WORKFLOW_USERCONCERNED_SETTING"."REMARK2" IS '2';
COMMENT ON COLUMN "QYB_WORKFLOW"."WORKFLOW_USERCONCERNED_SETTING"."REMARK3" IS '3';
COMMENT ON COLUMN "QYB_WORKFLOW"."WORKFLOW_USERCONCERNED_SETTING"."REMARK4" IS '4';

-- ----------------------------
-- Records of WORKFLOW_USERCONCERNED_SETTING
-- ----------------------------

-- ----------------------------
-- Table structure for WORKFLOW_WEBDISPLAY_CATEGORY
-- ----------------------------
DROP TABLE "QYB_WORKFLOW"."WORKFLOW_WEBDISPLAY_CATEGORY";
CREATE TABLE "QYB_WORKFLOW"."WORKFLOW_WEBDISPLAY_CATEGORY" (
"ID_" NVARCHAR2(50) NOT NULL ,
"CATEGORY_NO" NVARCHAR2(50) NULL ,
"CATEGORY_NAME" NVARCHAR2(50) NULL ,
"URL" NVARCHAR2(50) NULL ,
"DESCRIBE" NVARCHAR2(200) NULL ,
"REMARK1" NUMBER(4) NULL ,
"REMARK2" NVARCHAR2(100) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;
COMMENT ON COLUMN "QYB_WORKFLOW"."WORKFLOW_WEBDISPLAY_CATEGORY"."CATEGORY_NO" IS '编码';
COMMENT ON COLUMN "QYB_WORKFLOW"."WORKFLOW_WEBDISPLAY_CATEGORY"."CATEGORY_NAME" IS '名字';
COMMENT ON COLUMN "QYB_WORKFLOW"."WORKFLOW_WEBDISPLAY_CATEGORY"."URL" IS 'url';

-- ----------------------------
-- Records of WORKFLOW_WEBDISPLAY_CATEGORY
-- ----------------------------
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_WEBDISPLAY_CATEGORY" VALUES ('YwCHQQmmTviK7MWbPm7rqw', 'DJR', '登记人', 'f/openedpersonchoose', null, null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_WEBDISPLAY_CATEGORY" VALUES ('dcUTD4RWQEWIzw7L5IGiZQ', 'JGDX2', '机构多选输入2', 'f/distributeorg', null, null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_WEBDISPLAY_CATEGORY" VALUES ('d1FkklCES_q7kuqkWhSGHw', 'TZGGWJSC', '通知公告文件上传', 'f/noticefileupload', null, null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_WEBDISPLAY_CATEGORY" VALUES ('eUmJY3L6T-yNsex2FwQnug', 'FGLDSH', '分管领导', 'f/leader', null, null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_WEBDISPLAY_CATEGORY" VALUES ('DD4C307BF52F4A05B151B650CABA0444', 'SCFJ', '上传附件', 'f/fileupload', null, null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_WEBDISPLAY_CATEGORY" VALUES ('DD4C307BF52F4A05B151B650CABA0445', 'SXQZ', '手写签章', 'f/webrevision', null, null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_WEBDISPLAY_CATEGORY" VALUES ('YTKsbfnbQhWmH8HB0o0bUw', 'NGBM', '拟稿部门选择', 'f/draftorgchoose', null, null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_WEBDISPLAY_CATEGORY" VALUES ('NBkNToC1RW2aqZqercspUw', 'LWH', '来文号', 'f/receivednumber', null, null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_WEBDISPLAY_CATEGORY" VALUES ('7cHWEusYSEWnltZgvldU2A', 'NEWSPIC', '新闻配图', 'f/snewspicb', null, null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_WEBDISPLAY_CATEGORY" VALUES ('IYYtTtwJQa6TwCSjM6Y7IA', 'FWWH', '发文文号', 'f/doccode', '新发文文号', null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_WEBDISPLAY_CATEGORY" VALUES ('dS6-x-V-SYGoGFVjjaDSQg', 'HZ', '汇总审核', 'f/collect', null, null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_WEBDISPLAY_CATEGORY" VALUES ('0A8FC66CB6E244158CB0EA92F9D92039', 'WBY', '文本域', 'f/formtextarea', null, null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_WEBDISPLAY_CATEGORY" VALUES ('23F58CFE43184262AE7E47895BE925CA', 'YHXZS', '用户选择树', 'f/formuserchoose', null, null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_WEBDISPLAY_CATEGORY" VALUES ('425CA4DD80C0415DBCC33C38E767A253', 'AUDIT_OPINION', '审核意见', 'f/auditopinionsec', null, null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_WEBDISPLAY_CATEGORY" VALUES ('43223660004546FAAE4A3BC371C35ACA', 'ZDXLK', '字典下拉框', 'f/dictdropdownlist', null, null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_WEBDISPLAY_CATEGORY" VALUES ('48D29B8FB3194F649A3BD9E9F4A6D922', 'QYXX', '企业信息', 'ef/enterpriseInfo/add', null, null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_WEBDISPLAY_CATEGORY" VALUES ('4E11D002A7B94C80953EBFA695EFDD0F', 'JGXZS', '机构选择树', 'f/formorgchoose', null, null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_WEBDISPLAY_CATEGORY" VALUES ('5D88E3368DF94BF997C8F8C8A7FDCF84', 'RQXZK', '日期选择框', 'f/formdatepick', null, null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_WEBDISPLAY_CATEGORY" VALUES ('653816FCC3EC4A048391AA080D717733', 'SJRQXZ', '时间日期选择控件', 'f/formdatatimePick', null, null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_WEBDISPLAY_CATEGORY" VALUES ('78BDC48525504B38912CD314859E5D88', 'WBK', '文本框', 'f/formtextField', null, null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_WEBDISPLAY_CATEGORY" VALUES ('9C81F23089A244AAA160731BF54A1EF8', 'WH', '文号', 'f/docnumber', null, null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_WEBDISPLAY_CATEGORY" VALUES ('A28296DF020344DDA9103F6E4CF2022A', 'DHHM', '电话号码文本框', 'f/formtextphone', null, null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_WEBDISPLAY_CATEGORY" VALUES ('B92D75FBEB814C65A2EC83BDEE1129DE', 'GZBT', '稿纸标题', 'f/drafttitle', null, null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_WEBDISPLAY_CATEGORY" VALUES ('DD4C307BF52F4A05B151B650CABA0443', 'JGDX', '机构多选输入框', 'f/multipleorginput', null, null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_WEBDISPLAY_CATEGORY" VALUES ('Rugfixu5R2iIw_v1aHdTyQ', 'LWDW', '来文单位', 'f/receivedorgchoose', null, null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_WEBDISPLAY_CATEGORY" VALUES ('QXPkFy3SQGCSEiLf2ccBXw', 'WJMC', '文件名称', 'f/filename', null, null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_WEBDISPLAY_CATEGORY" VALUES ('pccwT6xHSz6shEHSf0ejgA', 'YHDX', '用户多选输入', 'f/multipleuserinput', null, null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_WEBDISPLAY_CATEGORY" VALUES ('GT6H9z8SSZ-ghkmbW-OsYA', 'FWB', '富文本', 'f/ueditor', null, null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_WEBDISPLAY_CATEGORY" VALUES ('t4HAlaZsRGOwEE2hmGyU7A', 'SZSR', '数字输入', 'f/wfnumberinput', null, null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_WEBDISPLAY_CATEGORY" VALUES ('U79ojea3S0mz8_OPOP-bjQ', 'HZR', '汇总人', 'f/collector', null, null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_WEBDISPLAY_CATEGORY" VALUES ('e7r4joNaS8KnP8OliYfWfQ', 'NGR', '拟稿人选择', 'f/draftuserchoose', null, null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_WEBDISPLAY_CATEGORY" VALUES ('yWP2tpU-RkqjGQeDDVvCFg', 'HYSXZ', '会议室选择', 'f/meetingroomchoose', null, null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_WEBDISPLAY_CATEGORY" VALUES ('oN08r0gGTGm1xzCj5H-LzQ', 'CLSR', '车辆选择输入', 'f/wfchoosecarinput', '车辆管理使用', null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_WEBDISPLAY_CATEGORY" VALUES ('ATXs7GboTpyYXtxfTWiiRw', 'SJXZSR', '司机选择输入', 'f/wfchoosedriverinput', '车辆管理模块', null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_WEBDISPLAY_CATEGORY" VALUES ('Nlou8l5iTGW1FPV3exITWg', 'LWXZ', '礼物选择', 'f/giftapply', null, null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_WEBDISPLAY_CATEGORY" VALUES ('NPvKSIpLRiasE4lMoB-BcQ', 'XZWJ', '文件下载', 'f/downloadfile', null, null, null);
INSERT INTO "QYB_WORKFLOW"."WORKFLOW_WEBDISPLAY_CATEGORY" VALUES ('7qtNFeX6SH-uVdSP8vTIDA', 'BMLD', '部门领导', 'f/partleader', null, null, null);

-- ----------------------------
-- Indexes structure for table REL_TAB
-- ----------------------------

-- ----------------------------
-- Checks structure for table REL_TAB
-- ----------------------------
ALTER TABLE "QYB_WORKFLOW"."REL_TAB" ADD CHECK ("REL_ID" IS NOT NULL);
ALTER TABLE "QYB_WORKFLOW"."REL_TAB" ADD CHECK ("REL_ID" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table REL_TAB
-- ----------------------------
ALTER TABLE "QYB_WORKFLOW"."REL_TAB" ADD PRIMARY KEY ("REL_ID");

-- ----------------------------
-- Indexes structure for table SERVICE_AGREEMENT
-- ----------------------------

-- ----------------------------
-- Checks structure for table SERVICE_AGREEMENT
-- ----------------------------
ALTER TABLE "QYB_WORKFLOW"."SERVICE_AGREEMENT" ADD CHECK ("SERVICE_AGREEMENT_ID" IS NOT NULL);
ALTER TABLE "QYB_WORKFLOW"."SERVICE_AGREEMENT" ADD CHECK ("SERVICE_AGREEMENT_ID" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table SERVICE_AGREEMENT
-- ----------------------------
ALTER TABLE "QYB_WORKFLOW"."SERVICE_AGREEMENT" ADD PRIMARY KEY ("SERVICE_AGREEMENT_ID");

-- ----------------------------
-- Indexes structure for table SERVICE_SERVICEPRODUCT
-- ----------------------------

-- ----------------------------
-- Checks structure for table SERVICE_SERVICEPRODUCT
-- ----------------------------
ALTER TABLE "QYB_WORKFLOW"."SERVICE_SERVICEPRODUCT" ADD CHECK ("PRODUCT_ID" IS NOT NULL);
ALTER TABLE "QYB_WORKFLOW"."SERVICE_SERVICEPRODUCT" ADD CHECK ("PRODUCT_ID" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table SERVICE_SERVICEPRODUCT
-- ----------------------------
ALTER TABLE "QYB_WORKFLOW"."SERVICE_SERVICEPRODUCT" ADD PRIMARY KEY ("PRODUCT_ID");

-- ----------------------------
-- Indexes structure for table SERVICE_SLA_KPI
-- ----------------------------

-- ----------------------------
-- Checks structure for table SERVICE_SLA_KPI
-- ----------------------------
ALTER TABLE "QYB_WORKFLOW"."SERVICE_SLA_KPI" ADD CHECK ("KPI_ID" IS NOT NULL);
ALTER TABLE "QYB_WORKFLOW"."SERVICE_SLA_KPI" ADD CHECK ("KPI_ID" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table SERVICE_SLA_KPI
-- ----------------------------
ALTER TABLE "QYB_WORKFLOW"."SERVICE_SLA_KPI" ADD PRIMARY KEY ("KPI_ID");

-- ----------------------------
-- Indexes structure for table SERVICE_SLA_KPI_REL
-- ----------------------------

-- ----------------------------
-- Checks structure for table SERVICE_SLA_KPI_REL
-- ----------------------------
ALTER TABLE "QYB_WORKFLOW"."SERVICE_SLA_KPI_REL" ADD CHECK ("REL_ID" IS NOT NULL);
ALTER TABLE "QYB_WORKFLOW"."SERVICE_SLA_KPI_REL" ADD CHECK ("REL_ID" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table SERVICE_SLA_KPI_REL
-- ----------------------------
ALTER TABLE "QYB_WORKFLOW"."SERVICE_SLA_KPI_REL" ADD PRIMARY KEY ("REL_ID");

-- ----------------------------
-- Indexes structure for table SERVICE_SLA_SP_REL
-- ----------------------------

-- ----------------------------
-- Checks structure for table SERVICE_SLA_SP_REL
-- ----------------------------
ALTER TABLE "QYB_WORKFLOW"."SERVICE_SLA_SP_REL" ADD CHECK ("REL_ID" IS NOT NULL);
ALTER TABLE "QYB_WORKFLOW"."SERVICE_SLA_SP_REL" ADD CHECK ("REL_ID" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table SERVICE_SLA_SP_REL
-- ----------------------------
ALTER TABLE "QYB_WORKFLOW"."SERVICE_SLA_SP_REL" ADD PRIMARY KEY ("REL_ID");

-- ----------------------------
-- Indexes structure for table SERVICE_TAB
-- ----------------------------

-- ----------------------------
-- Checks structure for table SERVICE_TAB
-- ----------------------------
ALTER TABLE "QYB_WORKFLOW"."SERVICE_TAB" ADD CHECK ("TAB_ID" IS NOT NULL);
ALTER TABLE "QYB_WORKFLOW"."SERVICE_TAB" ADD CHECK ("TAB_ID" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table SERVICE_TAB
-- ----------------------------
ALTER TABLE "QYB_WORKFLOW"."SERVICE_TAB" ADD PRIMARY KEY ("TAB_ID");

-- ----------------------------
-- Indexes structure for table SERVICE_UC_SP_REL
-- ----------------------------

-- ----------------------------
-- Checks structure for table SERVICE_UC_SP_REL
-- ----------------------------
ALTER TABLE "QYB_WORKFLOW"."SERVICE_UC_SP_REL" ADD CHECK ("REL_ID" IS NOT NULL);
ALTER TABLE "QYB_WORKFLOW"."SERVICE_UC_SP_REL" ADD CHECK ("REL_ID" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table SERVICE_UC_SP_REL
-- ----------------------------
ALTER TABLE "QYB_WORKFLOW"."SERVICE_UC_SP_REL" ADD PRIMARY KEY ("REL_ID");

-- ----------------------------
-- Indexes structure for table SERVICE_UPDERPIN_CONTRACT
-- ----------------------------

-- ----------------------------
-- Checks structure for table SERVICE_UPDERPIN_CONTRACT
-- ----------------------------
ALTER TABLE "QYB_WORKFLOW"."SERVICE_UPDERPIN_CONTRACT" ADD CHECK ("UC_ID" IS NOT NULL);
ALTER TABLE "QYB_WORKFLOW"."SERVICE_UPDERPIN_CONTRACT" ADD CHECK ("UC_ID" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table SERVICE_UPDERPIN_CONTRACT
-- ----------------------------
ALTER TABLE "QYB_WORKFLOW"."SERVICE_UPDERPIN_CONTRACT" ADD PRIMARY KEY ("UC_ID");

-- ----------------------------
-- Indexes structure for table SERVICE_WORK_KLG_REL
-- ----------------------------

-- ----------------------------
-- Checks structure for table SERVICE_WORK_KLG_REL
-- ----------------------------
ALTER TABLE "QYB_WORKFLOW"."SERVICE_WORK_KLG_REL" ADD CHECK ("REL_ID" IS NOT NULL);
ALTER TABLE "QYB_WORKFLOW"."SERVICE_WORK_KLG_REL" ADD CHECK ("WORK_ID" IS NOT NULL);
ALTER TABLE "QYB_WORKFLOW"."SERVICE_WORK_KLG_REL" ADD CHECK ("KNOWLEDGE_ID" IS NOT NULL);
ALTER TABLE "QYB_WORKFLOW"."SERVICE_WORK_KLG_REL" ADD CHECK ("REL_ID" IS NOT NULL);
ALTER TABLE "QYB_WORKFLOW"."SERVICE_WORK_KLG_REL" ADD CHECK ("WORK_ID" IS NOT NULL);
ALTER TABLE "QYB_WORKFLOW"."SERVICE_WORK_KLG_REL" ADD CHECK ("KNOWLEDGE_ID" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table SERVICE_WORK_KLG_REL
-- ----------------------------
ALTER TABLE "QYB_WORKFLOW"."SERVICE_WORK_KLG_REL" ADD PRIMARY KEY ("REL_ID");

-- ----------------------------
-- Indexes structure for table SERVICE_WORK_REL
-- ----------------------------

-- ----------------------------
-- Checks structure for table SERVICE_WORK_REL
-- ----------------------------
ALTER TABLE "QYB_WORKFLOW"."SERVICE_WORK_REL" ADD CHECK ("REL_ID" IS NOT NULL);
ALTER TABLE "QYB_WORKFLOW"."SERVICE_WORK_REL" ADD CHECK ("WORK_ID" IS NOT NULL);
ALTER TABLE "QYB_WORKFLOW"."SERVICE_WORK_REL" ADD CHECK ("WWORK_ID" IS NOT NULL);
ALTER TABLE "QYB_WORKFLOW"."SERVICE_WORK_REL" ADD CHECK ("REL_ID" IS NOT NULL);
ALTER TABLE "QYB_WORKFLOW"."SERVICE_WORK_REL" ADD CHECK ("WORK_ID" IS NOT NULL);
ALTER TABLE "QYB_WORKFLOW"."SERVICE_WORK_REL" ADD CHECK ("WWORK_ID" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table SERVICE_WORK_REL
-- ----------------------------
ALTER TABLE "QYB_WORKFLOW"."SERVICE_WORK_REL" ADD PRIMARY KEY ("REL_ID");

-- ----------------------------
-- Indexes structure for table TA_OFFICE_ARCHHANDLE
-- ----------------------------

-- ----------------------------
-- Checks structure for table TA_OFFICE_ARCHHANDLE
-- ----------------------------
ALTER TABLE "QYB_WORKFLOW"."TA_OFFICE_ARCHHANDLE" ADD CHECK ("OID" IS NOT NULL);
ALTER TABLE "QYB_WORKFLOW"."TA_OFFICE_ARCHHANDLE" ADD CHECK ("OID" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table TA_OFFICE_ARCHHANDLE
-- ----------------------------
ALTER TABLE "QYB_WORKFLOW"."TA_OFFICE_ARCHHANDLE" ADD PRIMARY KEY ("OID");



-- ----------------------------
-- Indexes structure for table WORKFLOW_ACTIVITY_SETTING
-- ----------------------------

-- ----------------------------
-- Checks structure for table WORKFLOW_ACTIVITY_SETTING
-- ----------------------------
ALTER TABLE "QYB_WORKFLOW"."WORKFLOW_ACTIVITY_SETTING" ADD CHECK ("ID" IS NOT NULL);
ALTER TABLE "QYB_WORKFLOW"."WORKFLOW_ACTIVITY_SETTING" ADD CHECK ("ID" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table WORKFLOW_ACTIVITY_SETTING
-- ----------------------------
ALTER TABLE "QYB_WORKFLOW"."WORKFLOW_ACTIVITY_SETTING" ADD PRIMARY KEY ("ID");

-- ----------------------------
-- Indexes structure for table WORKFLOW_FORM
-- ----------------------------

-- ----------------------------
-- Checks structure for table WORKFLOW_FORM
-- ----------------------------
ALTER TABLE "QYB_WORKFLOW"."WORKFLOW_FORM" ADD CHECK ("FORM_ID" IS NOT NULL);
ALTER TABLE "QYB_WORKFLOW"."WORKFLOW_FORM" ADD CHECK ("FORM_ID" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table WORKFLOW_FORM
-- ----------------------------
ALTER TABLE "QYB_WORKFLOW"."WORKFLOW_FORM" ADD PRIMARY KEY ("FORM_ID");

-- ----------------------------
-- Indexes structure for table WORKFLOW_FORM_FIELD
-- ----------------------------

-- ----------------------------
-- Checks structure for table WORKFLOW_FORM_FIELD
-- ----------------------------
ALTER TABLE "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" ADD CHECK ("FIELD_ID" IS NOT NULL);
ALTER TABLE "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" ADD CHECK ("FIELD_ID" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table WORKFLOW_FORM_FIELD
-- ----------------------------
ALTER TABLE "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD" ADD PRIMARY KEY ("FIELD_ID");

-- ----------------------------
-- Indexes structure for table WORKFLOW_FORM_FIELD_PERMISSON
-- ----------------------------

-- ----------------------------
-- Checks structure for table WORKFLOW_FORM_FIELD_PERMISSON
-- ----------------------------
ALTER TABLE "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD_PERMISSON" ADD CHECK ("ID" IS NOT NULL);
ALTER TABLE "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD_PERMISSON" ADD CHECK ("ID" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table WORKFLOW_FORM_FIELD_PERMISSON
-- ----------------------------
ALTER TABLE "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD_PERMISSON" ADD PRIMARY KEY ("ID");

-- ----------------------------
-- Indexes structure for table WORKFLOW_FORM_FIELD_REL
-- ----------------------------

-- ----------------------------
-- Checks structure for table WORKFLOW_FORM_FIELD_REL
-- ----------------------------
ALTER TABLE "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD_REL" ADD CHECK ("REL_ID" IS NOT NULL);
ALTER TABLE "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD_REL" ADD CHECK ("REL_ID" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table WORKFLOW_FORM_FIELD_REL
-- ----------------------------
ALTER TABLE "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD_REL" ADD PRIMARY KEY ("REL_ID");

-- ----------------------------
-- Indexes structure for table WORKFLOW_FORM_FIELD_VALUE
-- ----------------------------

-- ----------------------------
-- Checks structure for table WORKFLOW_FORM_FIELD_VALUE
-- ----------------------------
ALTER TABLE "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD_VALUE" ADD CHECK ("ID" IS NOT NULL);
ALTER TABLE "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD_VALUE" ADD CHECK ("ID" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table WORKFLOW_FORM_FIELD_VALUE
-- ----------------------------
ALTER TABLE "QYB_WORKFLOW"."WORKFLOW_FORM_FIELD_VALUE" ADD PRIMARY KEY ("ID");

-- ----------------------------
-- Indexes structure for table WORKFLOW_RETRIEVAL_ITEM
-- ----------------------------

-- ----------------------------
-- Checks structure for table WORKFLOW_RETRIEVAL_ITEM
-- ----------------------------
ALTER TABLE "QYB_WORKFLOW"."WORKFLOW_RETRIEVAL_ITEM" ADD CHECK ("ITEM_ID" IS NOT NULL);
ALTER TABLE "QYB_WORKFLOW"."WORKFLOW_RETRIEVAL_ITEM" ADD CHECK ("ITEM_ID" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table WORKFLOW_RETRIEVAL_ITEM
-- ----------------------------
ALTER TABLE "QYB_WORKFLOW"."WORKFLOW_RETRIEVAL_ITEM" ADD PRIMARY KEY ("ITEM_ID");

-- ----------------------------
-- Indexes structure for table WORKFLOW_USERCONCERNED_SETTING
-- ----------------------------

-- ----------------------------
-- Checks structure for table WORKFLOW_USERCONCERNED_SETTING
-- ----------------------------
ALTER TABLE "QYB_WORKFLOW"."WORKFLOW_USERCONCERNED_SETTING" ADD CHECK ("ID" IS NOT NULL);
ALTER TABLE "QYB_WORKFLOW"."WORKFLOW_USERCONCERNED_SETTING" ADD CHECK ("ID" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table WORKFLOW_USERCONCERNED_SETTING
-- ----------------------------
ALTER TABLE "QYB_WORKFLOW"."WORKFLOW_USERCONCERNED_SETTING" ADD PRIMARY KEY ("ID");

-- ----------------------------
-- Indexes structure for table WORKFLOW_WEBDISPLAY_CATEGORY
-- ----------------------------

-- ----------------------------
-- Checks structure for table WORKFLOW_WEBDISPLAY_CATEGORY
-- ----------------------------
ALTER TABLE "QYB_WORKFLOW"."WORKFLOW_WEBDISPLAY_CATEGORY" ADD CHECK ("ID_" IS NOT NULL);
ALTER TABLE "QYB_WORKFLOW"."WORKFLOW_WEBDISPLAY_CATEGORY" ADD CHECK ("ID_" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table WORKFLOW_WEBDISPLAY_CATEGORY
-- ----------------------------
ALTER TABLE "QYB_WORKFLOW"."WORKFLOW_WEBDISPLAY_CATEGORY" ADD PRIMARY KEY ("ID_");

--dicttype
-- ----------------------------
-- Records of TD_SM_DICTTYPE
-- ----------------------------
INSERT INTO "TD_SM_DICTTYPE" VALUES ('A410D0B53F624DCA9D142EBE6727688F', '工作流_审核操作', null);
INSERT INTO "TD_SM_DICTTYPE" VALUES ('5C545E3AE5124D00B78D27F760F987B8', '工作流_事项分类', '工作流_事项分类');
INSERT INTO "TD_SM_DICTTYPE" VALUES ('607BA39427D14D86B4C90AD495CB270D', '工作流_通知方式', null);
INSERT INTO "TD_SM_DICTTYPE" VALUES ('CB4107C36DCC437C8E1E994B74069E06', '工作流_字段类别', null);

-- dictdata

INSERT INTO "TD_SM_DICTDATA" VALUES ('110B9E1FB9874EEB96F6FAAFA90A67BC', 'A410D0B53F624DCA9D142EBE6727688F', 'TY', '同意', null, '0');
INSERT INTO "TD_SM_DICTDATA" VALUES ('05F889168B984776947EBBEC423CF09A', 'CB4107C36DCC437C8E1E994B74069E06', 'WTXX', '问题信息', '3', '0');
INSERT INTO "TD_SM_DICTDATA" VALUES ('215105F8036C4F4CA43761072F2E678E', 'CB4107C36DCC437C8E1E994B74069E06', 'FBXX', '发布信息', '5', '0');
INSERT INTO "TD_SM_DICTDATA" VALUES ('276D8A7E26F84C1FA26990CC3DF85C2D', 'CB4107C36DCC437C8E1E994B74069E06', 'BGXX', '变更信息', '4', '0');
INSERT INTO "TD_SM_DICTDATA" VALUES ('62AB3FFC85A142F1AA8BFA0223AA7C0C', 'CB4107C36DCC437C8E1E994B74069E06', 'PZXX', '配置信息', '6', '0');
INSERT INTO "TD_SM_DICTDATA" VALUES ('6401D489F10E4737A64A9807BF3D0515', 'CB4107C36DCC437C8E1E994B74069E06', 'SJXX', '事件信息', '2', '0');
INSERT INTO "TD_SM_DICTDATA" VALUES ('795B2D4EC59A400A883A054E2DCEBD5F', '607BA39427D14D86B4C90AD495CB270D', 'phone_msg', '短信通知', '0', '1');
INSERT INTO "TD_SM_DICTDATA" VALUES ('1E5E464722E149AD8B30356090C8E42F', '607BA39427D14D86B4C90AD495CB270D', 'sys_inner_msg', '站内消息', null, '0');
INSERT INTO "TD_SM_DICTDATA" VALUES ('2F661B4973C64D01AF761DFBEA7F6F73', 'A410D0B53F624DCA9D142EBE6727688F', 'BTY', '不同意', null, '0');
INSERT INTO "TD_SM_DICTDATA" VALUES ('E6C9BFE92F434F7AA5125BDE6496EAB9', 'CB4107C36DCC437C8E1E994B74069E06', 'SQRXX', '申请人信息', '0', '0');
INSERT INTO "TD_SM_DICTDATA" VALUES ('7BE28607EA7241DAA6961741D5789E7C', 'A410D0B53F624DCA9D142EBE6727688F', 'QXZ', '请选择', null, '1');
INSERT INTO "TD_SM_DICTDATA" VALUES ('DD01B1BF585C4E8C9EF6DF53A26D719A', '5C545E3AE5124D00B78D27F760F987B8', 'CL', '车辆管理', null, '0');
INSERT INTO "TD_SM_DICTDATA" VALUES ('F76ECBFAED1A413C954923094A42D0D7', 'CB4107C36DCC437C8E1E994B74069E06', 'JBXX', '基本信息', '1', '0');
