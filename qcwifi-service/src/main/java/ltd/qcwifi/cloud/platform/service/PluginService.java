package ltd.qcwifi.cloud.platform.service;

import ltd.qcwifi.model.dto.ExecuteResult;
import ltd.qcwifi.model.entity.cloud.platform.Plugin;

/**
 * 推送模版 服务接口
 * @author 张芳
 *
 */
public interface PluginService {

	ExecuteResult<Plugin> selectPluginByMD5(String md5);
	
	Plugin getPluginByGccv(String gccv);
}
