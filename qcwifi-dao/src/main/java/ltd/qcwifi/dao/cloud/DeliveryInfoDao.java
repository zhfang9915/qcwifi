package ltd.qcwifi.dao.cloud;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import ltd.qcwifi.base.dao.BaseDao;
import ltd.qcwifi.model.entity.cloud.platform.DeliveryInfo;

/**
 * 推送模版持久化
 * 	开发时请 继承  BaseDao<T, PK>，其中提供了基础的CRUD方法
 * @author 张芳
 *
 */
public interface DeliveryInfoDao 
	extends BaseDao<DeliveryInfo, String> {
	
	/**
	 * 删除配送信息
	 * @param id
	 * @param userId
	 * @return
	 */
	int deleteDelivery(@Param("id")Long id, @Param("userId")Long userId);
	
	/**
	 * 根据用户ID获取配送信息
	 * @param userId
	 * @return
	 */
	List<DeliveryInfo> selectByUserId(Long userId);

	/**
	 * 更新配送地址
	 * @param info
	 * @return
	 */
	int updateDelivery(DeliveryInfo info);
	
	/**
	 * 设置默认地址
	 * @param id
	 * @param userId
	 * @return
	 */
	int setDefault(@Param("id")Long id, @Param("userId")Long userId, @Param("isDefault")boolean isDefault);
	
	/**
	 * 已有地址数量
	 * @param userId
	 * @return
	 */
	int hasCount(@Param("userId")Long userId);
}
