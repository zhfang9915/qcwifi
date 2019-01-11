package ltd.qcwifi.backend.listener;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import ltd.qcwifi.backend.jobs.service.JobsService;
import ltd.qcwifi.backend.jobs.service.impl.QuartzManager;
import ltd.qcwifi.model.entity.jobs.Jobs;

@Component
public class StartTaskListener implements ApplicationListener<ApplicationEvent> {
	
	private static int flag = 0;
	
	@Autowired
	private JobsService jobsServiceImpl;

	@Autowired
	QuartzManager quartzManager;

	// spring加载后执行
	public void onApplicationEvent(ApplicationEvent arg0) {
		// 防止开启多次导致报错
		flag++;
		if (flag > 1) {
			return;
		}
		try {
			List<Jobs> jobs = jobsServiceImpl.searchJobs(null);
			for (Jobs job : jobs) {
				if ("1".equals(job.getCurrentStatus())) {
					System.out.println(job.getName() + "-->" + job.getClassName());
					quartzManager.addJob(job, Class.forName(job.getClassName()));
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
