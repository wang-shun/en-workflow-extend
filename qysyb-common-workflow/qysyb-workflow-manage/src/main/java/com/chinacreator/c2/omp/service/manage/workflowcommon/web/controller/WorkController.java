package com.chinacreator.c2.omp.service.manage.workflowcommon.web.controller;

import java.io.BufferedReader;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.chinacreator.c2.dao.Dao;
import com.chinacreator.c2.dao.DaoFactory;
import com.chinacreator.c2.flow.WfApiFactory;
import com.chinacreator.c2.flow.detail.WfOperator;
import com.chinacreator.c2.flow.detail.WfResult;
import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.omp.service.manage.workflowcommon.service.WorkFlowService;
import com.chinacreator.c2.omp.service.manage.workflowcommon.service.WorkProcess;
import com.chinacreator.c2.web.controller.ResponseFactory;

/**
 * 
 * 流程
 * @author qiao.li
 * @version 1.0
 * @date 2015-9-9
 */
@Controller
public class WorkController {
	@Autowired
	private WorkFlowService workFlowService;
	@Autowired
	private WorkProcess wp;
	/**
	 * 启动流程
	 * @return
	 */
	@RequestMapping(value = "flow/startflow", method = RequestMethod.POST)
	public Object startWorkFlow(HttpServletRequest request, HttpServletResponse response){	
		try{
			return wp.startWorkFlow(parseParams(request));
		}catch(Exception e){
			ResponseFactory responseFactory = new ResponseFactory();
			return responseFactory.createResponseBodyException(e);
		}
	}	
	/**
	 * 自有流
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "flow/goanywhere", method = RequestMethod.POST)
	public Object goAnyWhere(HttpServletRequest request, HttpServletResponse response){
		try{
			return wp.goAnyWhereTakeTransition(parseParams(request));
		}catch(Exception e){
			ResponseFactory responseFactory = new ResponseFactory();
			return responseFactory.createResponseBodyException(e);
		}
	}
	

	/**
	 * 回退流程活动，注意:如果被驳回活动有多个来源活动,且没有制定目标驳回活动的时候,随机驳回
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "flow/reject", method = RequestMethod.POST)
	@Transactional
	public Object rejectImp(HttpServletRequest request, HttpServletResponse response){
		try{
			return workFlowService.goAnyWhere(request, response);
		}catch(Exception e){
			ResponseFactory responseFactory = new ResponseFactory();
			return responseFactory.createResponseBodyException(e);
		}
	}
	/**
	 * 把流程请求的参数解析成map
	 * @param request
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map<String,Object> parseParams(HttpServletRequest request){
		Map<String,Object> result = new HashMap<String,Object>();
		Map map = request.getParameterMap();
		//get 方式获取参数
		if(map!=null&&map.size()>0){
			Set<String> ketSet = map.keySet();
			for(String key:ketSet){
				result.put(key, ((String[])map.get(key))[0]);
			}
		/*http post方法得到参数*/
		}else if(map==null||map.size()==0){
			StringBuffer jb = new StringBuffer();
			String line = null;
			try {				
				BufferedReader reader = request.getReader();
				while ((line = reader.readLine()) != null){
					jb.append(line);
				}					
				map = JSONObject.parseObject(jb.toString(),Map.class);
				Set<String> ketSet = map.keySet();
				for(String key:ketSet){
					if(key.equals("variables")||key.equals("wfOperator")||key.equals("entity")||key.equals("transition")){
						result.put(key,((JSONObject) map.get(key)).toString());
					}else{
						result.put(key, map.get(key));
					}				
				}
/*				formId = (String) map.get("formId");
				isStart = map.get("isStart").toString();
				entity = ((JSONObject) map.get("entity")).toString();
				wfOperatorStr = ((JSONObject) map.get("wfOperator")).toString();
				bussinessKey = ((String) map.get("businessKey"));
				currenTaskId = ((String) map.get("currenTaskId"));
				destTaskDefinitionKey = ((String) map.get("destTaskDefinitionKey"));
				processDefinitionId = ((String) map.get("processDefinitionId"));
				opinion = ((String) map.get("opinion"));
				proInsId = ((String) map.get("proInsId"));	
				transitionId = ((String) map.get("transitionId"));	
				variablesStr = ((JSONObject) map.get("variables")).toString();*/
				return result;
			}catch(Exception e){
				e.printStackTrace();
				throw new RuntimeException();
			}			
		}
		return result;
	}
}
