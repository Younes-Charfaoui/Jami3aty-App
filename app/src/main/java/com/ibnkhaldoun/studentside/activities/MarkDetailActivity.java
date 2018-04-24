package com.ibnkhaldoun.studentside.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.ibnkhaldoun.studentside.R;
import com.ibnkhaldoun.studentside.Utilities.AverageCalculus;
import com.ibnkhaldoun.studentside.models.Mark;
import com.ibnkhaldoun.studentside.models.MarkItem;

public class MarkDetailActivity extends AppCompatActivity {

    public static final String KEY_MARK = "keyMark";
    private TextView mExamTextView, mTdTextView, mTpTextView,
            mAverageTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        MarkItem mark = getIntent().getParcelableExtra(KEY_MARK);

        mExamTextView = findViewById(R.id.exam_mark);
        mTdTextView = findViewById(R.id.td_mark);
        mTpTextView = findViewById(R.id.tp_mark);
        mAverageTextView = findViewById(R.id.average);


        mExamTextView.setText(String.valueOf(mark.getExam()));
        mTdTextView.setText(String.valueOf(mark.getTD()));
        mTpTextView.setText(String.valueOf(mark.getTP()));
        mAverageTextView.setText(String.valueOf(AverageCalculus.getFullMarkAverage(
                mark.getTD(),
                mark.getTD(),
                mark.getTD())));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }
}
