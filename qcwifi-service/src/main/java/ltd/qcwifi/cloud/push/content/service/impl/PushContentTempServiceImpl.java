package ltd.qcwifi.cloud.push.content.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ltd.qcwifi.cloud.push.content.service.PushContentTempService;
import ltd.qcwifi.dao.cloud.push.content.PushContentTempDao;
import ltd.qcwifi.model.entity.cloud.push.content.PushContentTemp;

@Service
public class PushContentTempServiceImpl implements PushContentTempService {

	@Autowired
	PushContentTempDao tempDao;
	
	@Override
	public List<PushContentTemp> getTemps() {
		return tempDao.select();
	}

}
