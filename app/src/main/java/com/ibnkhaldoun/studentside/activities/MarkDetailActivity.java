package com.ibnkhaldoun.studentside.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.ibnkhaldoun.studentside.R;
import com.ibnkhaldoun.studentside.models.Mark;

public class MarkDetailActivity extends AppCompatActivity {

    public static final String KEY_MARK = "keyMark";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Mark mark = getIntent().getParcelableExtra(KEY_MARK);
        // TODO: 15/03/2018 add the code and the layout to handle the changes
    }

}
