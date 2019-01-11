package ltd.qcwifi.cloud.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ltd.qcwifi.cloud.service.SmsSettingService;
import ltd.qcwifi.dao.cloud.SmsConsumeDao;
import ltd.qcwifi.dao.cloud.SmsPacekageDao;
import ltd.qcwifi.dao.cloud.SmsSettingDao;
import ltd.qcwifi.model.dto.ExecuteResult;
import ltd.qcwifi.model.entity.cloud.platform.SmsSetting;
import ltd.qcwifi.service.impl.AbstractBaseServiceImpl;

/**
 * 短信信息服务实现类
 * 
 * @author 张芳
 *
 */
@Service
public class SmsSettingServiceImpl
	extends AbstractBaseServiceImpl<SmsSetting, String> 
	implements SmsSettingService {

	@Autowired
	SmsPacekageDao pacekageDao;
	
	@Autowired
	SmsSettingDao settingDao;
	
	@Autowired
	SmsConsumeDao consumeDao;

	@Override
	public SmsSetting querySmsInfo(long userId) {
		
		return settingDao.selectOneById(userId);
	}
	
	@Override
	public ExecuteResult<String> openAlarm(Long userId, int min) {
		int count = settingDao.openAlarm(userId, min);
		if (count == 1) {
			return new ExecuteResult<String>("短信告警通知已开启");
		}
		return new ExecuteResult<>(false, "短信告警通知开启失败");
	}
	
	@Override
	public ExecuteResult<String> cancleAlarm(Long userId, int min) {
		int count = settingDao.cancleAlarm(userId, min);
		if (count == 1) {
			return new ExecuteResult<String>("短信告警通知已关闭");
		}
		return new ExecuteResult<>(false, "短信告警通知关闭失败");
	}

}
