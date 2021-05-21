package com.example.englishforkidsfinal.models.db_models;

import java.util.Objects;

// Plain Old Java Object
public class Word {
    private Integer id;
    private String eng;
    private String ru;
    private String url;
    private Integer gr;
    private Boolean isLoaded = false;
    private Integer category_id;

    public Word(int id, String eng, String ru, String url, int gr, Integer category_id) {
        this.id = id;
        this.eng = eng;
        this.ru = ru;
        this.url = url;
        this.gr = gr;
        this.category_id = category_id;
    }

    public Word(int id, String eng, String ru, String url, int gr, Boolean isLoaded, Integer category_id) {
        this.id = id;
        this.eng = eng;
        this.ru = ru;
        this.url = url;
        this.gr = gr;
        this.isLoaded = isLoaded;
        this.category_id = category_id;
    }

    public Boolean isLoaded() {
        return isLoaded;
    }

    public void setLoaded(Boolean loaded) {
        isLoaded = loaded;
    }

    public String getEng() {
        return eng;
    }

    public String getUrl() {
        return url;
    }

    public String getRu() {
        return ru;
    }

    public Integer getId() {
        return id;
    }

    public Integer getGr() {
        return gr;
    }

    public Integer getCategory_id() {
        return category_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word = (Word) o;
        return Objects.equals(eng, word.eng) &&
                Objects.equals(ru, word.ru) &&
                Objects.equals(url, word.url) &&
                Objects.equals(gr, word.gr) &&
                Objects.equals(category_id, word.category_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eng, ru, url, gr, category_id);
    }

    @Override
    public String toString() {
        return "Word{" +
                "id=" + id +
                ", eng='" + eng + '\'' +
                ", ru='" + ru + '\'' +
                ", url='" + url + '\'' +
                ", gr=" + gr +
                ", isLoaded=" + isLoaded +
                ", category='" + category_id + '\'' +
                '}';
    }
}
