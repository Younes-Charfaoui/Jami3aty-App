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
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.ibnkhaldoun.studentside.R;
import com.ibnkhaldoun.studentside.Utilities.PreferencesManager;
import com.ibnkhaldoun.studentside.adapters.SavedAdapter;
import com.ibnkhaldoun.studentside.database.DatabaseContract;
import com.ibnkhaldoun.studentside.models.Saved;
import com.ibnkhaldoun.studentside.networking.models.RequestPackage;
import com.ibnkhaldoun.studentside.networking.utilities.NetworkUtilities;
import com.ibnkhaldoun.studentside.providers.EndPointsProvider;
import com.ibnkhaldoun.studentside.services.LoadDataService;

import java.util.ArrayList;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.ibnkhaldoun.studentside.Utilities.PreferencesManager.STUDENT;
import static com.ibnkhaldoun.studentside.networking.models.RequestPackage.POST;
import static com.ibnkhaldoun.studentside.networking.models.Response.IO_EXCEPTION;
import static com.ibnkhaldoun.studentside.networking.models.Response.JSON_EXCEPTION;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.JSON_STUDENT_ID;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.KEY_ANDROID;
import static com.ibnkhaldoun.studentside.services.LoadDataService.ACTION_ERROR;
import static com.ibnkhaldoun.studentside.services.LoadDataService.KEY_ACTION;
import static com.ibnkhaldoun.studentside.services.LoadDataService.KEY_ERROR;
import static com.ibnkhaldoun.studentside.services.LoadDataService.KEY_REQUEST;
import static com.ibnkhaldoun.studentside.services.LoadDataService.SAVED_ACTION;
import static com.ibnkhaldoun.studentside.services.LoadDataService.SAVED_TYPE;

public class SavedActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String KEY_SAVED = "keySaved";
    private static final int ID_SAVED_LOADER = 727;
    private RecyclerView mRecyclerView;
    private LinearLayout mEmptyView;
    private SavedAdapter mAdapter;
    private ProgressBar mLoadingProgressBar;


    private BroadcastReceiver mSavedReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.hasExtra(KEY_SAVED)) {
                ArrayList<Saved> savedList = intent.getParcelableArrayListExtra(KEY_SAVED);
                mLoadingProgressBar.setVisibility(GONE);
                if (savedList != null) {
                    if (savedList.size() != 0) {
                        mRecyclerView.setVisibility(VISIBLE);
                        mAdapter.swapList(savedList);
                    } else {
                        getSupportLoaderManager()
                                .restartLoader(ID_SAVED_LOADER, null, SavedActivity.this)
                                .forceLoad();
                    }
                } else {
                    getSupportLoaderManager()
                            .restartLoader(ID_SAVED_LOADER, null, SavedActivity.this)
                            .forceLoad();
                }
            }
        }
    };

    private BroadcastReceiver mFailedReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int type = intent.getIntExtra(KEY_ERROR, -1);
            getSupportLoaderManager().restartLoader(ID_SAVED_LOADER, null, SavedActivity.this).forceLoad();
            switch (type) {
                case JSON_EXCEPTION:
                    Snackbar JsonSnackBar = Snackbar.make(findViewById(R.id.subject_main_view)
                            , R.string.error_json,
                            Snackbar.LENGTH_SHORT);
                    JsonSnackBar.setAction(R.string.retry_string, v -> {
                        if (JsonSnackBar.isShownOrQueued()) {
                            JsonSnackBar.dismiss();
                        }
                        getSavedFromTheInternet();
                    });
                    JsonSnackBar.show();
                    break;
                case IO_EXCEPTION:
                    Snackbar IOSnackBar = Snackbar.make(findViewById(R.id.subject_main_view)
                            , R.string.error_json,
                            Snackbar.LENGTH_SHORT);
                    IOSnackBar.setAction(R.string.retry_string, v -> {
                        if (IOSnackBar.isShownOrQueued()) {
                            IOSnackBar.dismiss();
                        }
                        getSavedFromTheInternet();
                    });
                    IOSnackBar.show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mEmptyView = findViewById(R.id.saved_empty_view);
        mRecyclerView = findViewById(R.id.saved_recycler_view);
        mLoadingProgressBar = findViewById(R.id.saved_progressbar);

        setupRecyclerView();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        getSupportLoaderManager().initLoader(ID_SAVED_LOADER, null, this);

        LocalBroadcastManager.getInstance(this).registerReceiver(mSavedReceiver,
                new IntentFilter(SAVED_ACTION));
        LocalBroadcastManager.getInstance(this).registerReceiver(mFailedReceiver,
                new IntentFilter(ACTION_ERROR));
    }

    private void setupRecyclerView() {
        mAdapter = new SavedAdapter(this);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setHasFixedSize(true);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {"*"};
        switch (id) {
            case ID_SAVED_LOADER:
                return new CursorLoader(this,
                        DatabaseContract.SavedEntry.CONTENT_SAVED_URI,
                        projection, null, null, null);
            default:
                throw new IllegalStateException();
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mLoadingProgressBar.setVisibility(GONE);
        if (data.getCount() == 0) {
            mEmptyView.setVisibility(VISIBLE);
        } else {
            mRecyclerView.setVisibility(VISIBLE);
            mAdapter.swapCursor(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
        mRecyclerView.setVisibility(GONE);
        mEmptyView.setVisibility(VISIBLE);
        mLoadingProgressBar.setVisibility(GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_saved, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.saved_refresh:
                getSavedFromTheInternet();
                break;
            case android.R.id.home:
                onBackPressed();
                finish();
                break;
        }
        return true;
    }

    public void getSavedFromTheInternet() {
        if (NetworkUtilities.isConnected(this)) {
            mLoadingProgressBar.setVisibility(VISIBLE);
            mEmptyView.setVisibility(GONE);
            mRecyclerView.setVisibility(GONE);
            RequestPackage request = new RequestPackage();
            request.setEndPoint(EndPointsProvider.getSavedEndPoint());
            request.setMethod(POST);
            request.addParams(KEY_ANDROID, KEY_ANDROID);
            request.addParams(JSON_STUDENT_ID, new PreferencesManager(this, STUDENT).getId());
            Intent serviceIntent = new Intent(this, LoadDataService.class);
            serviceIntent.putExtra(KEY_REQUEST, request);
            serviceIntent.putExtra(KEY_ACTION, SAVED_TYPE);
            startService(serviceIntent);
        } else {
            Snackbar networkSnackBar = Snackbar.make(findViewById(R.id.subject_main_view)
                    , R.string.no_internet_connection_string,
                    Snackbar.LENGTH_SHORT);
            networkSnackBar.setAction(R.string.retry_string, v -> {
                if (networkSnackBar.isShownOrQueued()) {
                    networkSnackBar.dismiss();
                }
                getSavedFromTheInternet();
            });
            networkSnackBar.show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mSavedReceiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mFailedReceiver);
    }

}