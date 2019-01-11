package ltd.qcwifi.dao.cloud.platform;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import ltd.qcwifi.base.dao.BaseDao;
import ltd.qcwifi.model.dto.ExecuteResult;
import ltd.qcwifi.model.dto.param.FirmwareParam;
import ltd.qcwifi.model.entity.cloud.platform.Firmware;

/**
 * 固件持久化
 * 	开发时请 继承  BaseDao<T, PK>，其中提供了基础的CRUD方法
 * @author 张芳
 *
 */
public interface FirmwareDao extends BaseDao<Firmware, FirmwareParam> {
	/**
	 * 根据MD5获取固件信息
	 * @param md5
	 * @return
	 */
	public Firmware selectOneByMD5(@Param("md5")String md5);
	
	Firmware selectOneByBakMD5(@Param("md5")String md5);
	/**
	 * 获取GCC版本信息
	 * @param void
	 * @return
	 */	
	public List<String> searchCrosstool();
	/**
	 * 根据version获取固件信息
	 * @param version
	 * @return
	 */
	public Firmware selectByVersion(String version);
	/**
	 * 根据version获取GCC版本信息
	 * @param version
	 * @return
	 */
	public String searchCrosstoolByVersion(String version);
}
