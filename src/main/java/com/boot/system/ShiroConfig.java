package com.boot.system;

import com.boot.util.OrderProperties;
import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.authz.Authorizer;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
/**
 * @author chenjiang
 */
@Configuration
public class ShiroConfig { 
  
    @Bean 
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("mySecurityManager") SecurityManager securityManager) {  
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();  
        shiroFilterFactoryBean.setSecurityManager(securityManager);  
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();  
        //注意过滤器配置顺序 不能颠倒
        String path = System.getProperty("user.dir")+"\\src\\main\\resources\\shiroConfig.properties";
        File file = new File(path);
        Properties Properties = new OrderProperties();
        try {
            Reader Reader = new FileReader(file);
            Properties.load(Reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Object o : Properties.keySet()) {
            filterChainDefinitionMap.put(o.toString(),Properties.getProperty(o.toString()));
        }
        //登录界面
        shiroFilterFactoryBean.setLoginUrl("/login");
        //未授权界面
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);  
        return shiroFilterFactoryBean;  
    }
    
    @Bean("myHashedCredentialsMatcher")
    public HashedCredentialsMatcher hashedCredentialsMatcher() {  
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();  
        hashedCredentialsMatcher.setHashAlgorithmName("md5");//散列算法:这里使用MD5算法;  
        hashedCredentialsMatcher.setHashIterations(1024);//散列的次数，比如散列两次，相当于 md5(md5(""));
        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);
        return hashedCredentialsMatcher;  
    }  
  
    @Bean("myShiroRealm")  
    public MyShiroRealm myShiroRealm(@Qualifier("myHashedCredentialsMatcher")HashedCredentialsMatcher hashedCredentialsMatcher) {  
        MyShiroRealm myShiroRealm = new MyShiroRealm(); 
        myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        return myShiroRealm;
    } 
    
    @Bean("mySecurityManager")  
    public SecurityManager securityManager(@Qualifier("myShiroRealm")MyShiroRealm myShiroRealm) {  
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();  
        securityManager.setRealm(myShiroRealm); 
        securityManager.setAuthenticator(authenticator());
        securityManager.setAuthorizer(authorizer());
        securityManager.setRememberMeManager(rememberMeManager());
        return securityManager;  
    }

    @Bean
    public SimpleCookie rememberMeCookie(){
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        //<!-- 记住我cookie生效时间30天 ,单位秒;-->
        simpleCookie.setMaxAge(259200);
        return simpleCookie;
    }

    /**
     * cookie管理对象;
     * rememberMeManager()方法是生成rememberMe管理器，而且要将这个rememberMe管理器设置到securityManager中
     * @return
     */
    @Bean
    public CookieRememberMeManager rememberMeManager(){
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        //rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
        cookieRememberMeManager.setCipherKey(Base64.decode("2AvVhdsgUs0FSA3SDFAdag=="));
        return cookieRememberMeManager;
    }

    @Bean  
    public Authenticator authenticator() {
    	Authenticator authenticator = new ModularRealmAuthenticator();
    	return authenticator;  
    } 
    
    @Bean  
    public Authorizer authorizer() {
    	Authorizer Authorizer = new ModularRealmAuthorizer();
    	return Authorizer;  
    } 
    
    @Bean  
    public SessionManager sessionManager() {
    	SessionManager sessionManager= new DefaultWebSessionManager();
    	return sessionManager;  
    }
  
    @Bean  
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisorsec(@Qualifier("mySecurityManager")SecurityManager securityManager) {  
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();  
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);  
        return authorizationAttributeSourceAdvisor;  
    }  
}  
