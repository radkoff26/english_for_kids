package com.example.englishforkidsfinal.db;

import android.provider.BaseColumns;

public class DataBaseContract {

    public DataBaseContract() {}

    public class LearnedWords implements BaseColumns {
        // Base constants for Database and its columns
        public static final String TABLE_NAME = "learned_words";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_WORD = "word";
        public static final String COLUMN_TRANSLATION = "translation";
        public static final String PICTURE_ID = "picture";
        public static final String GROUP_ID = "gr";
    }
}
