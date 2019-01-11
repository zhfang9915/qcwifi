package ltd.qcwifi.backend.jobs.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import ltd.qcwifi.backend.jobs.service.JobsService;
import ltd.qcwifi.backend.jobs.util.ParameterUtil;
import ltd.qcwifi.dao.jobs.JobsDao;
import ltd.qcwifi.model.dto.param.JobParam;
import ltd.qcwifi.model.entity.jobs.Jobs;

@Service
public class JobsServiceImpl implements JobsService {
	
	@Autowired
	private JobsDao jobsDao;
	
	@Autowired
	QuartzManager quartzManager;

	/**
	 * 添加任务
	 */
	@Override
	public boolean addJobs(Jobs jobs) {
		// 添加的状态为1则开启任务
		if ("1".equals(jobs.getCurrentStatus())) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("fClassName", jobs.getClassName());
			try {
				quartzManager.addJob(jobs, Class.forName(jobs.getClassName()));
				// 将任务添加到数据库
				int count = jobsDao.addJobs(jobs);
				if (count == 1) {
					return true;
				}
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}

	/**
	 * 移除任务
	 */
	public int removeJobs(String[] fIds) {

		return 0;
	}

	/**
	 * 更改任务
	 */
	public int updateJobs(Jobs job) {
		// 根据fId查询原任务
		Jobs j = this.searchJobsById(job.getId());
		int flag = 0;

		// 判断任务状态，如果在运行中则移除旧任务
		if ("1".equals(j.getCurrentStatus())) {
			quartzManager.removeJob(j);
		}
		// 更改数据库
		flag = jobsDao.updateJobs(job);
		// 查询出新的任务
		Jobs j2 = this.searchJobsById(job.getId());
		try {
			// 如果更改状态为开启任务则执行添加
			if ("1".equals(j2.getCurrentStatus())) {
				// 添加更改之后的任务
				// 将接口别名存至map，传到Job实现类的execute方法中
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("fClassName", j2.getClassName());
				quartzManager.addJob(j2, Class.forName(j2.getClassName()));
			}
		} catch (Exception e) {
			// 如果添加任务失败,则改回原来的数据库；
			jobsDao.updateJobs(j);
			flag = 0;
		}

		return flag;
	}

	/**
	 * 分页查询任务
	 */
//	public Map<String, Object> searchJobs(JobParam param) {
//		// 设置分页
//		PageHelper.startPage(
//				param.get(ParameterUtil.page) == null ? 1 : Integer.valueOf(param.get(ParameterUtil.page).toString()),
//				param.get(ParameterUtil.rows) == null ? 20 : Integer.valueOf(param.get(ParameterUtil.rows).toString()));
//		// 查询数据
//		List<Jobs> list = jobsDao.searchJobs(param);
//		// 获取分页信息
//		PageInfo<Jobs> pageInfo = new PageInfo<Jobs>(list);
//		Map<String, Object> result = new HashMap<String, Object>();
//		result.put(ParameterUtil.records, pageInfo.getTotal()); // 总信息条数
//		result.put(ParameterUtil.page, pageInfo.getPageNum()); // 当前页数
//		result.put(ParameterUtil.total, pageInfo.getPages()); // 总页数
//		result.put(ParameterUtil.rows, list); // 分页数据
//		return result;
//
//	}

	/**
	 * ID查询
	 */
	public Jobs searchJobsById(String fId) {
		return jobsDao.searchJobsById(fId);
	}

	/**
	 * 精确查询
	 */
	public List<Jobs> searchJobs(JobParam param) {
		return jobsDao.searchJobs(param);
	}

}
