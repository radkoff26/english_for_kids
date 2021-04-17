package com.example.englishforkidsfinal.models;

import java.io.Serializable;

public class Category implements Serializable {
    private Integer id;
    private String title;

    public Category(Integer id, String title) {
        this.id = id;
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
