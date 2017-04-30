package com.cool.en.workflow.form.web.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chinacreator.c2.qyb.workflow.form.entity.Form;
import com.chinacreator.c2.qyb.workflow.form.entity.FormField;
import com.chinacreator.c2.qyb.workflow.form.impl.FormService;

@RestController
@RequestMapping("formmgt")
public class FormmgtController {
	@Autowired
	FormService formService;

	public void delFormFieldRel(String formId, String[] relIds) {
		formService.delFormFieldRel(formId, relIds);
	}

	@RequestMapping(value = "addformfieldrel", method = RequestMethod.POST)
	public int addFormFieldRel(@RequestParam(required = false) String fieldType, @RequestParam() String formId,
			@RequestBody() String[] fieldIds) {
		return formService.addFormFieldRel(fieldType, formId, fieldIds);
	}

	@RequestMapping(value = "getformdata", method = RequestMethod.GET)
	public Map<String, Object> getFormData(@RequestParam() String formId,
			@RequestParam(required = false) boolean filter, @RequestParam(required = false) String businessKey,
			@RequestParam(required = false) String proInsId) {
		return formService.getFormDataByFkFromProcessVariable(formId, filter ? false : filter, businessKey, proInsId);
	}

	@RequestMapping(value = "getformfield", method = RequestMethod.POST)
	public Map<String, List<Map>> getFormFieldByType(@RequestBody() JSONObject jsonObject) {
		String formId = jsonObject.getString("formId");
		JSONArray array = jsonObject.getJSONArray("fieldType");
		String[] fieldType = null;
		if (array != null) {
			array.toArray(new String[0]);
		}
		boolean isClassify = jsonObject.getBooleanValue("isClassify");
		return formService.getFormField(formId, fieldType, isClassify);
	}

	@RequestMapping(value = "getallform", method = RequestMethod.GET)
	public List<Form> getAllForm() {
		return formService.getAllForm();
	}

	@RequestMapping(value = "sortFormFieldRel", method = RequestMethod.POST)
	public void sortFormFieldRel(@RequestBody() JSONObject jsonObject) {
		int pageInt = jsonObject.getInteger("page");
		int rowNumInt = jsonObject.getInteger("rowNum");
		String[] ids = jsonObject.getJSONArray("ids").toArray(new String[0]);
		formService.sortFormFieldRel(pageInt, rowNumInt, ids);
	}
}
