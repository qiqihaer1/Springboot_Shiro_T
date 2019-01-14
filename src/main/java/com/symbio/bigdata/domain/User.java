package com.symbio.bigdata.domain;

import java.io.Serializable;

public class User implements Serializable {
    private String name;
    private String password;
    private String perms;

    public User() {
    }

    public User(String name, String password, String perms) {
        this.name = name;
        this.password = password;
        this.perms = perms;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPerms() {
        return perms;
    }

    public void setPerms(String perms) {
        this.perms = perms;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", perms='" + perms + '\'' +
                '}';
    }
}
