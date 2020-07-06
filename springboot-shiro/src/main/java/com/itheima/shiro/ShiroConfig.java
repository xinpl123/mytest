package com.itheima.shiro;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



/**
 * shiro的配置类
 *
 */
@Configuration
public class ShiroConfig {

	/** subject 用户主体（把操作交给securityManager）
	 * 创建ShiroFilterFactoryBean
	 * */
	@Bean
	public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager")DefaultWebSecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		
		//设置安全管理器
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		
		//添加shiro内置过滤器
		/**
		 * shiro 内置过滤器，可以实现权限相关的拦截器
		 * 常用的过滤器：
		 * anon：无需认证（登陆）就可以访问
		 * authc：必须认证才可以访问
		 * user：如果使用rememberMe的功能可以直接访问
		 * perms：该资源必须得到资源权限才可以访问
		 * role：该资源必须得到角色权限才可以访问
		 */
		Map<String,String> filterMap = new LinkedHashMap<String,String>();
		//filterMap.put(key, value);key:拦截的路径     value：过滤器类型   
//		filterMap.put("/add", "authc");
//		filterMap.put("/update", "authc");
		
		//通配模式(拦截所有请求) 有优先级 特殊的写前面
		filterMap.put("/testThymeleaf", "anon");//必须写在通配前面 否则失效
		filterMap.put("/userLogin", "anon");
		filterMap.put("/hello","anon");
		filterMap.put("/*", "authc");
		
		
		//修改跳转页面    默认跳转login
		shiroFilterFactoryBean.setLoginUrl("/toLogin");
		
		
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
		
		return shiroFilterFactoryBean;
	}
	
	/**
	 * securityManager 安全管理器 （关联realm）
	 * 创建DefaultWebSecurityManager
	 * 
	 */
	@Bean(name="securityManager")
	public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm")UserRealm userRealm) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		//关联realm
		securityManager.setRealm(userRealm);
		return securityManager;
	}
	
	/**
	 * realm shiro连接数据的桥梁
	 * 创建Realm(自定义)
	 */
	@Bean(name="userRealm")
	public UserRealm getRealm() {
		return new UserRealm();
	}
}
