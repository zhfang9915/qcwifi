package ltd.qcwifi.shiro.token;

import java.util.Date;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import ltd.qcwifi.model.entity.User;
import ltd.qcwifi.shiro.token.manager.TokenManager;
import ltd.qcwifi.system.service.PermissionService;
import ltd.qcwifi.system.service.RoleService;
import ltd.qcwifi.system.service.UserService;



/**
 * shiro 认证 + 授权   重写
 * @author 张芳
 *
 */
public class SampleRealm extends AuthorizingRealm {

	@Autowired
	UserService userService;
	
	@Autowired
	PermissionService permissionService;
	
	@Autowired
	RoleService roleService;
	
	public SampleRealm() {
		super();
	}
	/**
	 *  认证信息，主要针对用户登录， 
	 */
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) throws AuthenticationException {
		
		ShiroToken token = (ShiroToken) authcToken;
        User user = userService.findByUsername(token.getUsername());
        if(user == null) {
            throw new UnknownAccountException();//没找到帐号
        }
        if(Boolean.TRUE.equals(user.getLocked())) {
            throw new LockedAccountException(); //帐号锁定
        }
		//更新最后登录时间 last login time
		user.setLastLoginTime(new Date());
		userService.updateLastLoginTime(user);
        return new SimpleAuthenticationInfo(user, user.getPassword(), ByteSource.Util.bytes(user.getCredentialsSalt()),  getName());
    }

	 /** 
     * 授权 
     */  
    @Override  
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {  
    	
		SimpleAuthorizationInfo info =  new SimpleAuthorizationInfo();
		//根据用户ID查询角色（role），放入到Authorization里。
		Set<String> roles = roleService.findRolesByUserId(TokenManager.getUserId());
		info.setRoles(roles);
		//根据用户ID查询权限（permission），放入到Authorization里。
		Set<String> permissions = permissionService.findPermissionByUserId(TokenManager.getUserId());
		info.setStringPermissions(permissions);
        return info;  
    }  
    /**
     * 清空当前用户权限信息
     */
	public  void clearCachedAuthorizationInfo() {
		PrincipalCollection principalCollection = SecurityUtils.getSubject().getPrincipals();
		SimplePrincipalCollection principals = new SimplePrincipalCollection(
				principalCollection, getName());
		super.clearCachedAuthorizationInfo(principals);
	}
	/**
	 * 指定principalCollection 清除
	 */
	public void clearCachedAuthorizationInfo(PrincipalCollection principalCollection) {
		SimplePrincipalCollection principals = new SimplePrincipalCollection(
				principalCollection, getName());
		super.clearCachedAuthorizationInfo(principals);
	}
	
}
