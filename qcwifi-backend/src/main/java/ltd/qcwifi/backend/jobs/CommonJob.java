package ltd.qcwifi.backend.jobs;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.alibaba.fastjson.JSONObject;

import ltd.qcwifi.shiro.util.SpringContextUtil;
import ltd.qcwifi.system.service.UserService;
import ltd.qcwifi.system.service.impl.UserServiceImpl;

public class CommonJob implements Job{
	
	@Autowired
	UserService userService;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		//通过上下文获取  
	    JobKey jobKey = context.getJobDetail().getKey();  
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");    
	    System.out.println("CommonJob："+ jobKey + " 在 " + dateFormat.format(new Date())+" 时运行");
	    if(userService==null){  
            System.out.println("注入不成功------");  
        }else{  
            System.out.println("注入成功------");  
            List<Long> ls = userService.selectUserIdByRoleId(1L);
    		System.out.println(JSONObject.toJSON(ls));
        }  
	}

}
