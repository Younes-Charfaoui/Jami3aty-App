package com.ibnkhaldoun.studentside.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
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
import com.ibnkhaldoun.studentside.Utilities.Utilities;
import com.ibnkhaldoun.studentside.adapters.TabLayoutAdapter;
import com.ibnkhaldoun.studentside.database.DatabaseContract;
import com.ibnkhaldoun.studentside.fragments.ProfessorListFragment;
import com.ibnkhaldoun.studentside.interfaces.DataFragmentInterface;
import com.ibnkhaldoun.studentside.interfaces.LoadingDataCallbacks;
import com.ibnkhaldoun.studentside.interfaces.ProfessorDialogInterface;
import com.ibnkhaldoun.studentside.networking.models.RequestPackage;
import com.ibnkhaldoun.studentside.networking.utilities.NetworkUtilities;
import com.ibnkhaldoun.studentside.providers.DataProviders;
import com.ibnkhaldoun.studentside.providers.EndPointsProvider;
import com.ibnkhaldoun.studentside.providers.KeyDataProvider;
import com.ibnkhaldoun.studentside.services.LoadDataService;

import java.util.Calendar;

public class StudentMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        ProfessorDialogInterface, DataFragmentInterface, LoaderManager.LoaderCallbacks<Cursor> {

    public static final String KEY_MAILS = "keyMails";
    public static final int MAIL_TYPE = 15;
    public static final int DISPLAY_TYPE = 11;
    public static final int NOTIFICATION_TYPE = 10;
    private static final int DISPLAY_LOADER_ID = 190;
    private static final int MAIL_LOADER_ID = 191;
    private static final int NOTIFICATION_LOADER_ID = 192;
    private FloatingActionButton mAddMailFab;
    private ViewPager mMainViewPager;
    private TabLayout mTabLayout;
    private Toolbar mToolBar;
    private DrawerLayout mDrawer;
    private NavigationView mNavigation;

    private String[] mPagerTitles;
    private LoadingDataCallbacks fragmentsCallbacks = (LoadingDataCallbacks) this;


    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            assert intent.getAction() != null;
            switch (intent.getAction()) {
                case LoadDataService.DISPLAY_ACTION:
                    break;
                case LoadDataService.MAIL_ACTION:
                    fragmentsCallbacks.onNetworkLoadedSucceed(MAIL_TYPE,
                            intent.getParcelableArrayListExtra(KEY_MAILS));
                    break;
                case LoadDataService.NOTIFICATION_ACTION:
                    break;
            }
        }
    };

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
        circleImage.setText(Utilities.getFirstName(manager.getFullName()).charAt(0) +
                Utilities.getLastName(manager.getFullName()).charAt(0));
        circle.setColor(Utilities.getCircleColor(manager.getFullName().charAt(0), this));
        nameHeader.setText(manager.getFullName());
        String branch = manager.getGrade() + " ";
        branchHeader.setText(branch);

        setupViewPagerAndTabLayout();

        IntentFilter filter = new IntentFilter(LoadDataService.MAIL_ACTION);
        filter.addAction(LoadDataService.NOTIFICATION_ACTION);
        filter.addAction(LoadDataService.DISPLAY_ACTION);

        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver,
                filter);


        getSupportLoaderManager().initLoader(DISPLAY_LOADER_ID, null, this);
        getSupportLoaderManager().initLoader(NOTIFICATION_LOADER_ID, null, this);
        getSupportLoaderManager().initLoader(MAIL_LOADER_ID, null, this);
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
        adapter.setLists(DataProviders.getDisplayList(),
                DataProviders.getNotificationList(),
                DataProviders.getMailList());
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

    @Override
    public void onNeedData(int type) {
        if (NetworkUtilities.isConnected(this)) {
            fragmentsCallbacks.onNetworkStartLoading(type);
            switch (type) {
                case MAIL_TYPE:
                    RequestPackage request = new RequestPackage();
                    request.setMethod(RequestPackage.POST);
                    request.setEndPoint(EndPointsProvider.getMailEndPoint());
                    PreferencesManager manager = new PreferencesManager(this);
                    request.addParams(KeyDataProvider.KEY_ANDROID, KeyDataProvider.KEY_ANDROID);
                    request.addParams(KeyDataProvider.JSON_STUDENT_ID, manager.getId());
                    Intent intent = new Intent(this, LoadDataService.class);
                    intent.putExtra(LoadDataService.KEY_REQUEST, request);
                    intent.putExtra(LoadDataService.KEY_ACTION, LoadDataService.MAIL_TYPE);
                    startService(intent);
                    break;
                case DISPLAY_TYPE:
                    break;
                case NOTIFICATION_TYPE:
                    break;
            }
        } else {
            fragmentsCallbacks.onNetworkLoadingFailed(type, LoadingDataCallbacks.INTERNET_ERROR);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case DISPLAY_LOADER_ID:
                // TODO: 16/03/2018 code to launch for displays
                return null;
            case NOTIFICATION_LOADER_ID:
                // TODO: 16/03/2018 code to launch for notifications
                return null;
            case MAIL_LOADER_ID:
                fragmentsCallbacks.onDatabaseStartLoading(MAIL_TYPE);
                return new CursorLoader(this, DatabaseContract.MailEntry.CONTENT_MAIL_URI
                        , new String[]{"*"}, null, null, null);
            default:
                throw new IllegalStateException();
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch (loader.getId()) {
            case DISPLAY_LOADER_ID:
                // TODO: 16/03/2018 code to deliver result for displays fragment
                break;
            case NOTIFICATION_LOADER_ID:
                // TODO: 16/03/2018 code to deliver result for notification fragment
                break;
            case MAIL_LOADER_ID:
                fragmentsCallbacks.onDatabaseLoadingFinished(MAIL_TYPE, data);
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        switch (loader.getId()) {
            case DISPLAY_LOADER_ID:
                // TODO: 16/03/2018 code to deliver result for displays fragment
                break;
            case NOTIFICATION_LOADER_ID:
                // TODO: 16/03/2018 code to deliver result for notification fragment
                break;
            case MAIL_LOADER_ID:
                fragmentsCallbacks.onDatabaseStartLoading(MAIL_TYPE);
        }
    }
}
