package com.example.englishforkidsfinal.db.db_sql_models;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.englishforkidsfinal.db.LearnedWordsDataBase;
import com.example.englishforkidsfinal.models.db_models.CollectWordModel;

import java.util.ArrayList;
import java.util.List;

import static com.example.englishforkidsfinal.db.contractions.DataBaseContract.MainTableContractions.COLUMN_ENG;
import static com.example.englishforkidsfinal.db.contractions.DataBaseContract.MainTableContractions.COLUMN_ID;
import static com.example.englishforkidsfinal.db.contractions.DataBaseContract.MainTableContractions.COLUMN_URL;
import static com.example.englishforkidsfinal.db.contractions.DataBaseContract.MainTableContractions.TABLE_NAME;

public class CollectWord {

    private LearnedWordsDataBase thisDB;

    public CollectWord(Context ctx) {
        thisDB = new LearnedWordsDataBase(ctx);
    }

    // Method to get all words in Database
    public List<CollectWordModel> getWords() {
        List<CollectWordModel> words = new ArrayList<>();

        SQLiteDatabase db = thisDB.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);

        if (cursor.moveToFirst()) {
            int id = cursor.getColumnIndex(COLUMN_ID);
            int word = cursor.getColumnIndex(COLUMN_ENG);
            int url = cursor.getColumnIndex(COLUMN_URL);

            do {
                CollectWordModel word_to_add = new CollectWordModel(
                        cursor.getInt(id),
                        cursor.getString(word),
                        cursor.getString(url));
                words.add(word_to_add);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return words;
    }
}