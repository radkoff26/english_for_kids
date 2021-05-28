package com.example.englishforkidsfinal.models;

import android.graphics.Color;

public class ColorsPaint {
    private String color;
    private int number;

    public ColorsPaint(String color, int number) {
        this.color = color;
        this.number = number;
    }

    public String getColor() {
        return color;
    }

    public int getNumber() {
        return number;
    }

    public int getIntColor() {
        return Color.parseColor(color);
    }
}
