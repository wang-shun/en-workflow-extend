package com.chinacreator.c2.omp.common.service;
/**
 *定时任务公共服务
 */
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;

import com.chinacreator.c2.omp.common.ScheduleJob;
import com.chinacreator.c2.omp.common.service.inf.SchedulerTaskService;

public class SchedulerTaskServiceImpl implements SchedulerTaskService{
	
	public void createOrEditTimeTask(ScheduleJob job,Scheduler scheduler,Class<? extends Job> clazz) throws SchedulerException{
		TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobId(), job.getJobGroup());
		//获取trigger，即在spring配置文件中定义的 bean id="myTrigger"
		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
		//不存在，创建一个
		if (null == trigger) {
			JobDetail jobDetail = JobBuilder.newJob(clazz)
					.withIdentity(job.getJobId(), job.getJobGroup()).build();
			jobDetail.getJobDataMap().put("scheduleJob", job);
			//表达式调度构建器
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job
					.getCronExpression());
			//按新的cronExpression表达式构建一个新的trigger
			trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobId(), job.getJobGroup()).withSchedule(scheduleBuilder).build();
			scheduler.scheduleJob(jobDetail, trigger);
		} else {
			// Trigger已存在，那么更新相应的定时设置
			//表达式调度构建器
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job
					.getCronExpression());
			//按新的cronExpression表达式重新构建trigger
			trigger = trigger.getTriggerBuilder().withIdentity(triggerKey)
					.withSchedule(scheduleBuilder).build();
			//按新的trigger重新设置job执行
			scheduler.rescheduleJob(triggerKey, trigger);
		}
	}
	
	public void createTimeTask(ScheduleJob job,Scheduler scheduler,Class<? extends Job> clazz) throws SchedulerException{
			TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobId(), job.getJobGroup());
			//获取trigger，即在spring配置文件中定义的 bean id="myTrigger"
			CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
			//不存在，创建一个
			JobDetail jobDetail = JobBuilder.newJob(clazz)
					.withIdentity(job.getJobId(), job.getJobGroup()).build();
			jobDetail.getJobDataMap().put("scheduleJob", job);
			//表达式调度构建器
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job
					.getCronExpression());
			//按新的cronExpression表达式构建一个新的trigger
			trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobId(), job.getJobGroup()).withSchedule(scheduleBuilder).build();
			scheduler.scheduleJob(jobDetail, trigger);
		
	}
	
	public void editTimeTask(ScheduleJob job,Scheduler scheduler) throws SchedulerException{
			TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobId(), job.getJobGroup());
			//获取trigger，即在spring配置文件中定义的 bean id="myTrigger"
			CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
				// Trigger已存在，那么更新相应的定时设置
			//表达式调度构建器
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job
					.getCronExpression());
			//按新的cronExpression表达式重新构建trigger
			trigger = trigger.getTriggerBuilder().withIdentity(triggerKey)
					.withSchedule(scheduleBuilder).build();
			//按新的trigger重新设置job执行
			scheduler.rescheduleJob(triggerKey, trigger);
	}
	
	public CronTrigger creatTrigger(ScheduleJob job,Scheduler scheduler) throws SchedulerException{
		TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobId(), job.getJobGroup());
		//获取trigger，即在spring配置文件中定义的 bean id="myTrigger"
		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
		return trigger;
	}

	@Override
	public boolean removeTrigger(ScheduleJob job, Scheduler scheduler)
			throws SchedulerException {
		TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobId(), job.getJobGroup());
		scheduler.resumeTrigger(triggerKey);
		scheduler.unscheduleJob(triggerKey);//移除触发器   
		return true;
	}
}
