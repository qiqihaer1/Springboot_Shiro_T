package com.symbio.bigdata.repo;

import com.symbio.bigdata.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepo {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public User findUser(User user) {
        String sql = "select * from user where name=? and password=?";
        return jdbcTemplate.queryForObject(sql, new String[]{user.getName(), user.getPassword()}, new BeanPropertyRowMapper<User>(User.class));
    }

    public User findUserByName(String name) {
        User user = null;
        String sql = "select * from user where name=?";
        try {
            user = jdbcTemplate.queryForObject(sql, new String[]{name}, new BeanPropertyRowMapper<User>(User.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return user;

    }

    public void insertUser(String name, String password, String salt) {
        String sql = "insert into user(name,password,salt) values ( ?,?,?)";
        jdbcTemplate.update(sql,name, password, salt);
    }
}
