package ltd.qcwifi.jobs.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import ltd.qcwifi.jobs.jobs.CommonJob;
import ltd.qcwifi.jobs.model.Jobs;
import ltd.qcwifi.jobs.service.JobsService;
@Component
public class StartTaskListener2 implements ApplicationListener<ApplicationEvent>{
	private static int flag = 0;
	@Autowired
	private JobsService jobsServiceImpl;
	@Autowired
	private SchedulerFactoryBean schedulerFactoryBean;
	
	//spring加载后执行
	public void onApplicationEvent(ApplicationEvent arg0) {
		//防止开启多次导致报错
		flag++;
		if(flag>1){
			return;
		}
		Map<String,Object> param = new HashMap<String,Object>();
		Scheduler sched = schedulerFactoryBean.getScheduler();
		try {
			List<Map<String, Object>> list = (List<Map<String, Object>> )jobsServiceImpl.searchJobs(param).get(ParameterUtil.rows);
			for(int i = 0; i < list.size(); i++){
				Jobs job = (Jobs)list.get(i);
				String status = job.getfCurrentStatus();
				if("1".equals(status)){
					//将接口别名存至map
					param.put("fClassName", job.getfClassName());
					QuartzManager.addJob(sched, job.getfName(), CommonJob.class, job.getfSchedualTime(),param);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	

}
