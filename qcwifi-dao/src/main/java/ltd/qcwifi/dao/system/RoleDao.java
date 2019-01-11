package ltd.qcwifi.dao.system;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import ltd.qcwifi.base.dao.BaseDao;
import ltd.qcwifi.model.entity.Role;

/**
 * 角色持久化
 * @author 张芳
 *
 */
public interface RoleDao extends BaseDao<Role, Object> {
	
	/**
     * 根据用户id获取角色信息
     * @param userId
     * @return
     */
    Set<String> findRolesByUserId(@Param("userId")Long userId);
    
    /**
     * 删除用户角色关系
     * @param roleId
     */
    int deleteUserRole(@Param("roleId")Long roleId);
    
    /**
     * 删除角色权限关系
     * @param roleId
     * @return
     */
    int deleteRolePermission(@Param("roleId")Long roleId);

    /**
     * 批量删除用户角色关系
     * @param roles
     * @return
     */
    int batchDeleteUserRole(List<Role> roles);
    
    /**
     * 批量删除角色权限关系
     * @param roles
     * @return
     */
    int batchDeleteRolePermission(List<Role> roles);
    
    /**
     * 根据权限ID查询角色ID
     * @param permissionId
     * @return
     */
    List<Long> selectRoleIdByPermissionId(@Param("permissionId")Long permissionId);
    
    
    /**
     * 拥有/未拥有的角色列表
     * @param type
     * @param description
     * @return
     */
    List<Role> existOrNotRole(
    		@Param("type")String type, 
    		@Param("userId")Long userId, 
    		@Param("description")String description
    	);
    
    /**
     * 用户关联角色
     * @param userId
     * @param roles
     * @return
     */
    int correlationRoles(
    		@Param("userId")Long userId, 
    		@Param("list")List<Role> roles
    	);
    
    /**
     * 用户取消关联角色
     * @param userId
     * @param roles
     * @return
     */
    int uncorrelationRoles(
    		@Param("userId")Long userId, 
    		@Param("list")List<Role> roles
    	);

}
