package ltd.qcwifi.jobs.jobs;

import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import ltd.qcwifi.protocal.QcwifiService;
import ltd.qcwifi.protocal.factory.SpiExtensionFactory;

public class CommonJob implements Job{
	
	private static SpiExtensionFactory spiExtensionFactory;
	static{
		spiExtensionFactory = new SpiExtensionFactory();
	}
	
	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		
		Map<String, Object>  param = context.getJobDetail().getJobDataMap();
		
		//获取接口别名
		String interfaceName = (String)param.get("fClassName");
		
		//获取接口对象instance
		QcwifiService service = spiExtensionFactory.getExtensionInstances(interfaceName);
		
		//调用接口
		service.execute("");
		
		System.out.println("=================运行"+interfaceName+"任务=============================");
	}

}
