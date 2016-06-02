package com.chinacreator.c2.omp.common.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.chinacreator.asp.comp.sys.advanced.org.service.OrgService;
import com.chinacreator.asp.comp.sys.basic.org.dto.OrgDTO;
import com.chinacreator.c2.ioc.ApplicationContextManager;

/** 
 * @author qiao.li 
 * @version 创建时间：2016年5月31日 上午10:12:05 
 * 类说明 
 */
public class CustomOrgService {
	@Autowired
	OrgService orgService;
	public List<OrgDTO> getOrgDTOByIds(String ids){
		orgService = ApplicationContextManager.getContext().getBean(OrgService.class);
		List<OrgDTO> list = new ArrayList<OrgDTO>();
		if(ids!=null){
			String[] listids = ids.split(",");
			for(String s:listids){
				list.add(orgService.queryByPK(s));
			}
		}
		return list;
	}
}
