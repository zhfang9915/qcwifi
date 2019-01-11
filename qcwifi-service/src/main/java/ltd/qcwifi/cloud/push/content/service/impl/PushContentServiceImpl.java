package ltd.qcwifi.cloud.push.content.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ltd.qcwifi.cloud.push.content.service.PushContentService;
import ltd.qcwifi.dao.cloud.push.content.PushContentDao;
import ltd.qcwifi.model.entity.cloud.push.content.PushContent;

@Service
public class PushContentServiceImpl implements PushContentService {
	
	@Autowired
	PushContentDao contentDao;

	@Override
	@Transactional
	public boolean create(PushContent content) {
		int count = contentDao.insert(content);
		if (count > 0) {
			return true;
		}
		return false;
	}
	
	@Override
	public List<PushContent> getList(Long createBy, String contentName) {
		return contentDao.selects(createBy, contentName);
	}

	@Override
	public PushContent getById(Long createBy, String id) {
		return contentDao.selectDetail(createBy, id);
	}
	
	@Override
	@Transactional
	public boolean delete(Long createBy, String id) {
		int count = contentDao.delete(createBy, id);
		if (count > 0) {
			return true;
		}
		return false;
	}
	
	@Override
	@Transactional
	public boolean update(PushContent content) {
		int count = contentDao.update(content);
		if (count > 0) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean updateStatus(PushContent content) {
		int count = contentDao.updateStatus(content);
		if (count > 0) {
			return true;
		}
		return false;
	}
}
