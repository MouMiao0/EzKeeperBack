package com.example.ezkeeper.config;

import com.example.ezkeeper.filter.TokenFilter;
import com.example.ezkeeper.realm.DefaultRealm;
import com.example.ezkeeper.realm.TokenRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SessionStorageEvaluator;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSessionStorageEvaluator;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import java.util.*;

@Configuration
public class ShiroConfig {

    private String algorithmName = "MD5";

    private int iterationCount = 123;

    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        Map<String, Filter> filterMap = new LinkedHashMap<>();
        filterMap.put("jwt",new TokenFilter());
        shiroFilterFactoryBean.setFilters(filterMap);


        Map<String, String> urlFilterMap = new LinkedHashMap<>();
        urlFilterMap.put("/users/register","anon");
        urlFilterMap.put("/users/login","anon");
        urlFilterMap.put("/users/login_by_phone","anon");
        urlFilterMap.put("/users/logout","logout");

        urlFilterMap.put("/**","jwt");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(urlFilterMap);

        return shiroFilterFactoryBean;
    }


    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 禁用session, 不保存用户登录状态。保证每次请求都重新认证。
     * 需要注意的是，如果用户代码里调用Subject.getSession()还是可以用session，如果要完全禁用，要配合下面的noSessionCreation的Filter来实现
     */
    @Bean
    public SecurityManager securityManager(HashedCredentialsMatcher hashedCredentialsMatcher){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setAuthenticator(modularRealmAuthenticator());

        List<Realm> realms = new ArrayList<>();
        realms.add(realm(hashedCredentialsMatcher));
        realms.add(tokenRealm());

        securityManager.setRealms(realms);

        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultWebSessionStorageEvaluator sessionStorageEvaluator = new DefaultWebSessionStorageEvaluator();
        sessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(sessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);

        return securityManager;
    }

    @Bean
    public ModularRealmAuthenticator modularRealmAuthenticator(){
        return new ModularRealmAuthenticator();
    }

    /**
     * 用户密码Realm
     * @param hashedCredentialsMatcher 加密方式
     * @return realm
     */
    @Bean("DefaultRealm")
    public Realm realm(HashedCredentialsMatcher hashedCredentialsMatcher){
        DefaultRealm defaultRealm = new DefaultRealm();
        defaultRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        return defaultRealm;
    }

    @Bean("TokenRealm")
    public Realm tokenRealm(){
        return new TokenRealm();
    }

    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName(algorithmName);
        matcher.setHashIterations(iterationCount);
        return matcher;
    }
    @Bean
    public MethodInvokingFactoryBean getMethodInvokingFactoryBean(){
        MethodInvokingFactoryBean factoryBean = new MethodInvokingFactoryBean();
        factoryBean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
        factoryBean.setArguments(new Object[]{securityManager(hashedCredentialsMatcher())});
        return factoryBean;
    }


    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }
}
