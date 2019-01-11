package ltd.qcwifi.system.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import ltd.qcwifi.dao.system.PermissionDao;
import ltd.qcwifi.model.dto.ExecuteResult;
import ltd.qcwifi.model.dto.TableResult;
import ltd.qcwifi.model.dto.param.PermissionParam;
import ltd.qcwifi.model.entity.Permission;
import ltd.qcwifi.model.entity.cloud.platform.Plugin;
import ltd.qcwifi.service.impl.AbstractBaseServiceImpl;
import ltd.qcwifi.system.service.PermissionService;
import net.sf.json.JSONObject;

/**
 * 权限服务实现类
 * @author 张芳
 *
 */
@Service("permissionService")
public class PermissionServiceImpl extends AbstractBaseServiceImpl<Permission, PermissionParam> implements PermissionService {
	
	private Logger logger = LoggerFactory.getLogger(PermissionServiceImpl.class);

    @Autowired 
    PermissionDao permissionDao;
    
    @Transactional
    @Override
    public ExecuteResult<String> create(Permission permission) {
        int count = permissionDao.insert(permission);
        if (count == 1) {
        	List<Permission> permissions = new ArrayList<>();
        	permissions.add(permission);
        	permissionDao.correlationPermissions(1L, permissions);
			return new ExecuteResult<String>("创建权限成功");
		}
        return new ExecuteResult<>(false, "创建权限失败");
    }

    @Transactional
    @Override
    public ExecuteResult<String> delete(Object id) {
    	Long permissionId = (Long) id;
		permissionDao.deleteRolePermission(permissionId);
		int p = permissionDao.delete(permissionId);
		if (p > 0) {
        	return new ExecuteResult<String>("删除权限成功");
		}
        return new ExecuteResult<String>(false, "删除权限失败");
    }
    
    @Transactional
    @Override
    public ExecuteResult<String> batchDelete(List<Permission> permissions) {
		permissionDao.batchDelebteRolePermission(permissions);
		int p = permissionDao.batchDelete(permissions);
		if (p > 0) {
        	return new ExecuteResult<String>("删除权限成功");
		}
        return new ExecuteResult<String>(false, "删除权限失败");
    }

	@Override
	public Set<String> findPermissionByUserId(Long userId) {
		return permissionDao.findPermissionByUserId(userId);
	}

	@Override
	public TableResult<Permission> tables(PermissionParam param) {
		TableResult<Permission> result = new TableResult<>();
		// 设置分页
		PageHelper.startPage(param.getPageNumber(), param.getPageSize());
		// 查询数据
		List<Permission> permissions = permissionDao.selectByPage(param);
		// 获取分页信息
		PageInfo<Permission> pageInfo = new PageInfo<Permission>(permissions);
		result.setRows(permissions);
		result.setTotal(pageInfo.getTotal());
		return result;
	}

	@Override
	public ExecuteResult<Permission> searchById(Object id) {
		Long permissionId = (Long) id;
		Permission permission = permissionDao.selectOneById(permissionId);
		if (permission != null) {
			return new ExecuteResult<Permission>(permission);
		}
		return new ExecuteResult<>(false, "未查询到当前权限信息");
	}
	
	@Override
    public ExecuteResult<String> update(Permission permission) {
        int count = permissionDao.update(permission);
        if (count == 1) {
			return new ExecuteResult<String>("编辑权限成功");
		}
        return new ExecuteResult<>(false, "编辑权限失败");
    }
}
