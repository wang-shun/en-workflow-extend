package com.chinacreator.c2.qyb.workflow.read.provider;

import java.util.List;
import java.util.Map;

import com.chinacreator.c2.dao.Dao;
import com.chinacreator.c2.dao.DaoFactory;
import com.chinacreator.c2.dao.mybatis.enhance.Page;
import com.chinacreator.c2.dao.mybatis.enhance.Pageable;
import com.chinacreator.c2.dao.mybatis.enhance.RowBounds4Page;
import com.chinacreator.c2.qyb.workflow.read.entity.ProcInsReadRecordMapper;
import com.chinacreator.c2.web.ds.ArrayContentProvider;
import com.chinacreator.c2.web.ds.ArrayContext;

public class SendReadArrayContentProvider implements ArrayContentProvider {

	@Override
	public Page<?> getElements(ArrayContext context) {
		Pageable pageable = context.getPageable();
		Dao<Object> dao = DaoFactory.create(Object.class);
		ProcInsReadRecordMapper mapper = dao.getSession().getMapper(ProcInsReadRecordMapper.class);
		RowBounds4Page rb = new RowBounds4Page(context.getPageable(), context.getSortable(), null, true);
		List<Map> list = mapper.getMyPages(context.getCondition(), rb);
		Page<Map> page = new Page<Map>(pageable.getPageIndex(), pageable.getPageSize(), rb.getTotalSize(), list);
		return page;
	}

}
