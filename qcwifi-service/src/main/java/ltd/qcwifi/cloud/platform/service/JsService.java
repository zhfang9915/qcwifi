package ltd.qcwifi.cloud.platform.service;

import java.util.List;

import ltd.qcwifi.model.dto.ExecuteResult;
import ltd.qcwifi.model.dto.TableResult;
import ltd.qcwifi.model.dto.param.JscodeParam;
import ltd.qcwifi.model.entity.cloud.platform.Jscode;

/**
 * 推送模版 服务接口
 * @author 张芳
 *
 */
public interface JsService {

	TableResult<Jscode> tables(JscodeParam param);

	ExecuteResult<String> create(Jscode jscode);

	ExecuteResult<String> delete(Object id);

	ExecuteResult<String> batchDelete(List<Jscode> jscode);

	ExecuteResult<Jscode> searchById(Object id);

	ExecuteResult<String> update(Jscode jscode);

	/**
	 * 获取默认的JS
	 * @Title: getDefaultJS
	 * @author: zhfang
	 * @Description: TODO
	 * @return
	 * @return: Jscode
	 */
	Jscode getDefaultJS();
	
}
