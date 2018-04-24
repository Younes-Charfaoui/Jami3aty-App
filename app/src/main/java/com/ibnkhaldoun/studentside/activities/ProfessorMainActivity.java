package com.ibnkhaldoun.studentside.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.ibnkhaldoun.studentside.R;

public class ProfessorMainActivity extends AppCompatActivity {

    private FloatingActionButton mAddPostFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAddPostFab = findViewById(R.id.fab);

    }

}
