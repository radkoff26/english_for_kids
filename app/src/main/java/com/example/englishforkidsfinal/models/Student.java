package com.example.englishforkidsfinal.models;

public class Student {
    private int id;
    private String name;
    private Integer gr;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGr(Integer gr) {
        this.gr = gr;
    }

    public String getName() {
        return name;
    }

    public Integer getGr() {
        return gr;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gr=" + gr +
                '}';
    }
}
