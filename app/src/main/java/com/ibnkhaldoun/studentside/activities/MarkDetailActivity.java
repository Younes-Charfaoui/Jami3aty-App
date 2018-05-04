package com.ibnkhaldoun.studentside.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.ibnkhaldoun.studentside.R;
import com.ibnkhaldoun.studentside.Utilities.AverageCalculus;
import com.ibnkhaldoun.studentside.models.MarkItem;

public class MarkDetailActivity extends AppCompatActivity {

    public static final String KEY_MARK = "keyMark";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //get the mark passed by the intent.
        MarkItem mark = getIntent().getParcelableExtra(KEY_MARK);

        //initializing the views and populate them with the marks.
        TextView examTextView = findViewById(R.id.exam_mark);
        TextView tdTextView = findViewById(R.id.td_mark);
        TextView tpTextView = findViewById(R.id.tp_mark);
        TextView averageTextView = findViewById(R.id.average);
        TextView subjectTextView = findViewById(R.id.mark_subject);

        if (mark.getExam() != -1)
            examTextView.setText(String.valueOf(mark.getExam()));
        else
            examTextView.setText("---");

        if (mark.getTD() != -1)
            tdTextView.setText(String.valueOf(mark.getTD()));
        else
            tdTextView.setText("---");

        if (mark.getTP() != -1)
            tpTextView.setText(String.valueOf(mark.getTP()));
        else
            tpTextView.setText("---");

        averageTextView.setText(String.valueOf(AverageCalculus.getFullMarkAverage(
                mark.getTD(),
                mark.getTD(),
                mark.getTD())));

        subjectTextView.setText(mark.getSubjectName());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }
}
