package com.ibnkhaldoun.studentside;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.ibnkhaldoun.studentside.database.DatabaseContract;

public class NoteEditActivity extends AppCompatActivity {

    private EditText mSubjectEditText, mNoteEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_edit);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSubjectEditText = findViewById(R.id.note_edit_subject_edit_text);
        mNoteEditText = findViewById(R.id.note_edit_note_edit_text);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.note_edit_menu_done:
                getDataAndInsert();
                finish();
                break;
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
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
