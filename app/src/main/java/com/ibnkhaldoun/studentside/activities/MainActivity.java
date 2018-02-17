package com.ibnkhaldoun.studentside.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.ibnkhaldoun.studentside.R;
import com.ibnkhaldoun.studentside.adapters.TabLayoutAdapter;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private Toolbar mToolBar;

    private String[] mTitles = {"Home", "Notification", "Messages"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolBar = findViewById(R.id.toolbar);
        mToolBar.setTitle(mTitles[0]);
        setSupportActionBar(mToolBar);

        mTabLayout = findViewById(R.id.main_screen_tab_layout);
        mTabLayout.addTab(mTabLayout.newTab().setIcon(R.drawable.ic_home));
        mTabLayout.addTab(mTabLayout.newTab().setIcon(R.drawable.ic_notifications));
        mTabLayout.addTab(mTabLayout.newTab().setIcon(R.drawable.ic_email_white));

        mViewPager = findViewById(R.id.main_screen_pager);
        TabLayoutAdapter adapter = new TabLayoutAdapter(getSupportFragmentManager(), mTabLayout.getTabCount());
        mViewPager.setAdapter(adapter);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
                mToolBar.setTitle(mTitles[tab.getPosition()]);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_screen, menu);
        MenuItem search = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MainActivity.this, "Text Changing", Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Toast.makeText(MainActivity.this, "Text Submitted", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        return true;
    }
}
