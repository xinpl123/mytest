package com.itheima.shiro;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.itheima.domain.User;
import com.itheima.service.UserService;

/**
 * @author 62370
 *
 */
public class UserRealm extends AuthorizingRealm{

	@Resource
	private UserService userService;
	
	/**
	 * 执行授权逻辑
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("执行授权逻辑");
		return null;
	}

	/**
	 * 执行认证逻辑
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("执行认证逻辑");
		
		
		//编写shiro判断逻辑，判断用户名和密码
		UsernamePasswordToken u_token = (UsernamePasswordToken)token;
		
		User user = userService.findByUserName(u_token.getUsername());
		//1.判断用户名
		if(user == null) {
			//用户名不存在
			return null;//shiro底层会抛出UnKnowAccountException
		}
		//2.判断密码
		return new SimpleAuthenticationInfo("",user.getPassword(),"");
		
	}

	
}

