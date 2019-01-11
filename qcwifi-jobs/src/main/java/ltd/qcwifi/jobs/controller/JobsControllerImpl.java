package ltd.qcwifi.jobs.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import ltd.qcwifi.jobs.model.Jobs;
import ltd.qcwifi.jobs.service.JobsService;
import ltd.qcwifi.jobs.utils.ParameterUtil;
import ltd.qcwifi.jobs.utils.SysDbPkUtil;
@Controller
public class JobsControllerImpl implements JobsController{

	@Autowired
	private JobsService jobsService;
	/**
	 * 添加任务
	 */
	public Map<String, Object> addJobs(HttpServletRequest request,Jobs jobs) {
		jobs.setfId(SysDbPkUtil.createPk("jobs"));
		int flag = jobsService.addJobs(jobs);
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("result", flag);
		return result;
	}

	/**
	 * 移除任务（可批量）
	 */
	public Map<String, Object> removeJobs(HttpServletRequest request) {

		return null;
	}

	/**
	 * 更改任务
	 */
	public String updateJobs(HttpServletRequest request,Jobs job) {
		//如果状态为null则只更改属性
		if((job.getfCurrentStatus()==null||"".equals(job.getfCurrentStatus()))&&(job.getfSchedualTime()==null||"".equals(job.getfSchedualTime()))){
			//判断是否为空
			if(job.getfClassName()==null||"".equals(job.getfClassName())){
				return "nullClassName";
			}
			if(job.getfName()==null||"".equals(job.getfName())){
				return "nullName";
			}
			if(job.getfJobDesc()==null||"".equals(job.getfJobDesc())){
				return "nullDesc";
			}
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("fName", job.getfName());
			if(jobsService.searchJob(param).size()>0){
				return "reName";
			}
		}
		
		int flag = jobsService.updateJobs(job);
		
		return flag+"";
	}


	/**
	 * 查询任务
	 */
	public Map<String, Object> searchJobs(HttpServletRequest request) {
		Map<String,Object> param = ParameterUtil.getParameterMap(request);
		Map<String,Object> result = jobsService.searchJobs(param);
		return result;
	}

	/**
	 * 转发至jsp
	 */
	public String loadIndex() {

		return "jobs/jobs";
	}

	/**
	 * 精确查询
	 */
	public List<Jobs> searchJob(HttpServletRequest request) {
		Map<String,Object> param = ParameterUtil.getParameterMap(request);
		return jobsService.searchJob(param);
	
	}


	

}
