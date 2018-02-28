package com.ibnkhaldoun.studentside;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.ibnkhaldoun.studentside.database.DatabaseContract;

public class NoteEditActivity extends AppCompatActivity {

    public static final String KEY_SENDER = "sender";
    public static final String KEY_NEW = "new";
    public static final String KEY_MODIFY = "modify";
    public static final String KEY_SUBJECT = "subject";
    public static final String KEY_NOTE = "note";
    public static final String KEY_ID = "id";
    private EditText mSubjectEditText, mNoteEditText;

    private String sender;
    private String idCurrent;
    private Uri pathToNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_edit);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;

        mSubjectEditText = findViewById(R.id.note_edit_subject_edit_text);
        mNoteEditText = findViewById(R.id.note_edit_note_edit_text);
        toolbar.setTitle("New Note");
        getSupportActionBar().setTitle("New Note");
        sender = getIntent().getStringExtra(KEY_SENDER);
        if (sender.equals(KEY_MODIFY)) {
            getSupportActionBar().setTitle("Modify Note");
            toolbar.setTitle("Modify Note");
            idCurrent = getIntent().getStringExtra(KEY_ID);
            pathToNote = DatabaseContract.NoteEntry
                    .CONTENT_NOTE_URI
                    .buildUpon()
                    .appendPath(idCurrent)
                    .build();

            Cursor cursor = getContentResolver().query(pathToNote, new String[]{"*"},
                    null, null, null);
            assert cursor != null;
            cursor.moveToFirst();
            mSubjectEditText.setText(cursor.getString(cursor.getColumnIndex(DatabaseContract.NoteEntry.COLUMN_NOTE_SUBJECT)));
            mNoteEditText.setText(cursor.getString(cursor.getColumnIndex(DatabaseContract.NoteEntry.COLUMN_NOTE_TEXT)));
            cursor.close();
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (getIntent().getStringExtra(KEY_SENDER).equals(KEY_MODIFY)) {
            getMenuInflater().inflate(R.menu.menu_note_edit_modify, menu);
        } else {
            getMenuInflater().inflate(R.menu.menu_note_edit, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.note_edit_menu_done:
                if (validate()) {
                    switch (sender) {
                        case KEY_MODIFY:
                            getDataAndUpdate();
                            break;
                        case KEY_NEW:
                            getDataAndInsert();
                            break;
                    }
                    finish();
                }
                break;
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.note_edit_menu_delete:
                new AlertDialog.Builder(this)
                        .setTitle("Are you sure to delete the note")
                        .setPositiveButton("Delete", (dialog, which) -> {
                            Uri uri = DatabaseContract.NoteEntry.CONTENT_NOTE_URI
                                    .buildUpon()
                                    .appendPath(idCurrent)
                                    .build();

                            getContentResolver().delete(uri, null, null);
                            finish();
                        })
                        .setNegativeButton("No", null)
                        .show();
                break;
        }
        return true;
    }

    private void getDataAndUpdate() {
        String subject = mSubjectEditText.getText().toString();
        String note = mNoteEditText.getText().toString();
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.NoteEntry.COLUMN_NOTE_SUBJECT, subject);
        values.put(DatabaseContract.NoteEntry.COLUMN_NOTE_TEXT, note);
        getContentResolver().update(pathToNote, values, null, null);
    }

    public boolean validate() {

        boolean valid = true;

        if (mNoteEditText.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please add something", Toast.LENGTH_SHORT).show();
            valid = false;
        }

        if (mSubjectEditText.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Choose Subject", Toast.LENGTH_SHORT).show();
            valid = false;
        }

        return valid;
    }

    private void getDataAndInsert() {
        String subject = mSubjectEditText.getText().toString();
        String note = mNoteEditText.getText().toString();
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.NoteEntry.COLUMN_NOTE_SUBJECT, subject);
        values.put(DatabaseContract.NoteEntry.COLUMN_NOTE_TEXT, note);
        getContentResolver().insert(DatabaseContract.NoteEntry.CONTENT_NOTE_URI, values);
    }
}
