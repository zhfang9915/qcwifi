package ltd.qcwifi.jobs.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import ltd.qcwifi.jobs.model.Jobs;

/**
 * 任务dao层
 */
@Repository
public interface JobsDao {
	/**
	 * 添加任务
	 */
	int addJobs(Jobs jobs);
	
	/**
	 * 移除任务（可批量）
	 */
	int removeJobs(String[] fIds);
	
	/**
	 * 更改任务
	 */
	int updateJobs(Jobs job);
	
	/**
	 * 根据fId查询任务
	 */
	Jobs searchJobsById(String fId); 
	
	/**
	 * 查询任务
	 */
	List<Jobs> searchJobs(Map<String,Object> param);
	
	/**
	 * 精确查询
	 */
	List<Jobs> searchJob(Map<String,Object> param);
}
