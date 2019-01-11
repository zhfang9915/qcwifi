package ltd.qcwifi.cloud.service;

import ltd.qcwifi.model.dto.ExecuteResult;
import ltd.qcwifi.model.entity.cloud.platform.OrderMain;
import ltd.qcwifi.model.entity.cloud.platform.SmsPacekage;

/**
 * 订单 服务接口
 * @author 张芳
 *
 */
public interface OrderService {

	/**
	 * 保存短信包订购订单信息
	 * @param sp
	 * @return
	 */
	ExecuteResult<Long> saveSmsOrder(Long userId, SmsPacekage sp);
	
	/**
	 * 根据订单号查询用户订单信息
	 * @param orderNo
	 * @param userId
	 * @return
	 */
	OrderMain selectOrderByNo(Long orderNo, Long userId);
}
