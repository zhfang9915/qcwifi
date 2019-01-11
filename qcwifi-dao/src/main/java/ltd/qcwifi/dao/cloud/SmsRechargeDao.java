package ltd.qcwifi.dao.cloud;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import ltd.qcwifi.model.entity.cloud.platform.SmsRechargeHistory;

/**
 * 短信重置记录
 * 
 * @author 张芳
 *
 */
public interface SmsRechargeDao {

	/**
	 * 查询短信充值记录
	 * @param userId
	 * @param start
	 * @param end
	 * @return
	 */
	List<SmsRechargeHistory> rechargeHistory(
			@Param("createBy") Long userId, 
			@Param("startDate") String start,
			@Param("endDate") String end);
}
