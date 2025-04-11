package com.example.bellpepper_classify;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class DBHelper extends SQLiteOpenHelper {
    // Database name and version
    private static final String DATABASE_NAME = "newrecommendation.db";
    private static final int DATABASE_VERSION = 1;

    // Table name and columns
    public static final String TABLE_NAME = "newrecommendation_table";
    public static final String COLUMN_ID = "rec_Id";
    public static final String COLUMN_NAME = "recomValue";
    public static final String COLUMN_VALUE = "someText";


    // SQL query to create the table
    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_VALUE + " TEXT)";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the table when the database is first created
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrades (if needed)
        // This method is called when DATABASE_VERSION is increased

    }
}

