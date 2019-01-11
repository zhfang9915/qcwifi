package ltd.qcwifi.backend.jobs.service;

import java.util.List;
import java.util.Map;

import ltd.qcwifi.model.dto.param.JobParam;
import ltd.qcwifi.model.entity.jobs.Jobs;

/**
 * 任务服务
 */
public interface JobsService {
	/**
	 * 添加任务
	 */
	boolean addJobs(Jobs jobs);

	/**
	 * 移除任务（可批量）
	 */
	int removeJobs(String[] fIds);

	/**
	 * 更改任务
	 */
	int updateJobs(Jobs job);

	/**
	 * 模糊查询任务
	 */
//	Map<String, Object> searchJobs(JobParam param);

	/**
	 * 根据fId查询任务
	 */
	Jobs searchJobsById(String fId);

	/**
	 * 精确查询
	 */
	List<Jobs> searchJobs(JobParam param);
}
