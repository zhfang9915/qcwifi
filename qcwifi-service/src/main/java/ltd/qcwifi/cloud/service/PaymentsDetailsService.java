package ltd.qcwifi.cloud.service;

import java.util.List;

import ltd.qcwifi.model.dto.ExecuteResult;
import ltd.qcwifi.model.dto.LayTableResult;
import ltd.qcwifi.model.dto.TableResult;
import ltd.qcwifi.model.dto.param.PaymentsDetailsParam;
import ltd.qcwifi.model.entity.cloud.PaymentsDetails;
import ltd.qcwifi.model.entity.cloud.platform.DeliveryInfo;

public interface PaymentsDetailsService {
	
	/**
	 * 插入数据
	 * @param paymentsDetails
	 */
	public ExecuteResult<String> insert(PaymentsDetails paymentsDetails);
	
	/**
	 * 通过ID更新
	 * @param id
	 */
	public ExecuteResult<String> updateById(Long id, PaymentsDetails paymentsDetails);
	
	/**
	 * 通过userId查找
	 * @param userId
	 * @return 
	 */
	public LayTableResult<PaymentsDetails> selectByUserId(PaymentsDetailsParam paymentsDetailsParam);
	
	/**
	 * 通过Id查找
	 * @param id
	 * @return 
	 */
	public ExecuteResult<PaymentsDetails> selectById(Long id);
}
