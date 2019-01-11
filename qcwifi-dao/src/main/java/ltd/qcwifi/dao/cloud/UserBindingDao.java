package ltd.qcwifi.dao.cloud;

import org.apache.ibatis.annotations.Param;

import ltd.qcwifi.base.dao.BaseDao;
import ltd.qcwifi.model.entity.cloud.platform.DeliveryInfo;
import ltd.qcwifi.model.entity.cloud.platform.UserBinding;

/**
 * 推送模版持久化
 * 	开发时请 继承  BaseDao<T, PK>，其中提供了基础的CRUD方法
 * @author 张芳
 *
 */
public interface UserBindingDao 
	extends BaseDao<DeliveryInfo, String> {
	
	/**
	 * 创建用户空绑定信息
	 * @param userId
	 * @return
	 */
	int createBinding(@Param("userId")Long userId);
	
	/**
	 * 新建绑定手机号
	 * @param userId
	 * @param phone
	 * @return
	 */
	int createBindingPhone(@Param("userId")Long userId, @Param("phone")String phone);
	/**
	 * 根据用户ID查询设备绑定的信息
	 * @param userId
	 * @return
	 */
	UserBinding selectBindingByUserId(@Param("userId")Long userId);
	
	/**
	 * 验证手机号是否绑定了账号
	 * @param phone
	 * @return
	 */
	int selectCountByPhone(@Param("phone")String phone);
	
	/**
	 * 绑定手机号
	 * @param userId
	 * @param phone
	 * @return
	 */
	int updatePhoneByUserId(@Param("userId")Long userId, @Param("phone")String phone);
	
	/**
	 * 验证邮箱是否绑定了账号
	 * @param email
	 * @return
	 */
	int selectCountByEmail(@Param("email")String email);
	
	/**
	 * 绑定邮箱
	 * @param userId
	 * @param email
	 * @return
	 */
	int updateEmailByUserId(@Param("userId")Long userId, @Param("email")String email);
	
	
}
