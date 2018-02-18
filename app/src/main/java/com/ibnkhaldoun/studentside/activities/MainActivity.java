package com.ibnkhaldoun.studentside.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.ibnkhaldoun.studentside.R;
import com.ibnkhaldoun.studentside.adapters.TabLayoutAdapter;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private Toolbar mToolBar;
    private DrawerLayout mDrawer;

    private String[] mTitles = getResources().getStringArray(R.array.main_screen_titles);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen_drawer);


        mToolBar = findViewById(R.id.toolbar);
        mToolBar.setTitle(mTitles[0]);
        setSupportActionBar(mToolBar);


        setupNavigationDrawer();

        setupTabIcons();

        setupViewPagerAndTabLayout();
    }

    private void setupViewPagerAndTabLayout() {
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

    private void setupNavigationDrawer() {
        mDrawer = findViewById(R.id.drawer_main_screen_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolBar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_main_screen_view);
        navigationView.setCheckedItem(R.id.nav_home);
        navigationView.setNavigationItemSelectedListener(this);
        mTabLayout = findViewById(R.id.main_screen_tab_layout);
    }

    private void setupTabIcons() {
        mTabLayout.addTab(mTabLayout.newTab().setIcon(R.drawable.ic_home));
        mTabLayout.addTab(mTabLayout.newTab().setIcon(R.drawable.ic_notifications));
        mTabLayout.addTab(mTabLayout.newTab().setIcon(R.drawable.ic_email_white));
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.nav_home:

                break;
            case R.id.nav_schedule:

                break;
            case R.id.nav_notes:

                break;
            case R.id.nav_marks:

                break;
            case R.id.nav_saved:

                break;
            case R.id.nav_log_out:
                mTabLayout.setVisibility(View.VISIBLE);
                mViewPager.setVisibility(View.VISIBLE);
                break;
            case R.id.nav_setting:
                mTabLayout.setVisibility(View.GONE);
                mViewPager.setVisibility(View.GONE);
                break;
        }

        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
