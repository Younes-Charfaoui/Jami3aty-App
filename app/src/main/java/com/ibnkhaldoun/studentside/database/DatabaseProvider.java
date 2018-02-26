package com.ibnkhaldoun.studentside.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

    static {
        sUriMatcher.addURI(DatabaseContract.AUTHORITY, DatabaseContract.PATH_NOTES, NOTES);
        sUriMatcher.addURI(DatabaseContract.AUTHORITY, DatabaseContract.PATH_NOTES + "/#", NOTES_ID);
    }

    private DatabaseOpenHelper mDatabase;

    @Override
    public boolean onCreate() {
        mDatabase = new DatabaseOpenHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase database = mDatabase.getReadableDatabase();
        Cursor cursor;

        int match = sUriMatcher.match(uri);

        switch (match) {
            case NOTES:
                cursor = database.query(DatabaseContract.NoteEntry.TABLE_NAME,
                        projection, selection, selectionArgs, null, null, sortOrder
                );
                break;
            case NOTES_ID:
                selection = DatabaseContract.NoteEntry.COLUMN_NOTE_ID + "= ?";
                selectionArgs = new String[]{String.valueOf(uri.getLastPathSegment())};
                cursor = database.query(DatabaseContract.NoteEntry.TABLE_NAME,
                        projection, selection, selectionArgs, null, null, sortOrder
                );
                break;
            default:
                throw new IllegalArgumentException();
        }
        assert getContext() != null;
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        int match = sUriMatcher.match(uri);
        switch (match) {
            case NOTES:
                return insertNote(uri, values);
            default:
                throw new IllegalArgumentException();
        }
    }

    private Uri insertNote(Uri uri, ContentValues values) {
        SQLiteDatabase database = mDatabase.getWritableDatabase();
        long id = database.insert(DatabaseContract.NoteEntry.TABLE_NAME, null, values);
        if (id != -1) {
            assert getContext() != null;
            getContext().getContentResolver().notifyChange(uri, null);
            return ContentUris.withAppendedId(uri, id);
        }
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase database = mDatabase.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        switch (match) {
            case NOTES:
                return database.delete(DatabaseContract.NoteEntry.TABLE_NAME, selection, selectionArgs);

            case NOTES_ID:
                selection = DatabaseContract.NoteEntry.COLUMN_NOTE_ID + " = ?";
                selectionArgs = new String[]{String.valueOf(uri.getLastPathSegment())};
                int rowDeleted = database.delete(DatabaseContract.NoteEntry.TABLE_NAME, selection, selectionArgs);
                assert getContext() != null;
                if (rowDeleted != 0) getContext().getContentResolver().notifyChange(uri, null);
                return rowDeleted;
        }
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase database = mDatabase.getWritableDatabase();
        int match = sUriMatcher.match(uri);

        switch (match) {
            case NOTES:
                return updateNote(uri, values, selection, selectionArgs, database);
            case NOTES_ID:
                selection = DatabaseContract.NoteEntry.COLUMN_NOTE_ID + "= ?";
                selectionArgs = new String[]{String.valueOf(uri.getLastPathSegment())};
                return updateNote(uri, values, selection, selectionArgs, database);
            default:
                throw new IllegalArgumentException();
        }

    }

    private int updateNote(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs, SQLiteDatabase database) {
        int rowUpdated = database.update(DatabaseContract.NoteEntry.TABLE_NAME, values, selection, selectionArgs);
        assert getContext() != null;
        if (rowUpdated != 0) getContext().getContentResolver().notifyChange(uri, null);
        return rowUpdated;
    }
}
