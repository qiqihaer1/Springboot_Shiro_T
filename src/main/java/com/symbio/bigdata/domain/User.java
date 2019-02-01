package com.symbio.bigdata.domain;

import java.io.Serializable;

public class User implements Serializable {
    private String name;
    private String password;
    private String perms;
    private String salt;

    public User() {
    }

    public User(String name, String password, String perms, String salt) {
        this.name = name;
        this.password = password;
        this.perms = perms;
        this.salt = salt;
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

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", perms='" + perms + '\'' +
                ", salt='" + salt + '\'' +
                '}';
    }
}
