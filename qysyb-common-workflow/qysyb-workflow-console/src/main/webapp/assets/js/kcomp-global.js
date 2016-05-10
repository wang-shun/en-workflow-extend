//性能趋势图颜色变量v
var kcompglobal={};
var maxcolor = '#FB0418';
var avgcolor = '#0178d7';
var mincolor = '#20DF20';
kcompglobal.avgminmaxcolor = [avgcolor,mincolor,maxcolor];
/*工单字段约定key*/
var WORKNOKEY = "workNo";				//工单编号KEY
var SERVICETYPEIDKEY = "serviceTypeId"; //服务分类条件mapkey
var NEEDUNFINISHED = "needUnFinished";	//需要未完成工单传值条件mapkey
var NEEDINVOLVEDKEY = "needInvolved";	//需要本人涉及到的工单条件mapkey
/*服务分类 对应系统字典真实值*/
var SERVICETYPE_PZGD = "PZD";
var SERVICETYPE_SJGD = "eventworkflow";
/*操作工单action*/
kcompglobal.workoperate = {};
kcompglobal.workoperate.concernthiswork = "关注此工单";	//关注此工单
kcompglobal.workoperate.concernthisactivityinthiswork = "关注此工单此环节";	//关注此工单此环节
kcompglobal.workoperate.concerntheproduct = "关注此服务产品";		//关注此服务产品
kcompglobal.workoperate.concernthisactivityintheproduct = "关注此服务产品此环节";	//关注此服务产品此环节
/*工单关注状态*/
kcompglobal.workconcernstatus = {};
kcompglobal.workconcernstatus.YGZGD = "已关注此工单";		//已关注工单
kcompglobal.workconcernstatus.YGZFWCP = "已关注此服务";		//已关注服务产品
/*工单通知方式，字典 工作流_通知方式*/
kcompglobal.workinformtype = {};
kcompglobal.workinformtype.email = {};
kcompglobal.workinformtype.email.value = "email";
kcompglobal.workinformtype.email.name = "邮件通知";
kcompglobal.workinformtype.phonemsg = {};
kcompglobal.workinformtype.phonemsg.value = "phonemsg";
kcompglobal.workinformtype.phonemsg.name = "短信通知";