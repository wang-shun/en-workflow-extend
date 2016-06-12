package com.chinacreator.c2.qyb.workflow.inform.inf;

import java.util.Map;

import com.chinacreator.asp.comp.sys.advanced.user.service.UserService;
import com.chinacreator.asp.comp.sys.core.user.dto.UserDTO;
import com.chinacreator.c2.ioc.ApplicationContextManager;

/** 
 * @author qiao.li 
 * @version 创建时间：2016年5月23日 下午2:52:24 
 * 类说明 
 */
public abstract class InformTask extends Thread {
	public UserDTO userTo;
	protected String userIdTo;
	protected UserDTO userFrom;
	protected String userIdFrom;
	protected Map contentMap;
	UserService userService = ApplicationContextManager.getContext().getBean(UserService.class); 
	public abstract void init();
	public abstract void doInform() throws Exception;
	public InformTask(String userIdTo,String userIdFrom,Map content){
		this.userIdTo = userIdTo;
		this.userIdFrom = userIdFrom;
		this.contentMap = content;
	}
	public InformTask(UserDTO userTo,UserDTO userFrom,Map content){
		this.userTo = userTo;
		this.userFrom = userFrom;
		this.contentMap = content;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			doInform();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	

	@Override
	public int hashCode(){                 //hashCode主要是用来提高hash系统的查询效率。当hashCode中不进行任何操作时，可以直接让其返回 一常数，或者不进行重写。
		int result = getName().hashCode();
		result = 29 * result +this.contentMap.hashCode();
		return result;
	}
	public void ensureUserDTOToInitialized(){
		if(userTo==null&&userIdTo!=null){
			userTo = userService.queryByPK(userIdTo);	
		}		
	}
	protected void ensureUserDTOFromInitialized(){
		if(userFrom==null&&userIdFrom!=null){
			userFrom = userService.queryByPK(userIdFrom);	
		}	
	}
}
