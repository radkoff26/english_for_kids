package com.example.englishforkidsfinal.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.englishforkidsfinal.models.db_models.Word;

import java.util.ArrayList;
import java.util.List;

import static com.example.englishforkidsfinal.db.contractions.DataBaseContract.MainTableContractions.All_WORDS_TABLE_NAME;
import static com.example.englishforkidsfinal.db.contractions.DataBaseContract.MainTableContractions.COLUMN_CATEGORY;
import static com.example.englishforkidsfinal.db.contractions.DataBaseContract.MainTableContractions.COLUMN_ENG;
import static com.example.englishforkidsfinal.db.contractions.DataBaseContract.MainTableContractions.COLUMN_GR;
import static com.example.englishforkidsfinal.db.contractions.DataBaseContract.MainTableContractions.COLUMN_ID;
import static com.example.englishforkidsfinal.db.contractions.DataBaseContract.MainTableContractions.COLUMN_IS_LOADED;
import static com.example.englishforkidsfinal.db.contractions.DataBaseContract.MainTableContractions.COLUMN_RU;
import static com.example.englishforkidsfinal.db.contractions.DataBaseContract.MainTableContractions.COLUMN_URL;
import static com.example.englishforkidsfinal.db.contractions.DataBaseContract.MainTableContractions.TABLE_NAME;

public class LearnedWordsDataBase extends SQLiteOpenHelper {

    // Initializing constant variables that are necessary for instantiating of object
    public static final String DATABASE_NAME = "learned_words_db";
    public static final int VERSION = 9;

    // Default SQL queries to create and to delete table
    public static final String CREATE = "CREATE TABLE " + TABLE_NAME +
            " (" + COLUMN_ID + " INTEGER PRIMARY KEY, " +
            COLUMN_ENG + " TEXT, " +
            COLUMN_RU + " TEXT, " +
            COLUMN_URL + " TEXT, " +
            COLUMN_GR + " INTEGER, " +
            COLUMN_CATEGORY + " TEXT)";
    public static final String DELETE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    // Constructor
    public LearnedWordsDataBase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE);
        onCreate(db);
    }

    // Method to get all words in Database
    public List<Word> getWords() {
        List<Word> words = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);

        if (cursor.moveToFirst()) {
            int id = cursor.getColumnIndex(COLUMN_ID);
            int eng = cursor.getColumnIndex(COLUMN_ENG);
            int ru = cursor.getColumnIndex(COLUMN_RU);
            int url = cursor.getColumnIndex(COLUMN_URL);
            int gr = cursor.getColumnIndex(COLUMN_GR);
            int category = cursor.getColumnIndex(COLUMN_CATEGORY);

            do {
                Word word_to_add = new Word(
                        cursor.getInt(id),
                        cursor.getString(eng),
                        cursor.getString(ru),
                        cursor.getString(url),
                        cursor.getInt(gr),
                        cursor.getString(category));
                words.add(word_to_add);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return words;
    }

    // Method to get all words in Database by category parameter
    public List<Word> getWords(String category) {
        List<Word> words = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,
                null,
                COLUMN_CATEGORY + " = \"" + category + "\"",
                null,
                null,
                null,
                null);

        if (cursor.moveToFirst()) {
            int id = cursor.getColumnIndex(COLUMN_ID);
            int eng = cursor.getColumnIndex(COLUMN_ENG);
            int ru = cursor.getColumnIndex(COLUMN_RU);
            int url = cursor.getColumnIndex(COLUMN_URL);
            int gr = cursor.getColumnIndex(COLUMN_GR);
            int mCategory = cursor.getColumnIndex(COLUMN_CATEGORY);

            do {
                Word word_to_add = new Word(
                        cursor.getInt(id),
                        cursor.getString(eng),
                        cursor.getString(ru),
                        cursor.getString(url),
                        cursor.getInt(gr),
                        cursor.getString(mCategory));
                words.add(word_to_add);
                Log.d("DEBUG", word_to_add.getCategory() + "");
            } while (cursor.moveToNext());
        }

        cursor.close();

        return words;
    }

    // Method to insert word into Database
    public long add(Word word) {
        // Checking if the word in Database, it won't be inserted into Database
        if (isInDB(word)) {
            return 0;
        }

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ENG, word.getEng());
        cv.put(COLUMN_RU, word.getRu());
        cv.put(COLUMN_URL, word.getUrl());
        cv.put(COLUMN_GR, word.getGr());
        cv.put(COLUMN_CATEGORY, word.getCategory());

        return db.insert(TABLE_NAME, null, cv);
    }

    public int remove(Word word) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,
                COLUMN_ID + " = ?",
                new String[]{Integer.toString(word.getId())});
    }

    // Method to find out if the word is in Database
    public boolean isInDB(Word word) {
        List<Word> words = getWords();

        for (Word el : words) {
            if (word.equals(el)) {
                return true;
            }
        }
        return false;
    }

    // Method to check if the database is empty
    public boolean isEmpty() {
        return getWords().isEmpty();
    }
}
