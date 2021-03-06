/*------------------------------------------------------------------------------
 - Copyright (c) 2018. This code was created by Younes Charfaoui in the process of Graduation Project for the year of  2018 , which is about creating a platform  for students and professors to help them in the communication and the get known of the university information and so on.
 -----------------------------------------------------------------------------*/

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
import android.support.v4.view.animation.FastOutSlowInInterpolator;
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
import com.ibnkhaldoun.studentside.Utilities.ActivityUtilities;
import com.ibnkhaldoun.studentside.Utilities.PreferencesManager;
import com.ibnkhaldoun.studentside.adapters.NotesAdapter;
import com.ibnkhaldoun.studentside.database.DatabaseContract;
import com.ibnkhaldoun.studentside.models.Note;

import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class NotesActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {


    private static final int ID_LOADER = 162;
    private static int NUMBER_OF_NOTE;
    private RecyclerView mNoteRecyclerView;
    private NotesAdapter mAdapter;
    private LinearLayout mEmptyView;
    private Paint mPaint = new Paint();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NUMBER_OF_NOTE = 0;
        setContentView(R.layout.activity_notes);

        Toolbar toolbar = findViewById(R.id.note_toolbar);
        setSupportActionBar(toolbar);
        mEmptyView = findViewById(R.id.note_empty_view);
        mNoteRecyclerView = findViewById(R.id.note_recycler_view);

        setupRecyclerView();
        if (getSupportActionBar() != null)
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
                        Uri uri = DatabaseContract.NoteEntry.CONTENT_NOTE_URI.buildUpon()
                                .appendPath(id).build();
                        Snackbar snackbar = Snackbar.make(findViewById(R.id.note_coordinator),
                                R.string.one_note_deleted, Snackbar.LENGTH_LONG);
                        snackbar.setAction(R.string.undo, v -> mAdapter.restoreItem(position, note));

                        snackbar.addCallback(new Snackbar.Callback() {
                            @Override
                            public void onShown(Snackbar sb) {
                                super.onShown(sb);
                                if (NUMBER_OF_NOTE == 1) {
                                    mEmptyView.setVisibility(VISIBLE);
                                    mNoteRecyclerView.setVisibility(GONE);
                                } else if (NUMBER_OF_NOTE != 0) {
                                    if (mEmptyView.getVisibility() == VISIBLE) {
                                        mEmptyView.setVisibility(GONE);
                                    }
                                    if (mNoteRecyclerView.getVisibility() == GONE
                                            || mNoteRecyclerView.getVisibility() == INVISIBLE)
                                        mNoteRecyclerView.setVisibility(VISIBLE);

                                } else {
                                    mEmptyView.setVisibility(VISIBLE);
                                    mNoteRecyclerView.setVisibility(GONE);
                                }
                            }

                            @Override
                            public void onDismissed(Snackbar transientBottomBar, int event) {
                                super.onDismissed(transientBottomBar, event);

                                if (event == DISMISS_EVENT_CONSECUTIVE
                                        || event == DISMISS_EVENT_SWIPE
                                        || event == DISMISS_EVENT_TIMEOUT) {
                                    NUMBER_OF_NOTE -= getContentResolver().delete(uri, null, null);
                                    if (NUMBER_OF_NOTE == 1) {
                                        mEmptyView.setVisibility(VISIBLE);
                                        mNoteRecyclerView.setVisibility(GONE);
                                    } else if (NUMBER_OF_NOTE != 0) {
                                        if (mEmptyView.getVisibility() == VISIBLE) {
                                            mEmptyView.setVisibility(GONE);
                                        }
                                        if (mNoteRecyclerView.getVisibility() == GONE
                                                || mNoteRecyclerView.getVisibility() == INVISIBLE)
                                            mNoteRecyclerView.setVisibility(VISIBLE);

                                    } else {
                                        mEmptyView.setVisibility(VISIBLE);
                                        mNoteRecyclerView.setVisibility(GONE);
                                    }
                                }
                                if (event == DISMISS_EVENT_ACTION) {
                                    if (NUMBER_OF_NOTE != 0) {
                                        if (mEmptyView.getVisibility() == VISIBLE) {
                                            mEmptyView.setVisibility(GONE);
                                        }
                                        if (mNoteRecyclerView.getVisibility() == GONE
                                                || mNoteRecyclerView.getVisibility() == INVISIBLE)
                                            mNoteRecyclerView.setVisibility(VISIBLE);

                                    } else {
                                        mEmptyView.setVisibility(VISIBLE);
                                        mNoteRecyclerView.setVisibility(GONE);
                                    }
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
                if (NUMBER_OF_NOTE != 0)
                    new AlertDialog.Builder(this)
                            .setTitle(R.string.delete_all_note_confirmation)
                            .setPositiveButton(R.string.delete_string, (dialog, which) -> {
                                getContentResolver().delete(DatabaseContract.NoteEntry.CONTENT_NOTE_URI, null, null);
                                getSupportLoaderManager().restartLoader(ID_LOADER, null, NotesActivity.this);
                            }).setNegativeButton(android.R.string.no, null)
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
                int who = ActivityUtilities.whoIsUsing(this);
                if (who == MessageDetailActivity.PROFESSOR)
                    return new CursorLoader(this,
                            DatabaseContract.NoteEntry.CONTENT_NOTE_URI,
                            projection,
                            DatabaseContract.NoteEntry.COLUMN_USER_ID + " = ?",
                            new String[]{new PreferencesManager(this
                                    , PreferencesManager.PROFESSOR).getIdUser()},
                            null);
                else
                    return new CursorLoader(this,
                            DatabaseContract.NoteEntry.CONTENT_NOTE_URI,
                            projection,
                            DatabaseContract.NoteEntry.COLUMN_USER_ID + " = ?",
                            new String[]{new PreferencesManager(this
                                    , PreferencesManager.STUDENT).getIdUser()},
                            null);
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data.getCount() == 0) {
            mNoteRecyclerView.setVisibility(INVISIBLE);
            mEmptyView.setVisibility(VISIBLE);
            NUMBER_OF_NOTE = 0;
        } else {
            mEmptyView.setVisibility(INVISIBLE);
            mNoteRecyclerView.setVisibility(VISIBLE);
            NUMBER_OF_NOTE = data.getCount();
        }
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (new PreferencesManager(this, PreferencesManager.CONFIG).isAddNoteFirstTime()) {
            new MaterialTapTargetPrompt.Builder(this)
                    .setPrimaryText("Add New note")
                    .setSecondaryText("Click here to and new note.")
                    .setTarget(findViewById(R.id.note_add_fav))
                    .setBackgroundColour(getResources().getColor(R.color.colorPrimary))
                    .setAnimationInterpolator(new FastOutSlowInInterpolator())
                    .setPromptStateChangeListener((prompt, state) -> {

                        switch (state) {
                            case MaterialTapTargetPrompt.STATE_FOCAL_PRESSED:
                                new PreferencesManager(this, PreferencesManager.CONFIG).setAddNoteFirstTime();
                                break;
                        }
                    })
                    .show();
        } else if (new PreferencesManager(this, PreferencesManager.CONFIG).isMenuNoteFirstTime()) {

            new MaterialTapTargetPrompt.Builder(this)
                    .setPrimaryText("More Options")
                    .setSecondaryText("Click here to see more options.")
                    .setIcon(R.drawable.ic_more_vert_white)
                    .setBackgroundColour(getResources().getColor(R.color.colorPrimary))
                    .setTarget(((Toolbar) findViewById(R.id.note_toolbar)).getChildAt(2))
                    .setAnimationInterpolator(new FastOutSlowInInterpolator())
                    .setPromptStateChangeListener((prompt, state) -> {
                        switch (state) {
                            case MaterialTapTargetPrompt.STATE_FOCAL_PRESSED:
                                new PreferencesManager(this, PreferencesManager.CONFIG).setMenuNoteFirstTime();
                                break;
                        }
                    })
                    .show();
        }
    }
}
