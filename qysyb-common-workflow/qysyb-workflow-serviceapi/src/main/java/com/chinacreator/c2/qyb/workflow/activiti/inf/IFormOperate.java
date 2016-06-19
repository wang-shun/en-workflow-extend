package com.chinacreator.c2.qyb.workflow.activiti.inf;

import java.util.List;
import java.util.Map;

import com.chinacreator.c2.qyb.workflow.common.bean.WorkFlowActivity;
import com.chinacreator.c2.qyb.workflow.common.bean.WorkFlowTransition;


/**
 * 流程表单数据的操作
 * @author qiao.li
 * @version 1.0
 * @date 2016年3月29日
 * @param 
 */
public interface IFormOperate{
	/**
	 * 此方法发生在流程任务执行之前
	 * @param json 业务字符串
	 * @param businessKey 流程业务key
	 * @param proInsId 流程实例id
	 * @param moduleId 事项id即服务产品id
	 * @param curActivity 当前任务实体
	 * @param curUserId
	 * @return
	 * @throws Exception 
	 */
 	public int addOrUpdateEntity(String json,String businessKey,String proInsId,String moduleId,
 			WorkFlowActivity curActivity,String curUserId,WorkFlowActivity nextActivity,Map map) throws Exception;	/**
	 * 
	 * @param json
	 * @param businessKey
	 * @param proInsId
	 * @param moduleId
	 * @param lastActivity
	 * @param curUserId
	 * @return
	 */
 	public int addOrUpdateEntityAfterTaskExcu(String json,String businessKey,String proInsId,
 			String moduleId,WorkFlowActivity lastActivity,String curUserId,WorkFlowActivity nextActivity,Map map);	/**
	 * 获取entity接口
	 * @param json 业务字符串
	 * @param businessKey 流程业务key
	 * @param proInsId 流程实例id
	 * @param moduleId 事项id即服务产品id
	 * @param lastActivity 上个任务实体
	 * @param curUserId
	 * @return
	 */
 	public Map<String,Object> getEntityByBusinessKey(String businessKey,String proInsId,String moduleId,
 			WorkFlowActivity curActivity,String curUserId,Map map);	/**
	 * 获取用户需要自定义的 多实例处理人， 一般是指会签 需要哪些人处理
	 * @param businessJson  业务数据字符串
	 * @param businessKey 
	 * @param proInsId 流程实例id
	 * @param moduleId moduleId即服务产品id
	 * @param curActivity 当前任务
	 * @param nextActivity 下一步任务
	 * @param workFlowTransition 走的路径
	 * @return 
	 */
 	public List<String> getAssigneeList(String businessJson,String businessKey,String proInsId,
 			String moduleId,WorkFlowActivity curActivity,WorkFlowActivity nextActivity
 			,WorkFlowTransition workFlowTransition,String curUserId,Map map);	/**
	 * 
	 * @param businessJson 业务数据字符串
	 * @param businessKey
	 * @param proInsId 流程实例id
	 * @param moduleId moduleId即服务产品id
	 * @param curActivity 当前任务
	 * @param curTaskId 任务id
	 * @return key值为 WorkFlowService。TYPE_*  分别代表分派人 候选人 候选组 多个以逗号分隔 
	 */
 	public Map<String,String> getTaskHandler(String businessJson,String businessKey,String proInsId,
 			String moduleId,WorkFlowActivity lastActivity,WorkFlowActivity curActivity,
 			String curTaskId,String curUserId,Map map);	/**
	 * 
	 * @param businessJson  业务数据字符串
	 * @param businessKey 
	 * @param proInsId 流程实例id
	 * @param moduleId moduleId即服务产品id
	 * @param variables 当前流程变量
	 * @param curActivity 当前任务
	 * @param nextActivity 下一步任务
	 * @param workFlowTransition 走的路径
	 * @param curUserId
	 * @return 返回设置的流程变量Map
	 */
 	public Map<String,Object> setProsVariableBeforeTaskExcu(String businessJson,String businessKey,
 			String proInsId,String moduleId,Map variables,WorkFlowActivity curActivity,
 			WorkFlowActivity nextActivity,WorkFlowTransition workFlowTransition,
 			String curUserId,Map map);}
