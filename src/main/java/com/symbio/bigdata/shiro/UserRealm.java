package com.symbio.bigdata.shiro;

import com.symbio.bigdata.domain.User;
import com.symbio.bigdata.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;

/**
 * 自定义realm
 * @author leo
 *
 */
public class UserRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;

    //执行授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
        // TODO Auto-generated method stub
        System.out.println("授权");
        //获取当前登录用户
        Subject subject = SecurityUtils.getSubject();
        if(subject.getPrincipal()==null){
            System.out.println("没有登录");
        }
        Object principal = subject.getPrincipal();
        System.out.println(principal);
        User user = (User) subject.getPrincipal();
        //给资源授权
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addStringPermission(user.getPerms());
        return simpleAuthorizationInfo;
    }

    //执行身份验证逻辑，就是addAcount(a,b,c)
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken arg0) throws AuthenticationException {
        // TODO Auto-generated method stub
        System.out.println("进行认证");

        //shiro判断逻辑
//        UsernamePasswordToken user = (UsernamePasswordToken) arg0;
        UsernamePasswordToken user = new UsernamePasswordToken("111","1111");//模拟登录用户名和密码
        User realUser = new User();
        realUser.setName(user.getUsername());
        realUser.setPassword(String.copyValueOf(user.getPassword()));
        User newUser = userService.findUser(realUser);
        System.out.println("Realm中绑定的user："+newUser);
        //System.out.println(user.getUsername());
        if (newUser == null) {
            //用户名错误
            //shiro会抛出UnknownAccountException异常
            return null;
        }
        return new SimpleAuthenticationInfo(newUser.getName(), newUser.getPassword(), getName());
    }
}
