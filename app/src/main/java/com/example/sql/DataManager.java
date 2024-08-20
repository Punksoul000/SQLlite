package com.example.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DataManager {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public DataManager(Context context) {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public long insertRecord(String name, int age) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NAME, name);
        values.put(DatabaseHelper.COLUMN_AGE, age);

        return database.insert(DatabaseHelper.TABLE_NAME, null, values);
    }

    public void updateRecord(long id, String name, int age) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NAME, name);
        values.put(DatabaseHelper.COLUMN_AGE, age);

        String whereClause = DatabaseHelper.COLUMN_ID + " = ?";
        String[] whereArgs = { String.valueOf(id) };

        database.update(DatabaseHelper.TABLE_NAME, values, whereClause, whereArgs);
    }

    public Cursor getAllRecords() {
        String[] columns = { DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_AGE };
        return database.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null);
    }

    public void deleteRecord(long id) {
        String whereClause = DatabaseHelper.COLUMN_ID + " = ?";
        String[] whereArgs = { String.valueOf(id) };

        database.delete(DatabaseHelper.TABLE_NAME, whereClause, whereArgs);
    }

    public void close() {
        if (database != null && database.isOpen()) {
            database.close();
        }
        if (dbHelper != null) {
            dbHelper.close();
        }
    }
}