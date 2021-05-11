package com.example.englishforkidsfinal.models.db_models;

import java.util.Objects;

public class BigAnimal {
    private Integer id;
    private String uri;
    private String uri_bg;
    private String word;

    public BigAnimal(Integer id, String uri, String uri_bg, String word) {
        this.id = id;
        this.uri = uri;
        this.uri_bg = uri_bg;
        this.word = word;
    }

    public Integer getId() {
        return id;
    }

    public String getUri() {
        return uri;
    }

    public String getWord() {
        return word;
    }

    public String getUri_bg() {
        return uri_bg;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BigAnimal bigAnimal = (BigAnimal) o;
        return Objects.equals(uri, bigAnimal.uri) &&
                Objects.equals(uri_bg, bigAnimal.uri_bg) &&
                Objects.equals(word, bigAnimal.word);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uri, uri_bg, word);
    }
}

