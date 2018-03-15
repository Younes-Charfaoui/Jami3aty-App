package com.ibnkhaldoun.studentside.activities;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.ibnkhaldoun.studentside.R;
import com.ibnkhaldoun.studentside.Utilities.PreferencesManager;
import com.ibnkhaldoun.studentside.Utilities.Utils;
import com.ibnkhaldoun.studentside.adapters.TabLayoutAdapter;
import com.ibnkhaldoun.studentside.fragments.ProfessorListFragment;
import com.ibnkhaldoun.studentside.interfaces.ProfessorDialogInterface;

import java.util.Calendar;

public class StudentMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        ProfessorDialogInterface{

    private FloatingActionButton mAddMailFab;

    private ViewPager mMainViewPager;
    private TabLayout mTabLayout;
    private Toolbar mToolBar;
    private DrawerLayout mDrawer;
    private NavigationView mNavigation;

    private String[] mPagerTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen_drawer);

        //getting the arrays
        mPagerTitles = getResources().getStringArray(R.array.pager_titles_array_string);

        mToolBar = findViewById(R.id.toolbar);
        mToolBar.setTitle(mPagerTitles[0]);
        setSupportActionBar(mToolBar);

        mMainViewPager = findViewById(R.id.main_screen_view_pager);
        mTabLayout = findViewById(R.id.main_screen_tab_layout);

        Log.i("Hello", "The Time is : " + Calendar.getInstance().getTime());
        mAddMailFab = findViewById(R.id.mail_add_professor_fab);
        mAddMailFab.setBackgroundColor(getResources().getColor(R.color.deep_red));
        mAddMailFab.hide();

        mAddMailFab.setOnClickListener(v -> {
            ProfessorListFragment professorListFragment =
                    ProfessorListFragment.newInstance(null);
            professorListFragment.show(getSupportFragmentManager(), "TAG");
        });

        setupNavigationDrawer();

        TextView nameHeader = mNavigation.getHeaderView(0).findViewById(R.id.name_header_textView);
        TextView branchHeader = mNavigation.getHeaderView(0).findViewById(R.id.branch_header_textView);
        TextView circleImage = mNavigation.getHeaderView(0).findViewById(R.id.nav_imageView);
        GradientDrawable circle = (GradientDrawable) circleImage.getBackground();
        PreferencesManager manager = new PreferencesManager(this);
        circleImage.setText("YC");
        circle.setColor(Utils.getCircleColor(manager.getFullName().charAt(0), this));
        nameHeader.setText(manager.getFullName());
        String branch = manager.getGrade() + " ";
        branchHeader.setText(branch);

        setupViewPagerAndTabLayout();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_screen, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.nav_marks:
                startActivity(new Intent(this, MarkActivity.class));
                break;
            case R.id.nav_schedule:
                startActivity(new Intent(this, ScheduleActivity.class));
                break;
            case R.id.nav_notes:
                startActivity(new Intent(this, NotesActivity.class));
                break;
            case R.id.nav_saved:
                startActivity(new Intent(this, SavedActivity.class));
                break;

            case R.id.nav_subject:
                startActivity(new Intent(this, SubjectsActivity.class));
            case R.id.nav_setting:

                break;
            case R.id.nav_help:
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

    //helper method to initialize the view pager and the tab layout
    private void setupViewPagerAndTabLayout() {
        setupTabIcons();
        TabLayoutAdapter adapter = new TabLayoutAdapter(getSupportFragmentManager(), mTabLayout.getTabCount());
        mMainViewPager.setAdapter(adapter);
        mMainViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mMainViewPager.setCurrentItem(tab.getPosition());
                mToolBar.setTitle(mPagerTitles[tab.getPosition()]);
                if (tab.getPosition() == 2) mAddMailFab.show();
                else mAddMailFab.hide();
                AppBarLayout appBarLayout = findViewById(R.id.appbar);
                appBarLayout.setExpanded(true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    //helper method to set the navigation drawer and get a reference to it
    private void setupNavigationDrawer() {
        mDrawer = findViewById(R.id.drawer_main_screen_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolBar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        mNavigation = findViewById(R.id.navigation_main_screen_view);
        mNavigation.setCheckedItem(R.id.nav_home);
        mNavigation.setNavigationItemSelectedListener(this);
    }

    //method to add icons to the TabLayout
    private void setupTabIcons() {
        if (mTabLayout.getTabCount() > 0) {
            mTabLayout.removeAllTabs();
        }
        mTabLayout.addTab(mTabLayout.newTab().setIcon(R.drawable.ic_home));
        mTabLayout.addTab(mTabLayout.newTab().setIcon(R.drawable.ic_notifications_white));
        mTabLayout.addTab(mTabLayout.newTab().setIcon(R.drawable.ic_email_white));
    }

    @Override
    public void onProfessorChosen(String professor, String id) {
        
    }
}
