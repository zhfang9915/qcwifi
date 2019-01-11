package ltd.qcwifi.cloud.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ltd.qcwifi.cloud.service.PaymentsDetailsService;
import ltd.qcwifi.dao.cloud.PaymentsDetailsDao;
import ltd.qcwifi.model.dto.ExecuteResult;
import ltd.qcwifi.model.dto.LayTableResult;
import ltd.qcwifi.model.dto.TableResult;
import ltd.qcwifi.model.dto.param.PaymentsDetailsParam;
import ltd.qcwifi.model.entity.cloud.PaymentsDetails;
import ltd.qcwifi.model.entity.cloud.platform.Firmware;

@Service
public class PaymentsDetailsServiceImpl implements PaymentsDetailsService {
	@Autowired
	private PaymentsDetailsDao mPaymentsDetailsDao;
	
	/**
	 * 插入数据
	 * @param paymentsDetails
	 */
	public ExecuteResult<String> insert(PaymentsDetails paymentsDetails) {
		try {
			if (mPaymentsDetailsDao.selectById(paymentsDetails.getId()) != null) {
				return new ExecuteResult<>(false, "已经存在该 " + paymentsDetails.getId() + " 的数据，无需再次增加");
			}
			mPaymentsDetailsDao.insert(paymentsDetails);
		}
		catch (Exception e) {
			// TODO: handle exception
			return new ExecuteResult<>(false, "增加失败");
		}
		return new ExecuteResult<>(true, "增加成功");
	}
	
	/**
	 * 通过ID更新
	 * @param id
	 */
	public ExecuteResult<String> updateById(Long id, PaymentsDetails paymentsDetails) {
		try {
			mPaymentsDetailsDao.updateById(id, paymentsDetails);
		}
		catch (Exception e) {
			// TODO: handle exception
			return new ExecuteResult<>(false, "更新失败");
		}
		return new ExecuteResult<>(true, "更新成功");
	}
	
	/**
	 * 通过userId查找
	 * @param userId
	 * @return 
	 */
	public LayTableResult<PaymentsDetails> selectByUserId(PaymentsDetailsParam paymentsDetailsParam) {
		LayTableResult<PaymentsDetails> result = new LayTableResult<PaymentsDetails>();
		
		try {
			List<PaymentsDetails> paymentsDetailsList = mPaymentsDetailsDao.selectByUserId(paymentsDetailsParam);
			if (paymentsDetailsList != null) {
				int count = mPaymentsDetailsDao.selectCount(paymentsDetailsParam);
				result.setCount(count);
				result.setData(paymentsDetailsList);
			}
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		
		return result;
	}
	
	/**
	 * 通过Id查找
	 * @param id
	 * @return 
	 */
	public ExecuteResult<PaymentsDetails> selectById(Long id) {
		try {
			PaymentsDetails paymentsDetails = mPaymentsDetailsDao.selectById(id);
			if (paymentsDetails != null) {
				return new ExecuteResult<PaymentsDetails>(paymentsDetails);
			}
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return new ExecuteResult<>(false, "未查找到该ID的收支明细");
	}
}
