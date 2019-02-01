package com.symbio.bigdata.service.impl;

import com.symbio.bigdata.domain.User;
import com.symbio.bigdata.repo.UserRepo;
import com.symbio.bigdata.service.UserService;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

import static java.lang.String.*;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;

    @Override
    public User findUser(User user) {
        return userRepo.findUser(user);
    }

    @Override
    public User findUserByName(String name) {
        return userRepo.findUserByName(name);
    }


    @Override
    public boolean getRegister(String name, String password) {
        int salt = new Random().nextInt(10);
        String passwordSalt = "";
        /* * MD5加密：
        * 使用SimpleHash类对原始密码进行加密。
        * 第一个参数代表使用MD5方式加密
        * 第二个参数为原始密码 * 第三个参数为盐值，即用户名
        * 第四个参数为加密次数
        * 最后用toHex()方法将加密后的密码转成String
        * */
        passwordSalt = new SimpleHash("MD5", password, valueOf(salt), 1024).toHex();
        User oldUser = findUserByName(name);
        if(oldUser==null){//no register user
             userRepo.insertUser(name, passwordSalt, valueOf(salt));
             return true;
        }
        return false;
    }
}
