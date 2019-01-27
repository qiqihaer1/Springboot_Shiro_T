package com.symbio.bigdata.service.impl;

import com.symbio.bigdata.domain.User;
import com.symbio.bigdata.repo.UserRepo;
import com.symbio.bigdata.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
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
}
