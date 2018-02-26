package com.ibnkhaldoun.studentside.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.ibnkhaldoun.studentside.R;
import com.ibnkhaldoun.studentside.adapters.SchedulePagerAdapter;

public class ScheduleActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        mToolbar = findViewById(R.id.schedule_toolbar);
        setSupportActionBar(mToolbar);
        mTabLayout = findViewById(R.id.schedule_tab_layout);
        mViewPager = findViewById(R.id.schedule_view_pager);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setupViewPagerAndTabLayout();
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
            case R.id.schedule_choose_level:
                Toast.makeText(this, "Choose", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
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
        SchedulePagerAdapter adapter = new SchedulePagerAdapter(getSupportFragmentManager(), mTabLayout.getTabCount());
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
    }
}
