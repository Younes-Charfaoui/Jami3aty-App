/*
 * Copyright (c) 2018. This code was created by Younes Charfaoui in the process of Graduation
 * Project for the year of  2018 , which is about creating a platform  for students and professors
 * to help them in the communication and the get known of the university information and so on.
 */

package com.ibnkhaldoun.studentside.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import com.ibnkhaldoun.studentside.R;
import com.ibnkhaldoun.studentside.models.ProfessorInfo;
import com.ibnkhaldoun.studentside.services.LoadDataService;

import java.util.ArrayList;

public class AddPostActivity extends AppCompatActivity {

    private ArrayList<ProfessorInfo> mProfessorInformation;
    private ScrollView mScroll;
    private ProgressBar mLoadingBar;

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("Tag", "onReceive: i have a signal");
            mProfessorInformation = intent.getParcelableArrayListExtra(LoadDataService.KEY_DATA);
            mLoadingBar.setVisibility(View.GONE);
            if (mProfessorInformation.size() != 0){
                mScroll.setVisibility(View.VISIBLE);
                Log.i("Tag", "onReceive: the list is not empty");
            }
            else {
                Log.i("Tag", "onReceive: the list is empty");
                Toast.makeText(context, "You can't post anything for the moment, try later.", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mScroll = findViewById(R.id.add_post_scroll);
        mLoadingBar = findViewById(R.id.add_post_progress);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver,
                new IntentFilter(LoadDataService.PROFESSOR_INFO_ACTION));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_post, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.add_post_action:
                // TODO: 28/04/2018 send post to server
                Toast.makeText(this, "Add Post", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }
}
