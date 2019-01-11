package ltd.qcwifi.cloud.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ltd.qcwifi.cloud.service.SmsPackageService;
import ltd.qcwifi.dao.cloud.SmsPacekageDao;
import ltd.qcwifi.dao.cloud.SmsRechargeDao;
import ltd.qcwifi.model.entity.cloud.platform.SmsPacekage;
import ltd.qcwifi.model.entity.cloud.platform.SmsRechargeHistory;
import ltd.qcwifi.service.impl.AbstractBaseServiceImpl;

/**
 * 短信信息服务实现类
 * 
 * @author 张芳
 *
 */
@Service
public class SmsPackageServiceImpl
	extends AbstractBaseServiceImpl<SmsPacekage, String> 
	implements SmsPackageService {

	@Autowired
	SmsPacekageDao pacekageDao;
	
	@Autowired
	SmsRechargeDao rechargeDao;
	
	@Override
	public List<SmsPacekage> querySmsPackages() {
		
		return pacekageDao.selectAll();
	}
	
	
	@Override
	public SmsPacekage querySmsPackageById(Object id) {
		
		return pacekageDao.selectOneById(id);
	}
	
	@Override
	public List<SmsRechargeHistory> rechargeHistory(Long userId, String start, String end){
		
		List<SmsRechargeHistory> srhs = rechargeDao.rechargeHistory(userId, start, end);
		return srhs;
	}

}
