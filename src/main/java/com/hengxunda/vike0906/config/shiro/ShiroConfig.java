package com.hengxunda.vike0906.config.shiro;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {


    @Bean
    public SessionManager sessionManager(){
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionValidationSchedulerEnabled(true);
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        //sessionManager.setSessionIdCookieEnabled(false);
        return sessionManager;
    }

    /**
     * 凭证匹配器
     * （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了
     * ）
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");//散列算法:这里使用MD5算法;
        hashedCredentialsMatcher.setHashIterations(2);//散列的次数，比如散列两次，相当于 md5(md5(""));
        return hashedCredentialsMatcher;
    }

    @Bean
    public ManagerShiroRealm managerShiroRealm(){
        ManagerShiroRealm managerShiroRealm = new ManagerShiroRealm();
//        managerShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return managerShiroRealm;
    }

    @Bean("securityManager")
    public SecurityManager securityManager(ManagerShiroRealm managerShiroRealm, SessionManager sessionManager) {

        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(managerShiroRealm);
        securityManager.setSessionManager(sessionManager);
        return securityManager;
    }


    @Bean(name = "ShiroFilter")
    public ShiroFilterFactoryBean ShiroFilter(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);
        Map<String, Filter> filters = new HashMap<>();
        filters.put("auth", new OAuth2Filter());
        shiroFilter.setFilters(filters);
        Map<String,String> filterMap = new LinkedHashMap<>();

        filterMap.put("/logout", "logout");
        filterMap.put("/manager/login", "anon");


        //swagger配置
        filterMap.put("/swagger**", "anon");
        filterMap.put("/v2/api-docs", "anon");
        filterMap.put("/swagger-resources/configuration/ui", "anon");

        filterMap.put("/**/*.png", "anon");
        filterMap.put("/**/*.jpg", "anon");
        filterMap.put("/**/*.jpeg", "anon");
        filterMap.put("/**/*.css", "anon");
        filterMap.put("/**/*.js", "anon");
        filterMap.put("/**/*.html", "anon");

        filterMap.put("/**", "auth");

        shiroFilter.setFilterChainDefinitionMap(filterMap);


        return shiroFilter;
//        //权限管理
//        filterMap.put("/user/manager/recommender", "auth,perms[/user/manager/recommender]");
//        //角色管理
//        filterMap.put("/user/manager/recommender", "auth,roles[admin]");
//        //使用默认过滤器
//        filterMap.put("/**", "authc");
    }









    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
        proxyCreator.setProxyTargetClass(true);
        return proxyCreator;
    }

    @Bean("lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }


}
