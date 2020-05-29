package com.example.todolist.model;

public class Info {

    private final String info;

    private final long id;

    public Info(String info, long id) {
        this.info = info;
        this.id = id;
    }

    public long getInfoId() {
        return id;
    }

    public String getInfo() {
        return info;
    }
}
