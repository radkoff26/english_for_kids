package com.example.englishforkidsfinal.models;

public class Drawing {
    private int painted, unpainted;
    private String word;

    public Drawing(int painted, int unpainted, String word) {
        this.painted = painted;
        this.unpainted = unpainted;
        this.word = word;
    }

    public int getPainted() {
        return painted;
    }

    public int getUnpainted() {
        return unpainted;
    }

    public String getWord() {
        return word;
    }
}
