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
    private final static int SUBJECT = 700;
    private final static int SCHEDULE = 800;

    private final static int DISPLAY_ID = 101;
    private final static int NOTES_ID = 201;
    private final static int MARKS_ID = 301;
    private final static int SAVED_ID = 401;
    private final static int NOTIFICATION_ID = 501;
    private final static int MAIL_ID = 601;
    private final static int SUBJECT_ID = 701;
    private final static int SCHEDULE_LEVEL_GROUP_SECTION = 801;

    private final static UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(DatabaseContract.AUTHORITY, DatabaseContract.PATH_NOTES, NOTES);
        sUriMatcher.addURI(DatabaseContract.AUTHORITY, DatabaseContract.PATH_NOTES + "/#", NOTES_ID);

        sUriMatcher.addURI(DatabaseContract.AUTHORITY, DatabaseContract.PATH_SAVED, SAVED);
        sUriMatcher.addURI(DatabaseContract.AUTHORITY, DatabaseContract.PATH_SAVED + "/#", SAVED_ID);

        sUriMatcher.addURI(DatabaseContract.AUTHORITY, DatabaseContract.PATH_SUBJECT, SUBJECT);
        sUriMatcher.addURI(DatabaseContract.AUTHORITY, DatabaseContract.PATH_SUBJECT + "/#", SUBJECT_ID);

        sUriMatcher.addURI(DatabaseContract.AUTHORITY, DatabaseContract.PATH_MARK, MARKS);

        sUriMatcher.addURI(DatabaseContract.AUTHORITY, DatabaseContract.PATH_SCHEDULE, SCHEDULE);
        sUriMatcher.addURI(DatabaseContract.AUTHORITY, DatabaseContract.PATH_SCHEDULE + "/#/#/#",
                SCHEDULE_LEVEL_GROUP_SECTION);
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
        Cursor cursor = null;

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
                        projection, selection, selectionArgs, null, null, sortOrder);
                break;

            case SAVED:
                cursor = database.query(DatabaseContract.SavedEntry.TABLE_NAME, projection,
                        selection, selectionArgs, null, null, sortOrder);
                break;
            case SAVED_ID:
                selection = DatabaseContract.SavedEntry.COLUMN_ID + "= ?";
                selectionArgs = new String[]{String.valueOf(uri.getLastPathSegment())};
                cursor = database.query(DatabaseContract.SavedEntry.TABLE_NAME, projection,
                        selection, selectionArgs, null, null, sortOrder);
                break;
            case SUBJECT:
                cursor = database.query(DatabaseContract.SubjectEntry.TABLE_NAME, projection,
                        selection, selectionArgs, null, null, null);
                break;
            case SUBJECT_ID:
                selection = DatabaseContract.SubjectEntry.COLUMN_ID + "= ?";
                selectionArgs = new String[]{String.valueOf(uri.getLastPathSegment())};
                cursor = database.query(DatabaseContract.SubjectEntry.TABLE_NAME, projection,
                        selection, selectionArgs, null, null, sortOrder);
                break;
            case SCHEDULE_LEVEL_GROUP_SECTION:
                selection = DatabaseContract.ScheduleEntry.COLUMN_LEVEL + "=? AND " +
                        DatabaseContract.ScheduleEntry.COLUMN_SECTION + "=? AND " +
                        DatabaseContract.ScheduleEntry.COLUMN_GROUP + "=? ";
                selectionArgs = new String[]{String.valueOf(uri.getPathSegments().get(1))
                        , String.valueOf(uri.getPathSegments().get(2)),
                        String.valueOf(uri.getPathSegments().get(3))};
                cursor = database.query(DatabaseContract.ScheduleEntry.TABLE_NAME,
                        projection, selection, selectionArgs, null, null, null);
                break;
            default:
                throw new IllegalArgumentException();
        }
        if (getContext() != null)
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
                return insertData(uri, values, DatabaseContract.NoteEntry.TABLE_NAME);
            case SAVED:
                return insertData(uri, values, DatabaseContract.SavedEntry.TABLE_NAME);
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        int match = sUriMatcher.match(uri);
        int insertedRow = 0;
        SQLiteDatabase database = mDatabase.getWritableDatabase();
        switch (match) {
            case SUBJECT:
                for (ContentValues value :
                        values) {
                    long row = database.insert(DatabaseContract.SubjectEntry.TABLE_NAME, null, value);
                    if (row > 0) insertedRow++;
                }
                return insertedRow;
            case MARKS:
                for (ContentValues value :
                        values) {
                    long row = database.insert(DatabaseContract.MarkEntry.TABLE_NAME, null, value);
                    if (row > 0) insertedRow++;
                }
                return insertedRow;
            default:
                throw new IllegalStateException();
        }
    }

    private Uri insertData(Uri uri, ContentValues values, String table) {
        SQLiteDatabase database = mDatabase.getWritableDatabase();
        long id = database.insert(table, null, values);
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
                return deleteElement(uri, database,
                        DatabaseContract.NoteEntry.COLUMN_NOTE_ID,
                        DatabaseContract.NoteEntry.TABLE_NAME);
            case SAVED:
                return database.delete(DatabaseContract.SavedEntry.TABLE_NAME, selection, selectionArgs);

            case SAVED_ID:
                return deleteElement(uri, database,
                        DatabaseContract.SavedEntry.COLUMN_ID,
                        DatabaseContract.SavedEntry.TABLE_NAME);
            case SUBJECT:
                return database.delete(DatabaseContract.SubjectEntry.TABLE_NAME, selection, selectionArgs);
            case MARKS:
                return database.delete(DatabaseContract.MarkEntry.TABLE_NAME, selection, selectionArgs);
        }
        return 0;
    }

    private int deleteElement(@NonNull Uri uri, SQLiteDatabase database, String id, String table) {
        String selection;
        String[] selectionArgs;
        selection = id + " = ?";
        selectionArgs = new String[]{String.valueOf(uri.getLastPathSegment())};
        int rowDeleted = database.delete(table, selection, selectionArgs);
        assert getContext() != null;
        if (rowDeleted != 0) getContext().getContentResolver().notifyChange(uri, null);
        return rowDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase database = mDatabase.getWritableDatabase();
        int match = sUriMatcher.match(uri);

        switch (match) {
            case NOTES:
                return updateElement(uri, values, selection, selectionArgs,
                        database, DatabaseContract.NoteEntry.TABLE_NAME);
            case NOTES_ID:
                selection = DatabaseContract.NoteEntry.COLUMN_NOTE_ID + "= ?";
                selectionArgs = new String[]{String.valueOf(uri.getLastPathSegment())};
                return updateElement(uri, values, selection, selectionArgs,
                        database, DatabaseContract.NoteEntry.TABLE_NAME);
            case SAVED:
                return updateElement(uri, values, selection, selectionArgs,
                        database, DatabaseContract.SavedEntry.TABLE_NAME);
            case SAVED_ID:
                selection = DatabaseContract.SavedEntry.COLUMN_ID + "= ?";
                selectionArgs = new String[]{String.valueOf(uri.getLastPathSegment())};
                return updateElement(uri, values, selection, selectionArgs,
                        database, DatabaseContract.SavedEntry.TABLE_NAME);
            default:
                throw new IllegalArgumentException();
        }

    }

    private int updateElement(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection,
                              @Nullable String[] selectionArgs, SQLiteDatabase database, String table) {
        int rowUpdated = database.update(table, values, selection, selectionArgs);
        assert getContext() != null;
        if (rowUpdated != 0) getContext().getContentResolver().notifyChange(uri, null);
        return rowUpdated;
    }
}
