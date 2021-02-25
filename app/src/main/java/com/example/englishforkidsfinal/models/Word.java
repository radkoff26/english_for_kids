package com.example.englishforkidsfinal.models;

public class Word {
    private int id;
    private String animal;
    private String translation;
    private int res;

    public Word(int id, String animal, String translation, int res) {
        this.id = id;
        this.animal = animal;
        this.translation = translation;
        this.res = res;
    }

    public String getAnimal() {
        return animal;
    }

    public int getRes() {
        return res;
    }

    public String getTranslation() {
        return translation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean equals(Object o) {
        if (o instanceof Word) {
            return this.animal.toLowerCase().equals(((Word) o).animal.toLowerCase());
        }
        return false;
    }
}
