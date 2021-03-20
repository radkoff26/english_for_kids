package com.example.englishforkidsfinal.models.db_models;

// Plain Old Java Object
public class SpeakRightModel {
    private Integer id;
    private String eng;
    private String url;

    public SpeakRightModel(Integer id, String eng, String url) {
        this.id = id;
        this.eng = eng;
        this.url = url;
    }

    public Integer getId() {
        return id;
    }

    public String getEng() {
        return eng;
    }

    public String getUrl() {
        return url;
    }
}