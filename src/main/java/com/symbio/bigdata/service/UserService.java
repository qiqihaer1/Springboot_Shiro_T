package com.symbio.bigdata.service;

import com.symbio.bigdata.domain.User;

public interface UserService {
    public User findUser(User user);
    public User findUserByName(String name);
}
