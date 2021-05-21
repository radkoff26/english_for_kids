package com.example.englishforkidsfinal.models;

import java.io.Serializable;
import java.util.Objects;

public class RestAlphabetLetter implements Serializable {
    private Integer id;
    private String letter;
    private String uri;
    private String picture_uri;
    private boolean isLoaded;

    public RestAlphabetLetter(Integer id, String letter, String uri, String picture_uri, boolean isLoaded) {
        this.id = id;
        this.letter = letter;
        this.uri = uri;
        this.picture_uri = picture_uri;
        this.isLoaded = isLoaded;
    }

    public Integer getId() {
        return id;
    }

    public String getLetter() {
        return letter;
    }

    public String getUri() {
        return uri;
    }

    public boolean isLoaded() {
        return isLoaded;
    }

    public String getPicture_uri() {
        return picture_uri;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestAlphabetLetter that = (RestAlphabetLetter) o;
        return Objects.equals(letter, that.letter) &&
                Objects.equals(uri, that.uri) &&
                Objects.equals(picture_uri, that.picture_uri);
    }

    @Override
    public int hashCode() {
        return Objects.hash(letter, uri, picture_uri);
    }

    @Override
    public String toString() {
        return "RestAlphabetLetter{" +
                "id=" + id +
                ", letter='" + letter + '\'' +
                ", uri='" + uri + '\'' +
                ", picture_uri='" + picture_uri + '\'' +
                ", isLoaded=" + isLoaded +
                '}';
    }
}
