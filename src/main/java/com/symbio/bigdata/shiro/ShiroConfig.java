package com.symbio.bigdata.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;


@Configuration
public class ShiroConfig {
    /**
     * 密码校验规则HashedCredentialsMatcher
     * 这个类是为了对密码进行编码的 ,
     * 防止密码在数据库里明码保存 , 当然在登陆认证的时候 ,
     * 这个类也负责对form里输入的密码进行编码
     * 处理认证匹配处理器：如果自定义需要实现继承HashedCredentialsMatcher
     */
    @Bean("hashedCredentialsMatcher")
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        //指定加密方式为MD5
        credentialsMatcher.setHashAlgorithmName("MD5");
        //加密次数
        credentialsMatcher.setHashIterations(1024);
        credentialsMatcher.setStoredCredentialsHexEncoded(true);
        return credentialsMatcher;
    }


    //创建Realm并加密返回
    @Bean(name="userRealm")
    public UserRealm getUserRealm(@Qualifier("hashedCredentialsMatcher") HashedCredentialsMatcher matcher){
        UserRealm userRealm = new UserRealm();
        userRealm.setAuthenticationCachingEnabled(false);//认证不缓存
        userRealm.setCredentialsMatcher(matcher);//设置密码器进行加密
        return userRealm;
    }

    //    //创建Realm，没有加密
//    @Bean(name="userRealm")
//    public UserRealm getUserRealm(){
//        return new UserRealm();
//    }

    @Bean(name="defaultWebSecurityManager")
    //创建DefaultWebSecurityManager
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm")UserRealm userRealm){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(userRealm);
        return defaultWebSecurityManager;

    }

    //创建ShiroFilterFactoryBean
    @Bean(name="shiroFilterFactoryBean")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("defaultWebSecurityManager")DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
        System.out.println("shiroFilterFactoryBean");
        //添加shiro内置过滤器
        /*
         * anon:表示可以匿名使用。
           authc:表示需要认证(登录)才能使用，没有参数
           roles：参数可以写多个，多个时必须加上引号，并且参数之间用逗号分割，当有多个参数时，例如admins/user/**=roles["admin,guest"],每个参数通过才算通过，相当于hasAllRoles()方法。
           perms：参数可以写多个，多个时必须加上引号，并且参数之间用逗号分割，例如/admins/user/**=perms["user:add:*,user:modify:*"]，当有多个参数时必须每个参数都通过才通过，想当于isPermitedAll()方法。
           rest：根据请求的方法，相当于/admins/user/**=perms[user:method] ,其中method为post，get，delete等。
           port：当请求的url的端口不是8081是跳转到schemal://serverName:8081?queryString,其中schmal是协议http或https等，serverName是你访问的host,8081是url配置里port的端口，queryString是你访问的url里的？后面的参数。
           authcBasic：没有参数表示httpBasic认证
           ssl:表示安全的url请求，协议为https
           user:当登入操作时不做检查
         */
        Map<String, String> fMap = new LinkedHashMap<>();
        //拦截页面 ,key是指@RequestMapping("x)的x
//        fMap.put("/login", "anon");
        fMap.put("/login", "anon");
        fMap.put("/toLogin", "anon");
        fMap.put("/register", "anon");
        fMap.put("/toRegister", "anon");
        fMap.put("/logout", "anon");
        fMap.put("/index", "authc");
        fMap.put("/test", "authc");
//        fMap.put("/one", "authc");
//        fMap.put("/two", "authc");
        //拦截未授权
        fMap.put("/one", "perms[user:one]");
        fMap.put("/two", "perms[user:two]");
        fMap.put("/**", "authc");

        shiroFilterFactoryBean.setLoginUrl("/login"); //登录页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/permission");//授权拦截返回页面
        shiroFilterFactoryBean.setSuccessUrl("/index");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(fMap);
        return shiroFilterFactoryBean;
    }


    /**
     * 配置ShiroDialect,用于thymeleaf和shiro标签配合使用
     * @return
     */
    @Bean
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }
}
