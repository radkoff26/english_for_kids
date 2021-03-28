package com.example.englishforkidsfinal.models;

// Plain Old Java Object
public class Game {
    private int res;
    private Class<?> cls;

    public Game(int res, Class<?> cls) {
        this.res = res;
        this.cls = cls;
    }

    public int getRes() {
        return res;
    }

    public Class<?> getGameClass() {
        return cls;
    }
}
