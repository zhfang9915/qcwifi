package ltd.qcwifi.cloud.push.content.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ltd.qcwifi.cloud.push.content.service.PushContentGroupService;
import ltd.qcwifi.dao.cloud.push.content.PushContentGroupDao;
import ltd.qcwifi.model.entity.cloud.push.content.PushContentGroup;

@Service
public class PushContentGroupServiceImpl implements PushContentGroupService {

	@Autowired
	PushContentGroupDao groupDao;
	
	@Override
	public boolean create(PushContentGroup g) {
		int count = groupDao.insert(g);
		if (count > 0) {
			return true;
		}
		return false;
	}

	@Override
	public List<PushContentGroup> getByUserId(Long createBy) {
		return groupDao.selectByUserId(createBy);
	}
	
	@Override
	public PushContentGroup getByGroupId(Long groupId) {
		return groupDao.selectByGroupId(groupId);
	}

}
