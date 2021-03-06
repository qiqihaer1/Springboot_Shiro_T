package com.symbio.bigdata.controller;

import com.symbio.bigdata.domain.User;
import com.symbio.bigdata.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("index")
    public String getIndex(){
        return "index";
    }

    @RequestMapping("login")
    public String getLogin( ){
        return "login";
    }

    @RequestMapping("permission")
    public String getPermission( ){
        return "permission";
    }

    @RequestMapping("register")
    public String toRegister() {
        return "register";
    }

    @RequestMapping("logout")
    public String getlogout( ){
        Subject subject = SecurityUtils.getSubject();//取出当前验证主体
        if (subject != null) {
            subject.logout();//不为空，执行一次logout的操作，将session全部清空
        }
        return "login";
    }

    @RequestMapping("one")
    public String one( ){
        return "one";
    }

    @RequestMapping("two")
    public String two( ){
        return "two";
    }

    //shiro用户认证
    @RequestMapping("/toLogin")
    public String toLogin(User user, Model model){
        //获取subject
        Subject subject = SecurityUtils.getSubject();
        //封装用户数据
        UsernamePasswordToken userToken = new UsernamePasswordToken(user.getName(),user.getPassword());
        //执行登录方法,用捕捉异常去判断是否登录成功
        try {
            subject.login(userToken);
            return "index";//成功后跳转到请求
        } catch (UnknownAccountException e) {
            //用户名不存在
            model.addAttribute("msg","用户名不存在");
            return "login";//return "redirect:/login"只定向到/login请求但无法带参数msg传入
        }catch (IncorrectCredentialsException e) {
            //密码错误
            model.addAttribute("msg","密码错误");
            return "login";//定向到login页面
        }

    }

    //shiro用户注册
    @RequestMapping("/toRegister")
    public String toRegister(@RequestParam("name") String name, @RequestParam("password") String password, Model model){
        //register
        boolean result = userService.getRegister(name,password);
        //执行登录方法,用捕捉异常去判断是否登录成功
        if(result){
            model.addAttribute("msg","注册成功");
            return "/login";
        }else {
            model.addAttribute("msg","该用户已存在");
            return "/register";
        }

    }

    @RequestMapping("test")
    public String thymeleaf( Model model){
        model.addAttribute("name","thymeleaf is ok");
        return "test";
    }



}

