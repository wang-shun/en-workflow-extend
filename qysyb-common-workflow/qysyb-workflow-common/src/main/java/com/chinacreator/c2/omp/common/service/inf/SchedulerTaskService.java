package com.chinacreator.c2.omp.common.service.inf;
/**
 * 定时任务公共服务接口
 */
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;

import com.chinacreator.c2.omp.common.ScheduleJob;



//构建定时任务公共服务
public interface SchedulerTaskService {
	/**
	 * 创建或修改定时任务
	 * @param job
	 * @param scheduler
	 * @param clazz
	 * @throws SchedulerException
	 */
	public void createOrEditTimeTask(ScheduleJob job,Scheduler scheduler,Class<? extends Job> clazz) throws SchedulerException;
	/**
	 * 创建定时任务
	 * @param job
	 * @param scheduler
	 * @param clazz
	 * @throws SchedulerException
	 */
	
	public void createTimeTask(ScheduleJob job,Scheduler scheduler,Class<? extends Job> clazz) throws SchedulerException;
	/**
	 * 修改定时任务
	 * @param job
	 * @param scheduler
	 * @throws SchedulerException
	 */
	
	public void editTimeTask(ScheduleJob job,Scheduler scheduler) throws SchedulerException;
	/**
	 * 创建定时器
	 * @param job
	 * @param scheduler
	 * @return
	 * @throws SchedulerException
	 */
	
	public CronTrigger creatTrigger(ScheduleJob job,Scheduler scheduler) throws SchedulerException;
	/**
	 * 移除定时任务
	 * @param job
	 * @param scheduler
	 * @return
	 * @throws SchedulerException
	 */
	
	public boolean removeTrigger(ScheduleJob job,Scheduler scheduler) throws SchedulerException;
}
