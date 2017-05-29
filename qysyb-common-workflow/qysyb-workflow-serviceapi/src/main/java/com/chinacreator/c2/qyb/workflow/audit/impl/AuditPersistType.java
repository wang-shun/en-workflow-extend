package com.chinacreator.c2.qyb.workflow.audit.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.task.Task;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chinacreator.c2.qyb.workflow.activiti.impl.WorkProcess;
import com.chinacreator.c2.qyb.workflow.activiti.impl.WorkProcessEasy;
import com.chinacreator.c2.qyb.workflow.activiti.inf.IPersistType;
import com.chinacreator.c2.qyb.workflow.audit.entity.Archhandle;
@Component
public class AuditPersistType implements IPersistType{

	@Override
	public String getName() {
		return "AUDIT_OPINION";
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map parseTypeValue(Object value, Object field, Task task, Map entityOld) {
		Map result = new HashMap();
		Map fieldm = (Map) field;
		String webDisplayType = (String) fieldm.get("webDisplayType");
		String key = (String) fieldm.get("fieldNo");
		if(WorkProcessEasy.UI_TYPE_AUDIT_NO.equals(webDisplayType)){
			if(value instanceof List){
				List v = (List) value;
				Map o = (Map) v.get(0);
				String jsonStrng = JSON.toJSONString(o);
				//转换一下 判断一些值的正确性
				Archhandle archhandle = JSON.parseObject(jsonStrng, Archhandle.class);
				if(archhandle.getAuditState() == null){
					throw new RuntimeException("审核状态不能为空");
				}
				result.put(WorkProcess.INLINE_AUDIT_KEY, o);				
			}
		}else{
			if(value instanceof List){
				List v = (List) value;
				Map o = (Map) v.get(0);
				String jsonStrng = JSON.toJSONString(o);
				//转换一下 判断一些值的正确性
				Archhandle archhandle = JSON.parseObject(jsonStrng, Archhandle.class);
				if(archhandle.getAuditState() == null){
					throw new RuntimeException("审核状态不能为空");
				}
				o.put("local",true);
				result.put(key, v);				
			}

		}
		return result;
	}

}
