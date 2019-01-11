package ltd.qcwifi.cloud.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ltd.qcwifi.cloud.service.OperateService;
import ltd.qcwifi.dao.cloud.OperateDao;
import ltd.qcwifi.model.entity.cloud.platform.Operate;

@Service
public class OperateServiceImpl implements OperateService {

	@Autowired
	OperateDao operateDao;
	
	@Override
	public List<Operate> selectOperateByLevel(Integer level, Integer parent) {
		return operateDao.selectOperateByLevel(level, parent);
	}

}
