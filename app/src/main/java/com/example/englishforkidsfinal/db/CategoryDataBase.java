package com.example.englishforkidsfinal.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.englishforkidsfinal.models.db_models.Category;

import java.util.ArrayList;
import java.util.List;

import static com.example.englishforkidsfinal.db.contractions.DataBaseContractions.MainTableContractions.*;

public class CategoryDataBase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "categories";
    private static final Integer VERSION = 1;

    // Default SQL queries to create and to delete table
    private static final String CREATE = "CREATE TABLE " + CATEGORY_TABLE_NAME +
            " (" + COLUMN_ID + " INTEGER PRIMARY KEY, " +
            COLUMN_CATEGORY_TITLE + " TEXT)";
    private static final String DELETE = "DROP TABLE IF EXISTS " + CATEGORY_TABLE_NAME;

    public CategoryDataBase(@Nullable Context context) {
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

    public List<Category> getCategories() {
        List<Category> categories = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(CATEGORY_TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);

        if (cursor.moveToFirst()) {
            int id = cursor.getColumnIndex(COLUMN_ID);
            int title = cursor.getColumnIndex(COLUMN_CATEGORY_TITLE);

            do {
                Category category = new Category(
                        cursor.getInt(id),
                        cursor.getString(title)
                );
                categories.add(category);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return categories;
    }

    public long addCategory(Category category) {
        // Checking if the word in Database, it won't be inserted into Database
        if (isInDB(category)) {
            return 0;
        }

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_CATEGORY_TITLE, category.getTitle());

        return db.insert(CATEGORY_TABLE_NAME, null, cv);
    }

    // Method to find out if the word is in Database
    public boolean isInDB(Category category) {
        List<Category> categories = getCategories();

        for (Category el : categories) {
            if (category.equals(el)) {
                return true;
            }
        }
        return false;
    }

    // Method to check if the database is empty
    public boolean isEmpty() {
        return getCategories().isEmpty();
    }
}
