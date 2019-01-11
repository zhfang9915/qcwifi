package ltd.qcwifi.jobs.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import ltd.qcwifi.jobs.dao.JobsDao;
import ltd.qcwifi.jobs.jobs.CommonJob;
import ltd.qcwifi.jobs.model.Jobs;
import ltd.qcwifi.jobs.utils.ParameterUtil;
import ltd.qcwifi.jobs.utils.QuartzManager;

@Service
public class JobsServiceImpl implements JobsService {
	
	@Autowired
	private JobsDao jobsDao;
	
	@Autowired
	private SchedulerFactoryBean schedulerFactoryBean;

	/**
	 * 添加任务
	 */
	public int addJobs(Jobs jobs) {
		// 添加的状态为1则开启任务
		if ("1".equals(jobs.getfCurrentStatus())) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("fClassName", jobs.getfClassName());
			try {
				QuartzManager.addJob(schedulerFactoryBean.getScheduler(), jobs.getfName(), CommonJob.class,
						jobs.getfSchedualTime(), param);
			} catch (Exception e) {
				return 0;
			}

		}
		// 将任务添加到数据库
		return jobsDao.addJobs(jobs);
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
		Jobs j = this.searchJobsById(job.getfId());
		int flag = 0;

		// 判断任务状态，如果在运行中则移除旧任务
		if ("1".equals(j.getfCurrentStatus())) {
			QuartzManager.removeJob(schedulerFactoryBean.getScheduler(), j.getfName());
		}
		// 更改数据库
		flag = jobsDao.updateJobs(job);
		// 查询出新的任务
		Jobs j2 = this.searchJobsById(job.getfId());
		try {
			// 如果更改状态为开启任务则执行添加
			if ("1".equals(j2.getfCurrentStatus())) {
				// 添加更改之后的任务
				// 将接口别名存至map，传到Job实现类的execute方法中
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("fClassName", j2.getfClassName());
				Class commJob = CommonJob.class;
				QuartzManager.addJob(schedulerFactoryBean.getScheduler(), j2.getfName(), commJob, j2.getfSchedualTime(),
						param);
			}
		} catch (Exception e) {
			// 如果添加任务失败,则改回原来的数据库；
			jobsDao.updateJobs(j);
			flag = 0;
		}

		return flag;
	}

	/**
	 * 查询任务
	 */
	public Map<String, Object> searchJobs(Map<String, Object> param) {
		// 设置分页
		PageHelper.startPage(
				param.get(ParameterUtil.page) == null ? 1 : Integer.valueOf(param.get(ParameterUtil.page).toString()),
				param.get(ParameterUtil.rows) == null ? 20 : Integer.valueOf(param.get(ParameterUtil.rows).toString()));
		// 查询数据
		List<Jobs> list = jobsDao.searchJobs(param);
		// 获取分页信息
		PageInfo<Jobs> pageInfo = new PageInfo<Jobs>(list);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(ParameterUtil.records, pageInfo.getTotal()); // 总信息条数
		result.put(ParameterUtil.page, pageInfo.getPageNum()); // 当前页数
		result.put(ParameterUtil.total, pageInfo.getPages()); // 总页数
		result.put(ParameterUtil.rows, list); // 分页数据
		return result;

	}

	/**
	 * 模糊查询
	 */
	public Jobs searchJobsById(String fId) {
		return jobsDao.searchJobsById(fId);
	}

	/**
	 * 精确查询
	 */
	public List<Jobs> searchJob(Map<String, Object> param) {
		return jobsDao.searchJob(param);
	}
}
