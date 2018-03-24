package com.ibnkhaldoun.studentside.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.ibnkhaldoun.studentside.R;
import com.ibnkhaldoun.studentside.adapters.SchedulePagerAdapter;
import com.ibnkhaldoun.studentside.database.DatabaseContract;
import com.ibnkhaldoun.studentside.enums.Day;
import com.ibnkhaldoun.studentside.fragments.DayScheduleFragment;
import com.ibnkhaldoun.studentside.interfaces.ScheduleCallbacks;
import com.ibnkhaldoun.studentside.models.Schedule;
import com.ibnkhaldoun.studentside.networking.utilities.NetworkUtilities;

import java.util.ArrayList;
import java.util.Calendar;

public class ScheduleActivity extends AppCompatActivity
        implements ScheduleCallbacks, LoaderManager.LoaderCallbacks<Cursor> {

    public static final String KEY_SCHEDULE = "keySchedule";
    private static final int ID_SCHEDULE_LOADER = 891;

    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private DayScheduleFragment mSundayFragment, mMondayFragment,
            mTuesdayFragment, mWednesdayFragment, mThursdayFragment;

    private BroadcastReceiver mScheduleReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ArrayList<Schedule> schedules = intent.getParcelableArrayListExtra(KEY_SCHEDULE);
            for (Schedule schedule : schedules) {
                switch (schedule.getDayOfSchedule()) {
                    case Day.SUNDAY:
                        mSundayFragment.setScheduleList(schedule.getScheduleItemList());
                        break;
                    case Day.MONDAY:
                        mMondayFragment.setScheduleList(schedule.getScheduleItemList());
                        break;
                    case Day.TUESDAY:
                        mTuesdayFragment.setScheduleList(schedule.getScheduleItemList());
                        break;
                    case Day.WEDNESDAY:
                        mWednesdayFragment.setScheduleList(schedule.getScheduleItemList());
                        break;
                    case Day.THURSDAY:
                        mThursdayFragment.setScheduleList(schedule.getScheduleItemList());
                        break;
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        mToolbar = findViewById(R.id.schedule_toolbar);
        setSupportActionBar(mToolbar);
        mTabLayout = findViewById(R.id.schedule_tab_layout);
        mViewPager = findViewById(R.id.schedule_view_pager);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setupViewPagerAndTabLayout();

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
                Toast.makeText(this, "Exam", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_schedule_refresh:
                getScheduleFromService();
                break;
        }
        return true;
    }

    private void getScheduleFromService() {
        if (NetworkUtilities.isConnected(this)) {

        } else {
            Snackbar networkSnackBar = Snackbar.make(findViewById(R.id.schedule_main_view)
                    , R.string.no_internet_connection_string,
                    Snackbar.LENGTH_SHORT);
            networkSnackBar.setAction(R.string.retry_string, v -> {
                if (networkSnackBar.isShownOrQueued()) {
                    networkSnackBar.dismiss();
                }
                getScheduleFromService();
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
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        switch (day) {
            case Calendar.SUNDAY:
                mViewPager.setCurrentItem(0);
                break;
            case Calendar.MONDAY:
                mViewPager.setCurrentItem(1);
                break;
            case Calendar.TUESDAY:
                mViewPager.setCurrentItem(2);
                break;
            case Calendar.WEDNESDAY:
                mViewPager.setCurrentItem(3);
                break;
            case Calendar.THURSDAY:
                mViewPager.setCurrentItem(4);
                break;
        }
    }

    @Override
    public void onAttach(DayScheduleFragment fragment, int day) {
        switch (day) {
            case Day.SUNDAY:
                mSundayFragment = fragment;
                break;
            case Day.MONDAY:
                mMondayFragment = fragment;
                break;
            case Day.TUESDAY:
                mTuesdayFragment = fragment;
                break;
            case Day.WEDNESDAY:
                mWednesdayFragment = fragment;
                break;
            case Day.THURSDAY:
                mThursdayFragment = fragment;
                break;
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case ID_SCHEDULE_LOADER:
                return new CursorLoader(this,
                        DatabaseContract.ScheduleEntry.CONTENT_SCHEDULE_URI,
                        new String[]{"*"}, null, null, null);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // TODO: 24/03/2018 add code to handle the data cursor for schedule
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
