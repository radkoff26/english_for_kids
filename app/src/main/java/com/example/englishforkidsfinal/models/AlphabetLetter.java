package com.example.englishforkidsfinal.models;

import java.io.Serializable;

public class AlphabetLetter {
    private String letter;
    private int anim;
    private int res;

    public AlphabetLetter(String letter, int anim, int res) {
        this.letter = letter;
        this.anim = anim;
        this.res = res;
    }

    public String getLetter() {
        return letter;
    }

    public int getAnim() {
        return anim;
    }

    public int getRes() {
        return res;
    }

    @Override
    public String toString() {
        return letter;
    }
}
