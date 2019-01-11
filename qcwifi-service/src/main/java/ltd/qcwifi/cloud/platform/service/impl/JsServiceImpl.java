package ltd.qcwifi.cloud.platform.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import ltd.qcwifi.cloud.platform.service.JsService;
import ltd.qcwifi.comm.utils.SourceCodeFormat;
import ltd.qcwifi.dao.cloud.platform.JscodeDao;
import ltd.qcwifi.model.dto.ExecuteResult;
import ltd.qcwifi.model.dto.TableResult;
import ltd.qcwifi.model.dto.param.JscodeParam;
import ltd.qcwifi.model.entity.cloud.platform.Jscode;
import ltd.qcwifi.model.entity.cloud.platform.ShopRouter;
import ltd.qcwifi.service.impl.AbstractBaseServiceImpl;

/**
 * 推送模版服务实现类
 * 
 * @author 张芳
 *
 */
@Service
public class JsServiceImpl implements JsService {

	@Autowired
	JscodeDao jscodeDao;
	
	@Override
	@Transactional
	public TableResult<Jscode> tables(JscodeParam param) {
		TableResult<Jscode> result = new TableResult<>();
		// 设置分页
		PageHelper.startPage(param.getPageNumber(), param.getPageSize());
		// 查询数据
		List<Jscode> jss = jscodeDao.selectByPage(param);
		// 获取分页信息
		PageInfo<Jscode> pageInfo = new PageInfo<Jscode>(jss);
		result.setRows(jss);
		result.setTotal(pageInfo.getTotal());
		return result;
	}
	
	@Override
	@Transactional
	public ExecuteResult<String> create(Jscode jscode) {
		// 查询是否已经存在JS
		if (jscodeDao.queryAllJscodeCount() > 0 ) {
			checkIfNeedUpdateWithJscode(jscode);
		} else {
			// 如果不存在则设置为默认的
			jscode.setIsDefault(1);
		}
		
		// 新增操作
		jscode.setCode(SourceCodeFormat.sourceCodeEncode(jscode.getCode()));
		int count = jscodeDao.insert(jscode);
        if (count > 0) {
			return new ExecuteResult<String>("创建JS成功");
		}
        return new ExecuteResult<>(false, "创建JS失败");
	}
	
	@Override
	public ExecuteResult<String> delete(Object id) {
		Long jscodeId = (Long)id;
    	int count = jscodeDao.delete(jscodeId);
    	if (count > 0) {
			return new ExecuteResult<String>("删除JS成功");
		}
        return new ExecuteResult<String>(false, "删除JS失败");
	}
	
	@Override
	public ExecuteResult<String> batchDelete(List<Jscode> jscode) {
		int count = jscodeDao.batchDelete(jscode);
    	if (count > 0) {
			return new ExecuteResult<String>("删除JS成功");
		}
        return new ExecuteResult<String>(false, "删除JS失败");
	}
	
	@Override
	public ExecuteResult<Jscode> searchById(Object id) {
		Long jscodeId = (Long) id;
		Jscode jscode = jscodeDao.selectOneById(jscodeId);
		if (jscode != null) {
			return new ExecuteResult<Jscode>(jscode);
		}
		return new ExecuteResult<Jscode>(false, "未查询到指定的JS信息");
	}
	
	@Override
	public ExecuteResult<String> update(Jscode jscode) {
		checkIfNeedUpdateWithJscode(jscode);
		
		int count = jscodeDao.update(jscode);
        if (count > 0) {
			return new ExecuteResult<String>("更新JS成功");
		}
        return new ExecuteResult<>(false, "更新JS失败");
	}
	
	/*
	 * 判断是否需要把原先默认的JS置为非默认
	 */
	public void checkIfNeedUpdateWithJscode(Jscode jscode) {
		// 如果要成为默认的
		if (jscode.getIsDefault() == 1) {
			// 则把原先默认的置为非默认
			jscodeDao.updateDefaultToUndefault();
		}
	}
	
	@Override
	public Jscode getDefaultJS() {
		return jscodeDao.queryDefaultJscode();
	}
}
