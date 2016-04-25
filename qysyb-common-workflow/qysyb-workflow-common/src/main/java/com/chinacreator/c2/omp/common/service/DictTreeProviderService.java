package com.chinacreator.c2.omp.common.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.chinacreator.asp.comp.sys.basic.dict.dto.DictDataDTO;
import com.chinacreator.asp.comp.sys.basic.dict.dto.DictTypeDTO;
import com.chinacreator.asp.comp.sys.basic.dict.service.DictDataService;
import com.chinacreator.asp.comp.sys.basic.dict.service.DictTypeService;
import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.web.ds.TreeContentProvider;
import com.chinacreator.c2.web.ds.TreeContext;
import com.chinacreator.c2.web.ds.TreeNode;
import com.chinacreator.c2.web.ds.impl.DefaultTreeNode;

public class DictTreeProviderService implements TreeContentProvider {

	public TreeNode[] getElements(TreeContext context) {
		List<DefaultTreeNode> list = new ArrayList<DefaultTreeNode>();
		DictDataService dictDataService = ApplicationContextManager
				.getContext().getBean(DictDataService.class);
		DictTypeService dictTypeService = ApplicationContextManager
				.getContext().getBean(DictTypeService.class);
		if (null != context) {
			Map<String, Object> map = context.getConditions();
			String rootName = (String) map.get("rootName");
			if (null != rootName && !rootName.trim().equals("")) {
				DictTypeDTO dictTypeDTO = new DictTypeDTO();
				dictTypeDTO.setDicttypeName(rootName);
				List<DictTypeDTO> tdtos = dictTypeService
						.queryByDictType(dictTypeDTO);
				if (!tdtos.isEmpty()) {
					DictDataDTO dictData = new DictDataDTO();
					dictData.setDicttypeId(tdtos.get(0).getDicttypeId());
					List<DictDataDTO> dtos = dictDataService
							.queryByDictData(dictData);

					DefaultTreeNode root = new DefaultTreeNode("root", rootName);
					list.add(root);
					root.setParent(true);
					for (DictDataDTO dto : dtos) {

						DefaultTreeNode serviceNode = new DefaultTreeNode(
								dto.getDictdataId(), dto.getDictdataName());
						serviceNode.setId(dto.getDictdataId());
						serviceNode.setName(dto.getDictdataValue());
						serviceNode.setParent(false);
						serviceNode.setPid("root");
						list.add(serviceNode);
					}
				}
			}
		}
		return (TreeNode[]) list.toArray(new DefaultTreeNode[list.size()]);
	}
}
