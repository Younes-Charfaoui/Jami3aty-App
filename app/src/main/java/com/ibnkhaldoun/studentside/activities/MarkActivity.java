/*------------------------------------------------------------------------------
 - Copyright (c) 2018. This code was created by Younes Charfaoui in the process of Graduation Project for the year of  2018 , which is about creating a platform  for students and professors to help them in the communication and the get known of the university information and so on.
 -----------------------------------------------------------------------------*/

package com.ibnkhaldoun.studentside.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.ibnkhaldoun.studentside.R;
import com.ibnkhaldoun.studentside.Utilities.PreferencesManager;
import com.ibnkhaldoun.studentside.adapters.MarksAdapter;
import com.ibnkhaldoun.studentside.models.MarkItem;
import com.ibnkhaldoun.studentside.networking.models.RequestPackage;
import com.ibnkhaldoun.studentside.networking.utilities.NetworkUtilities;
import com.ibnkhaldoun.studentside.providers.EndPointsProvider;
import com.ibnkhaldoun.studentside.providers.KeyDataProvider;
import com.ibnkhaldoun.studentside.services.LoadDataService;

import java.util.ArrayList;

import static com.ibnkhaldoun.studentside.Utilities.PreferencesManager.STUDENT;

/**
 * @definition: this activity will show the student the marks they have in the
 * different subject in the university.
 */
public class MarkActivity extends AppCompatActivity {

    //the key for taking the information passed by an intent.
    public static final String KEY_MARKS = "keyMarks";

    //UI elements
    private RecyclerView mRecyclerView;
    private MarksAdapter mAdapter;
    private LinearLayout mEmptyLayout;
    private ProgressBar mLoadingProgressBar;

    //receiver to receive the data from the background service.
    private BroadcastReceiver mMarkReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ArrayList<MarkItem> list = intent.getParcelableArrayListExtra(KEY_MARKS);
            mLoadingProgressBar.setVisibility(View.GONE);
            if (list.size() != 0) {
                mAdapter.swapList(list);
                mRecyclerView.setVisibility(View.VISIBLE);
            } else {
                mEmptyLayout.setVisibility(View.VISIBLE);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //initializing the view
        mRecyclerView = findViewById(R.id.marks_recycler_view);
        mLoadingProgressBar = findViewById(R.id.mark_progress_bar);
        mEmptyLayout = findViewById(R.id.mark_empty_view);

        setupRecyclerView();

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mEmptyLayout.setOnClickListener(v ->
                getNewMarkIfThereIs()
        );

        //registering the receiver.
        LocalBroadcastManager.getInstance(this).registerReceiver(mMarkReceiver, new IntentFilter(LoadDataService.MARK_ACTION));

        getNewMarkIfThereIs();
    }

    private void setupRecyclerView() {
        mAdapter = new MarksAdapter(this);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_mark, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.menu_mark_refresh:
                getNewMarkIfThereIs();
                break;
        }
        return true;
    }


    //method to get the marks from background service.
    private void getNewMarkIfThereIs() {
        if (NetworkUtilities.isConnected(this)) {
            mLoadingProgressBar.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
            mEmptyLayout.setVisibility(View.GONE);
            PreferencesManager manager = new PreferencesManager(this, STUDENT);
            RequestPackage request = new RequestPackage.Builder()
                    .addEndPoint(EndPointsProvider.getMarksEndPoint())
                    .addMethod(RequestPackage.POST)
                    .addParams(KeyDataProvider.JSON_STUDENT_ID, manager.getIdUser())
                    .addParams(KeyDataProvider.KEY_AJAX, KeyDataProvider.KEY_ANDROID)
                    .create();

            Intent intent = new Intent(this, LoadDataService.class);
            intent.putExtra(LoadDataService.KEY_ACTION, LoadDataService.MARK_TYPE);
            intent.putExtra(LoadDataService.KEY_REQUEST, request);
            startService(intent);
        } else {
            Snackbar snackbar = Snackbar.make(findViewById(R.id.mark_coordinator),
                    R.string.no_internet_connection_string, Snackbar.LENGTH_LONG);
            snackbar.setAction(R.string.retry_string,
                    v -> getNewMarkIfThereIs());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //unregister the receiver to avoid memory leaks.
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMarkReceiver);
    }
}