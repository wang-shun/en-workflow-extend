package com.chinacreator.c2.omp.common.service.inf;

public abstract class MessageServiceRunnable implements Runnable {
	public abstract void sendMessageToThread();
	public void run() {
		sendMessageToThread();
	}
}
