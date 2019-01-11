package ltd.qcwifi.backend.jobs.service.impl;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ltd.qcwifi.model.entity.jobs.Jobs;
import ltd.qcwifi.system.service.UserService;

/**
 * Quartz调度管理器
 * 
 * @author Administrator
 * 
 */
@Component
public class QuartzManager {
    private static String GROUP_NAME = "EXTJWEB_GROUP_NAME";
//    private static String TRIGGER_GROUP_NAME = "EXTJWEB_TRIGGERGROUP_NAME";
    
    @Autowired
    Scheduler sched;
    
    @Autowired
	UserService userService;
    
	/**
	 * 添加一个定时任务，使用默认的任务组名，触发器名，触发器组名
	 * @param jobName
	 * @param cls
	 * @param time
	 * @throws SchedulerException 
	 */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void addJob(Jobs job, Class cls) throws SchedulerException {
		//这里获取任务信息数据  
		TriggerKey triggerKey = TriggerKey.triggerKey(job.getName(), GROUP_NAME);  
		CronTrigger trigger = (CronTrigger) sched.getTrigger(triggerKey);  
		if(trigger==null){  
		    System.out.println("trigger is null----");  
		    //不存在，创建一个  
		    JobDetail jobDetail = JobBuilder.newJob(cls).withIdentity(job.getName(), GROUP_NAME).build();  
		    //表达式调度构建器  
		    CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getSchedualTime());  
		    //按新的cronExpression表达式构建一个新的trigger  
		    trigger = TriggerBuilder.newTrigger().withIdentity(job.getName(), GROUP_NAME).withSchedule(scheduleBuilder).build();  
		    sched.scheduleJob(jobDetail,trigger);  
		}else{  
		    // Trigger已存在，那么更新相应的定时设置  
		    //表达式调度构建器  
		    CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getSchedualTime());  
		    //按新的cronExpression表达式重新构建trigger  
		    trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();  
		    //按新的trigger重新设置job执行  
		    sched.rescheduleJob(triggerKey, trigger);  
		}
    }
    
    /**
     * 修改一个任务的触发时间(使用默认的任务组名，触发器名，触发器组名)
     * @param job
     */
    public void modifyJobTime(Jobs job) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(job.getName(), GROUP_NAME);
            
            //构造任务触发器
            Trigger trigger =  TriggerBuilder.newTrigger()
                    .withIdentity(job.getName(), GROUP_NAME)
                    .withSchedule(CronScheduleBuilder.cronSchedule(job.getSchedualTime()))
                    .build(); 
            
            sched.rescheduleJob(triggerKey, trigger);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    /**
     * 移除一个任务(使用默认的任务组名，触发器名，触发器组名)
     * @param job
     */
    public void removeJob(Jobs job) {
    	 try {
             TriggerKey triggerKey = TriggerKey.triggerKey(job.getName(), GROUP_NAME);
             
             sched.pauseTrigger(triggerKey);//停止触发器  
             
             sched.unscheduleJob(triggerKey);//移除触发器
             
             JobKey jobKey = JobKey.jobKey(job.getName(), GROUP_NAME);
             
             sched.deleteJob(jobKey);//删除作业
         } catch (SchedulerException e) {
             e.printStackTrace();
         }
    }


    /**
     * 启动所有定时任务
     */
    public void startJobs() {
        try {
        	sched.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 关闭所有定时任务
     */
    public void shutdownJobs() {
        try {
            if (!sched.isShutdown()) {
                sched.shutdown();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}