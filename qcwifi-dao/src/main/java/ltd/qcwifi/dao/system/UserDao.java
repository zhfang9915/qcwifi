package ltd.qcwifi.dao.system;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import ltd.qcwifi.base.dao.BaseDao;
import ltd.qcwifi.model.entity.Permission;
import ltd.qcwifi.model.entity.Role;
import ltd.qcwifi.model.entity.User;

/**
 * 用户信息持久化
 * 
 * @author 张芳
 *
 */
public interface UserDao extends BaseDao<User, Object>{

	/**
	 * 根据账号查询账号信息
	 * @param username
	 * @return
	 */
	User findUserByName(@Param("username")String username);

	 /**
     * 更新最后登录时间
     * @param user
     */
    void updateLastLoginTime(User user);
    
    /**
     * 更新用户的昵称和状态
     * @param user
     * @return
     */
    int updateUserNickAndLock(User user);
    
    /**
     * 密码更新
     * @param username
     * @param password
     * @return
     */
    int updatePasswordByUsername(@Param("username")String username, @Param("password")String password);
    
    /**
     * 根据角色ID查询用户ID
     * @param roleId
     * @return
     */
    List<Long> selectUserIdByRoleId(@Param("roleId")Long roleId);
    
    List<Long> selectUserIdByPermission(List<Permission> permissionsd);
    
    /**
     * 删除用户角色关系
     * @param roleId
     */
    int deleteUserRole(@Param("userId")Long userId);
    
    /**
     * 批量删除用户角色关系
     * @param roles
     * @return
     */
    int batchDeleteUserRole(List<User> users);
    
   
}
