package com.example.englishforkidsfinal.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.englishforkidsfinal.models.Word;

import java.util.ArrayList;
import java.util.List;

public class DataBase extends SQLiteOpenHelper {

    // Initializing constant variables that are necessary for instantiating of object
    public static final String DATABASE_NAME = "english_for_kids";
    public static final int VERSION = 2;

    // Default SQL queries to create and to delete table
    public static final String CREATE = "CREATE TABLE " + DataBaseContract.LearnedWords.TABLE_NAME +
            " (" + DataBaseContract.LearnedWords.COLUMN_ID + " INTEGER PRIMARY KEY, " +
            DataBaseContract.LearnedWords.COLUMN_WORD + " TEXT, " +
            DataBaseContract.LearnedWords.COLUMN_TRANSLATION + " TEXT, " +
            DataBaseContract.LearnedWords.PICTURE_ID + " INTEGER, " +
            DataBaseContract.LearnedWords.GROUP_ID + " INTEGER)";

    private static final String DELETE = "DROP TABLE IF EXISTS " + DataBaseContract.LearnedWords.TABLE_NAME;

    // Constructor
    public DataBase(@Nullable Context context) {
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
        Cursor cursor = db.query(DataBaseContract.LearnedWords.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);

        if (cursor.moveToFirst()) {

            int id = cursor.getColumnIndex(DataBaseContract.LearnedWords.COLUMN_ID);
            int word = cursor.getColumnIndex(DataBaseContract.LearnedWords.COLUMN_WORD);
            int translation = cursor.getColumnIndex(DataBaseContract.LearnedWords.COLUMN_TRANSLATION);
            int picture = cursor.getColumnIndex(DataBaseContract.LearnedWords.PICTURE_ID);
            int gr = cursor.getColumnIndex(DataBaseContract.LearnedWords.GROUP_ID);

            do {
                Word word_to_add = new Word(
                        cursor.getInt(id),
                        cursor.getString(word),
                        cursor.getString(translation),
                        cursor.getInt(picture),
                        cursor.getInt(gr));
                words.add(word_to_add);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return words;
    }

    // Method to insert word into Database
    public long add(Word word) {
        List<Word> words = getWords();

        // Checking if the word in Database, it won't be inserted into Database
        if (isInDB(word)) {
            return 0;
        }

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DataBaseContract.LearnedWords.COLUMN_WORD, word.getAnimal());
        cv.put(DataBaseContract.LearnedWords.COLUMN_TRANSLATION, word.getTranslation());
        cv.put(DataBaseContract.LearnedWords.PICTURE_ID, word.getRes());
        cv.put(DataBaseContract.LearnedWords.GROUP_ID, word.getGr());

        word.setId((int) db.insert(DataBaseContract.LearnedWords.TABLE_NAME, null, cv));

        return word.getId();
    }

    public int remove(Word word) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(DataBaseContract.LearnedWords.TABLE_NAME,
                DataBaseContract.LearnedWords.COLUMN_ID + " = ?",
                new String[] {Integer.toString(word.getId())});
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
}
