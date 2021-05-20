package com.example.englishforkidsfinal.models;

// Plain Old Java Object
public class Letter {
    private Character letter;
    private Integer index;

    public Letter(Character letter) {
        this.letter = letter;
    }

    public Character getLetter() {
        return letter;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }
}
