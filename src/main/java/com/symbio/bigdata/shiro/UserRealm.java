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
import org.springframework.dao.EmptyResultDataAccessException;

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
        //获取当前登录用户,从SimpleAuthenticationInfo(x,y,z)来的x
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        if(user==null){
            System.out.println("没有登录");
        }

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addStringPermission(user.getPerms()); //给资源授权
        return simpleAuthorizationInfo;
    }

    //执行身份验证逻辑，就是addAcount(a,b,c)
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken arg0) throws AuthenticationException {
        // TODO Auto-generated method stub
        System.out.println("进行认证");
        //shiro判断逻辑,从subject.login(x)传来的x
        UsernamePasswordToken user = (UsernamePasswordToken) arg0;
//        UsernamePasswordToken user = new UsernamePasswordToken("111","1111");//模拟登录用户名和密码
        try{
            User newUser = userService.findUserByName(user.getUsername());
            System.out.println("Realm中绑定的user："+newUser);
            if (newUser == null) {
                //用户名错误
                //shiro会抛出UnknownAccountException异常
                System.out.println("没有newUser");
                return null;
            }
            return new SimpleAuthenticationInfo(newUser, newUser.getPassword(), getName());
        }catch (EmptyResultDataAccessException e){//jdbcTemplate.queryForObject报错
                return null;
        }


    }
}
