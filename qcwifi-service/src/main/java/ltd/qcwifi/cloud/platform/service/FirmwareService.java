package ltd.qcwifi.cloud.platform.service;

import java.util.List;

import ltd.qcwifi.model.dto.ExecuteResult;
import ltd.qcwifi.model.entity.cloud.platform.Firmware;

/**
 * 推送模版 服务接口
 * @author 张芳
 *
 */
public interface FirmwareService {
	
	public ExecuteResult<Firmware> selectByMD5(String md5);
	
	public ExecuteResult<List<String>> searchCrosstool();
	
	public Firmware selectByVersion(String version);
	
	public String searchCrosstoolByVersion(String version);
	
	public Firmware selectById(Object id);

	public boolean deleteFirwmare(Object id);
	
	public boolean createFirmware(Firmware firmware);
	
	public boolean selectFirmwareByMD5(String md5);
	
	boolean selectFirmwareByBakMD5(String md5);
}
