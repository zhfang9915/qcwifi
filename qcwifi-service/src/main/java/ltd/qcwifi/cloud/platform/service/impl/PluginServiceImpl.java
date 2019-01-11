package ltd.qcwifi.cloud.platform.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import ltd.qcwifi.cloud.platform.service.PluginService;
import ltd.qcwifi.dao.cloud.platform.PluginDao;
import ltd.qcwifi.model.dto.ExecuteResult;
import ltd.qcwifi.model.dto.TableResult;
import ltd.qcwifi.model.dto.param.PluginParam;
import ltd.qcwifi.model.entity.cloud.platform.Firmware;
import ltd.qcwifi.model.entity.cloud.platform.Plugin;
import ltd.qcwifi.service.impl.AbstractBaseServiceImpl;

/**
 * 推送模版服务实现类
 * 
 * @author 张芳
 *
 */
@Service("pluginService")
public class PluginServiceImpl 
	extends AbstractBaseServiceImpl<Plugin, PluginParam> 
	implements PluginService {

	@Autowired
	PluginDao pluginDao;
	
	@Override
	public ExecuteResult<String> create(Plugin plugin) {
		int count = pluginDao.insert(plugin);
        if (count > 0) {
			return new ExecuteResult<String>("创建插件成功");
		}
        return new ExecuteResult<>(false, "创建插件失败");
	}
	
	@Override
	public ExecuteResult<String> delete(Object id) {
		Long pluginId = (Long) id;
    	int count = pluginDao.delete(pluginId);
    	if (count > 0) {
			return new ExecuteResult<String>("删除插件成功");
		}
        return new ExecuteResult<String>(false, "删除插件失败");
	}
	
	@Override
	public ExecuteResult<String> batchDelete(List<Plugin> plugins) {
		int count = pluginDao.batchDelete(plugins);
    	if (count > 0) {
			return new ExecuteResult<String>("删除插件成功");
		}
        return new ExecuteResult<String>(false, "删除插件失败");
	}

	
	@Override
	public ExecuteResult<Plugin> searchById(Object id) {
		Long pluginId = (Long) id;
		Plugin plugin = pluginDao.selectOneById(pluginId);
		if (plugin != null) {
			return new ExecuteResult<Plugin>(plugin);
		}
		return new ExecuteResult<Plugin>(false, "未查询到指定的插件信息");
	}
	
	@Override
	public TableResult<Plugin> tables(PluginParam param) {
		TableResult<Plugin> result = new TableResult<>();
		
		// 设置分页
		PageHelper.startPage(param.getPageNumber(), param.getPageSize());
		// 查询数据
		List<Plugin> plugins = pluginDao.selectByPage(param);
		// 获取分页信息
		PageInfo<Plugin> pageInfo = new PageInfo<Plugin>(plugins);
		result.setRows(plugins);
		result.setTotal(pageInfo.getTotal());
		return result;
	}
	
	@Override
	public ExecuteResult<String> update(Plugin plugin) {
		int count = pluginDao.update(plugin);
    	if (count > 0) {
			return new ExecuteResult<String>("更新插件成功");
		}
        return new ExecuteResult<String>(false, "更新插件失败");
	}
	
	@Override
	public ExecuteResult<Plugin> selectPluginByMD5(String md5) {
		Plugin plugin = pluginDao.selectOneByMD5(md5);
		if (plugin == null) {
			return new ExecuteResult<>(false, "未查询到此插件信息");
		}
		return new ExecuteResult<Plugin>(plugin);
	}
	
	@Override
	public Plugin getPluginByGccv(String gccv) {
		return pluginDao.selectByGccv(gccv);
	}
}
