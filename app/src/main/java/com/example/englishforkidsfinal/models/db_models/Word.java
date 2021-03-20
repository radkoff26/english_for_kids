package com.example.englishforkidsfinal.models.db_models;

// Plain Old Java Object
public class Word {
    private Integer id;
    private String eng;
    private String ru;
    private String url;
    private Integer gr;

    public Word(int id, String eng, String ru, String url, int gr) {
        this.id = id;
        this.eng = eng;
        this.ru = ru;
        this.url = url;
        this.gr = gr;
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

    public boolean equals(Object o) {
        if (o instanceof Word) {
            return this.eng.toLowerCase().equals(((Word) o).eng.toLowerCase());
        }
        return false;
    }

    @Override
    public String toString() {
        return "Word{" +
                "id=" + id +
                ", eng='" + eng + '\'' +
                ", ru='" + ru + '\'' +
                ", url=" + url +
                ", gr=" + gr +
                '}';
    }
}