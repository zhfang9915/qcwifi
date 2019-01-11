package ltd.qcwifi.system.service;

import ltd.qcwifi.model.dto.TableResult;
import ltd.qcwifi.model.dto.param.ApiLogParam;
import ltd.qcwifi.model.entity.log.ApiLog;

public interface LogService {

	/**
	 * 保存SPI日志
	 * @Title: save
	 * @author: zhfang
	 * @Description: TODO
	 * @param log
	 * @return: void
	 */
	void saveApiLog(ApiLog log);

	/**
	 * 查询API日志
	 * @Title: getApiLogs
	 * @author: zhfang
	 * @Description: TODO
	 * @param param
	 * @return
	 * @return: TableResult<ApiLog>
	 */
	TableResult<ApiLog> getApiLogs(ApiLogParam param);
}
