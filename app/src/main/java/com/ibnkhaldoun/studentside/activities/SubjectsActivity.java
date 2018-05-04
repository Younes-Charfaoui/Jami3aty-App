/*------------------------------------------------------------------------------
 - Copyright (c) 2018. This code was created by Younes Charfaoui in the process of Graduation Project for the year of  2018 , which is about creating a platform  for students and professors to help them in the communication and the get known of the university information and so on.
 -----------------------------------------------------------------------------*/

package com.ibnkhaldoun.studentside.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
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
import android.widget.Toast;

import com.ibnkhaldoun.studentside.R;
import com.ibnkhaldoun.studentside.Utilities.PreferencesManager;
import com.ibnkhaldoun.studentside.adapters.SubjectsAdapter;
import com.ibnkhaldoun.studentside.database.DatabaseContract;
import com.ibnkhaldoun.studentside.models.Subject;
import com.ibnkhaldoun.studentside.networking.models.RequestPackage;
import com.ibnkhaldoun.studentside.networking.utilities.NetworkUtilities;
import com.ibnkhaldoun.studentside.providers.EndPointsProvider;
import com.ibnkhaldoun.studentside.services.DatabaseService;
import com.ibnkhaldoun.studentside.services.LoadDataService;

import java.util.ArrayList;

import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.JSON_STUDENT_GROUP;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.JSON_STUDENT_LEVEL;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.JSON_STUDENT_SECTION;

/**
 * @definition this activity will handle the user subject and it's
 * interaction with the list and it's implementation.
 */
public class SubjectsActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {

    //constant to extract the content of the intent.
    public static final String KEY_SUBJECTS = "keySubjects";

    //id for the loader.
    private static final int SUBJECT_LOADER_ID = 1292;

    // the basic the UI views of the Activity.
    private RecyclerView mSubjectRecyclerView;
    private SubjectsAdapter mAdapter;
    private LinearLayout mEmptyLayout;
    private ProgressBar mProgressBar;

    /**
     * this broadcast receiver will Receive the subject after they had been downloaded
     * by the {@link LoadDataService} Intent service , which will broadcast this subject ,
     * and pass it to the {@link DatabaseService} which will put this subject's in the database.
     */
    private BroadcastReceiver mSubjectReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ArrayList<Subject> subjectList = intent.getParcelableArrayListExtra(KEY_SUBJECTS);
            //if it was null then the response was not correct.
            if (subjectList != null) {
                mProgressBar.setVisibility(View.GONE);
                if (subjectList.size() != 0) {
                    mSubjectRecyclerView.setVisibility(View.VISIBLE);
                    mAdapter.swapList(subjectList);
                } else {
                    mEmptyLayout.setVisibility(View.VISIBLE);
                }
            } else {
                mProgressBar.setVisibility(View.GONE);
                mEmptyLayout.setVisibility(View.VISIBLE);
                Toast.makeText(context, R.string.error_io_exception, Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // getting reference to the views.
        mEmptyLayout = findViewById(R.id.subject_empty_view);
        mProgressBar = findViewById(R.id.subject_progress_bar);

        setupRecyclerView();

        getSupportLoaderManager().initLoader(SUBJECT_LOADER_ID, null, this).forceLoad();


        mEmptyLayout.setOnClickListener(v -> getSubjectFromService());

        //the local broadcast Manager to register our BroadcastReceiver.
        LocalBroadcastManager.getInstance(this).registerReceiver(mSubjectReceiver,
                new IntentFilter(LoadDataService.SUBJECT_ACTION));

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_subject, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.subject_refresh:
                getSubjectFromService();
                break;
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //the local broadcast Manager to unregister our BroadcastReceiver.
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mSubjectReceiver);
    }

    /**
     * this helper addMethod will try to get the subject from the internet
     * if ther is connection of course.
     */
    private void getSubjectFromService() {
        if (NetworkUtilities.isConnected(this)) {
            mEmptyLayout.setVisibility(View.GONE);
            mSubjectRecyclerView.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.VISIBLE);
            Intent intent = new Intent(this, LoadDataService.class);
            PreferencesManager manager = new PreferencesManager(this, PreferencesManager.STUDENT);
            RequestPackage request = new RequestPackage.Builder()
                    .addMethod(RequestPackage.POST)
                    .addEndPoint(EndPointsProvider.getSubjectAllEndpoint())
                    .addParams(JSON_STUDENT_SECTION, manager.getSectionStudent())
                    .addParams(JSON_STUDENT_LEVEL, manager.getLevelStudent())
                    .addParams(JSON_STUDENT_GROUP, manager.getGroupStudent())
                    .create();

            intent.putExtra(LoadDataService.KEY_REQUEST, request);
            intent.putExtra(LoadDataService.KEY_ACTION, LoadDataService.SUBJECT_TYPE);
            startService(intent);
        } else {
            Snackbar networkSnackBar = Snackbar.make(findViewById(R.id.subject_main_view)
                    , R.string.no_internet_connection_string,
                    Snackbar.LENGTH_SHORT);
            networkSnackBar.setAction(R.string.retry_string, v -> {
                if (networkSnackBar.isShownOrQueued()) {
                    networkSnackBar.dismiss();
                }
                getSubjectFromService();
            });
            networkSnackBar.show();
        }
    }

    /**
     * helper addMethod to initialize the recycler view and it components.
     */
    private void setupRecyclerView() {
        mSubjectRecyclerView = findViewById(R.id.subject_recycler_view);
        mAdapter = new SubjectsAdapter(this);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mSubjectRecyclerView.setAdapter(mAdapter);
        mSubjectRecyclerView.setLayoutManager(manager);
        mSubjectRecyclerView.setHasFixedSize(true);
    }

    /**
     * the famous addMethod that will initialize the loader and create it.
     *
     * @param id   this is the ID of the loader.
     * @param args this is the bundle that have been passed throw the
     *             initialization addMethod.
     * @return
     */
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        mProgressBar.setVisibility(View.VISIBLE);
        mEmptyLayout.setVisibility(View.GONE);
        mSubjectRecyclerView.setVisibility(View.GONE);
        return new CursorLoader(
                this,
                DatabaseContract.SubjectEntry.CONTENT_SUBJECT_URI,
                new String[]{"*"}, null, null, null);
    }

    /**
     * this callback is called when the loader finish working.
     *
     * @param loader
     */
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mProgressBar.setVisibility(View.GONE);
        if (data.getCount() != 0) {
            mSubjectRecyclerView.setVisibility(View.VISIBLE);
            mAdapter.swapCursor(data);
        } else {
            mEmptyLayout.setVisibility(View.VISIBLE);
        }
    }

    /**
     * this callback is called when the loader reset.
     *
     * @param loader
     */
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mProgressBar.setVisibility(View.VISIBLE);
        mEmptyLayout.setVisibility(View.GONE);
        mSubjectRecyclerView.setVisibility(View.GONE);
        mAdapter.swapCursor(null);
    }
}