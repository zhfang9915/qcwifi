package ltd.qcwifi.jobs.service;

import java.util.List;
import java.util.Map;

import ltd.qcwifi.jobs.model.Jobs;

/**
 * 任务服务
 */
public interface JobsService {
	/**
	 * 添加任务
	 */
	public int addJobs(Jobs jobs);

	/**
	 * 移除任务（可批量）
	 */
	public int removeJobs(String[] fIds);

	/**
	 * 更改任务
	 */
	public int updateJobs(Jobs job);

	/**
	 * 模糊查询任务
	 */
	public Map<String, Object> searchJobs(Map<String, Object> param);

	/**
	 * 根据fId查询任务
	 */
	public Jobs searchJobsById(String fId);

	/**
	 * 精确查询
	 */
	public List<Jobs> searchJob(Map<String, Object> param);
}
