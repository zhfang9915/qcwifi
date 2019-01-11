package ltd.qcwifi.dao.system;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import ltd.qcwifi.base.dao.BaseDao;
import ltd.qcwifi.model.dto.param.PermissionParam;
import ltd.qcwifi.model.entity.Permission;

/**
 * 权限持久化
 * @author 张芳
 *
 */
public interface PermissionDao extends BaseDao<Permission, PermissionParam> {


    /**
     * 根据用户Id查询Permission
     * @param userId
     * @return
     */
    Set<String> findPermissionByUserId(@Param("userId")Long userId);
    
    /**
     * 删除角色权限关系
     * @param permissionId
     * @return
     */
    int deleteRolePermission(@Param("permissionId")Long permissionId);
    
    /**
     * 批量
     * @param permissions
     * @return
     */
    int batchDelebteRolePermission(List<Permission> permissions);
    
    
    /**
     * 拥有/未拥有的权限列表
     * @param type
     * @param description
     * @return
     */
    List<Permission> existOrNotPermission(
    		@Param("type")String type, 
    		@Param("roleId")Long roleId, 
    		@Param("description")String description
    	);
    
    /**
     * 角色关联权限
     * @param roleId
     * @param permissionIds
     */
    int correlationPermissions(
    		@Param("roleId")Long roleId, 
    		@Param("list")List<Permission> permissions
    	);
    
    /**
     * 角色取消关联权限
     * @param roleId
     * @param permissionIds
     */
    int uncorrelationPermissions(
    		@Param("roleId")Long roleId, 
    		@Param("list")List<Permission> permissions
    	);

}
