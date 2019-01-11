package ltd.qcwifi.system.service;

import java.util.List;

import ltd.qcwifi.model.dto.ExecuteResult;
import ltd.qcwifi.model.dto.TableResult;
import ltd.qcwifi.model.entity.Permission;
import ltd.qcwifi.model.entity.Role;
import ltd.qcwifi.model.entity.User;

/**
 * 账号信息
 * @author 张芳
 *
 */
public interface UserService{

    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    User findByUsername(String username);
    
    /**
     * 根据ID获取用户信息
     * @param userId
     * @return
     */
//    ExecuteResult<User> findByUserId(Long userId);

    /**
     * 更新最后登录时间
     * @param user
     */
    void updateLastLoginTime(User user);

    /**
     * 根据角色ID查找用户ID
     * @param roleId
     * @return
     */
    List<Long> selectUserIdByRoleId(Long roleId);
    
    List<Long> selectUserIdByPermission(List<Permission> permissions);
    
    /**
     * 表格展示用户	列表
     * @param param
     * @return
     */
//    TableResult<User> tableUsers(UserParam param);
    
    /**
	 * 创建用户
	 * @param role
	 * @return
	 */
//	ExecuteResult<String> createUser(User user);
	
	/**
	 * 删除用户
	 * @param userId
	 * @return
	 */
//	ExecuteResult<String> deleteUser(Long userId);
	
	/**
	 * 批量删除会员
	 * @param users
	 * @return
	 */
//	ExecuteResult<String> batchDeleteUser(List<User> users);
	
	/**
	 * 更新会员账号昵称和状态
	 * @param user
	 * @return
	 */
	ExecuteResult<String> updateUserNickAndLock(User user);
	
	/**
	 * 密码更新
	 * @param username
	 * @param password
	 * @return
	 */
	ExecuteResult<String> updatePasswordByUsername(String username, String password);
	
	/**
	 * 已有/未有角色
	 * @param method
	 * @param userId
	 * @param description
	 * @return
	 */
	TableResult<Role> existOrNotRole(String method, Long userId, String description);
	
	/**
	 * 角色分配
	 * @param method
	 * @param userId
	 * @param roles
	 * @return
	 */
	ExecuteResult<String> roleConfiguration(String method, Long userId, List<Role> roles);
	
}
