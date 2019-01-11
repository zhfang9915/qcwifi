package ltd.qcwifi.cloud.service;

import ltd.qcwifi.model.dto.ExecuteResult;
import ltd.qcwifi.model.dto.param.BalanceParam;
import ltd.qcwifi.model.entity.cloud.Balance;

public interface BalanceService {
	
	/**
	 * 插入数据
	 * @param balance
	 */
	public ExecuteResult<String> insert(Balance balance);
	
	/**
	 * 通过userId删除余额
	 * @param userId
	 * @return
	 */
	public ExecuteResult<String> deleteByUserId(Long userId);
	
	/**
	 * 通过id删除余额
	 * @param id
	 * @return
	 */
	public ExecuteResult<String> deleteById(Long id);
	
	/**
	 * 通过userId更新余额
	 * @param userId
	 * @return
	 */
	public ExecuteResult<String> updateByUserId(Long userId, int totalBalance);
	
	/**
	 * 通过id更新余额
	 * @param id
	 * @return
	 */
	public ExecuteResult<String> updateById(Long id, int totalBalance);
	
	/**
	 * 通过userId查找余额
	 * @param userId
	 * @return
	 */
	public ExecuteResult<Balance> selectByUserId(Long userId);
	
	/**
	 * 通过id查找余额
	 * @param id
	 * @return
	 */
	public ExecuteResult<Balance> selectById(Long id);
}
