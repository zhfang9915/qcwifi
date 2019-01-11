package ltd.qcwifi.cloud.service;

import java.util.List;

import ltd.qcwifi.model.dto.ExecuteResult;
import ltd.qcwifi.model.entity.cloud.platform.DeliveryInfo;

/**
 * 配送地址 服务接口
 * @author 张芳
 *
 */
public interface DeliveryInfoService {

	/**
	 * 删除配送信息
	 * @param id
	 * @param userId
	 * @return
	 */
	ExecuteResult<String> deleteDelivery(Long id, Long userId);
	
	/**
	 * 查询用户名下的配送信息
	 * @param userId
	 * @return
	 */
	ExecuteResult<List<DeliveryInfo>> selectByUserId(Long userId);
	
	/**
	 * 设为默认地址
	 * @param id
	 * @param userId
	 * @return
	 */
	ExecuteResult<String> setDefault(Long id, Long userId);
	
	int hasCount(Long userId);
}
