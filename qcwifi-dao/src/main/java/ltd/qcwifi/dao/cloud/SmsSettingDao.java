package ltd.qcwifi.dao.cloud;

import org.apache.ibatis.annotations.Param;

import ltd.qcwifi.base.dao.BaseDao;
import ltd.qcwifi.model.entity.cloud.platform.SmsSetting;

/**
 * 短信设置
 * @author 张芳
 *
 */
public interface SmsSettingDao extends BaseDao<SmsSetting, String> {

	/**
	 * 开启短信告警
	 * @param userId
	 * @param min
	 * @return
	 */
	int openAlarm(@Param("userId")Long userId, @Param("min")int min);
	
	/**
	 * 关闭短信告警
	 * @param userId
	 * @param min
	 * @return
	 */
	int cancleAlarm(@Param("userId")Long userId, @Param("min")int min);
}
