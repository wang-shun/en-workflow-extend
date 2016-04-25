package com.chinacreator.c2.omp.service.manage.workflowcommon.Inform.inf;


public abstract class EmailTask extends Thread{
	public abstract void init();
	public abstract void sendEmail();
	@Override
	public void run() {
		// TODO Auto-generated method stub
		sendEmail();
	}
}
