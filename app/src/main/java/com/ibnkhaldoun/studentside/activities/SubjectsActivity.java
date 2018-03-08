package com.ibnkhaldoun.studentside.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.ibnkhaldoun.studentside.R;
import com.ibnkhaldoun.studentside.adapters.SubjectsAdapter;

public class SubjectsActivity extends AppCompatActivity {

    private RecyclerView mSubjectRecyclerView;
    private SubjectsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupRecyclerView();

    }

    private void setupRecyclerView() {
        mSubjectRecyclerView = findViewById(R.id.subject_recycler_view);
        mAdapter = new SubjectsAdapter();
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mSubjectRecyclerView.setAdapter(mAdapter);
        mSubjectRecyclerView.setLayoutManager(manager);
        mSubjectRecyclerView.setHasFixedSize(true);
    }

    //todo add a subject table in the database of course
    //todo add the layout item for this adapter
    //todo add the loader manager for loading the subject from the database
}
