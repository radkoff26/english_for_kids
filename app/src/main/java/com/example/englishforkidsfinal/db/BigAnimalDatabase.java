package com.example.englishforkidsfinal.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.englishforkidsfinal.models.db_models.BigAnimal;

import java.util.ArrayList;
import java.util.List;

import static com.example.englishforkidsfinal.db.contractions.DataBaseContractions.MainTableContractions.*;

public class BigAnimalDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "big_animals";
    private static final Integer VERSION = 2;

    // Default SQL queries to create and to delete table
    private static final String CREATE = "CREATE TABLE " + BIG_ANIMAL_TABLE_NAME +
            " (" + COLUMN_ID + " INTEGER PRIMARY KEY, " +
            COLUMN_BIG_ANIMAL_URI + " TEXT," +
            COLUMN_BIG_ANIMAL_URI_BG + " TEXT," +
            COLUMN_BIG_ANIMAL_IS_LOADED + " INTEGER," +
            " " + COLUMN_BIG_ANIMAL_WORD + " TEXT)";
    private static final String DELETE = "DROP TABLE IF EXISTS " + BIG_ANIMAL_TABLE_NAME;

    public BigAnimalDatabase(@Nullable Context context) {
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

    public List<BigAnimal> getBigAnimals() {
        List<BigAnimal> bigAnimals = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(BIG_ANIMAL_TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);

        if (cursor.moveToFirst()) {
            int id = cursor.getColumnIndex(COLUMN_ID);
            int uri = cursor.getColumnIndex(COLUMN_BIG_ANIMAL_URI);
            int uri_bg = cursor.getColumnIndex(COLUMN_BIG_ANIMAL_URI_BG);
            int word = cursor.getColumnIndex(COLUMN_BIG_ANIMAL_WORD);
            int isLoaded = cursor.getColumnIndex(COLUMN_BIG_ANIMAL_IS_LOADED);

            do {
                BigAnimal bigAnimal = new BigAnimal(
                        cursor.getInt(id),
                        cursor.getString(uri),
                        cursor.getString(uri_bg),
                        cursor.getString(word),
                        cursor.getInt(isLoaded) == 1
                );
                bigAnimals.add(bigAnimal);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return bigAnimals;
    }

    public long addBigAnimal(BigAnimal bigAnimal) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_BIG_ANIMAL_URI, bigAnimal.getUri());
        cv.put(COLUMN_BIG_ANIMAL_URI_BG, bigAnimal.getUri_bg());
        cv.put(COLUMN_BIG_ANIMAL_WORD, bigAnimal.getWord());
        if (bigAnimal.getLoaded() != null) {
            cv.put(COLUMN_BIG_ANIMAL_IS_LOADED, bigAnimal.getLoaded() ? 1 : 0);
        } else {
            cv.put(COLUMN_BIG_ANIMAL_IS_LOADED, 0);
        }

        if (isInDB(bigAnimal)) {
            return db.update(BIG_ANIMAL_TABLE_NAME, cv, "id = ?", new String[] {bigAnimal.getId().toString()});
        } else {
            return db.insert(BIG_ANIMAL_TABLE_NAME, null, cv);
        }
    }

    // Method to find out if the word is in Database
    public boolean isInDB(BigAnimal bigAnimal) {
        List<BigAnimal> bigAnimals = getBigAnimals();

        for (BigAnimal el : bigAnimals) {
            if (bigAnimal.equals(el)) {
                return true;
            }
        }
        return false;
    }

    // Method to check if the database is empty
    public boolean isEmpty() {
        return getBigAnimals().isEmpty();
    }
}