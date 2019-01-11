package ltd.qcwifi.jobs.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ltd.qcwifi.jobs.model.Jobs;

@RequestMapping("/jobs")
public interface JobsController {
	/**
	 * 添加任务
	 */
	@ResponseBody
	@RequestMapping("/addJobs")
	public Map<String, Object> addJobs(HttpServletRequest request, Jobs job);

	/**
	 * 移除任务（可批量）
	 */
	@ResponseBody
	@RequestMapping("/removeJobs")
	public Map<String, Object> removeJobs(HttpServletRequest request);

	/**
	 * 更改任务
	 */
	@ResponseBody
	@RequestMapping("/updateJobs")
	public String updateJobs(HttpServletRequest request, Jobs job);

	/**
	 * 查询任务
	 */
	@ResponseBody
	@RequestMapping("/searchJobs")
	public Map<String, Object> searchJobs(HttpServletRequest request);
	

	@RequestMapping("/index")
	public String loadIndex() throws Exception;

	/**
	 * 精确查询
	 */
	@RequestMapping("/searchJob")
	@ResponseBody
	public List<Jobs> searchJob(HttpServletRequest request);

}
