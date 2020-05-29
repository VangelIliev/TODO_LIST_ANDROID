package com.example.todolist;

import java.util.HashMap;
import java.util.Map;

public class Session {

    private static Session instance;

    private Map data;

    private Session() {
        data = new HashMap<>();
    }

    public synchronized static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    public Map getData() {
        return data;
    }
}
