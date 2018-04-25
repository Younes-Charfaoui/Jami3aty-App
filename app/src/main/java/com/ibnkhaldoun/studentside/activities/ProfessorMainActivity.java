package com.ibnkhaldoun.studentside.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.ibnkhaldoun.studentside.R;
import com.ibnkhaldoun.studentside.Utilities.PreferencesManager;
import com.ibnkhaldoun.studentside.adapters.TabLayoutAdapter;
import com.ibnkhaldoun.studentside.fragments.DisplaysFragment;
import com.ibnkhaldoun.studentside.fragments.MessageFragment;
import com.ibnkhaldoun.studentside.interfaces.IDataProfessorFragment;
import com.ibnkhaldoun.studentside.networking.models.RequestPackage;
import com.ibnkhaldoun.studentside.networking.utilities.NetworkUtilities;
import com.ibnkhaldoun.studentside.providers.EndPointsProvider;
import com.ibnkhaldoun.studentside.services.LoadDataService;

import static com.ibnkhaldoun.studentside.Utilities.PreferencesManager.PROFESSOR;
import static com.ibnkhaldoun.studentside.Utilities.PreferencesManager.STUDENT;
import static com.ibnkhaldoun.studentside.fragments.BaseMainFragment.INTERNET_ERROR;
import static com.ibnkhaldoun.studentside.networking.models.RequestPackage.POST;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.JSON_STUDENT_GROUP;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.JSON_STUDENT_ID;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.JSON_STUDENT_LEVEL;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.JSON_STUDENT_SECTION;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.KEY_ANDROID;
import static com.ibnkhaldoun.studentside.services.LoadDataService.KEY_DATA;

public class ProfessorMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        IDataProfessorFragment {

    private FloatingActionButton mAddPostFab;
    private ViewPager mMainViewPager;
    private TabLayout mTabLayout;
    private Toolbar mToolBar;
    private DrawerLayout mDrawer;
    private NavigationView mNavigation;
    private String[] mPagerTitles;
    private DisplaysFragment mDisplays;
    private MessageFragment mMessages;
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            assert intent.getAction() != null;
            switch (intent.getAction()) {
                case LoadDataService.DISPLAY_ACTION:
                    mDisplays.onNetworkLoadedSucceed(intent.getParcelableArrayListExtra(KEY_DATA));
                    break;
                case LoadDataService.MAIL_ACTION:
                    Log.i("MailCall", "onReceive: message was been reeived");
                    mMessages.onNetworkLoadedSucceed(intent.getParcelableArrayListExtra(KEY_DATA));
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor_main);
        mToolBar = findViewById(R.id.toolbar);
        mToolBar.setTitle(mPagerTitles[0]);

        setSupportActionBar(mToolBar);

        mAddPostFab = findViewById(R.id.post_add_fab);
        mMainViewPager = findViewById(R.id.main_screen_professor_view_pager);
        mNavigation = findViewById(R.id.navigation_view_professor);
        mDrawer = findViewById(R.id.drawer_main_screen_professor_layout);

        mPagerTitles = getResources().getStringArray(R.array.pager_professor_titles_array_string);
        setupViewPagerAndTabLayout();
        mAddPostFab.setOnClickListener(v -> {

        });

        IntentFilter filter = new IntentFilter(LoadDataService.MAIL_ACTION);
        filter.addAction(LoadDataService.DISPLAY_ACTION);

        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver,
                filter);

    }

    private void setupTabIcons() {
        if (mTabLayout.getTabCount() > 0) {
            mTabLayout.removeAllTabs();
        }
        mTabLayout.addTab(mTabLayout.newTab().setIcon(R.drawable.ic_home));
        mTabLayout.addTab(mTabLayout.newTab().setIcon(R.drawable.ic_email_white));
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_sign_out:
                //todo sign out professor
                break;
        }
        return true;
    }

    @Override
    public void onNeedData(DisplaysFragment displaysFragment) {
        if (NetworkUtilities.isConnected(this)) {
            displaysFragment.onNetworkStartLoading();
            PreferencesManager manager = new PreferencesManager(this, PROFESSOR);
            RequestPackage request = new RequestPackage.Builder()
                    .addEndPoint(EndPointsProvider.getDisplays())
                    .addMethod(POST)

                    // the professor ids
                    .create();
            Intent intent = new Intent(this, LoadDataService.class);
            intent.putExtra(LoadDataService.KEY_REQUEST, request);
            intent.putExtra(LoadDataService.KEY_ACTION, LoadDataService.DISPLAY_TYPE);
            //startService(intent);
        } else {
            displaysFragment.onNetworkLoadingFailed(INTERNET_ERROR);
        }
    }

    @Override
    public void onNeedData(MessageFragment messageFragment) {
        if (NetworkUtilities.isConnected(this)) {
            PreferencesManager manager = new PreferencesManager(this, PROFESSOR);
            messageFragment.onNetworkStartLoading();
            RequestPackage request = new RequestPackage.Builder()
                    .addMethod(POST)
                    .addEndPoint(EndPointsProvider.getAllMailEndPoint())
                    .addParams(KEY_ANDROID, KEY_ANDROID)
                    // professor id
                    .create();

            Intent intent = new Intent(this, LoadDataService.class);
            intent.putExtra(LoadDataService.KEY_REQUEST, request);
            intent.putExtra(LoadDataService.KEY_ACTION, LoadDataService.MAIL_TYPE);
            //startService(intent);
        } else {
            messageFragment.onNetworkLoadingFailed(INTERNET_ERROR);
        }
    }

    @Override
    public void onAttach(DisplaysFragment displaysFragment) {
        this.mDisplays = displaysFragment;
    }

    @Override
    public void onAttach(MessageFragment messageFragment) {
        this.mMessages = messageFragment;
    }
}
