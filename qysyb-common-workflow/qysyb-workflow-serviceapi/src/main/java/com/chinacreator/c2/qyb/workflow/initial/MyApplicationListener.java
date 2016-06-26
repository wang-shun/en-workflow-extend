package com.chinacreator.c2.qyb.workflow.initial;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.chinacreator.c2.qyb.workflow.inform.impl.MyActivitiEventListener;

public class MyApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		// TODO Auto-generated method stub
		if(event.getApplicationContext().getParent() == null){//root application context 没有parent，他就是老大.
			//需要执行的逻辑代码，当spring容器初始化完成后就会执行该方法。
			RuntimeService runtimeService = event.getApplicationContext().getBean(RuntimeService.class);
			ActivitiEventListener listenerToAdd = new MyActivitiEventListener();
			runtimeService.addEventListener(listenerToAdd);
			
/*			Template con = new Template();
			con.setCode(InformService.TEMPLATE_COMMENT_INFORM_NO);
			Dao<Template> dao = DaoFactory.create(Template.class);
			Template tem = dao.selectOne(con);
			if(tem==null){
				String temStr = ""
				con.setTemplate(template);
			}
			String template = templateObj.getTemplate();*/
		}		
	}

}
