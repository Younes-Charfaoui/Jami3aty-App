package com.ibnkhaldoun.studentside.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


public class DatabaseProvider extends ContentProvider {

    private final static int DISPLAY = 100;
    private final static int NOTES = 200;
    private final static int MARKS = 300;
    private final static int SAVED = 400;
    private final static int NOTIFICATION = 500;
    private final static int MAIL = 600;

    private final static int DISPLAY_ID = 101;
    private final static int NOTES_ID = 201;
    private final static int MARKS_ID = 301;
    private final static int SAVED_ID = 401;
    private final static int NOTIFICATION_ID = 501;
    private final static int MAIL_ID = 601;

    private final static UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private DatabaseOpenHelper mDatabase;

    @Override
    public boolean onCreate() {
        mDatabase = new DatabaseOpenHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
