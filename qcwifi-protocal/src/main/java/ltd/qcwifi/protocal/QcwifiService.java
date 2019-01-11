package ltd.qcwifi.protocal;

import ltd.qcwifi.model.dto.ExecuteResult;

public interface QcwifiService {
	/**
	 * @return 返回对像规范是 BaseResult<T>的实现
	 */
	public ExecuteResult<?> execute(Object... args);
}
