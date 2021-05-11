package com.example.englishforkidsfinal.db.contractions;

import android.provider.BaseColumns;

public class DataBaseContractions {

    public DataBaseContractions() {}

    public class MainTableContractions implements BaseColumns {
        // Base constants for Database and its columns
        public static final String TABLE_NAME = "learned_words";
        public static final String All_WORDS_TABLE_NAME = "all_words";
        public static final String CATEGORY_TABLE_NAME = "categories";
        public static final String BIG_ANIMAL_TABLE_NAME = "animals";
        public static final String ALPHABET_TABLE_NAME = "alphabet";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_ENG = "eng";
        public static final String COLUMN_RU = "ru";
        public static final String COLUMN_URL = "url";
        public static final String COLUMN_GR = "gr";
        public static final String COLUMN_IS_LOADED = "is_loaded";
        public static final String COLUMN_CATEGORY_ID = "category";
        public static final String COLUMN_CATEGORY_TITLE = "title";
        public static final String COLUMN_BIG_ANIMAL_URI = "animal_uri";
        public static final String COLUMN_BIG_ANIMAL_URI_BG = "animal_uri_bg";
        public static final String COLUMN_BIG_ANIMAL_WORD = "animal_word";
        public static final String COLUMN_ALPHABET_LETTER = "letter";
        public static final String COLUMN_ALPHABET_LETTER_PICTURE_URI = "picture_uri";
        public static final String COLUMN_ALPHABET_LETTER_LOADED = "is_letter_loaded";
        public static final String COLUMN_ALPHABET_LETTER_URI = "uri";
    }
}
