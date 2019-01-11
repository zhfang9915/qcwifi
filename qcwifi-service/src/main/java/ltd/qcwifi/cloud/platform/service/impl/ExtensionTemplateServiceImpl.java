package ltd.qcwifi.cloud.platform.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import ltd.qcwifi.cloud.platform.service.ExtensionTemplateService;
import ltd.qcwifi.dao.cloud.platform.ExtensionTemplateDao;
import ltd.qcwifi.model.dto.ExecuteResult;
import ltd.qcwifi.model.dto.TableResult;
import ltd.qcwifi.model.dto.param.ExtensionTemplateParam;
import ltd.qcwifi.model.entity.cloud.platform.ExtensionTemplate;
import ltd.qcwifi.model.entity.cloud.platform.Jscode;
import ltd.qcwifi.service.impl.AbstractBaseServiceImpl;

/**
 * 推送模版服务实现类
 * @author 张芳
 *
 */
@Service("extensionTemplateService")
public class ExtensionTemplateServiceImpl 
	extends AbstractBaseServiceImpl<ExtensionTemplate, ExtensionTemplateParam> 
	implements ExtensionTemplateService {

	@Autowired
	ExtensionTemplateDao extensionTemplateDao;
	
	/**		重写抽象类*/
	@Override
	public ExecuteResult<String> create(ExtensionTemplate etemp) {
		int count = extensionTemplateDao.insert(etemp);
        if (count > 0) {
			return new ExecuteResult<String>("创建推送模版成功");
		}
        return new ExecuteResult<>(false, "创建推送模版失败");
	}
	
	@Override
	public ExecuteResult<String> delete(Object id) {
		Long tempId = (Long) id;
    	int count = extensionTemplateDao.delete(tempId);
    	if (count > 0) {
			return new ExecuteResult<String>("删除推送模版成功");
		}
        return new ExecuteResult<String>(false, "删除推送模版失败");
	}
	
	@Override
	public ExecuteResult<String> batchDelete(List<ExtensionTemplate> eTemplates) {
		int count = extensionTemplateDao.batchDelete(eTemplates);
    	if (count > 0) {
			return new ExecuteResult<String>("删除推送模版成功");
		}
        return new ExecuteResult<String>(false, "删除推送模版失败");
	}

	
	@Override
	public ExecuteResult<ExtensionTemplate> searchById(Object id) {
		Integer tempId = (Integer) id;
		ExtensionTemplate eTemplate = extensionTemplateDao.selectOneById(tempId);
		if (eTemplate != null) {
			return new ExecuteResult<ExtensionTemplate>(eTemplate);
		}
		return new ExecuteResult<ExtensionTemplate>(false, "未查询到指定的推送模版信息");
	}
	
	@Override
	public TableResult<ExtensionTemplate> tables(ExtensionTemplateParam param) {
		TableResult<ExtensionTemplate> result = new TableResult<>();
		// 设置分页
		PageHelper.startPage(param.getPageNumber(), param.getPageSize());
		// 查询数据
		List<ExtensionTemplate> plugins = extensionTemplateDao.selectByPage(param);
		// 获取分页信息
		PageInfo<ExtensionTemplate> pageInfo = new PageInfo<ExtensionTemplate>(plugins);
		result.setRows(plugins);
		result.setTotal(pageInfo.getTotal());
		return result;
	}
	
	@Override
	public ExecuteResult<String> update(ExtensionTemplate etemp) {
		int count = extensionTemplateDao.update(etemp);
    	if (count > 0) {
			return new ExecuteResult<String>("更新推送模版成功");
		}
        return new ExecuteResult<String>(false, "更新推送模版失败");
	}
	
	
	
}
