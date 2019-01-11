package ltd.qcwifi.cloud.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.sql.visitor.functions.If;

import ltd.qcwifi.cloud.service.BalanceService;
import ltd.qcwifi.dao.cloud.BalanceDao;
import ltd.qcwifi.model.dto.ExecuteResult;
import ltd.qcwifi.model.entity.cloud.Balance;
import ltd.qcwifi.model.entity.cloud.platform.DeliveryInfo;
import ltd.qcwifi.service.impl.AbstractBaseServiceImpl;

@Service
public class BalanceServiceImpl implements BalanceService {
	
	@Autowired
	private BalanceDao mBalanceDao;
	
	/**
	 * 插入数据
	 * @param balance
	 */
	public ExecuteResult<String> insert(Balance balance) {
		try {
			if (mBalanceDao.selectByUserId(balance.getUserId()) != null) {
				return new ExecuteResult<>(false, "已经存在该用户的余额信息，无需增加！");
			}
			mBalanceDao.insert(balance);
		}
		catch (Exception e) {
			// TODO: handle exception
			return new ExecuteResult<>(true, "增加失败，异常错误");
		}
		
		return new ExecuteResult<>(true, "增加成功");
	}
	
	/**
	 * 通过userId删除余额
	 * @param userId
	 * @return
	 */
	public ExecuteResult<String> deleteByUserId(Long userId) {
		try {
			mBalanceDao.deleteByUserId(userId);
		}
		catch (Exception e) {
			// TODO: handle exception
			return new ExecuteResult<>(true, "删除失败，异常错误");
		}
		
		return new ExecuteResult<>(true, "删除成功");
	}
	
	/**
	 * 通过id删除余额
	 * @param id
	 * @return
	 */
	public ExecuteResult<String> deleteById(Long id) {
		try {
			mBalanceDao.deleteById(id);
		}
		catch (Exception e) {
			// TODO: handle exception
			return new ExecuteResult<>(true, "删除失败，异常错误");
		}
		
		return new ExecuteResult<>(true, "删除成功");
	}
	
	/**
	 * 通过userId更新余额
	 * @param userId
	 * @return
	 */
	public ExecuteResult<String> updateByUserId(Long userId, int totalBalance) {
		try {
			mBalanceDao.updateByUserId(userId, totalBalance);
		}
		catch (Exception e) {
			// TODO: handle exception
			return new ExecuteResult<>(true, "更新失败，异常错误");
		}
		
		return new ExecuteResult<>(true, "更新成功");
	}
	
	/**
	 * 通过id更新余额
	 * @param id
	 * @return
	 */
	public ExecuteResult<String> updateById(Long id, int totalBalance) {
		try {
			mBalanceDao.updateById(id, totalBalance);
		}
		catch (Exception e) {
			// TODO: handle exception
			return new ExecuteResult<>(true, "更新失败，异常错误");
		}
		
		return new ExecuteResult<>(true, "更新成功");
	}
	
	/**
	 * 通过userId查找余额
	 * @param userId
	 * @return
	 */
	public ExecuteResult<Balance> selectByUserId(Long userId) {
		try {
			Balance balance = mBalanceDao.selectByUserId(userId);
			if (balance != null) {
				balance.setFreezeBalance((int) (balance.getTotalBalance() * 0.4));
				balance.setAvailableBalance((float) (balance.getTotalBalance() * 0.6));
				balance.setWithdrawMinBalance(200);
				return new ExecuteResult<Balance>(balance);
			}
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		
		return new ExecuteResult<>(false, "查找该用户余额失败");
	}
	
	/**
	 * 通过id查找余额
	 * @param id
	 * @return
	 */
	public ExecuteResult<Balance> selectById(Long id) {
		try {
			Balance balance = mBalanceDao.selectById(id);
			if (balance != null) {
				balance.setFreezeBalance((float) (balance.getTotalBalance() * 0.4));
				balance.setAvailableBalance((float) (balance.getTotalBalance() * 0.6));
				balance.setWithdrawMinBalance(200);
				return new ExecuteResult<Balance>(balance);
			}
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		
		return new ExecuteResult<>(false, "查找该ID余额失败");
	}
}
