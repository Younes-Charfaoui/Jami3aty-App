package com.ibnkhaldoun.studentside.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ibnkhaldoun.studentside.R;
import com.ibnkhaldoun.studentside.Utilities.ActivityUtilities;
import com.ibnkhaldoun.studentside.Utilities.Utilities;
import com.ibnkhaldoun.studentside.models.Comment;
import com.ibnkhaldoun.studentside.models.Saved;
import com.ibnkhaldoun.studentside.services.LoadDataService;

import java.util.ArrayList;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class SavedDetailActivity extends AppCompatActivity {

    public static final String KEY_SAVED = "keySaved";
    private ProgressBar mLoadingCommentProgress;
    private LinearLayout mCommentLinearLayout;
    private TextView mNoCommentTv;

    private BroadcastReceiver mCommentsReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mLoadingCommentProgress.setVisibility(GONE);
            ArrayList<Comment> comments = intent.getParcelableArrayListExtra(LoadDataService.KEY_DATA);
            if (comments.size() != 0) {
                mCommentLinearLayout.setVisibility(VISIBLE);
                for (Comment comment : comments) {
                    mCommentLinearLayout.addView(ActivityUtilities
                            .createNoteView(context, Utilities.getProfessorShortName(comment.getName()),
                                    comment.getComment(),
                                    comment.getName(),
                                    comment.getDate(), mCommentLinearLayout));
                }
            } else {
                mNoCommentTv.setVisibility(VISIBLE);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mLoadingCommentProgress = findViewById(R.id.saved_detail_progress);
        mCommentLinearLayout = findViewById(R.id.saved_detail_comments_linear_layout);
        mNoCommentTv = findViewById(R.id.saved_detail_no_comment);

        Saved savedPost = getIntent().getParcelableExtra(KEY_SAVED);

        TextView professorShortName = findViewById(R.id.saved_detail_professor_short_name_text_view);
        professorShortName.setText(Utilities.getProfessorShortName(savedPost.getProfessor()));

        GradientDrawable circleImage = (GradientDrawable) professorShortName.getBackground();
        circleImage.setColor(Utilities.getCircleColor(this));

        ((TextView) findViewById(R.id.saved_detail_professor_name_text_view)).
                setText(savedPost.getProfessor());

        ((TextView) findViewById(R.id.saved_detail__date_and_time_text_view)).
                setText(Utilities.getDateFormat(savedPost.getDate()));

        ((TextView) findViewById(R.id.saved_detail__text_text_view)).
                setText(savedPost.getText());

        LocalBroadcastManager.getInstance(this).registerReceiver(mCommentsReceiver,
                new IntentFilter(LoadDataService.COMMENTS_ACTION));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mCommentsReceiver);
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
}
