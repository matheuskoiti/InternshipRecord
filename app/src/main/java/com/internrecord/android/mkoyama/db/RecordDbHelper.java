package com.internrecord.android.mkoyama.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mkoyama on 23/02/17.
 */

public class RecordDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "records.db";
    public static final int DATABASE_VERSION = 1;

    public RecordDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_RECORD = "CREATE TABLE " +
                RecordContract.RecordEntry.TABLE_NAME + "(" +
                RecordContract.RecordEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                RecordContract.RecordEntry.COLUMN_SUMMARY + " TEXT NOT NULL, " +
                RecordContract.RecordEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
                RecordContract.RecordEntry.COLUMN_WEEK + " TEXT NOT NULL" + ");";
        db.execSQL(SQL_CREATE_RECORD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + RecordContract.RecordEntry.TABLE_NAME);
        onCreate(db);
    }
}
