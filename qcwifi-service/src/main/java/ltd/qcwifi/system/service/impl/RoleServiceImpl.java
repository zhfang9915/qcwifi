package ltd.qcwifi.system.service.impl;

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
import ltd.qcwifi.dao.system.RoleDao;
import ltd.qcwifi.model.dto.ExecuteResult;
import ltd.qcwifi.model.dto.TableResult;
import ltd.qcwifi.model.dto.param.RoleParam;
import ltd.qcwifi.model.entity.Permission;
import ltd.qcwifi.model.entity.Role;
import ltd.qcwifi.service.impl.AbstractBaseServiceImpl;
import ltd.qcwifi.system.service.RoleService;
import net.sf.json.JSONObject;

/**
 * 角色服务实现
 * @author 张芳
 *
 */
@Service("roleService")
public class RoleServiceImpl extends AbstractBaseServiceImpl<Role, RoleParam> implements RoleService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    RoleDao roleDao;
    
    @Autowired
    PermissionDao permissionDao;

    @Override
    public ExecuteResult<String> create(Role role) {
        int count = roleDao.insert(role);
        if (count == 1) {
			return new ExecuteResult<String>("创建角色成功");
		}
        return new ExecuteResult<String>(false, "创建角色失败");
    }
    
    @Override
    @Transactional
    public ExecuteResult<String> delete(Object id) {
    	Long roleId = (Long) id;
    	if (new Long(1).equals(roleId)) {
    		return new ExecuteResult<String>(false, "系统管理员不允许删除");
		}
    	//1. 删除角色权限关系
    	roleDao.deleteRolePermission(roleId);
    	
    	//2.删除用户角色
        roleDao.deleteUserRole(roleId);
        
        //3.删除角色
        int count = roleDao.delete(roleId);
        
        if (count == 1) {
			return new ExecuteResult<String>("删除角色成功");
		}
        return new ExecuteResult<String>(false, "删除角色失败");
    }
    
    @Override
    @Transactional
	public ExecuteResult<String> batchDelete(List<Role> roles) {
    	Role role = null;
    	String msg = "";
    	for (Role r : roles) {
			Long id = new Long(r.getId());
			if(new Long(1).equals(id)){
				role = r;
				break;
			}
		}
    	if (role != null) {
    		msg = "但系统管理员不允许删除";
			roles.remove(role);
		}
		//1. 删除该权限与角色的关联关系，既从角色中删除该权限
		roleDao.batchDeleteRolePermission(roles);
    	
		//2. 删除用户角色关系 
		roleDao.batchDeleteUserRole(roles);
		
    	//3. 删除权限
    	int r = roleDao.batchDelete(roles);
    	
    	if (r > 0) {
        	return new ExecuteResult<String>("删除角色成功！" + msg);
		}
        return new ExecuteResult<String>(false, "删除角色失败");
	}


	@Override
	public Set<String> findRolesByUserId(Long userId) {
		return roleDao.findRolesByUserId(userId);
	}
	

	@Override
	public TableResult<Role> tables(RoleParam param) {
		TableResult<Role> result = new TableResult<>();
		// 设置分页
		PageHelper.startPage(param.getPageNumber(), param.getPageSize());
		// 查询数据
		List<Role> roles = roleDao.selectByPage(param);
		// 获取分页信息
		PageInfo<Role> pageInfo = new PageInfo<Role>(roles);
		result.setRows(roles);
		result.setTotal(pageInfo.getTotal());
		return result;
	}

	@Override
	public ExecuteResult<Role> searchById(Object id) {
		Long roleId = (Long) id;
		Role role = roleDao.selectOneById(roleId);
		if (role != null) {
			return new ExecuteResult<Role>(role);
		}
		return new ExecuteResult<>(false, "未查询到当前角色信息");
	}

	@Override
	public ExecuteResult<String> update(Role role) {
		int count = roleDao.update(role);
        if (count == 1) {
			return new ExecuteResult<String>("编辑角色成功");
		}
        return new ExecuteResult<>(false, "编辑角色失败");
	}

	@Override
	public TableResult<Permission> existOrNotPermission(String method, Long roleId, String description) {
		TableResult<Permission> result = new TableResult<>();
		String type = null;
		switch (method) {
		case "exist":
			type = "IN";//已有的权限
			break;
		default:
			type = "NOT";//未拥有有的权限
			break;
		}
		List<Permission> permissions = permissionDao.existOrNotPermission(type, roleId, description);
		result.setRows(permissions);
		result.setTotal(permissions.size());
		return result;
	}

	@Override
	public ExecuteResult<String> permissionConfiguration(String method, Long roleId, List<Permission> permissions) {
		switch (method) {
		case "correlation":
			int correlation = permissionDao.correlationPermissions(roleId, permissions);
			if (correlation > 0) {
				return new ExecuteResult<String>("添加权限成功");
			}
			return new ExecuteResult<String>(false, "添加权限失败");
		case "uncorrelation":
			int uncorrelation = permissionDao.uncorrelationPermissions(roleId, permissions);
			if (uncorrelation > 0) {
				return new ExecuteResult<String>("移除权限成功");
			}
			return new ExecuteResult<String>(false, "移除权限失败");
		default:
			return new ExecuteResult<>(false, "未指定操作，请联系管理员");
		}
	}
}
