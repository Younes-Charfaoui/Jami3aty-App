/*------------------------------------------------------------------------------
 - Copyright (c) 2018. This code was created by Younes Charfaoui in the process of Graduation Project for the year of  2018 , which is about creating a platform  for students and professors to help them in the communication and the get known of the university information and so on.
 -----------------------------------------------------------------------------*/

package com.ibnkhaldoun.studentside.activities;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.ibnkhaldoun.studentside.R;
import com.ibnkhaldoun.studentside.Utilities.ActivityUtilities;
import com.ibnkhaldoun.studentside.Utilities.PreferencesManager;
import com.ibnkhaldoun.studentside.database.DatabaseContract;
import com.ibnkhaldoun.studentside.fragments.SubjectListFragment;
import com.ibnkhaldoun.studentside.interfaces.ISubjectDialog;
import com.ibnkhaldoun.studentside.networking.models.RequestPackage;
import com.ibnkhaldoun.studentside.networking.utilities.NetworkUtilities;
import com.ibnkhaldoun.studentside.providers.EndPointsProvider;

import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.JSON_STUDENT_GROUP;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.JSON_STUDENT_LEVEL;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.JSON_STUDENT_SECTION;

public class NoteEditActivity extends AppCompatActivity implements ISubjectDialog {

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


        mSubjectEditText = findViewById(R.id.note_edit_subject_edit_text);
        mNoteEditText = findViewById(R.id.note_edit_note_edit_text);

        if (!new PreferencesManager(this, PreferencesManager.SUBJECT).isSubjectsExists()
                && NetworkUtilities.isConnected(this)) {
            PreferencesManager manager = new PreferencesManager(this, PreferencesManager.STUDENT);

            RequestPackage requestAllSchedule = new RequestPackage.Builder()
                    .addMethod(RequestPackage.POST)
                    .addParams(JSON_STUDENT_LEVEL, manager.getLevelStudent())
                    .addParams(JSON_STUDENT_SECTION, manager.getSectionStudent())
                    .addParams(JSON_STUDENT_GROUP, manager.getGroupStudent())
                    .addEndPoint(EndPointsProvider.getScheduleAllEndpoint())
                    .create();

        }
        toolbar.setTitle(R.string.new_note);
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(R.string.new_note);
        sender = getIntent().getStringExtra(KEY_SENDER);
        if (sender.equals(KEY_MODIFY)) {
            getSupportActionBar().setTitle(R.string.modify_note);
            toolbar.setTitle(R.string.modify_note);
            idCurrent = getIntent().getStringExtra(KEY_ID);
            pathToNote = DatabaseContract.NoteEntry
                    .CONTENT_NOTE_URI
                    .buildUpon()
                    .appendPath(idCurrent)
                    .build();


            Cursor cursor;
            int who = ActivityUtilities.whoIsUsing(this);
            if (who == MessageDetailActivity.PROFESSOR)
                cursor = getContentResolver().query(pathToNote, new String[]{"*"},
                        DatabaseContract.NoteEntry.COLUMN_USER_ID + " = ?",
                        new String[]{new PreferencesManager(this, PreferencesManager.PROFESSOR)
                                .getIdUser()}, null);
            else
                cursor = getContentResolver().query(pathToNote, new String[]{"*"},
                        DatabaseContract.NoteEntry.COLUMN_USER_ID + " = ?",
                        new String[]{new PreferencesManager(this, PreferencesManager.STUDENT)
                                .getIdUser()}, null);
            if (cursor != null) {
                cursor.moveToFirst();
                mSubjectEditText.setText(cursor.getString(cursor.getColumnIndex(DatabaseContract.NoteEntry.COLUMN_NOTE_SUBJECT)));
                mNoteEditText.setText(cursor.getString(cursor.getColumnIndex(DatabaseContract.NoteEntry.COLUMN_NOTE_TEXT)));
                cursor.close();
            }
        }
        mNoteEditText.requestFocus();
        if (ActivityUtilities.whoIsUsing(this) == MessageDetailActivity.PROFESSOR) {
            //mSubjectEditText.setFocusable(true);
            //mSubjectEditText.setClickable(true);
            //mSubjectEditText.requestFocus();
        } else {
            mSubjectEditText.setClickable(false);
            mSubjectEditText.setFocusable(false);
            mSubjectEditText.setOnClickListener(v -> {
                if (ActivityUtilities.whoIsUsing(this) != MessageDetailActivity.PROFESSOR) {

                    PreferencesManager manager = new PreferencesManager(this, PreferencesManager.SUBJECT);
                    if (manager.isSubjectsExists()) {
                        SubjectListFragment dialog = SubjectListFragment.newInstance(manager.getSubjects());
                        dialog.setCancelable(true);
                        dialog.show(getSupportFragmentManager(), "TAG");
                    } else {

                    }
                }
            });
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
                        .setTitle(R.string.delete_note_confirmation)
                        .setPositiveButton(R.string.delete_string, (dialog, which) -> {
                            Uri uri = DatabaseContract.NoteEntry.CONTENT_NOTE_URI
                                    .buildUpon()
                                    .appendPath(idCurrent)
                                    .build();

                            getContentResolver().delete(uri, null, null);
                            getContentResolver().notifyChange(uri, null);
                            finish();
                        })
                        .setNegativeButton(android.R.string.no, null)
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
        Snackbar snackbar;
        if (mNoteEditText.getText().toString().isEmpty()) {
            snackbar = Snackbar.make(findViewById(R.id.note_main_view), R.string.add_something
                    , Snackbar.LENGTH_SHORT);
            if (snackbar.isShownOrQueued()) snackbar.dismiss();
            snackbar.show();
            return false;
        }

        if (mSubjectEditText.getText().toString().isEmpty()) {
            snackbar = Snackbar.make(findViewById(R.id.note_main_view),
                    R.string.choose_subject_please
                    , Snackbar.LENGTH_SHORT);
            if (snackbar.isShownOrQueued()) snackbar.dismiss();
            snackbar.show();
            return false;
        }
        return true;
    }

    private void getDataAndInsert() {
        String subject = mSubjectEditText.getText().toString();
        String note = mNoteEditText.getText().toString();
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.NoteEntry.COLUMN_NOTE_SUBJECT, subject);
        values.put(DatabaseContract.NoteEntry.COLUMN_NOTE_TEXT, note);
        if (ActivityUtilities.whoIsUsing(this) == MessageDetailActivity.PROFESSOR)
            values.put(DatabaseContract.NoteEntry.COLUMN_USER_ID,
                    new PreferencesManager(this, PreferencesManager.PROFESSOR).getIdUser());
        else values.put(DatabaseContract.NoteEntry.COLUMN_USER_ID,
                new PreferencesManager(this, PreferencesManager.STUDENT).getIdUser());
        getContentResolver().insert(DatabaseContract.NoteEntry.CONTENT_NOTE_URI, values);
    }

    @Override
    public void onSubjectChosen(String subject) {
        mSubjectEditText.setText(subject);
    }


}
