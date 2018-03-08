package com.ibnkhaldoun.studentside.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "university.db";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_QUERY_CREATE_NOTE = "CREATE TABLE " +
            DatabaseContract.NoteEntry.TABLE_NAME + " ( " +
            DatabaseContract.NoteEntry.COLUMN_NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DatabaseContract.NoteEntry.COLUMN_NOTE_TEXT + " TEXT NOT NULL, " +
            DatabaseContract.NoteEntry.COLUMN_NOTE_SUBJECT + " TEXT NOT NULL);";

    private static final String SQL_QUERY_DROP_NOTE = "DROP TABLE IF EXISTS " + DatabaseContract.NoteEntry.TABLE_NAME;

    private static final String SQL_QUERY_CREATE_SAVED = "CREATE TABLE " +
            DatabaseContract.SavedEntry.TABLE_NAME + " ( " +
            DatabaseContract.SavedEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DatabaseContract.SavedEntry.COLUMN_NAME + " TEXT NOT NULL, " +
            DatabaseContract.SavedEntry.COLUMN_DATE + " TEXT NOT NULL, " +
            DatabaseContract.SavedEntry.COLUMN_DISPLAY_TEXT + " TEXT NOT NULL);";

    private static final String SQL_QUERY_DROP_SAVED = "DROP TABLE IF EXISTS " + DatabaseContract.SavedEntry.TABLE_NAME;

    private static final String SQL_QUERY_CREATE_NOTE_DISPLAY = "CREATE TABLE " +
            DatabaseContract.NoteOfDisplaysEntry.TABLE_NAME + " ( " +
            DatabaseContract.NoteOfDisplaysEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DatabaseContract.NoteOfDisplaysEntry.COLUMN_NAME + " TEXT NOT NULL, " +
            DatabaseContract.NoteOfDisplaysEntry.COLUMN_TEXT + " TEXT NOT NULL, " +
            DatabaseContract.NoteOfDisplaysEntry.COLUMN_ID_DISPLAY + " INTEGER NOT NULL , " +
            "FOREIGN KEY (" + DatabaseContract.NoteOfDisplaysEntry.COLUMN_ID_DISPLAY + ") " +
            "REFERENCES " + DatabaseContract.SavedEntry.TABLE_NAME +
            "(" + DatabaseContract.SavedEntry.COLUMN_ID + "));";

    private static final String SQL_QUERY_DROP_NOTE_DISPLAY = "DROP TABLE IF EXISTS " + DatabaseContract.NoteOfDisplaysEntry.TABLE_NAME;

    private static final String SQL_QUERY_CREATE_DISPLAY = "";
    private static final String SQL_QUERY_DROP_DISPLAY = "";

    private static final String SQL_QUERY_CREATE_SUBJECT = "CREATE TABLE " +
            DatabaseContract.SubjectEntry.TABLE_NAME + " ( " +
            DatabaseContract.SubjectEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DatabaseContract.SubjectEntry.COLUMN_TITLE + " TEXT NOT NULL , " +
            DatabaseContract.SubjectEntry.COLUMN_SHORT_TITLE + " TEXT NOT NULL , " +
            DatabaseContract.SubjectEntry.COLUMN_COEFFICIENT + " TEXT NOT NULL , " +
            DatabaseContract.SubjectEntry.COLUMN_SUMMARY + " TEXT NOT NULL , " +
            DatabaseContract.SubjectEntry.COLUMN_TABLE_CONTENT + " TEXT NOT NULL , " +
            DatabaseContract.SubjectEntry.COLUMN_CREDIT + " TEXT NOT NULL , " +
            DatabaseContract.SubjectEntry.COLUMN_LEVEL + " INTEGER NOT NULL , " +
            DatabaseContract.SubjectEntry.COLUMN_UNITY_TYPE + " INTEGER NOT NULL ); ";


    private static final String SQL_QUERY_DROP_SUBJECT = "DROP TABLE IF EXISTS " + DatabaseContract.SubjectEntry.TABLE_NAME;

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
        database.execSQL(SQL_QUERY_CREATE_NOTE_DISPLAY);
        database.execSQL(SQL_QUERY_CREATE_NOTE);
        database.execSQL(SQL_QUERY_CREATE_SUBJECT);
        //database.execSQL(SQL_QUERY_CREATE_NOTIFICATION);
        database.execSQL(SQL_QUERY_CREATE_SAVED);
        //database.execSQL(SQL_QUERY_CREATE_MAIL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        //todo code to drop the table
        //database.execSQL(SQL_QUERY_DROP_DISPLAY);
        database.execSQL(SQL_QUERY_DROP_NOTE_DISPLAY);
        database.execSQL(SQL_QUERY_DROP_NOTE);
//        database.execSQL(SQL_QUERY_DROP_NOTIFICATION);
        database.execSQL(SQL_QUERY_DROP_SAVED);
        database.execSQL(SQL_QUERY_DROP_SUBJECT);
//        database.execSQL(SQL_QUERY_DROP_MAIL);
        onCreate(database);
    }
}
