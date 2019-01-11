package ltd.qcwifi.cloud.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ltd.qcwifi.cloud.service.ShopMarkService;
import ltd.qcwifi.dao.cloud.ShopMarkDao;
import ltd.qcwifi.model.entity.cloud.platform.ShopMark;

@Service
public class ShopMarkServiceImpl implements ShopMarkService {

	@Autowired
	ShopMarkDao shopMarkDao;
	
	@Override
	public List<ShopMark> getMarksByUserId(Long userId) {
		
		return shopMarkDao.selectAll(userId);
	}

}
