package com.ibnkhaldoun.studentside.activities;

import android.app.ActivityManager;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ibnkhaldoun.studentside.R;
import com.ibnkhaldoun.studentside.Utilities.PreferencesManager;
import com.ibnkhaldoun.studentside.Utilities.Utils;
import com.ibnkhaldoun.studentside.adapters.TabLayoutAdapter;
import com.ibnkhaldoun.studentside.data_providers.DataProviders;
import com.ibnkhaldoun.studentside.fragments.MarksFragment;
import com.ibnkhaldoun.studentside.fragments.NotesFragment;
import com.ibnkhaldoun.studentside.fragments.SavedFragment;
import com.ibnkhaldoun.studentside.fragments.ScheduleFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //some constant needed for comparision
    public static final int HOME = 4;
    public static final int MARKS = 0;
    public static final int SCHEDULE = 1;
    public static final int NOTES = 2;
    public static final int SAVED = 3;

    private FloatingActionButton mAddMailFab;

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private Toolbar mToolBar;
    private DrawerLayout mDrawer;
    private NavigationView mNavigation;
    private View mFrame;

    private String[] mPagerTitles;
    private String[] mFragmentsTitles;
    private int mCurrentState = 4;
    private MarksFragment marksFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen_drawer);

        //getting the arrays
        mFragmentsTitles = getResources().getStringArray(R.array.fragment_title_array_string);
        mPagerTitles = getResources().getStringArray(R.array.pager_titles_array_string);

        mToolBar = findViewById(R.id.toolbar);
        mToolBar.setTitle(mPagerTitles[0]);
        mFrame = findViewById(R.id.frame);
        setSupportActionBar(mToolBar);


        mAddMailFab = findViewById(R.id.mail_add_professor_fab);
        mAddMailFab.setBackgroundColor(getResources().getColor(R.color.deep_red));
        mAddMailFab.hide();
        mAddMailFab.setOnClickListener(v -> {
            Toast.makeText(this, "Messages Fragment", Toast.LENGTH_SHORT).show();
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
        String branch = manager.getGrade() + " " + manager.getBranch();
        branchHeader.setText(branch);
        setupViewPagerAndTabLayout();
    }

    //helper method to initialize the view pager and the tab layout
    private void setupViewPagerAndTabLayout() {
        mViewPager = findViewById(R.id.main_screen_view_pager);
        mTabLayout = findViewById(R.id.main_screen_tab_layout);
        setupTabIcons();

        TabLayoutAdapter adapter = new TabLayoutAdapter(getSupportFragmentManager(), mTabLayout.getTabCount());
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
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
                this, mDrawer, mToolBar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        mNavigation = findViewById(R.id.navigation_main_screen_view);
        mNavigation.setCheckedItem(R.id.nav_home);
        mNavigation.setNavigationItemSelectedListener(this);
    }

    //method to add icons to the TabLayout
    private void setupTabIcons() {
        mTabLayout.addTab(mTabLayout.newTab().setIcon(R.drawable.ic_home));
        mTabLayout.addTab(mTabLayout.newTab().setIcon(R.drawable.ic_notifications_white));
        mTabLayout.addTab(mTabLayout.newTab().setIcon(R.drawable.ic_email_white));
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
            case R.id.nav_home:
                if (mCurrentState != HOME) {
                    mCurrentState = HOME;
                    mViewPager.setVisibility(View.VISIBLE);
                    mTabLayout.setVisibility(View.VISIBLE);
                    mFrame.setVisibility(View.GONE);
                    mToolBar.setTitle(mPagerTitles[mTabLayout.getSelectedTabPosition()]);
                }
                break;
            case R.id.nav_marks:
                checkingAndChanging(MARKS);
                break;
            case R.id.nav_schedule:
                checkingAndChanging(SCHEDULE);
                break;
            case R.id.nav_notes:
                checkingAndChanging(NOTES);
                break;

            case R.id.nav_saved:
                checkingAndChanging(SAVED);
                break;
            case R.id.nav_setting:

                break;
            case R.id.nav_help:

                break;

        }

        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //helper method to check the state and replace the fragment and chang the title
    private void checkingAndChanging(int index) {
        if (mCurrentState != index) {
            mCurrentState = index;

            new Handler().post(() -> {
                        mViewPager.setVisibility(View.GONE);
                        mTabLayout.setVisibility(View.GONE);
                        mFrame.setVisibility(View.VISIBLE);
                        getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                                .replace(R.id.frame, getFragmentByIndex(index))
                                .commitAllowingStateLoss();
                        mToolBar.setTitle(mFragmentsTitles[index]);
                    }
            );
        }

    }

    /**
     * this method will return a fragment by its index
     *
     * @return Fragment
     */
    private Fragment getFragmentByIndex(int index) {
        switch (index) {
            case 0:

                if (marksFragment == null)
                    marksFragment = MarksFragment.newInstance(DataProviders.getMarkList());
                return marksFragment;
            case 1:
                return new ScheduleFragment();
            case 2:
                return new NotesFragment();
            case 3:
                return new SavedFragment();
            default:
                return new MarksFragment();
        }
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            ActivityManager hello = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
            hello.moveTaskToFront(getTaskId(), 0);
            super.onBackPressed();
        }
    }
}
