package ltd.qcwifi.cloud.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ltd.qcwifi.cloud.service.UserInfoAuthService;
import ltd.qcwifi.dao.cloud.UserInfoAuthDao;
import ltd.qcwifi.model.dto.ExecuteResult;
import ltd.qcwifi.model.entity.UserInfoAuth;
import ltd.qcwifi.service.impl.AbstractBaseServiceImpl;

@Service
public class UserInfoAuthServiceImpl extends AbstractBaseServiceImpl<UserInfoAuth, String> implements UserInfoAuthService {

	@Autowired
	UserInfoAuthDao authDao;
	
	@Override
	public ExecuteResult<String> willAuth(UserInfoAuth userInfo) {
		authDao.insert(userInfo);
		return new ExecuteResult<String>(true, "成功了");
	}

	@Override
	public UserInfoAuth queryInfoById(long id) {
		System.out.println("query user info:" + id);
		return authDao.selectOneById(id);
	}
}
