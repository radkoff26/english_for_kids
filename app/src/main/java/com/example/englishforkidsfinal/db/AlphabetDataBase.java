package com.example.englishforkidsfinal.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.englishforkidsfinal.models.RestAlphabetLetter;

import java.util.ArrayList;
import java.util.List;

import static com.example.englishforkidsfinal.db.contractions.DataBaseContractions.MainTableContractions.*;

public class AlphabetDataBase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "alphabet_db";
    private static final Integer VERSION = 1;

    // Default SQL queries to create and to delete table
    private static final String CREATE = "CREATE TABLE " + ALPHABET_TABLE_NAME +
            " (" + COLUMN_ID + " INTEGER PRIMARY KEY, " +
            COLUMN_ALPHABET_LETTER + " TEXT, " +
            COLUMN_ALPHABET_LETTER_PICTURE_URI + " TEXT, " +
            COLUMN_ALPHABET_LETTER_LOADED + " INTEGER, " +
            COLUMN_ALPHABET_LETTER_URI + " TEXT)";
    private static final String DELETE = "DROP TABLE IF EXISTS " + ALPHABET_TABLE_NAME;

    public AlphabetDataBase(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            db.execSQL(DELETE);
            onCreate(db);
        }
    }

    public List<RestAlphabetLetter> getAlphabet() {
        List<RestAlphabetLetter> letters = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(ALPHABET_TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);

        if (cursor.moveToFirst()) {
            int id = cursor.getColumnIndex(COLUMN_ID);
            int mLetter = cursor.getColumnIndex(COLUMN_ALPHABET_LETTER);
            int isLoaded = cursor.getColumnIndex(COLUMN_ALPHABET_LETTER_LOADED);
            int uri = cursor.getColumnIndex(COLUMN_ALPHABET_LETTER_URI);
            int picture_uri = cursor.getColumnIndex(COLUMN_ALPHABET_LETTER_PICTURE_URI);

            do {
                RestAlphabetLetter letter = new RestAlphabetLetter(
                        cursor.getInt(id),
                        cursor.getString(mLetter),
                        cursor.getString(uri),
                        cursor.getString(picture_uri),
                        cursor.getInt(isLoaded) == 1);
                letters.add(letter);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return letters;
    }

    public long addLetter(RestAlphabetLetter letter) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ALPHABET_LETTER, letter.getLetter());
        cv.put(COLUMN_ALPHABET_LETTER_LOADED, letter.isLoaded());
        cv.put(COLUMN_ALPHABET_LETTER_URI, letter.getUri());
        cv.put(COLUMN_ALPHABET_LETTER_PICTURE_URI, letter.getPicture_uri());

        if (isInDB(letter)) {
            return db.update(ALPHABET_TABLE_NAME, cv, "id = " + letter.getId(), null);
        } else {
            return db.insert(ALPHABET_TABLE_NAME, null, cv);
        }
    }

    public boolean isInDB(RestAlphabetLetter letter) {
        return getAlphabet().contains(letter);
    }
}
