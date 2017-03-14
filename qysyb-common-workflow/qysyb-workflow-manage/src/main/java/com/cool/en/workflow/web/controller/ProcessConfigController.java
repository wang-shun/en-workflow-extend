package com.cool.en.workflow.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.chinacreator.asp.comp.sys.advanced.job.dto.JobDTO;
import com.chinacreator.asp.comp.sys.advanced.job.service.JobService;
import com.chinacreator.c2.flow.api.WfManagerService;
import com.chinacreator.c2.qyb.workflow.config.entity.ActivityConfig;
import com.chinacreator.c2.qyb.workflow.config.impl.ActivityConfigService;

@RestController
@RequestMapping("workflow")
public class ProcessConfigController {
	@Autowired
	ActivityConfigService acs;
	WfManagerService s;
	@Autowired
	JobService jobService;

	@RequestMapping(value = "saveactivityconfig", method = RequestMethod.POST)
	public void saveActivityConfig(@RequestBody() ActivityConfig ac) {
		acs.saveActivityConfig(ac);
	}

	@RequestMapping(value = "getactivityconfig", method = RequestMethod.POST)
	public ActivityConfig getAcitivityConfig(@RequestBody() ActivityConfig ac) {
		return acs.getActivityConfigOne(ac);
	}

	@RequestMapping(value = "getjobs", method = RequestMethod.GET)
	public List<JobDTO> getJobs() {
		return jobService.queryAll();
	}
}
