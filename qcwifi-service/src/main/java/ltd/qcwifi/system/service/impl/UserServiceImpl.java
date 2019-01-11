package ltd.qcwifi.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import ltd.qcwifi.dao.cloud.UserBindingDao;
import ltd.qcwifi.dao.system.RoleDao;
import ltd.qcwifi.dao.system.UserDao;
import ltd.qcwifi.model.dto.ExecuteResult;
import ltd.qcwifi.model.dto.TableResult;
import ltd.qcwifi.model.dto.param.UserParam;
import ltd.qcwifi.model.entity.Permission;
import ltd.qcwifi.model.entity.Role;
import ltd.qcwifi.model.entity.User;
import ltd.qcwifi.service.impl.AbstractBaseServiceImpl;
import ltd.qcwifi.system.service.UserService;
import net.sf.json.JSONObject;

/**
 * 账户服务实现
 * @author 张芳
 *
 */
@Service("userService")
public class UserServiceImpl extends AbstractBaseServiceImpl<User, UserParam> implements UserService {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserDao userDao;
    
    @Autowired
    RoleDao roleDao;
    
    @Autowired
    UserBindingDao userBindingDao;
    
    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    @Override
    public User findByUsername(String username) {
        return userDao.findUserByName(username);
    }
    
    @Override
    public ExecuteResult<User> searchById(Object id) {
    	Long userId = (Long) id;
    	User user = userDao.selectOneById(userId);
    	if (user == null) {
			return new ExecuteResult<>(false, "未查询到用户信息");
		}
    	return new ExecuteResult<User>(user);
    }

	@Override
	public void updateLastLoginTime(User user) {
		userDao.updateLastLoginTime(user);
	}
	
	@Override
	public List<Long> selectUserIdByRoleId(Long roleId) {
		return userDao.selectUserIdByRoleId(roleId);
	}
	
	@Override
	public List<Long> selectUserIdByPermission(List<Permission> permissions) {
		return userDao.selectUserIdByPermission(permissions);
	}

	@Override
	public TableResult<User> tables(UserParam param) {
		TableResult<User> result = new TableResult<>();
		// 设置分页
		PageHelper.startPage(param.getPageNumber(), param.getPageSize());
		// 查询数据
		List<User> users = userDao.selectByPage(param);
		// 获取分页信息
		PageInfo<User> pageInfo = new PageInfo<User>(users);
		result.setRows(users);
		result.setTotal(pageInfo.getTotal());
		return result;
	}

	@Transactional
	@Override
	public ExecuteResult<String> create(User user) {
		int count = userDao.insert(user);
		//默认创建的用户为普通用户
		List<Role> roles = new ArrayList<>();
		Role role = new Role();
		role.setId(4L);
		roles.add(role);
		roleDao.correlationRoles(user.getId(), roles);
		
		//默认空绑定信息
		userBindingDao.createBinding(user.getId());
		
		if (count > 0) {
			return new ExecuteResult<String>("创建会员成功");
		}
		return new ExecuteResult<String>(false, "创建会员失败");
	}
	
	@Override
    @Transactional
    public ExecuteResult<String> delete(Object id) {
		Long userId = (Long) id;
    	//1. 删除用户角色关系
    	userDao.deleteUserRole(userId);
    	
        //2.删除用户
        int count = userDao.delete(userId);
        
        if (count == 1) {
			return new ExecuteResult<String>("删除会员成功");
		}
        return new ExecuteResult<String>(false, "删除会员失败");
    }
	
	@Override
    @Transactional
	public ExecuteResult<String> batchDelete(List<User> users) {
		//1. 删除用户角色关系 
		userDao.batchDeleteUserRole(users);
		
    	//2. 删除用户
    	int u = userDao.batchDelete(users);
    	
    	if (u > 0) {
        	return new ExecuteResult<String>("删除会员成功");
		}
        return new ExecuteResult<String>(false, "删除会员失败");
	}
	
	@Override
	public ExecuteResult<String> updateUserNickAndLock(User user) {
		int count = userDao.updateUserNickAndLock(user);
		if (count > 0) {
			return new ExecuteResult<String>("更新会员账号信息成功");
		}
		return new ExecuteResult<>(false, "更新会员账号信息失败");
	}
	
	@Override
	public ExecuteResult<String> updatePasswordByUsername(String username, String password) {
		int count = userDao.updatePasswordByUsername(username, password);
		if (count > 0) {
			return new ExecuteResult<String>("新密码已生效，本公司不会以任何名义向您索要密码信息！");
		}
		return new ExecuteResult<>(false, "密码修改失败");
	}
	
	@Override
	public TableResult<Role> existOrNotRole(String method, Long userId, String description) {
		TableResult<Role> result = new TableResult<>();
		String type = null;
		switch (method) {
		case "exist":
			type = "IN";//已有的角色
			break;
		default:
			type = "NOT";//未拥有的角色
			break;
		}
		List<Role> roles = roleDao.existOrNotRole(type, userId, description);
		result.setRows(roles);
		result.setTotal(roles.size());
		return result;
	}
	
	@Override
	public ExecuteResult<String> roleConfiguration(String method, Long userId, List<Role> roles) {
		switch (method) {
		case "correlation":
			int correlation = roleDao.correlationRoles(userId, roles);
			if (correlation > 0) {
				return new ExecuteResult<String>("添加角色成功");
			}
			return new ExecuteResult<String>(false, "添加角色失败");
		case "uncorrelation":
			int uncorrelation = roleDao.uncorrelationRoles(userId, roles);
			if (uncorrelation > 0) {
				return new ExecuteResult<String>("移除角色成功");
			}
			return new ExecuteResult<String>(false, "移除角色失败");
		default:
			return new ExecuteResult<>(false, "未指定操作，请联系管理员");
		}
	}

}
