package com.example.englishforkidsfinal.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.englishforkidsfinal.models.db_models.Word;

import java.util.ArrayList;
import java.util.List;

import static com.example.englishforkidsfinal.db.contractions.DataBaseContractions.MainTableContractions.*;

public class AllWordsDataBase extends SQLiteOpenHelper {

    // Initializing constant variables that are necessary for instantiating of object
    private static final String DATABASE_NAME = "all_words_db";
    private static final int VERSION = 9;

    // Default SQL queries to create and to delete table
    private static final String CREATE = "CREATE TABLE " + All_WORDS_TABLE_NAME +
            " (" + COLUMN_ID + " INTEGER PRIMARY KEY, " +
            COLUMN_ENG+ " TEXT, " +
            COLUMN_RU + " TEXT, " +
            COLUMN_URL + " TEXT, " +
            COLUMN_GR + " INTEGER, " +
            COLUMN_IS_LOADED + " INTEGER," +
            COLUMN_CATEGORY_ID + " TEXT)";
    private static final String DELETE = "DROP TABLE IF EXISTS " + All_WORDS_TABLE_NAME;

    // Constructor
    public AllWordsDataBase(@Nullable Context context) {
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
    public List<Word> getWords(@Nullable Integer gr) {
        List<Word> words = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(All_WORDS_TABLE_NAME,
                null,
                gr != null ? "gr = " + gr : null,
                null,
                null,
                null,
                null);

        if (cursor.moveToFirst()) {
            int id = cursor.getColumnIndex(COLUMN_ID);
            int eng = cursor.getColumnIndex(COLUMN_ENG);
            int ru = cursor.getColumnIndex(COLUMN_RU);
            int url = cursor.getColumnIndex(COLUMN_URL);
            int group = cursor.getColumnIndex(COLUMN_GR);
            int isLoaded = cursor.getColumnIndex(COLUMN_IS_LOADED);
            int category = cursor.getColumnIndex(COLUMN_CATEGORY_ID);

            do {
                Word word_to_add = new Word(
                        cursor.getInt(id),
                        cursor.getString(eng),
                        cursor.getString(ru),
                        cursor.getString(url),
                        cursor.getInt(group),
                        cursor.getInt(isLoaded) == 1,
                        cursor.getInt(category));
                words.add(word_to_add);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return words;
    }

    // Method to insert word into Database
    public long add(Word word) {
        // Checking if the word in Database, it won't be inserted into Database

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ENG, word.getEng());
        cv.put(COLUMN_RU, word.getRu());
        cv.put(COLUMN_URL, word.getUrl());
        cv.put(COLUMN_GR, word.getGr());
        cv.put(COLUMN_IS_LOADED, word.isLoaded() ? 1 : 0);
        cv.put(COLUMN_CATEGORY_ID, word.getCategory_id());

        Log.d("DEBUG", (word.isLoaded() ? 1 : 0) + "");

        if (isInDB(word)) {
            Log.d("DEBUG", "UPDATE");
            return db.update(All_WORDS_TABLE_NAME, cv, "id = ?", new String[] {word.getId().toString()});
        } else {
            return db.insert(All_WORDS_TABLE_NAME, null, cv);
        }
    }

    // Method to find out if the word is in Database
    public boolean isInDB(Word word) {
        List<Word> words = getWords(null);

        for (Word el : words) {
            if (word.equals(el)) {
                return true;
            }
        }
        return false;
    }
}
