package com.ibnkhaldoun.studentside.activities;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.ibnkhaldoun.studentside.R;
import com.ibnkhaldoun.studentside.adapters.SubjectsAdapter;
import com.ibnkhaldoun.studentside.database.DatabaseContract;

public class SubjectsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int SUBJECT_LOADER_ID = 1292;
    private RecyclerView mSubjectRecyclerView;
    private SubjectsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupRecyclerView();

        getSupportLoaderManager().initLoader(SUBJECT_LOADER_ID, null, this);
    }

    private void setupRecyclerView() {
        mSubjectRecyclerView = findViewById(R.id.subject_recycler_view);
        mAdapter = new SubjectsAdapter(this);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mSubjectRecyclerView.setAdapter(mAdapter);
        mSubjectRecyclerView.setLayoutManager(manager);
        mSubjectRecyclerView.setHasFixedSize(true);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                this,
                DatabaseContract.SubjectEntry.CONTENT_SUBJECT_URI,
                new String[]{"*"}, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data.getCount() != 0) {

        } else {

        }
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }


}
