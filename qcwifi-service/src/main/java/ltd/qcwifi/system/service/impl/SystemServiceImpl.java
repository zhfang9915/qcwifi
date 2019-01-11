package ltd.qcwifi.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ltd.qcwifi.base.dao.mybatis.ecache.MybatisRedisCache;
import ltd.qcwifi.comm.utils.ClassUtil;
import ltd.qcwifi.system.service.SystemService;

@Service
public class SystemServiceImpl implements SystemService {
	
	@Autowired
	MybatisRedisCache mybatisRedisCache;

	@Override
	public boolean flushDBCache() {
		try {
			List<Class> classes = ClassUtil.getClasses("ltd.qcwifi.dao");
			for (Class clas : classes) {
				mybatisRedisCache.clear(clas.getName());
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}

}
