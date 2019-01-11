package ltd.qcwifi.cloud.service;

import java.util.List;

import ltd.qcwifi.model.entity.cloud.platform.SmsPacekage;
import ltd.qcwifi.model.entity.cloud.platform.SmsRechargeHistory;

/**
 * 短信设置 服务接口
 * @author 张芳
 *
 */
public interface SmsPackageService {

	/**
	 * 查询所有的短信包信息
	 * @return
	 */
	List<SmsPacekage> querySmsPackages();
	
	/**
	 * 查询短信包信息
	 * @param id
	 * @return
	 */
	SmsPacekage querySmsPackageById(Object id);
	
	/**
	 * 查询短信包重置历史
	 * @param userId
	 * @param date
	 * @return
	 */
	List<SmsRechargeHistory> rechargeHistory(Long userId, String start, String end);
	
}
