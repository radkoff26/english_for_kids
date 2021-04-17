package com.example.englishforkidsfinal.db.contractions;

import android.provider.BaseColumns;

import static com.example.englishforkidsfinal.db.contractions.DataBaseContract.MainTableContractions.COLUMN_ENG;
import static com.example.englishforkidsfinal.db.contractions.DataBaseContract.MainTableContractions.COLUMN_GR;
import static com.example.englishforkidsfinal.db.contractions.DataBaseContract.MainTableContractions.COLUMN_ID;
import static com.example.englishforkidsfinal.db.contractions.DataBaseContract.MainTableContractions.COLUMN_RU;
import static com.example.englishforkidsfinal.db.contractions.DataBaseContract.MainTableContractions.COLUMN_URL;
import static com.example.englishforkidsfinal.db.contractions.DataBaseContract.MainTableContractions.TABLE_NAME;

public class DataBaseContract {

    public DataBaseContract() {}

    public class MainTableContractions implements BaseColumns {
        // Base constants for Database and its columns
        public static final String TABLE_NAME = "learned_words";
        public static final String All_WORDS_TABLE_NAME = "all_words";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_ENG = "eng";
        public static final String COLUMN_RU = "ru";
        public static final String COLUMN_URL = "url";
        public static final String COLUMN_GR = "gr";
        public static final String COLUMN_IS_LOADED = "is_loaded";
        public static final String COLUMN_CATEGORY = "category";
    }
}
