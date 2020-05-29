package com.example.todolist.model;

import java.util.ArrayList;
import java.util.List;

public class UserInfo {

    private List<Info> data;

    public UserInfo() {
        this.data = new ArrayList<>();
    }

    public void addData(String s, long id) {
        this.data.add(new Info(s, id));
    }

    public List<Info> getData() {
        return data;
    }
}
