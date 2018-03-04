package com.ibnkhaldoun.studentside.activities;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.support.v7.widget.helper.ItemTouchHelper.SimpleCallback;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.ibnkhaldoun.studentside.R;
import com.ibnkhaldoun.studentside.adapters.NotesAdapter;
import com.ibnkhaldoun.studentside.database.DatabaseContract;
import com.ibnkhaldoun.studentside.models.Note;

public class NotesActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int ID_LOADER = 162;
    private RecyclerView mNoteRecyclerView;
    private NotesAdapter mAdapter;
    private LinearLayout mEmptyView;
    private Paint mPaint = new Paint();

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
            Intent intent = new Intent(NotesActivity.this, NoteEditActivity.class);
            intent.putExtra(NoteEditActivity.KEY_SENDER, NoteEditActivity.KEY_NEW);
            startActivity(intent);
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
                        String id = viewHolder.itemView.getTag().toString();
                        int position = viewHolder.getAdapterPosition();
                        Note note = mAdapter.removeItem(viewHolder.getAdapterPosition());
                        Uri uri = DatabaseContract.NoteEntry.CONTENT_NOTE_URI.buildUpon().appendPath(id).build();
                        Snackbar snackbar = Snackbar.make(findViewById(R.id.note_coordinator), "1 note deleted", Snackbar.LENGTH_LONG);
                        snackbar.setAction("Undo", v -> {
                            mAdapter.restoreItem(position, note);
                        });

                        snackbar.addCallback(new Snackbar.Callback() {
                            @Override
                            public void onShown(Snackbar sb) {
                                super.onShown(sb);
                            }

                            @Override
                            public void onDismissed(Snackbar transientBottomBar, int event) {
                                super.onDismissed(transientBottomBar, event);
                                if (event == Snackbar.Callback.DISMISS_EVENT_TIMEOUT
                                        ) {
                                    getContentResolver().delete(uri, null, null);
                                }
                                if (event == Snackbar.Callback.DISMISS_EVENT_CONSECUTIVE) {
                                    getContentResolver().delete(uri, null, null);
                                    getSupportLoaderManager().restartLoader(ID_LOADER, null, NotesActivity.this);
                                }
                            }
                        });

                        snackbar.show();
                    }

                    @Override
                    public void onChildDraw(Canvas canvas, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                        Bitmap icon;
                        View itemView = viewHolder.itemView;
                        float height = itemView.getHeight();
                        float width = height / 3;
                        if (dX >= 0) {
                            mPaint.setColor(getResources().getColor(R.color.red_me));
                            RectF background = new RectF((float) itemView.getLeft(),
                                    (float) itemView.getTop(), dX,
                                    (float) itemView.getBottom());

                            canvas.drawRect(background, mPaint);

                            icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_delete);
                            RectF iconRectF = new RectF((float) itemView.getLeft() + width,
                                    (float) itemView.getTop() + width,
                                    (float) itemView.getLeft() + 2 * width,
                                    (float) itemView.getBottom() - width);
                            canvas.drawBitmap(icon, null, iconRectF, mPaint);
                        } else {
                            mPaint.setColor(getResources().getColor(R.color.red_me));
                            RectF background = new RectF((float) itemView.getRight() + dX,
                                    (float) itemView.getTop(), (float) itemView.getRight(),
                                    (float) itemView.getBottom());

                            canvas.drawRect(background, mPaint);

                            icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_delete);
                            RectF iconRectF = new RectF((float) itemView.getRight() - 2 * width,
                                    (float) itemView.getTop() + width,
                                    (float) itemView.getRight() - width,
                                    (float) itemView.getBottom() - width);
                            canvas.drawBitmap(icon, null, iconRectF, mPaint);
                        }
                        super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
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
            case R.id.note_menu_delete_all:
                new AlertDialog.Builder(this)
                        .setTitle("Are you sure to delete all notes ?")
                        .setPositiveButton("Delete", (dialog, which) -> {
                            getContentResolver().delete(DatabaseContract.NoteEntry.CONTENT_NOTE_URI, null, null);
                            getSupportLoaderManager().restartLoader(ID_LOADER, null, NotesActivity.this);
                        }).setNegativeButton("No", null)
                        .show();
                break;
            case R.id.note_menu_add_note:
                Intent intent = new Intent(NotesActivity.this, NoteEditActivity.class);
                intent.putExtra(NoteEditActivity.KEY_SENDER, NoteEditActivity.KEY_NEW);
                startActivity(intent);
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note, menu);
        return super.onCreateOptionsMenu(menu);
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
