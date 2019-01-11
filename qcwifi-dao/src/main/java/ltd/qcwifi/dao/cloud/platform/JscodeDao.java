package ltd.qcwifi.dao.cloud.platform;

import ltd.qcwifi.base.dao.BaseDao;
import ltd.qcwifi.model.entity.cloud.platform.Jscode;
import ltd.qcwifi.model.dto.param.JscodeParam;
import ltd.qcwifi.model.entity.cloud.platform.Jscode;

/**
 * JS持久化
 * 	开发时请 继承  BaseDao<T, PK>，其中提供了基础的CRUD方法
 * @author 张芳
 *
 */
public interface JscodeDao extends BaseDao<Jscode, JscodeParam> {
	
	/*
	 * 查询JS总数量
	 * @author 许延明
	 */
	int queryAllJscodeCount();
	
	/*
	 * 查询默认的JS
	 * @author 许延明
	 */
	Jscode queryDefaultJscode();
	
	/*
	 * 把默认的JS设置为非默认
	 * @author 许延明
	 */
	int updateDefaultToUndefault();
}
