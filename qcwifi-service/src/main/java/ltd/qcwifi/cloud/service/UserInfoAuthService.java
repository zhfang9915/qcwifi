package ltd.qcwifi.cloud.service;

import ltd.qcwifi.model.dto.ExecuteResult;
import ltd.qcwifi.model.entity.UserInfoAuth;

public interface UserInfoAuthService {
	public ExecuteResult<String> willAuth(UserInfoAuth userInfo);
	public UserInfoAuth queryInfoById(long id);
}
