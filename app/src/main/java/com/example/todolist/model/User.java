package com.example.todolist.model;

public class User {

    private String username;

    private UserInfo userInfo;

    public User(String username, UserInfo userInfo) {
        this.username = username;
        this.userInfo = userInfo;
    }

    public String getUsername() {
        return username;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }
}
