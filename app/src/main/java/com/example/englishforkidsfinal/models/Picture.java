package com.example.englishforkidsfinal.models;

import java.util.List;

// Plain Old Java Object
public class Picture {
    private int resource;
    private List<ColorsPaint> colors;

    public Picture(int resource, List<ColorsPaint> colors) {
        this.resource = resource;
        this.colors = colors;
    }

    public int getResource() {
        return resource;
    }

    public List<ColorsPaint> getColors() {
        return colors;
    }
}
