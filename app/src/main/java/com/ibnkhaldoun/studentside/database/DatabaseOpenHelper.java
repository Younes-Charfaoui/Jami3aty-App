package com.ibnkhaldoun.studentside.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "university.db";
    private static final int DATABASE_VERSION = 1;

    //todo add the query for creating the tables and dropping the tables
    private static final String SQL_QUERY_CREATE_NOTE = "CREATE TABLE " +
            DatabaseContract.NoteEntry.TABLE_NAME + " ( " +
            DatabaseContract.NoteEntry.COLUMN_NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DatabaseContract.NoteEntry.COLUMN_NOTE_TEXT + " TEXT NOT NULL, " +
            DatabaseContract.NoteEntry.COLUMN_NOTE_SUBJECT + " TEXT NOT NULL);";

    private static final String SQL_QUERY_DROP_NOTE = "DROP TABLE IF EXISTS " + DatabaseContract.NoteEntry.TABLE_NAME;

    private static final String SQL_QUERY_CREATE_SAVED = "";
    private static final String SQL_QUERY_DROP_SAVED = "";

    private static final String SQL_QUERY_CREATE_DISPLAY = "";
    private static final String SQL_QUERY_DROP_DISPLAY = "";

    private static final String SQL_QUERY_CREATE_NOTIFICATION = "";
    private static final String SQL_QUERY_DROP_NOTIFICATION = "";

    private static final String SQL_QUERY_CREATE_MAIL = "";
    private static final String SQL_QUERY_DROP_MAIL = "";

    DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        //todo code to create the tables
        //database.execSQL(SQL_QUERY_CREATE_DISPLAY);
        database.execSQL(SQL_QUERY_CREATE_NOTE);
        //database.execSQL(SQL_QUERY_CREATE_NOTIFICATION);
        //database.execSQL(SQL_QUERY_CREATE_SAVED);
        //database.execSQL(SQL_QUERY_CREATE_MAIL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        //todo code to drop the table
        //database.execSQL(SQL_QUERY_DROP_DISPLAY);
        database.execSQL(SQL_QUERY_DROP_NOTE);
//        database.execSQL(SQL_QUERY_DROP_NOTIFICATION);
//        database.execSQL(SQL_QUERY_DROP_SAVED);
//        database.execSQL(SQL_QUERY_DROP_MAIL);
        onCreate(database);
    }
}
