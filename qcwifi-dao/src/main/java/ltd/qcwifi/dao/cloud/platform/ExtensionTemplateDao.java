package ltd.qcwifi.dao.cloud.platform;

import ltd.qcwifi.base.dao.BaseDao;
import ltd.qcwifi.model.dto.param.ExtensionTemplateParam;
import ltd.qcwifi.model.entity.cloud.platform.ExtensionTemplate;

/**
 * 推送模版持久化
 * 	开发时请 继承  BaseDao<T, PK>，其中提供了基础的CRUD方法
 * @author 张芳
 *
 */
public interface ExtensionTemplateDao 
	extends BaseDao<ExtensionTemplate, ExtensionTemplateParam> {

}
