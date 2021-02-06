package com.example.englishforkidsfinal.models;

public class Model {
    private String animal;
    private int res;

    public Model(String animal, int res) {
        this.animal = animal;
        this.res = res;
    }

    public String getAnimal() {
        return animal;
    }

    public int getRes() {
        return res;
    }
}
