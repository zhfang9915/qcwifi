package ltd.qcwifi.cloud.service;

import ltd.qcwifi.model.dto.ExecuteResult;
import ltd.qcwifi.model.entity.cloud.platform.SmsSetting;

/**
 * 短信设置 服务接口
 * @author 张芳
 *
 */
public interface SmsSettingService {

	/**
	 * 根据用户查询短信信息
	 * @param userId
	 * @return
	 */
	SmsSetting querySmsInfo(long userId);
	
	/**
	 * 开启短信告知通知
	 * @param userId
	 * @param min
	 * @return
	 */
	ExecuteResult<String> openAlarm(Long userId, int min);
	
	/**
	 * 关闭短信告知
	 * @param userId
	 * @param min
	 * @return
	 */
	ExecuteResult<String> cancleAlarm(Long userId, int min);
	
}
