package ltd.qcwifi.cloud.platform.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import ltd.qcwifi.cloud.platform.service.FirmwareService;
import ltd.qcwifi.dao.cloud.platform.FirmwareDao;
import ltd.qcwifi.model.dto.ExecuteResult;
import ltd.qcwifi.model.dto.TableResult;
import ltd.qcwifi.model.dto.param.FirmwareParam;
import ltd.qcwifi.model.entity.cloud.platform.ExtensionTemplate;
import ltd.qcwifi.model.entity.cloud.platform.Firmware;
import ltd.qcwifi.service.impl.AbstractBaseServiceImpl;

/**
 * 推送模版服务实现类
 * @author 张芳
 *
 */
@Service("firmwareService")
public class FirmwareServiceImpl 
	extends AbstractBaseServiceImpl<Firmware, FirmwareParam> 
	implements FirmwareService {

	@Autowired
	FirmwareDao firmwareDao;
	
	@Override
	public TableResult<Firmware> tables(FirmwareParam param) {
		TableResult<Firmware> result = new TableResult<>();
		// 设置分页
		PageHelper.startPage(param.getPageNumber(), param.getPageSize());
		// 查询数据
		List<Firmware> firmwares = firmwareDao.selectByPage(param);
		// 获取分页信息
		PageInfo<Firmware> pageInfo = new PageInfo<Firmware>(firmwares);
		result.setRows(firmwares);
		result.setTotal(pageInfo.getTotal());
		return result;
	}
	
	@Override
	public ExecuteResult<String> create(Firmware firmware) {
		int count = firmwareDao.insert(firmware);
        if (count > 0) {
			return new ExecuteResult<String>("创建插件成功");
		}
        return new ExecuteResult<>(false, "创建插件失败");
	}
	
	public boolean createFirmware(Firmware firmware) {
		int count = firmwareDao.insert(firmware);
        if (count > 0) {
			return true;
		}
        return false;
	}
	
	@Override
	public ExecuteResult<String> update(Firmware firmware) {
		int count = firmwareDao.update(firmware);
        if (count > 0) {
			return new ExecuteResult<String>("更新插件成功");
		}
        return new ExecuteResult<>(false, "更新插件失败");
	}
	
	@Override
	public ExecuteResult<Firmware> searchById(Object id) {
		Firmware firmware = firmwareDao.selectOneById(id);
		if (firmware != null) {
			return new ExecuteResult<Firmware>(firmware);
		}
		return new ExecuteResult<Firmware>(false, "未查询到指定的固件信息");
	}
	
	@Override
	public ExecuteResult<String> delete(Object id) {
    	int count = firmwareDao.delete(id);
    	if (count > 0) {
			return new ExecuteResult<String>("删除固件成功");
		}
        return new ExecuteResult<String>(false, "删除固件失败");
	}
	
	@Override
	public boolean deleteFirwmare(Object id) {
    	int count = firmwareDao.delete(id);
    	if (count > 0) {
			return true;
		}
        return false;
	}
	
	@Override
	public ExecuteResult<String> batchDelete(List<Firmware> firmwares) {
		int count = firmwareDao.batchDelete(firmwares);
    	if (count > 0) {
			return new ExecuteResult<String>("删除固件成功");
		}
        return new ExecuteResult<String>(false, "删除固件失败");
	}
	
	@Override
	public ExecuteResult<Firmware> selectByMD5(String md5) {
		Firmware firmware = firmwareDao.selectOneByMD5(md5);
		if (firmware == null) {
			return new ExecuteResult<>(false, "未查询到此固件信息");
		}
		return new ExecuteResult<Firmware>(firmware);
	}
	
	@Override
	public boolean selectFirmwareByMD5(String md5) {
		Firmware firmware = firmwareDao.selectOneByMD5(md5);
		if (firmware == null) {
			return false;
		}
		return true;
	}
	
	@Override
	public boolean selectFirmwareByBakMD5(String md5) {
		Firmware firmware = firmwareDao.selectOneByBakMD5(md5);
		if (firmware == null) {
			return false;
		}
		return true;
	}
	
	@Override
	public ExecuteResult<List<String>> searchCrosstool() {
		List<String> strings = firmwareDao.searchCrosstool();
		if (strings == null) {
			return new ExecuteResult<>(false, "未查询到此GCC版本信息");
		}
		return new ExecuteResult<List<String>>(strings);
	}
	
	@Override
	public Firmware selectByVersion(String version) {
		return firmwareDao.selectByVersion(version);
	}
	
	@Override
	public String searchCrosstoolByVersion(String version) {
		return firmwareDao.searchCrosstoolByVersion(version);
	}
	
	@Override
	public Firmware selectById(Object id) {
		return firmwareDao.selectOneById(id);
	}
}
