package ltd.qcwifi.system.service;

import java.util.List;
import java.util.Set;

import ltd.qcwifi.model.dto.ExecuteResult;
import ltd.qcwifi.model.dto.TableResult;
import ltd.qcwifi.model.entity.Permission;

/**
 * 角色服务接口
 * @author 张芳
 *
 */
public interface RoleService {

	/**
	 * 创建角色
	 * @param role
	 * @return
	 */
//	ExecuteResult<String> createRole(Role role);
    
    /**
     * 删除角色
     * @param roleId
     */
//	ExecuteResult<String> deleteRole(Long roleId);
	
	/**
	 * 批量删除权限
	 * @param permissions
	 * @return
	 */
//	ExecuteResult<String> batchDeleteRole(List<Role> roles);

    /**
     * 根据用户id获取角色信息
     * @param userId
     * @return
     */
    Set<String> findRolesByUserId(Long userId);
    
    /**
     * 表格展示权限列表
     * @param param
     * @return
     */
//    TableResult<Role> tableRoles(RoleParam param);
    
    /**
     * 根据ID获取权限信息
     * @param permissionId
     * @return
     */
//    ExecuteResult<Role> searchRoleById(Long roleId);
    
    /**
     * 编辑权限信息
     * @param permission
     * @return
     */
//    ExecuteResult<String> updateRole(Role role);
    
    /**
     * 拥有/未拥有的权限列表信息
     * @param method
     * @param description
     * @return
     */
    TableResult<Permission> existOrNotPermission(String method, Long roleId, String description);

    /**
     * 添加/移除角色-权限之间关系
     * @param roleId
     * @param permissionIds
     */
	ExecuteResult<String> permissionConfiguration(String method, Long roleId, List<Permission> permissions);
}
