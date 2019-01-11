package ltd.qcwifi.system.service;

import java.util.Set;

/**
 * 权限接口服务
 * @author 张芳
 *
 */
public interface PermissionService {
	
	/**
	 * 创建权限
	 * @param permission
	 * @return
	 */
//	ExecuteResult<String> createPermission(Permission permission);
    
    /**
     * 删除权限
     * @param permissionId
     */
//	ExecuteResult<String> deletePermission(Long permissionId);
	
	/**
	 * 批量删除权限
	 * @param permissions
	 * @return
	 */
//	ExecuteResult<String> batchDeletePermission(List<Permission> permissions);
    
    /**
     * 根据用户Id查询Permission
     * @param userId
     * @return
     */
    Set<String> findPermissionByUserId(Long userId);
    
    /**
     * 表格展示权限列表
     * @param param
     * @return
     */
//    TableResult<Permission> tablePermissions(PermissionParam param);
    
    /**
     * 根据ID获取权限信息
     * @param permissionId
     * @return
     */
//    ExecuteResult<Permission> searchPermissionById(Long permissionId);
    
    /**
     * 编辑权限信息
     * @param permission
     * @return
     */
//    ExecuteResult<String> updatePermission(Permission permission);
}
