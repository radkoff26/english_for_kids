package com.example.englishforkidsfinal.models;

// Plain Old Java Object
public class Word {
    private int id;
    private String animal;
    private String translation;
    private int res;
    private int gr;

    public Word(int id, String animal, String translation, int res, int gr) {
        this.id = id;
        this.animal = animal;
        this.translation = translation;
        this.res = res;
        this.gr = gr;
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

    public int getGr() {
        return gr;
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

    @Override
    public String toString() {
        return "Word{" +
                "id=" + id +
                ", animal='" + animal + '\'' +
                ", translation='" + translation + '\'' +
                ", res=" + res +
                '}';
    }
}
