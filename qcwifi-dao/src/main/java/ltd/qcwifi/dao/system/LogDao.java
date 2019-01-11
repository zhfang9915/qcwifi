package ltd.qcwifi.dao.system;

import java.util.List;

import ltd.qcwifi.model.dto.param.ApiLogParam;
import ltd.qcwifi.model.entity.log.ApiLog;

public interface LogDao {

	/**
	 * 插入API日志
	 * 
	 * @param userId
	 * @return
	 */
	void insertApiLog(ApiLog log);

	/**
	 * 查询SPI日志
	 * @Title: selectApiLog
	 * @author: zhfang
	 * @Description: TODO
	 * @param invokeApi
	 * @param startTime
	 * @param endTime
	 * @return
	 * @return: List<ApiLog>
	 */
	List<ApiLog> selectApiLog(ApiLogParam param);
}
