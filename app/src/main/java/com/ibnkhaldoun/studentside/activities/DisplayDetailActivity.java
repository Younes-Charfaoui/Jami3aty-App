package com.ibnkhaldoun.studentside.activities;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ibnkhaldoun.studentside.R;
import com.ibnkhaldoun.studentside.Utilities.PreferencesManager;
import com.ibnkhaldoun.studentside.Utilities.Utilities;
import com.ibnkhaldoun.studentside.adapters.NotesOfDisplayAdapter;
import com.ibnkhaldoun.studentside.asyncTask.CommentAsyncTask;
import com.ibnkhaldoun.studentside.asyncTask.ResponseAsyncTask;
import com.ibnkhaldoun.studentside.fragments.NoteBottomSheet;
import com.ibnkhaldoun.studentside.fragments.NoteOfDisplayDialog;
import com.ibnkhaldoun.studentside.models.Comment;
import com.ibnkhaldoun.studentside.models.Display;
import com.ibnkhaldoun.studentside.networking.models.RequestPackage;
import com.ibnkhaldoun.studentside.networking.models.Response;
import com.ibnkhaldoun.studentside.networking.utilities.NetworkUtilities;
import com.ibnkhaldoun.studentside.networking.utilities.RequestPackageFactory;
import com.ibnkhaldoun.studentside.providers.EndPointsProvider;

import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.ibnkhaldoun.studentside.networking.models.RequestPackage.POST;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.JSON_COMMENT_ID2;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.JSON_POST_ID2;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.JSON_SAVE_ACTION;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.JSON_STUDENT_ID;


public class DisplayDetailActivity extends AppCompatActivity implements NoteOfDisplayDialog.INoteOfDisplay,
        ResponseAsyncTask.IResponseListener,
        NotesOfDisplayAdapter.INoteOfDisplayMore,
        CommentAsyncTask.ICommentListener,
        NoteBottomSheet.INoteBottomSheet {

    public static final String SENDER = "sender";
    public static final String DATA = "data";


    private RecyclerView mNoteRecyclerView;
    private NotesOfDisplayAdapter mAdapter;
    private ProgressBar mLoadingProgress;
    private TextView mNoCommentTv;
    private RequestPackage mRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        setupRecyclerView();

        mLoadingProgress = findViewById(R.id.display_detail_note_progress);
        mNoCommentTv = findViewById(R.id.display_detail_no_comment);

        Display display = getIntent().getParcelableExtra(DATA);


        mRequest = new RequestPackage.Builder()
                .addMethod(POST)
                .addEndPoint(EndPointsProvider.getAllCommentsEndpoint())
                .addParams(JSON_POST_ID2, String.valueOf(display.getId()))
                .create();

        new CommentAsyncTask(this).execute(mRequest);


        TextView professorShortNameTextView = findViewById(R.id.display_detail_professor_short_name_text_view);
        professorShortNameTextView.setText(Utilities.getProfessorShortName(display.getProfessor()));

        GradientDrawable circle = (GradientDrawable) professorShortNameTextView.getBackground();
        circle.setColor(Utilities.getCircleColor(this));

        TextView professorNameTextView = findViewById(R.id.display_detail_professor_name_text_view);
        professorNameTextView.setText(display.getProfessor());

        TextView dateTextView = findViewById(R.id.display_detail_date_and_time_text_view);
        dateTextView.setText(display.getDate());

        TextView textTextView = findViewById(R.id.display_detail_text_text_view);
        textTextView.setText(display.getText());


        if (display.isSaved())
            findViewById(R.id.display_detail_save_button).setEnabled(false);
        else
            findViewById(R.id.display_detail_save_button).setEnabled(true);

        findViewById(R.id.display_detail_save_button).setOnClickListener(v -> {
            if (NetworkUtilities.isConnected(this)) {
                ResponseAsyncTask task = new ResponseAsyncTask(response -> {
                    if (response.getStatus() != Response.RESPONSE_SUCCESS) {
                        Toast.makeText(this, "Try later , problem with connecting", Toast.LENGTH_SHORT).show();
                    } else {
                        v.setEnabled(false);
                    }
                });
                RequestPackage request = new RequestPackage.Builder().addEndPoint(EndPointsProvider.getUnsaveEndpoint())
                        .addMethod(POST)
                        .addParams(JSON_STUDENT_ID,
                                new PreferencesManager(this, PreferencesManager.STUDENT).getIdUser())
                        .addParams(JSON_POST_ID2, String.valueOf(display.getId()))
                        .addParams(JSON_SAVE_ACTION, String.valueOf(1))
                        .create();
                task.execute(request);
            }
        });

        findViewById(R.id.display_detail_note_button).setOnClickListener(v -> {
            if (NetworkUtilities.isConnected(this)) {
                NoteOfDisplayDialog dialog =
                        NoteOfDisplayDialog.newInstance(display.getId(), NoteOfDisplayDialog.ADD);
                dialog.show(getSupportFragmentManager(), "Tag");
            } else {
                Toast.makeText(this, R.string.no_internet_connection_string,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupRecyclerView() {
        mNoteRecyclerView = findViewById(R.id.display_detail_notes_recycler);
        mAdapter = new NotesOfDisplayAdapter(this, this);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mNoteRecyclerView.setAdapter(mAdapter);
        mNoteRecyclerView.setLayoutManager(manager);
        mNoteRecyclerView.setHasFixedSize(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    @Override
    public void OnFinishNoting(String note, long id, int type) {
        if (type == NoteOfDisplayDialog.ADD) {
            RequestPackage request = RequestPackageFactory.createNoteAddingRequest(id, note, this);
            ResponseAsyncTask task = new ResponseAsyncTask(response -> {
                if (response.getStatus() == 200) {
                    new CommentAsyncTask(this).execute(mRequest);
                } else {
                    Toast.makeText(this, "Error when connectiong to server", Toast.LENGTH_SHORT).show();
                }
            });
            task.execute(request);
        } else if (type == NoteOfDisplayDialog.EDIT) {
            RequestPackage request = RequestPackageFactory.createNoteEditingRequest(id, note);
            ResponseAsyncTask task = new ResponseAsyncTask(response -> {
                if (response.getStatus() == 200) {
                    mAdapter.updateElement(note, id);
                } else {
                    Toast.makeText(this, "Error when connectiong to server", Toast.LENGTH_SHORT).show();
                }
            });
            task.execute(request);
        }
    }

    @Override
    public void onGetResponse(Response response) {
        new CommentAsyncTask(this).execute(mRequest);
    }

    @Override
    public void OnLongClick(long idComment, String comment) {
        Log.i("Remove", "OnLongClick: " + idComment);
        NoteBottomSheet bottomSheet = NoteBottomSheet.newInstance(idComment, comment);
        bottomSheet.show(getSupportFragmentManager(), "Tag");
    }

    @Override
    public void OnStartLoading() {
        mLoadingProgress.setVisibility(VISIBLE);
        mNoteRecyclerView.setVisibility(GONE);
        mNoCommentTv.setVisibility(GONE);
    }

    @Override
    public void OnFinishedLoading(List<Comment> comments) {
        mLoadingProgress.setVisibility(GONE);
        if (comments.size() == 0) {
            mNoCommentTv.setVisibility(VISIBLE);
            mNoteRecyclerView.setVisibility(GONE);
        } else {
            mNoCommentTv.setVisibility(GONE);
            mNoteRecyclerView.setVisibility(VISIBLE);
            mAdapter.swapList(comments);
        }
    }

    @Override
    public void OnEdit(long id, String note) {
        NoteOfDisplayDialog dialog = NoteOfDisplayDialog.newInstance(id, NoteOfDisplayDialog.EDIT, note);
        dialog.show(getSupportFragmentManager(), "Tag");
    }

    @Override
    public void OnRemove(long id) {
        Log.i("Remove", "OnRemove: " + id);
        if (NetworkUtilities.isConnected(this)) {
            new AlertDialog.Builder(this)
                    .setTitle("Deleting your note")
                    .setMessage("Are you sure you want to delete this note ?")
                    .setPositiveButton(R.string.delete_string, (dialog, which) -> {
                        RequestPackage request = new RequestPackage.Builder()
                                .addMethod(POST)
                                .addEndPoint(EndPointsProvider.getRemoveCommentsEndpoint())
                                .addParams(JSON_COMMENT_ID2, String.valueOf(id))
                                .create();
                        ResponseAsyncTask task = new ResponseAsyncTask(response -> {
                            if (response.getStatus() == 200) {
                                Toast.makeText(this, "Comment removed successfully !", Toast.LENGTH_SHORT).show();
                                mAdapter.removeElement(id);
                            } else {
                                Toast.makeText(this, "Try later, problem when connecting", Toast.LENGTH_SHORT).show();
                            }
                        });
                        task.execute(request);
                    })
                    .setNegativeButton(android.R.string.no, null)
                    .show();
        } else {
            Toast.makeText(this, R.string.no_internet_connection_string, Toast.LENGTH_SHORT).show();
        }
    }
}