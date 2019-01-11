package ltd.qcwifi.dao.cloud.platform;

import org.apache.ibatis.annotations.Param;

import ltd.qcwifi.base.dao.BaseDao;
import ltd.qcwifi.model.dto.param.PluginParam;
import ltd.qcwifi.model.entity.cloud.platform.Plugin;

/**
 * 插件持久化
 * @author 张芳
 *
 */
public interface PluginDao extends BaseDao<Plugin, PluginParam> {

	/**
	 * 根据MD5获取插件信息
	 * @param md5
	 * @return
	 */
	Plugin selectOneByMD5(@Param("md5")String md5);
	
	Plugin selectByGccv(@Param("gccv")String gccv);
}
