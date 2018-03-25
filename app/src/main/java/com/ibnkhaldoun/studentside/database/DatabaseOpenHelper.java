package com.ibnkhaldoun.studentside.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "university.db";
    private static final int DATABASE_VERSION = 1;

    private static final String BASE_DROP = "DROP TABLE IF EXISTS ";
    private static final String BASE_CREATE = "CREATE TABLE ";

    private static final String SQL_QUERY_CREATE_NOTE = BASE_CREATE +
            DatabaseContract.NoteEntry.TABLE_NAME + " ( " +
            DatabaseContract.NoteEntry.COLUMN_NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DatabaseContract.NoteEntry.COLUMN_NOTE_TEXT + " TEXT NOT NULL, " +
            DatabaseContract.NoteEntry.COLUMN_NOTE_SUBJECT + " TEXT NOT NULL);";

    private static final String SQL_QUERY_DROP_NOTE = BASE_DROP + DatabaseContract.NoteEntry.TABLE_NAME;

    private static final String SQL_QUERY_CREATE_SAVED = BASE_CREATE +
            DatabaseContract.SavedEntry.TABLE_NAME + " ( " +
            DatabaseContract.SavedEntry.COLUMN_ID + " INTEGER PRIMARY KEY , " +
            DatabaseContract.SavedEntry.COLUMN_NAME + " TEXT NOT NULL, " +
            DatabaseContract.SavedEntry.COLUMN_DATE + " TEXT NOT NULL, " +
            DatabaseContract.SavedEntry.COLUMN_DISPLAY_TEXT + " TEXT NOT NULL);";

    private static final String SQL_QUERY_DROP_SAVED = BASE_DROP + DatabaseContract.SavedEntry.TABLE_NAME;

    private static final String SQL_QUERY_CREATE_NOTE_DISPLAY = BASE_CREATE +
            DatabaseContract.NoteOfDisplaysEntry.TABLE_NAME + " ( " +
            DatabaseContract.NoteOfDisplaysEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DatabaseContract.NoteOfDisplaysEntry.COLUMN_NAME + " TEXT NOT NULL, " +
            DatabaseContract.NoteOfDisplaysEntry.COLUMN_TEXT + " TEXT NOT NULL, " +
            DatabaseContract.NoteOfDisplaysEntry.COLUMN_ID_DISPLAY + " INTEGER NOT NULL , " +
            "FOREIGN KEY (" + DatabaseContract.NoteOfDisplaysEntry.COLUMN_ID_DISPLAY + ") " +
            "REFERENCES " + DatabaseContract.SavedEntry.TABLE_NAME +
            "(" + DatabaseContract.SavedEntry.COLUMN_ID + "));";

    private static final String SQL_QUERY_DROP_NOTE_DISPLAY = BASE_DROP + DatabaseContract.NoteOfDisplaysEntry.TABLE_NAME;

    private static final String SQL_QUERY_CREATE_DISPLAY = "";
    private static final String SQL_QUERY_DROP_DISPLAY = "";

    private static final String SQL_QUERY_CREATE_SUBJECT = BASE_CREATE +
            DatabaseContract.SubjectEntry.TABLE_NAME + " ( " +
            DatabaseContract.SubjectEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
            DatabaseContract.SubjectEntry.COLUMN_TITLE + " TEXT NOT NULL , " +
            DatabaseContract.SubjectEntry.COLUMN_SHORT_TITLE + " TEXT NOT NULL , " +
            DatabaseContract.SubjectEntry.COLUMN_COEFFICIENT + " TEXT NOT NULL , " +
            DatabaseContract.SubjectEntry.COLUMN_SUMMARY + " TEXT NOT NULL , " +
            DatabaseContract.SubjectEntry.COLUMN_TABLE_CONTENT + " TEXT NOT NULL , " +
            DatabaseContract.SubjectEntry.COLUMN_CREDIT + " TEXT NOT NULL , " +
            DatabaseContract.SubjectEntry.COLUMN_LEVEL + " INTEGER NOT NULL , " +
            DatabaseContract.SubjectEntry.COLUMN_COURSE_PROFESSOR + " TEXT , " +
            DatabaseContract.SubjectEntry.COLUMN_TD_PROFESSOR + " TEXT , " +
            DatabaseContract.SubjectEntry.COLUMN_TP_PROFESSOR + " TEXT , " +
            DatabaseContract.SubjectEntry.COLUMN_UNITY_TYPE + " INTEGER NOT NULL ); ";


    private static final String SQL_QUERY_DROP_SUBJECT = BASE_DROP +
            DatabaseContract.SubjectEntry.TABLE_NAME;

    private static final String SQL_QUERY_CREATE_NOTIFICATION = "";
    private static final String SQL_QUERY_DROP_NOTIFICATION = "";

    private static final String SQL_QUERY_CREATE_SCHEDULE = BASE_CREATE +
            DatabaseContract.ScheduleEntry.TABLE_NAME + " ( " +
            DatabaseContract.ScheduleEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            DatabaseContract.ScheduleEntry.COLUMN_LEVEL + " INTEGER NOT NULL , " +
            DatabaseContract.ScheduleEntry.COLUMN_GROUP + " INTEGER NOT NULL , " +
            DatabaseContract.ScheduleEntry.COLUMN_SECTION + " INTEGER NOT NULL , " +
            DatabaseContract.ScheduleEntry.COLUMN_DAY + " INTEGER NOT NULL , " +
            DatabaseContract.ScheduleEntry.COLUMN_HOUR + " INTEGER NOT NULL , " +
            DatabaseContract.ScheduleEntry.COLUMN_PLACE + " TEXT NOT NULL , " +
            DatabaseContract.ScheduleEntry.COLUMN_PROFESSOR + " TEXT NOT NULL , " +
            DatabaseContract.ScheduleEntry.COLUMN_SUBJECT + " TEXT NOT NULL ); ";

    private static final String SQL_QUERY_DROP_SCHEDULE = BASE_DROP +
            DatabaseContract.ScheduleEntry.TABLE_NAME;

    private static final String SQL_QUERY_CREATE_MAIL = BASE_CREATE +
            DatabaseContract.MailEntry.TABLE_NAME + " ( " +
            DatabaseContract.MailEntry.COLUMN_ID + " INTEGER PRIMARY KEY, " +
            DatabaseContract.MailEntry.COLUMN_PROFESSOR_NAME + " TEXT NOT NULL, " +
            DatabaseContract.MailEntry.COLUMN_PROFESSOR_ID + " INTEGER NOT NULL, " +
            DatabaseContract.MailEntry.COLUMN_DATE + " TEXT NOT NULL, " +
            DatabaseContract.MailEntry.COLUMN_SENDER + " BOOLEAN NOT NULL, " +
            DatabaseContract.MailEntry.COLUMN_MESSAGE_SUBJECT + " TEXT NOT NULL, " +
            DatabaseContract.MailEntry.COLUMN_MESSAGE + " TEXT NOT NULL );";

    private static final String SQL_QUERY_DROP_MAIL = BASE_DROP + DatabaseContract.MailEntry.TABLE_NAME;

    private static final String SQL_QUERY_CREATE_MARK = BASE_CREATE +
            DatabaseContract.MarkEntry.TABLE_NAME + " ( " +
            DatabaseContract.MarkEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DatabaseContract.MarkEntry.COLUMN_SUBJECT_ID + " INTEGER NOT NULL , " +
            DatabaseContract.MarkEntry.COLUMN_TITLE_SUBJECT + " TEXT NOT NULL , " +
            DatabaseContract.MarkEntry.COLUMN_SHORT_TITLE + " TEXT NOT NULL , " +
            DatabaseContract.MarkEntry.COLUMN_TD_MARK + " TEXT, " +
            DatabaseContract.MarkEntry.COLUMN_TP_MARK + " TEXT, " +
            DatabaseContract.MarkEntry.COLUMN_EXAM_MARK + " TEXT );";

    private static final String SQL_QUERY_DROP_MARK = BASE_DROP +
            DatabaseContract.MarkEntry.TABLE_NAME;

    DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {

        //database.execSQL(SQL_QUERY_CREATE_DISPLAY);
        database.execSQL(SQL_QUERY_CREATE_NOTE_DISPLAY);
        database.execSQL(SQL_QUERY_CREATE_NOTE);
        database.execSQL(SQL_QUERY_CREATE_SUBJECT);
        database.execSQL(SQL_QUERY_CREATE_SCHEDULE);
        database.execSQL(SQL_QUERY_CREATE_SAVED);
        database.execSQL(SQL_QUERY_CREATE_MARK);
        database.execSQL(SQL_QUERY_CREATE_MAIL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        //database.execSQL(SQL_QUERY_DROP_DISPLAY);
        database.execSQL(SQL_QUERY_DROP_NOTE_DISPLAY);
        database.execSQL(SQL_QUERY_DROP_NOTE);
       database.execSQL(SQL_QUERY_DROP_SCHEDULE);
        database.execSQL(SQL_QUERY_DROP_SAVED);
        database.execSQL(SQL_QUERY_DROP_SUBJECT);
        database.execSQL(SQL_QUERY_DROP_MAIL);
        database.execSQL(SQL_QUERY_DROP_MARK);
        onCreate(database);
    }
}
