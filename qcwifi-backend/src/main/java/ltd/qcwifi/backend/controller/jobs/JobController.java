package ltd.qcwifi.backend.controller.jobs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import ltd.qcwifi.backend.controller.BaseController;
import ltd.qcwifi.backend.jobs.service.JobsService;
import ltd.qcwifi.backend.jobs.util.ParameterUtil;
import ltd.qcwifi.comm.utils.RandCodeUtil;
import ltd.qcwifi.comm.utils.SysDbPkUtil;
import ltd.qcwifi.model.dto.ExecuteResult;
import ltd.qcwifi.model.dto.TableResult;
import ltd.qcwifi.model.dto.param.JobParam;
import ltd.qcwifi.model.entity.User;
import ltd.qcwifi.model.entity.jobs.Jobs;
import ltd.qcwifi.shiro.util.SimpleHashUtil;

@RequestMapping("/job")
@Controller
public class JobController extends BaseController {
	
	@Autowired
	JobsService jobService;

	/**
	 * 转发至jsp
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String loadIndex() {
		return "jobs/job";
	}
	
	@RequestMapping(value = "/list/pages", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public TableResult<Jobs> query4page(@RequestBody JobParam param) {
		// 设置分页
		PageHelper.startPage(param.getPageNumber(), param.getPageSize());
		// 查询数据
		List<Jobs> list = jobService.searchJobs(param);
		// 获取分页信息
		PageInfo<Jobs> pageInfo = new PageInfo<Jobs>(list);
		TableResult<Jobs> result = new TableResult<>();
		result.setRows(list);
		result.setTotal(pageInfo.getTotal());
		return result;
	}
	
	/**
	 * 创建定时任务
	 * @return
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public ExecuteResult<String> createJob(Jobs jobs) {
		jobs.setId(SysDbPkUtil.createPk("JOB"));
		if (jobService.addJobs(jobs)) {
			return new ExecuteResult<>("创建定时任务成功");
		}
		return new ExecuteResult<>(false, "创建定时任务失败");
	}
	
	/**
	 * 更新任务
	 * @param job
	 * @return
	 */
	@RequestMapping(value = "/cron", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public ExecuteResult<String> updateCron(Jobs job) {
		int flag = jobService.updateJobs(job);
		if (flag > 0) {
			return new ExecuteResult<>("设置cron成功");
		}
		return new ExecuteResult<>(false, "设置cron失败");
	}
	
	/**
	 * 启动/停止任务
	 * @param job
	 * @return
	 */
	@RequestMapping(value = "/{id}/status/{status}", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public ExecuteResult<String> updateCron(@PathVariable("id")String id, @PathVariable("status")String status) {
		Jobs job = new Jobs();
		job.setId(id);
		job.setCurrentStatus(status);
		int flag = jobService.updateJobs(job);
		if (flag > 0) {
			return new ExecuteResult<>("操作成功");
		}
		return new ExecuteResult<>(false, "操作失败");
	}
}
