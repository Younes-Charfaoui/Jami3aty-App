package com.ibnkhaldoun.studentside.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.ibnkhaldoun.studentside.R;
import com.ibnkhaldoun.studentside.Utilities.PreferencesManager;
import com.ibnkhaldoun.studentside.adapters.SchedulePagerAdapter;
import com.ibnkhaldoun.studentside.database.DatabaseContract;
import com.ibnkhaldoun.studentside.enums.Day;
import com.ibnkhaldoun.studentside.fragments.DayMondayFragment;
import com.ibnkhaldoun.studentside.fragments.DaySundayFragment;
import com.ibnkhaldoun.studentside.fragments.DayThursdayFragment;
import com.ibnkhaldoun.studentside.fragments.DayTuesdayFragment;
import com.ibnkhaldoun.studentside.fragments.DayWednesdayFragment;
import com.ibnkhaldoun.studentside.interfaces.ScheduleCallbacks;
import com.ibnkhaldoun.studentside.models.Schedule;
import com.ibnkhaldoun.studentside.models.ScheduleItem;
import com.ibnkhaldoun.studentside.networking.models.RequestPackage;
import com.ibnkhaldoun.studentside.networking.utilities.JsonUtilities;
import com.ibnkhaldoun.studentside.networking.utilities.NetworkUtilities;
import com.ibnkhaldoun.studentside.providers.EndPointsProvider;
import com.ibnkhaldoun.studentside.services.LoadDataService;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Calendar;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.ibnkhaldoun.studentside.Utilities.PreferencesManager.STUDENT;
import static com.ibnkhaldoun.studentside.database.DatabaseContract.ScheduleEntry.COLUMN_DAY;
import static com.ibnkhaldoun.studentside.database.DatabaseContract.ScheduleEntry.COLUMN_GROUP;
import static com.ibnkhaldoun.studentside.database.DatabaseContract.ScheduleEntry.COLUMN_HOUR;
import static com.ibnkhaldoun.studentside.database.DatabaseContract.ScheduleEntry.COLUMN_LEVEL;
import static com.ibnkhaldoun.studentside.database.DatabaseContract.ScheduleEntry.COLUMN_PLACE;
import static com.ibnkhaldoun.studentside.database.DatabaseContract.ScheduleEntry.COLUMN_PROFESSOR;
import static com.ibnkhaldoun.studentside.database.DatabaseContract.ScheduleEntry.COLUMN_SECTION;
import static com.ibnkhaldoun.studentside.database.DatabaseContract.ScheduleEntry.COLUMN_SUBJECT;
import static com.ibnkhaldoun.studentside.database.DatabaseContract.ScheduleEntry.COLUMN_TYPE;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.JSON_STUDENT_GROUP;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.JSON_STUDENT_LEVEL;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.JSON_STUDENT_SECTION;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.KEY_ANDROID;

/**
 * @definition Schedule Activity is the activity responsible for displaying
 * the schedule to the user in a way that the every day is in a separate fragments,
 * that means each day in it's fragment ,which
 * has the methods and the logic for displaying the schedules item in a simple and
 * a friendly user way.
 * <p>
 * these fragments are managed by the view pager and the tab layout views to handle
 * user interactions and some callbacks and method are present in both sides to
 * communicate between this activity , fragments ,tab layout and the view pager.
 * <p>
 * this activity implements the loader manager call backs to load data from the database
 * and also the schedules callback to get reference to the fragments of each day
 * to pass data to it.
 */

public class ScheduleActivity extends AppCompatActivity
        implements ScheduleCallbacks, LoaderManager.LoaderCallbacks<Cursor> {

    //key to get inn incoming data from the service
    public static final String KEY_SCHEDULE = "keySchedule";

    private static final int ID_SCHEDULE_LOADER = 891;
    private static final int EXAM_TYPE = 16;
    private static final int SCHEDULE_TYPE = 17;

    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ProgressBar mLoadingProgressBar;

    //the fragments to use in each day
    private DaySundayFragment mSundayFragment;
    private DayMondayFragment mMondayFragment;
    private DayTuesdayFragment mTuesdayFragment;
    private DayWednesdayFragment mWednesdayFragment;
    private DayThursdayFragment mThursdayFragment;

    //the data provider which gonna hold the days and it correspond schedule.
    private SparseArray<Schedule> mCurrentSchedule;

    //the receiver from the service which will load the data from the internet
    private BroadcastReceiver mScheduleReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mTabLayout.setVisibility(VISIBLE);
            mViewPager.setVisibility(VISIBLE);
            mLoadingProgressBar.setVisibility(GONE);
            String response = intent.getStringExtra(KEY_SCHEDULE);

            try {
                mCurrentSchedule = JsonUtilities.getSchedulesList(response);
                for (int i = 0; i < mCurrentSchedule.size(); i++) {
                    int day = mCurrentSchedule.keyAt(i);
                    switchFragments(day, mCurrentSchedule);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    //receiver will get notified if an error will occur in the service.
    private BroadcastReceiver mFailReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            getSupportLoaderManager().restartLoader(ID_SCHEDULE_LOADER, null, ScheduleActivity.this).forceLoad();

            Snackbar IOSnackBar = Snackbar.make(findViewById(R.id.subject_main_view)
                    , R.string.error_json,
                    Snackbar.LENGTH_SHORT);
            IOSnackBar.setAction(R.string.retry_string, v -> {
                if (IOSnackBar.isShownOrQueued()) {
                    IOSnackBar.dismiss();
                }
                getScheduleFromService(SCHEDULE_TYPE);
            });
            IOSnackBar.show();
        }
    };

    /**
     * the famous method on create will be responsible for th initialization work
     * and
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        mToolbar = findViewById(R.id.schedule_toolbar);
        setSupportActionBar(mToolbar);
        mTabLayout = findViewById(R.id.schedule_tab_layout);
        mViewPager = findViewById(R.id.schedule_view_pager);
        mLoadingProgressBar = findViewById(R.id.schedule_progress_bar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setupViewPagerAndTabLayout();

        LocalBroadcastManager.getInstance(this).registerReceiver(mScheduleReceiver,
                new IntentFilter(LoadDataService.SCHEDULE_ACTION));
        LocalBroadcastManager.getInstance(this).registerReceiver(mFailReceiver,
                new IntentFilter(LoadDataService.ACTION_ERROR));
        getSupportLoaderManager().initLoader(ID_SCHEDULE_LOADER, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_schedule, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.menu_schedule_exam_schedule:
                getScheduleFromService(EXAM_TYPE);
                break;
            case R.id.menu_schedule_refresh:
                getScheduleFromService(SCHEDULE_TYPE);
                break;
        }
        return true;
    }

    private void getScheduleFromService(int type) {
        if (NetworkUtilities.isConnected(this)) {
            mViewPager.setVisibility(GONE);
            mTabLayout.setVisibility(GONE);
            mLoadingProgressBar.setVisibility(VISIBLE);
            RequestPackage request = new RequestPackage();
            if (type == SCHEDULE_TYPE)
                request.setEndPoint(EndPointsProvider.getScheduleAllEndpoint());
            else request.setEndPoint(EndPointsProvider.getExamScheduleEndpoint());
            request.setMethod(RequestPackage.POST);
            PreferencesManager manager = new PreferencesManager(this, STUDENT);
            request.addParams(KEY_ANDROID, KEY_ANDROID);
            request.addParams(JSON_STUDENT_GROUP, manager.getGroup());
            request.addParams(JSON_STUDENT_LEVEL, manager.getLevel());
            request.addParams(JSON_STUDENT_SECTION, manager.getSection());
            Intent intentService = new Intent(this, LoadDataService.class);
            intentService.putExtra(LoadDataService.KEY_REQUEST, request);
            intentService.putExtra(LoadDataService.KEY_ACTION, LoadDataService.SCHEDULE_TYPE);
            startService(intentService);
        } else {
            Snackbar networkSnackBar = Snackbar.make(findViewById(R.id.schedule_main_view)
                    , R.string.no_internet_connection_string,
                    Snackbar.LENGTH_SHORT);
            networkSnackBar.setAction(R.string.retry_string, v -> {
                if (networkSnackBar.isShownOrQueued()) {
                    networkSnackBar.dismiss();
                }
                getScheduleFromService(type);
            });
            networkSnackBar.show();
        }
    }

    private void setupDaysTabLayout() {
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.sunday_string));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.monday_string));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.tuesday_string));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.wednesday_string));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.thursday_string));
    }

    private void setupViewPagerAndTabLayout() {
        setupDaysTabLayout();
        SchedulePagerAdapter adapter = new SchedulePagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());

                switch (tab.getPosition()) {
                    case 0:
                        mToolbar.setTitle(R.string.sunday);
                        if (getSupportActionBar() != null)
                            getSupportActionBar().setTitle(R.string.sunday);
                        break;
                    case 1:
                        mToolbar.setTitle(R.string.monday);
                        if (getSupportActionBar() != null)
                            getSupportActionBar().setTitle(R.string.monday);
                        break;
                    case 2:
                        mToolbar.setTitle(R.string.tuesday);
                        if (getSupportActionBar() != null)
                            getSupportActionBar().setTitle(R.string.tuesday);
                        break;
                    case 3:
                        mToolbar.setTitle(R.string.wednesday);
                        if (getSupportActionBar() != null)
                            getSupportActionBar().setTitle(R.string.wednesday);
                        break;
                    case 4:
                        mToolbar.setTitle(R.string.thursday);
                        if (getSupportActionBar() != null)
                            getSupportActionBar().setTitle(R.string.thursday);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        Log.i("Schedule", "setupViewPagerAndTabLayout: " + day);
        switch (day) {
            case Calendar.SUNDAY:
                Log.i("Schedule", "setupViewPagerAndTabLayout: it was sunday " );
                mViewPager.setCurrentItem(0);
                break;
            case Calendar.MONDAY:
                Log.i("Schedule", "setupViewPagerAndTabLayout: it was monday " );
                mViewPager.setCurrentItem(1);
                break;
            case Calendar.TUESDAY:
                Log.i("Schedule", "setupViewPagerAndTabLayout: it was tuesday " );
                mViewPager.setCurrentItem(2);
                break;
            case Calendar.WEDNESDAY:
                Log.i("Schedule", "setupViewPagerAndTabLayout: it was wednesday " );
                mViewPager.setCurrentItem(3);
                break;
            case Calendar.THURSDAY:
                Log.i("Schedule", "setupViewPagerAndTabLayout: it was thursday " );
                mViewPager.setCurrentItem(4);
                break;
        }


    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case ID_SCHEDULE_LOADER:
                mLoadingProgressBar.setVisibility(VISIBLE);
                mViewPager.setVisibility(GONE);
                mTabLayout.setVisibility(GONE);
                PreferencesManager manager = new PreferencesManager(this, STUDENT);
                //building the uri with content://<Authority>/schedules/level/section/group
                Uri pathToSchedule = DatabaseContract.ScheduleEntry.CONTENT_SCHEDULE_URI.buildUpon()
                        .appendEncodedPath(manager.getLevel())
                        .appendEncodedPath(manager.getSection())
                        .appendEncodedPath(manager.getGroup())
                        .build();
                Log.i("Schedule", "onCreateLoader: " + pathToSchedule.toString());
                return new CursorLoader(this,
                        pathToSchedule,
                        new String[]{"*"}, null, null, null);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        sendSchedulesToFragments(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mLoadingProgressBar.setVisibility(VISIBLE);
        mViewPager.setVisibility(GONE);
        mTabLayout.setVisibility(GONE);
    }

    private void sendSchedulesToFragments(Cursor data) {
        if (data.getCount() != 0) {
            SparseArray<Schedule> schedules = new SparseArray<>();
            Log.i("Schedule", "sendSchedulesToFragments: the count of cursor was "
                    + data.getCount());
            while (data.moveToNext()) {
                int day = data.getInt(data.getColumnIndex(COLUMN_DAY));
                int level = data.getInt(data.getColumnIndex(COLUMN_LEVEL));
                int section = data.getInt(data.getColumnIndex(COLUMN_SECTION));
                int group = data.getInt(data.getColumnIndex(COLUMN_GROUP));
                Schedule schedule = schedules.get(day, null) != null ? schedules.get(day)
                        : new Schedule(day, level, section, group);
                int time = data.getInt(data.getColumnIndex(COLUMN_HOUR));
                String place = data.getString(data.getColumnIndex(COLUMN_PLACE));
                String subject = data.getString(data.getColumnIndex(COLUMN_SUBJECT));
                String professor = data.getString(data.getColumnIndex(COLUMN_PROFESSOR));
                int type = data.getInt(data.getColumnIndex(COLUMN_TYPE));
                ScheduleItem item = new ScheduleItem(time, place, subject, professor, type);
                schedule.getScheduleItemList().add(item);
                schedules.put(day, schedule);
                mCurrentSchedule = schedules;
            }

            for (int i = 0; i < schedules.size(); i++) {
                int day = schedules.keyAt(i);
                switchFragments(day, schedules);
            }
        }else {
            Log.i("Schedule", "sendSchedulesToFragments: the count of cursor was 0");
        }
        mLoadingProgressBar.setVisibility(GONE);
        mViewPager.setVisibility(VISIBLE);
        mTabLayout.setVisibility(VISIBLE);
    }

    private void switchFragments(int day, SparseArray<Schedule> schedule) {
        ArrayList<ScheduleItem> scheduleItems = (ArrayList<ScheduleItem>) schedule.get(day).getScheduleItemList();
        switch (day) {
            case Day.SUNDAY:
                if (mSundayFragment != null)
                    mSundayFragment.setScheduleList(scheduleItems);
                break;
            case Day.MONDAY:
                if (mMondayFragment != null)
                    mMondayFragment.setScheduleList(scheduleItems);
                break;
            case Day.TUESDAY:
                if (mTuesdayFragment != null)
                    mTuesdayFragment.setScheduleList(scheduleItems);
                break;
            case Day.WEDNESDAY:
                if (mWednesdayFragment != null)
                    mWednesdayFragment.setScheduleList(scheduleItems);
                break;
            case Day.THURSDAY:
                if (mThursdayFragment != null)
                    mThursdayFragment.setScheduleList(scheduleItems);
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mFailReceiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mScheduleReceiver);
    }

    @Override
    public void onAttach(DaySundayFragment fragment) {
        mSundayFragment = fragment;
        if (mCurrentSchedule != null && mCurrentSchedule.get(Day.SUNDAY, null) != null)
            mSundayFragment.setScheduleList(mCurrentSchedule.get(Day.SUNDAY).getScheduleItemList());
    }

    @Override
    public void onAttach(DayMondayFragment fragment) {
        mMondayFragment = fragment;
        if (mCurrentSchedule != null && mCurrentSchedule.get(Day.MONDAY, null) != null)
            mMondayFragment.setScheduleList(mCurrentSchedule.get(Day.MONDAY).getScheduleItemList());
    }

    @Override
    public void onAttach(DayTuesdayFragment fragment) {
        mTuesdayFragment = fragment;
        if (mCurrentSchedule != null && mCurrentSchedule.get(Day.TUESDAY, null) != null)
            mTuesdayFragment.setScheduleList(mCurrentSchedule.get(Day.TUESDAY).getScheduleItemList());
    }

    @Override
    public void onAttach(DayWednesdayFragment fragment) {
        mWednesdayFragment = fragment;
        if (mCurrentSchedule != null && mCurrentSchedule.get(Day.WEDNESDAY, null) != null)
            mWednesdayFragment.setScheduleList(mCurrentSchedule.get(Day.WEDNESDAY).getScheduleItemList());
    }

    @Override
    public void onAttach(DayThursdayFragment fragment) {
        mThursdayFragment = fragment;

        if (mCurrentSchedule != null && mCurrentSchedule.get(Day.THURSDAY, null) != null)
            mThursdayFragment.setScheduleList(mCurrentSchedule.get(Day.THURSDAY).getScheduleItemList());
    }
}