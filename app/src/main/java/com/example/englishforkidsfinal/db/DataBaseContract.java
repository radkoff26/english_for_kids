package com.example.englishforkidsfinal.db;

public class DataBaseContract {

    public DataBaseContract() {}

    public class LearnedWords {
        public static final String TABLE_NAME = "learned_words";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_WORD = "word";
        public static final String COLUMN_TRANSLATION = "translation";
        public static final String PICTURE_ID = "picture";
    }
}
