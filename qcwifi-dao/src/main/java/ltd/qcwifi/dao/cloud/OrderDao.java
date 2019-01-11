package ltd.qcwifi.dao.cloud;

import org.apache.ibatis.annotations.Param;

import ltd.qcwifi.base.dao.BaseDao;
import ltd.qcwifi.model.entity.cloud.platform.OrderMain;

public interface OrderDao extends BaseDao<OrderMain, String> {

	/**
	 * 保存短信订购订单
	 * @param order
	 * @return
	 */
	int saveSmsOrder(OrderMain order);
	
	/**
	 * 根据订单号查询订单
	 * @param orderNo
	 * @param userId
	 * @return
	 */
	OrderMain selectOrderByNo(@Param("orderNo")Long orderNo, @Param("createBy")Long userId);
}
