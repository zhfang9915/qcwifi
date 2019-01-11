package ltd.qcwifi.cloud.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ltd.qcwifi.cloud.service.UserBindingService;
import ltd.qcwifi.comm.utils.SensitiveUtils;
import ltd.qcwifi.comm.utils.SensitiveUtils.Type;
import ltd.qcwifi.dao.cloud.UserBindingDao;
import ltd.qcwifi.model.entity.cloud.platform.DeliveryInfo;
import ltd.qcwifi.model.entity.cloud.platform.UserBinding;
import ltd.qcwifi.service.impl.AbstractBaseServiceImpl;

/**
 * 用户安全设备绑定服务实现类
 * 
 * @author 张芳
 *
 */
@Service
public class UserBindingServiceImpl
	extends AbstractBaseServiceImpl<DeliveryInfo, String> 
	implements UserBindingService {

	@Autowired
	UserBindingDao userBindingDao;

	@Override
	public UserBinding getUserBindingByUserId(Long userId) {
		UserBinding ub = userBindingDao.selectBindingByUserId(userId);
		//对敏感信息脱敏
		if (ub != null) {
			if (StringUtils.isNotBlank(ub.getPhone())) {
				ub.setPhone(SensitiveUtils.sensitive(Type.phone, ub.getPhone()));
			}
			if (StringUtils.isNotBlank(ub.getEmail())) {
				ub.setEmail(SensitiveUtils.sensitive(Type.email, ub.getEmail()));
			}
		}
		return ub;
	}
	
	@Override
	public UserBinding getUserBindingByUserIdclear(Long userId) {
		return userBindingDao.selectBindingByUserId(userId);
	}

	@Override
	public boolean isBindingPhone(String phone) {
		int count = userBindingDao.selectCountByPhone(phone);
		if (count == 1) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean bindingPhone(Long userId, String phone) {
		int count = userBindingDao.updatePhoneByUserId(userId, phone);
		if (count == 1) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean isBindingEmail(String email) {
		int count = userBindingDao.selectCountByEmail(email);
		if (count == 1) {
			return true;
		}
		return false;
	}

	@Override
	public boolean bindingEmail(Long userId, String email) {
		int count = userBindingDao.updateEmailByUserId(userId, email);
		if (count == 1) {
			return true;
		}
		return false;
	}
	
	
	
}
