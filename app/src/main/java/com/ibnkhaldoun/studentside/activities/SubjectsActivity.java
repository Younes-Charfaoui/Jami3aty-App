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
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.ibnkhaldoun.studentside.R;
import com.ibnkhaldoun.studentside.Utilities.PreferencesManager;
import com.ibnkhaldoun.studentside.adapters.SubjectsAdapter;
import com.ibnkhaldoun.studentside.database.DatabaseContract;
import com.ibnkhaldoun.studentside.networking.models.RequestPackage;
import com.ibnkhaldoun.studentside.networking.utilities.NetworkUtilities;
import com.ibnkhaldoun.studentside.providers.EndPointsProvider;
import com.ibnkhaldoun.studentside.services.LoadDataService;

import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.JSON_STUDENT_ID;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.KEY_ANDROID;

public class SubjectsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int SUBJECT_LOADER_ID = 1292;
    private RecyclerView mSubjectRecyclerView;
    private SubjectsAdapter mAdapter;
    private LinearLayout mEmptyView;
    private ProgressBar mProgressBar;

    private BroadcastReceiver mSubjectReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            getSupportLoaderManager()
                    .restartLoader(SUBJECT_LOADER_ID, null, SubjectsActivity.this)
                    .forceLoad();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mEmptyView = findViewById(R.id.subject_empty_view);
        mProgressBar = findViewById(R.id.subject_progress_bar);

        setupRecyclerView();

        getSupportLoaderManager().initLoader(SUBJECT_LOADER_ID, null, this).forceLoad();
        assert getSupportActionBar() != null;
        getSupportActionBar().setHomeButtonEnabled(true);

        mEmptyView.setOnClickListener(v -> getSubjectFromService());

        LocalBroadcastManager.getInstance(this).registerReceiver(mSubjectReceiver, new IntentFilter(LoadDataService.SUBJECT_ACTION));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mSubjectReceiver);
    }

    private void getSubjectFromService() {
        if (NetworkUtilities.isConnected(this)) {
            mEmptyView.setVisibility(View.GONE);
            mSubjectRecyclerView.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.VISIBLE);
            Intent intent = new Intent(this, LoadDataService.class);
            RequestPackage request = new RequestPackage();
            request.setMethod(RequestPackage.POST);
            request.setEndPoint(EndPointsProvider.getSubjectEndpoint());
            request.addParams(KEY_ANDROID, KEY_ANDROID);
            PreferencesManager manager = new PreferencesManager(this);
            request.addParams(JSON_STUDENT_ID, manager.getId());
            intent.putExtra(LoadDataService.KEY_REQUEST, request);
            intent.putExtra(LoadDataService.KEY_ACTION, LoadDataService.SUBJECT_TYPE);
            startService(intent);
        } else {
            Snackbar networkSnackBar = Snackbar.make(findViewById(R.id.subject_main_view)
                    , "No Network Available",
                    Snackbar.LENGTH_SHORT);
            networkSnackBar.setAction("Retry", v -> {
                if (networkSnackBar.isShownOrQueued()) {
                    networkSnackBar.dismiss();
                }
                getSubjectFromService();
            });
            networkSnackBar.show();
        }
    }

    private void setupRecyclerView() {
        mSubjectRecyclerView = findViewById(R.id.subject_recycler_view);
        mAdapter = new SubjectsAdapter(this);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mSubjectRecyclerView.setAdapter(mAdapter);
        mSubjectRecyclerView.setLayoutManager(manager);
        mSubjectRecyclerView.setHasFixedSize(true);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        return new CursorLoader(
                this,
                DatabaseContract.SubjectEntry.CONTENT_SUBJECT_URI,
                new String[]{"*"}, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mProgressBar.setVisibility(View.GONE);
        if (data.getCount() != 0) {
            mSubjectRecyclerView.setVisibility(View.VISIBLE);
            mAdapter.swapCursor(data);
        } else {
            mEmptyView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mProgressBar.setVisibility(View.VISIBLE);
        mEmptyView.setVisibility(View.GONE);
        mSubjectRecyclerView.setVisibility(View.GONE);
        mAdapter.swapCursor(null);
    }
}
