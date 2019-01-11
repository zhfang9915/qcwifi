package ltd.qcwifi.jobs.utils;

import java.util.Map;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;

/**
 * Quartz调度管理器
 * 
 * @author Administrator
 * 
 */
public class QuartzManager {
    private static String GROUP_NAME = "EXTJWEB_GROUP_NAME";
//    private static String TRIGGER_GROUP_NAME = "EXTJWEB_TRIGGERGROUP_NAME";

    /**
     * @Description: 添加一个定时任务，使用默认的任务组名，触发器名，触发器组名
     * 
     * @param sched
     *            调度器
     * 
     * @param jobName
     *            任务名
     * @param cls
     *            任务
     * @param time
     *            时间设置，参考quartz说明文档
     *            
     * 
     * @Title: QuartzManager.java
     */
    public static void addJob(Scheduler sched, String jobName, @SuppressWarnings("rawtypes") Class cls, String time) {
        try {
        	JobDetail jobDetail = JobBuilder.newJob(cls) 
                    .withIdentity(jobName, GROUP_NAME)
                    .build();  
        	
            // 触发器
        	CronScheduleBuilder builder = CronScheduleBuilder.cronSchedule(time); 
        	Trigger trigger = TriggerBuilder.newTrigger()
        			.withIdentity(jobName, GROUP_NAME).startNow()  
		            .withSchedule(builder)  
		            .build();  
        	sched.scheduleJob(jobDetail, trigger);
            // 启动
            if (!sched.isShutdown()) {
                sched.start();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * @Description: 添加一个定时任务，使用默认的任务组名，触发器名，触发器组名
     * 
     * @param sched
     *            调度器
     * 
     * @param jobName
     *            任务名
     * @param cls
     *            任务
     * @param time
     *            时间设置，参考quartz说明文档
     *            
     * @param  paramMap  传入参数MAP
     * 
     * @Title: QuartzManager.java
     */
    public static void addJob(Scheduler sched, String jobName, @SuppressWarnings("rawtypes") Class cls, String time,Map paramMap) {
        try {
        	JobDetail jobDetail = JobBuilder.newJob(cls) 
                    .withIdentity(jobName, GROUP_NAME)
                    .build();  
        	
            // 触发器
        	CronScheduleBuilder builder = CronScheduleBuilder.cronSchedule(time); 
        	Trigger trigger = TriggerBuilder.newTrigger()
        			.withIdentity(jobName, GROUP_NAME).startNow()  
		            .withSchedule(builder)  
		            .build();  
        	//传入参数
        	if(paramMap != null && paramMap.size() > 0){
        		jobDetail.getJobDataMap().putAll(paramMap);
        	}
        	sched.scheduleJob(jobDetail, trigger);
            // 启动
            if (!sched.isShutdown()) {
                sched.start();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description: 修改一个任务的触发时间(使用默认的任务组名，触发器名，触发器组名)
     * 
     * @param sched
     *            调度器
     * @param jobName
     * @param time
     * 
     * @Title: QuartzManager.java
     */
    @SuppressWarnings("rawtypes")
    public static void modifyJobTime(Scheduler sched, String jobName, String time) {
        try {
            TriggerKey tk = TriggerKey.triggerKey(jobName, GROUP_NAME);
            //构造任务触发器
            Trigger trg =  TriggerBuilder.newTrigger()
                    .withIdentity(jobName, GROUP_NAME)
                    .withSchedule(CronScheduleBuilder.cronSchedule(time))
                    .build();       
            sched.rescheduleJob(tk, trg);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    /**
     * @Description: 移除一个任务(使用默认的任务组名，触发器名，触发器组名)
     * 
     * @param sched
     *            调度器
     * @param jobName
     * 
     * @Title: QuartzManager.java
     */
    public static void removeJob(Scheduler sched, String jobName) {
    	 try {
             TriggerKey tk = TriggerKey.triggerKey(jobName, GROUP_NAME);
             sched.pauseTrigger(tk);//停止触发器  
             sched.unscheduleJob(tk);//移除触发器
             JobKey jobKey = JobKey.jobKey(jobName, GROUP_NAME);
             sched.deleteJob(jobKey);//删除作业
         } catch (SchedulerException e) {
             e.printStackTrace();
         }
    }


    /**
     * @Description:启动所有定时任务
     * 
     * @param sched
     *            调度器
     * 
     * @Title: QuartzManager.java
     */
    public static void startJobs(Scheduler sched) {
        try {
            sched.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description:关闭所有定时任务
     * 
     * 
     * @param sched
     *            调度器
     * 
     * 
     * @Title: QuartzManager.java
     */
    public static void shutdownJobs(Scheduler sched) {
        try {
            if (!sched.isShutdown()) {
                sched.shutdown();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}