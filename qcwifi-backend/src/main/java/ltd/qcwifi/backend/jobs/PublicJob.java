package ltd.qcwifi.backend.jobs;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;

public class PublicJob implements Job{
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		//通过上下文获取  
	    JobKey jobKey = context.getJobDetail().getKey();  
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");    
	    System.out.println("PublicJob："+ jobKey + " 在 " + dateFormat.format(new Date())+" 时运行");
	    
	}

}
