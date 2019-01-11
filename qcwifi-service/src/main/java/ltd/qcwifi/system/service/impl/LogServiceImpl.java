package ltd.qcwifi.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mysql.fabric.xmlrpc.base.Struct;

import ltd.qcwifi.dao.system.LogDao;
import ltd.qcwifi.model.dto.TableResult;
import ltd.qcwifi.model.dto.param.ApiLogParam;
import ltd.qcwifi.model.entity.cloud.platform.ShopRouter;
import ltd.qcwifi.model.entity.log.ApiLog;
import ltd.qcwifi.system.service.LogService;

@Service
public class LogServiceImpl implements LogService {

	@Autowired
	LogDao logDao;
	
	@Override
	@Async
	public void saveApiLog(ApiLog log) {
		logDao.insertApiLog(log);
	}
	
	@Override
	public TableResult<ApiLog> getApiLogs(ApiLogParam param) {
		TableResult<ApiLog> result = new TableResult<>();
		// 设置分页
		PageHelper.startPage(param.getPageNumber(), param.getPageSize());
		// 查询数据
		List<ApiLog> logs = logDao.selectApiLog(param);
		// 获取分页信息
		PageInfo<ApiLog> pageInfo = new PageInfo<ApiLog>(logs);
		result.setRows(logs);
		result.setTotal(pageInfo.getTotal());
		return result;
	}

}
