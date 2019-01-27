package com.symbio.bigdata.repo;

import com.symbio.bigdata.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepo {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public User findUser(User user){
        String sql = "select * from user where name=? and password=?";
        User user1 = jdbcTemplate.queryForObject(sql, new String[]{user.getName(), user.getPassword()}, new BeanPropertyRowMapper<User>(User.class));
        return user1;
    }

    public User findUserByName(String name){
        String sql = "select * from user where name=?";
        User user1 = jdbcTemplate.queryForObject(sql, new String[]{name}, new BeanPropertyRowMapper<User>(User.class));
        return user1;
    }
}
