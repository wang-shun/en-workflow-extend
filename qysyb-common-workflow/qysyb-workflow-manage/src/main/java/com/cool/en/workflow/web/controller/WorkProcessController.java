package com.cool.en.workflow.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chinacreator.c2.flow.WfApiFactory;
import com.chinacreator.c2.flow.Exception.C2FlowRuntimeException;
import com.chinacreator.c2.flow.api.WfFormService;
import com.chinacreator.c2.flow.api.WfManagerService;
import com.chinacreator.c2.flow.api.WfRepositoryService;
import com.chinacreator.c2.flow.detail.WfActivity;
import com.chinacreator.c2.flow.detail.WfModuleBean;
import com.chinacreator.c2.flow.detail.WfProcessConfigProperty;
import com.chinacreator.c2.flow.detail.WfProcessDefinition;
import com.chinacreator.c2.qyb.workflow.activiti.impl.WorkProcess;
import com.chinacreator.c2.workflow.api.WfExtendService;

@RestController
@RequestMapping("flow")
public class WorkProcessController {
	@Autowired
	WorkProcess workProcess;
	private WfManagerService wfManagerService;
	private WfRepositoryService wfRepositoryService;
	@Autowired
	private WfExtendService wfExtendService;
	// private WfExtendService wfExtendService = (WfExtendService)
	// ApplicationContextManager.getContext()
	// .getBean(WfExtendService.class);
	// private WfRuntimeService wfRuntimeService = WfApiFactory
	// .getWfRuntimeService();
	// private WfHistoryService wfHistoryService = WfApiFactory
	// .getWfHistoryService();
	private WfFormService wfFormService;

	public void startProcess() {

	}

	@RequestMapping(value = "getstartinfo", method = RequestMethod.GET)
	public Map<String, Object> getStartInfo(@RequestParam() String moduleId) throws Exception {

		Map<String, Object> paramMap = new HashMap<String, Object>();

		// 启动流程
		// Map<String, String> body = new HashMap<String, String>();
		// body.put("moduleCode", moduleId);
		//
		WfModuleBean wfModuleBean = wfExtendService.queryByMenuCode(moduleId);
		// if (null == wfModuleBean)
		// throw new C2FlowRuntimeException("不存在流程事项菜单[" + moduleId + "]");
		//
		// String moduleId = wfModuleBean.getId();

		WfProcessDefinition wfProcessDefinition = this.getWfManagerService().getBindProcessByModuleId(moduleId);
		if (null == wfProcessDefinition)
			throw new C2FlowRuntimeException("事项菜单[" + wfModuleBean.getName() + "]还未和任何流程定义进行绑定！");
		String processDefinitionId = wfProcessDefinition.getId();
		paramMap.put("processDefinitionId", processDefinitionId);
		List<WfActivity> wfActivityList = this.getWfRepositoryService().getActivitiesByDefinition(processDefinitionId);
		for (WfActivity wfActivity : wfActivityList) {
			if ("startEvent".equals(wfActivity.getProperties().get("type"))) {
				paramMap.put("startActivity", wfActivity);
			}
		}
		// WfProcessConfigProperty wfProcessConfigProperty =
		// findProcessStartConfig(moduleId, processDefinitionId);
		// if (null == wfProcessConfigProperty ||
		// StringUtils.isEmpty(wfProcessConfigProperty.getBindForm()))
		// throw new C2FlowRuntimeException("事项[" + wfModuleBean.getName() +
		// "]起始节点表单配置为空，无法自动进入启动表单！");
		//
		// String alias = wfProcessConfigProperty.getAlias();
		// String bindForm = wfProcessConfigProperty.getBindForm();
		// String configId = wfProcessConfigProperty.getConfigId();
		// Integer duration = wfProcessConfigProperty.getDuration();
		// String durationUnit = wfProcessConfigProperty.getDurationUnit();
		// String performer = wfProcessConfigProperty.getPerformer();
		// String taskDefKey = wfProcessConfigProperty.getTaskDefKey();
		// String groupPerformer = wfProcessConfigProperty.getGroupPerformer();
		//
		// if (!StringUtils.isEmpty(bindForm)) {
		// paramMap.put("bindForm", bindForm);
		// }
		//
		// if (!StringUtils.isEmpty(taskDefKey)) {
		// paramMap.put("taskDefKey", taskDefKey);
		// }
		//
		// if (!StringUtils.isEmpty(groupPerformer)) {
		// paramMap.put("groupPerformer", groupPerformer);
		// }
		//
		// if (!StringUtils.isEmpty(configId)) {
		// paramMap.put("configId", configId);
		// }
		//
		// if (!StringUtils.isEmpty(alias)) {
		// paramMap.put("alias", alias);
		// }
		// if (!StringUtils.isEmpty(duration)) {
		// paramMap.put("duration", duration);
		// }
		//
		// if (!StringUtils.isEmpty(durationUnit)) {
		// paramMap.put("durationUnit", durationUnit);
		// }
		//
		// if (!StringUtils.isEmpty(performer)) {
		// paramMap.put("performer", performer);
		// }
		//
		// if (!StringUtils.isEmpty(processDefinitionId)) {
		// paramMap.put("processDefinitionId", processDefinitionId);
		// }
		//
		// if (!StringUtils.isEmpty(moduleId)) {
		// paramMap.put("moduleId", moduleId);
		// }

		return paramMap;
	}

	public WfProcessConfigProperty findProcessStartConfig(String moduleId, String processDefinitionId)
			throws Exception {

		WfProcessConfigProperty wfProcessConfigProperty = null;
		List<WfActivity> wfActivityList = this.getWfRepositoryService().getActivitiesByDefinition(processDefinitionId);
		for (WfActivity wfActivity : wfActivityList) {
			if ("startEvent".equals(wfActivity.getProperties().get("type"))) {
				wfProcessConfigProperty = this.getWfManagerService().findProcessConfigProperty(processDefinitionId,
						moduleId, wfActivity.getId());
			}
		}

		// 如果流程定义图中配置了表单，如果外围配置没配置表单，可以用流程定义中的表单，但是外围配置表单优先级>流程定义的表单
		if (null == wfProcessConfigProperty) {
			wfProcessConfigProperty = new WfProcessConfigProperty();
		}

		if (StringUtils.isEmpty(wfProcessConfigProperty.getBindForm())) {
			// 以流程定义中的表单为准
			String bindFormInDefinition = this.getWfFormService().getStartFormKey(processDefinitionId);
			wfProcessConfigProperty.setBindForm(bindFormInDefinition);
		}
		return wfProcessConfigProperty;
	}

	private WfManagerService getWfManagerService() {
		if (this.wfManagerService == null) {
			this.wfManagerService = WfApiFactory.getWfManagerService();

		}
		return this.wfManagerService;
	}

	private WfRepositoryService getWfRepositoryService() {
		if (this.wfRepositoryService == null) {
			this.wfRepositoryService = WfApiFactory.getWfRepositoryService();

		}
		return this.wfRepositoryService;
	}

	private WfFormService getWfFormService() {
		if (this.wfFormService == null) {
			this.wfFormService = WfApiFactory.getWfFormService();

		}
		return this.wfFormService;
	}
}
