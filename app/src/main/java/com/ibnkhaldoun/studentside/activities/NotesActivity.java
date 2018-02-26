package com.ibnkhaldoun.studentside.activities;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.support.v7.widget.helper.ItemTouchHelper.SimpleCallback;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.ibnkhaldoun.studentside.NoteEditActivity;
import com.ibnkhaldoun.studentside.R;
import com.ibnkhaldoun.studentside.adapters.NotesAdapter;
import com.ibnkhaldoun.studentside.database.DatabaseContract;

public class NotesActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int ID_LOADER = 162;
    private RecyclerView mNoteRecyclerView;
    private NotesAdapter mAdapter;
    private LinearLayout mEmptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        Toolbar toolbar = findViewById(R.id.note_toolbar);
        setSupportActionBar(toolbar);
        mEmptyView = findViewById(R.id.note_empty_view);
        mNoteRecyclerView = findViewById(R.id.note_recycler_view);

        setupRecyclerView();
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setupFloatingActionButton();

        getSupportLoaderManager().initLoader(ID_LOADER, null, this);
    }

    private void setupFloatingActionButton() {
        FloatingActionButton mAddNoteFloatingActionButton = findViewById(R.id.note_add_fav);
        mAddNoteFloatingActionButton.setOnClickListener(v -> {

            startActivity(new Intent(NotesActivity.this, NoteEditActivity.class));
        });
    }

    private void setupRecyclerView() {
        mAdapter = new NotesAdapter(this);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mNoteRecyclerView.setAdapter(mAdapter);
        mNoteRecyclerView.setLayoutManager(manager);
        mNoteRecyclerView.setHasFixedSize(true);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        String id = String.valueOf((int) viewHolder.itemView.getTag());
                        Uri uri = DatabaseContract.NoteEntry.CONTENT_NOTE_URI;
                        uri = uri.buildUpon().appendPath(id).build();

                        getContentResolver().delete(uri, null, null);

                        getSupportLoaderManager().restartLoader(ID_LOADER, null, NotesActivity.this);
                    }
                });
        itemTouchHelper.attachToRecyclerView(mNoteRecyclerView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {"*"};
        switch (id) {
            case ID_LOADER:
                return new CursorLoader(this,
                        DatabaseContract.NoteEntry.CONTENT_NOTE_URI,
                        projection,
                        null,
                        null,
                        null);
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data.getCount() == 0) {
            mNoteRecyclerView.setVisibility(View.INVISIBLE);
            mEmptyView.setVisibility(View.VISIBLE);
        } else {
            mEmptyView.setVisibility(View.INVISIBLE);
            mNoteRecyclerView.setVisibility(View.VISIBLE);
        }
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }
}
