package ltd.qcwifi.dao.cloud;

import org.apache.ibatis.annotations.Param;

import ltd.qcwifi.base.dao.BaseDao;
import ltd.qcwifi.model.entity.cloud.Balance;


public interface BalanceDao {
	
	/**
	 * 插入数据
	 * @param balance
	 */
	public void insert(Balance balance);
	
	/**
	 * 通过userId删除余额
	 * @param userId
	 * @return
	 */
	public void deleteByUserId(@Param("userId")Long userId);
	
	/**
	 * 通过id删除余额
	 * @param id
	 * @return
	 */
	public void deleteById(@Param("id")Long id);
	
	/**
	 * 通过userId更新余额
	 * @param userId
	 * @return
	 */
	public void updateByUserId(@Param("userId")Long userId, @Param("totalBalance")int totalBalance);
	
	/**
	 * 通过id更新余额
	 * @param id
	 * @return
	 */
	public void updateById(@Param("id")Long id, @Param("totalBalance")int totalBalance);
	
	/**
	 * 通过userId查找余额
	 * @param userId
	 * @return
	 */
	public Balance selectByUserId(@Param("userId")Long userId);
	
	/**
	 * 通过id查找余额
	 * @param id
	 * @return
	 */
	public Balance selectById(@Param("id")Long id);
}
