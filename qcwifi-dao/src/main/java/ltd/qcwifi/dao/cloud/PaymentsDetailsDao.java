package ltd.qcwifi.dao.cloud;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import ltd.qcwifi.base.dao.BaseDao;
import ltd.qcwifi.model.dto.param.PaymentsDetailsParam;
import ltd.qcwifi.model.entity.cloud.PaymentsDetails;

public interface PaymentsDetailsDao {

	/**
	 * 插入数据
	 * @param paymentsDetails
	 */
	public void insert(PaymentsDetails paymentsDetails);
	
	/**
	 * 通过ID更新
	 * @param id
	 */
	public void updateById(@Param("id")Long id, PaymentsDetails paymentsDetails);
	
	/**
	 * 通过userId查找
	 * @param userId
	 * @return 
	 */
	public List<PaymentsDetails> selectByUserId(PaymentsDetailsParam paymentsDetailsParam);
	
	/**
	 * 通过Id查找
	 * @param id
	 * @return 
	 */
	public PaymentsDetails selectById(@Param("id")Long id);
	
	/**
	 * 查询总数
	 * @return
	 */
	public int selectCount(PaymentsDetailsParam paymentsDetailsParam);
}
