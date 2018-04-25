package com.ibnkhaldoun.studentside.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
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
import android.widget.Toast;

import com.ibnkhaldoun.studentside.R;
import com.ibnkhaldoun.studentside.Utilities.PreferencesManager;
import com.ibnkhaldoun.studentside.Utilities.Utilities;
import com.ibnkhaldoun.studentside.adapters.TabLayoutAdapter;
import com.ibnkhaldoun.studentside.asyncTask.ResponseAsyncTask;
import com.ibnkhaldoun.studentside.database.DatabaseContract;
import com.ibnkhaldoun.studentside.fragments.DisplaysFragment;
import com.ibnkhaldoun.studentside.fragments.MessageFragment;
import com.ibnkhaldoun.studentside.fragments.NoteOfDisplayDialog;
import com.ibnkhaldoun.studentside.fragments.NotificationFragment;
import com.ibnkhaldoun.studentside.fragments.ProfessorListFragment;
import com.ibnkhaldoun.studentside.interfaces.IDataFragment;
import com.ibnkhaldoun.studentside.interfaces.IProfessorDialog;
import com.ibnkhaldoun.studentside.models.Display;
import com.ibnkhaldoun.studentside.models.MailProfessor;
import com.ibnkhaldoun.studentside.models.Message;
import com.ibnkhaldoun.studentside.models.Notification;
import com.ibnkhaldoun.studentside.networking.models.RequestPackage;
import com.ibnkhaldoun.studentside.networking.models.Response;
import com.ibnkhaldoun.studentside.networking.utilities.JsonUtilities;
import com.ibnkhaldoun.studentside.networking.utilities.NetworkUtilities;
import com.ibnkhaldoun.studentside.networking.utilities.RequestPackageFactory;
import com.ibnkhaldoun.studentside.providers.EndPointsProvider;
import com.ibnkhaldoun.studentside.services.LoadDataService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.ibnkhaldoun.studentside.Utilities.PreferencesManager.STUDENT;
import static com.ibnkhaldoun.studentside.fragments.BaseMainFragment.INTERNET_ERROR;
import static com.ibnkhaldoun.studentside.networking.models.RequestPackage.POST;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.JSON_STUDENT_GROUP;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.JSON_STUDENT_ID;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.JSON_STUDENT_LEVEL;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.JSON_STUDENT_SECTION;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.KEY_AJAX;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.KEY_ANDROID;
import static com.ibnkhaldoun.studentside.services.LoadDataService.KEY_DATA;

public class StudentMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        IProfessorDialog, IDataFragment, NoteOfDisplayDialog.INoteOfDisplay,
        LoaderManager.LoaderCallbacks<Cursor>, ResponseAsyncTask.IResponseListener {


    //the types of the fragments
    public static final int MAIL_TYPE = 15;
    public static final int DISPLAY_TYPE = 11;
    public static final int NOTIFICATION_TYPE = 10;
    //the Loader ID's for the database work
    private static final int DISPLAY_LOADER_ID = 190;
    private static final int MAIL_LOADER_ID = 191;
    private static final int NOTIFICATION_LOADER_ID = 192;

    @SuppressLint("StaticFieldLeak")
    public static StudentMainActivity ACTIVITY;
    private FloatingActionButton mAddMailFab;
    private ViewPager mMainViewPager;
    private TabLayout mTabLayout;
    private Toolbar mToolBar;
    private DrawerLayout mDrawer;
    private NavigationView mNavigation;

    private String[] mPagerTitles;

    private DisplaysFragment mDisplayFragment;
    private NotificationFragment mNotificationFragment;
    private MessageFragment mMessageFragment;


    private ArrayList<Display> mDisplaysList;
    private ArrayList<Notification> mNotificationsList;
    private ArrayList<Message> mMessagesList;

    /**
     * the receiver which will listen when the data has been downloaded from the
     * internet and pass them to the fragments
     */
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            assert intent.getAction() != null;
            switch (intent.getAction()) {
                case LoadDataService.DISPLAY_ACTION:
                    mDisplaysList = intent.getParcelableArrayListExtra(KEY_DATA);
                    mDisplayFragment.onNetworkLoadedSucceed(mDisplaysList);
                    break;
                case LoadDataService.MAIL_ACTION:
                    Log.i("MailCall", "onReceive: message was been reeived");
                    mMessagesList = intent.getParcelableArrayListExtra(KEY_DATA);
                    mMessageFragment.onNetworkLoadedSucceed(mMessagesList);
                    break;
                case LoadDataService.NOTIFICATION_ACTION:
                    mNotificationsList = intent.getParcelableArrayListExtra(KEY_DATA);
                    mNotificationFragment.onNetworkLoadedSucceed(mNotificationsList);
                    break;
            }
        }
    };

    /**
     * the onCreate addMethod which in we have done most of the
     * initialisation work also with some thing else.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen_drawer);
        ACTIVITY = this;
        //getting the arrays
        mPagerTitles = getResources().getStringArray(R.array.pager_titles_array_string);

        mToolBar = findViewById(R.id.toolbar);
        mToolBar.setTitle(mPagerTitles[0]);
        setSupportActionBar(mToolBar);

        mMainViewPager = findViewById(R.id.main_screen_view_pager);
        mTabLayout = findViewById(R.id.main_screen_tab_layout);

        mAddMailFab = findViewById(R.id.mail_add_professor_fab);
        mAddMailFab.setBackgroundColor(getResources().getColor(R.color.deep_red));
        mAddMailFab.hide();

        mAddMailFab.setOnClickListener(v -> {
            if (NetworkUtilities.isConnected(this)) {

                ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setTitle("Loading Professors");
                progressDialog.setMessage(getString(R.string.wait_please));
                PreferencesManager manager = new PreferencesManager(this, STUDENT);

                RequestPackage request = new RequestPackage.Builder()
                        .addEndPoint(EndPointsProvider.getProfessorEndpoint())
                        .addMethod(POST)
                        .addParams(KEY_AJAX, KEY_ANDROID)
                        .addParams(JSON_STUDENT_LEVEL, manager.getLevelStudent())
                        .addParams(JSON_STUDENT_GROUP, manager.getGroupStudent())
                        .addParams(JSON_STUDENT_SECTION, manager.getSectionStudent())
                        .create();

                ResponseAsyncTask task = new ResponseAsyncTask(response -> {
                    progressDialog.dismiss();
                    if (response.getStatus() == 200) {
                        List<MailProfessor> professor = JsonUtilities.getProfessorList(response.getData());
                        ProfessorListFragment professorListFragment =
                                ProfessorListFragment.newInstance(professor);
                        professorListFragment.show(getSupportFragmentManager(), "TAG");
                    } else {
                        Toast.makeText(this, "Problem happened, try later", Toast.LENGTH_SHORT).show();
                    }

                });
                task.execute(request);

            } else {
                Toast.makeText(this, R.string.no_internet_connection_string, Toast.LENGTH_SHORT).show();
            }
        });

        setupNavigationDrawer();

        TextView nameHeader = mNavigation.getHeaderView(0).findViewById(R.id.name_header_textView);
        TextView branchHeader = mNavigation.getHeaderView(0).findViewById(R.id.branch_header_textView);
        TextView circleImage = mNavigation.getHeaderView(0).findViewById(R.id.nav_imageView);

        GradientDrawable circle = (GradientDrawable) circleImage.getBackground();

        PreferencesManager manager = new PreferencesManager(this, STUDENT);
        String text = Character.toString(Utilities.getFirstName(manager.getFullNameStudent()).charAt(0)) +
                Character.toString(Utilities.getLastName(manager.getFullNameStudent()).charAt(0));
        circleImage.setText(text);

        circle.setColor(Utilities.getCircleColor(manager.getFullNameStudent().charAt(0), this));
        nameHeader.setText(manager.getFullNameStudent());
        branchHeader.setText(manager.getGradeStudent());

        setupViewPagerAndTabLayout();

        IntentFilter filter = new IntentFilter(LoadDataService.MAIL_ACTION);
        filter.addAction(LoadDataService.NOTIFICATION_ACTION);
        filter.addAction(LoadDataService.DISPLAY_ACTION);

        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver,
                filter);

        //getSupportLoaderManager().initLoader(DISPLAY_LOADER_ID, null, this).forceLoad();
        //getSupportLoaderManager().initLoader(NOTIFICATION_LOADER_ID, null, this).forceLoad();
        //getSupportLoaderManager().initLoader(MAIL_LOADER_ID, null, this).forceLoad();
    }

    /**
     * Class addMethod for inflating the menu from
     * the resources.
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_screen, menu);
        return true;
    }

    /**
     * this addMethod wil handle the user interaction with the
     * navigation drawer menu , it will make the corresponded
     * action for all the items.
     *
     * @param item
     * @return a boolean indicating that the item was selected.
     */
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
                break;
            case R.id.nav_setting:
                startActivity(new Intent(this, SettingActivity.class));
                break;
            case R.id.nav_help:
                startActivity(new Intent(this, HelpActivity.class));
                break;
        }
        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * as this addMethod will be called this code will handle
     * the case if the the navigation drawer was open or not.
     */
    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * this addMethod is a helper addMethod for initializing the
     * view pager and the tab layout.
     */
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

    /**
     * this addMethod is a helper addMethod for initializing the
     * navigation drawer.
     */
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

    /**
     * this addMethod is a helper addMethod for initializing the
     * tabs that in the tab layout.
     */
    private void setupTabIcons() {
        if (mTabLayout.getTabCount() > 0) {
            mTabLayout.removeAllTabs();
        }
        mTabLayout.addTab(mTabLayout.newTab().setIcon(R.drawable.ic_home));
        mTabLayout.addTab(mTabLayout.newTab().setIcon(R.drawable.ic_notifications_white));
        mTabLayout.addTab(mTabLayout.newTab().setIcon(R.drawable.ic_email_white));
    }

    /**
     * this addMethod was implemented by the {@link IProfessorDialog}
     * for the purpose of communicating with the fragment of which
     * the user can choose a professor to send mails to it.
     *
     * @param professor
     * @param id
     */
    @Override
    public void onProfessorChosen(String professor, String id) {
        Intent intent = new Intent(this, NewMessageActivity.class);
        intent.putExtra(NewMessageActivity.KEY_PROFESSOR, professor);
        intent.putExtra(NewMessageActivity.KEY_ID, id);
        startActivity(intent);
    }

    /**
     * this addMethod was implemented by the {@link IDataFragment}
     * for the purpose of communicating with the {@link DisplaysFragment} to download
     * data when it needed.
     *
     * @param which
     */
    @Override
    public void onNeedData(DisplaysFragment which) {
        if (NetworkUtilities.isConnected(this)) {
            which.onNetworkStartLoading();
            PreferencesManager manager = new PreferencesManager(this, STUDENT);
            RequestPackage request = new RequestPackage.Builder()
                    .addEndPoint(EndPointsProvider.getDisplays())
                    .addMethod(POST)
                    .addParams(JSON_STUDENT_GROUP, manager.getGroupStudent())
                    .addParams(JSON_STUDENT_LEVEL, manager.getLevelStudent())
                    .addParams(JSON_STUDENT_SECTION, manager.getSectionStudent())
                    .create();
            Intent intent = new Intent(this, LoadDataService.class);
            intent.putExtra(LoadDataService.KEY_REQUEST, request);
            intent.putExtra(LoadDataService.KEY_ACTION, LoadDataService.DISPLAY_TYPE);
            startService(intent);
        } else {
            which.onNetworkLoadingFailed(INTERNET_ERROR);
        }
    }

    /**
     * this addMethod was implemented by the {@link IDataFragment}
     * for the purpose of communicating with the {@link NotificationFragment} to download
     * data when it needed.
     *
     * @param which
     */
    @Override
    public void onNeedData(NotificationFragment which) {

        if (NetworkUtilities.isConnected(this)) {
            which.onNetworkStartLoading();
            RequestPackage request = new RequestPackage.Builder()
                    .addEndPoint(EndPointsProvider.getNotifications())
                    .addMethod(POST)
                    .addParams(JSON_STUDENT_ID,
                            new PreferencesManager(this, PreferencesManager.STUDENT).getIdStudent())
                    .create();
            Intent intent = new Intent(this, LoadDataService.class);
            intent.putExtra(LoadDataService.KEY_REQUEST, request);
            intent.putExtra(LoadDataService.KEY_ACTION, LoadDataService.NOTIFICATION_TYPE);
            startService(intent);
        } else {
            which.onNetworkLoadingFailed(INTERNET_ERROR);
        }
    }

    /**
     * this addMethod was implemented by the {@link IDataFragment}
     * for the purpose of communicating with the {@link MessageFragment} to download
     * data when it needed.
     *
     * @param which
     */
    @Override
    public void onNeedData(MessageFragment which) {
        if (NetworkUtilities.isConnected(this)) {
            PreferencesManager manager = new PreferencesManager(this, STUDENT);
            which.onNetworkStartLoading();
            RequestPackage request = new RequestPackage.Builder()
                    .addMethod(POST)
                    .addEndPoint(EndPointsProvider.getAllMailEndPoint())
                    .addParams(KEY_ANDROID, KEY_ANDROID)
                    .addParams(JSON_STUDENT_ID, manager.getIdStudent())
                    .create();

            Intent intent = new Intent(this, LoadDataService.class);
            intent.putExtra(LoadDataService.KEY_REQUEST, request);
            intent.putExtra(LoadDataService.KEY_ACTION, LoadDataService.MAIL_TYPE);
            startService(intent);
        } else {
            which.onNetworkLoadingFailed(INTERNET_ERROR);
        }
    }

    /**
     * this addMethod was implemented by the {@link IDataFragment}
     * for the purpose of communicating with the {@link DisplaysFragment} to hold reference
     * to it for future communications.
     *
     * @param displaysFragment
     */
    @Override
    public void onAttach(DisplaysFragment displaysFragment) {
        mDisplayFragment = displaysFragment;
    }

    /**
     * this addMethod was implemented by the {@link IDataFragment}
     * for the purpose of communicating with the {@link MessageFragment} to hold reference
     * to it for future communications.
     *
     * @param messageFragment
     */
    @Override
    public void onAttach(MessageFragment messageFragment) {
        mMessageFragment = messageFragment;
    }

    /**
     * this addMethod was implemented by the {@link IDataFragment}
     * for the purpose of communicating with the {@link NotificationFragment} to hold reference
     * to it for future communications.
     *
     * @param notificationFragment
     */
    @Override
    public void onAttach(NotificationFragment notificationFragment) {
        mNotificationFragment = notificationFragment;

    }

    /**
     * we had override this addMethod for unregister receivers.
     */
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
                mMessageFragment.onDatabaseStartLoading();
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
                //mMessageFragment.onDatabaseLoadingFinished(data);
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
                //mMessageFragment.onDatabaseStartLoading();
        }
    }

    @Override
    public void OnFinishNoting(String note, long id, int type) {
        Toast.makeText(this, note + " " + id, Toast.LENGTH_SHORT).show();
        RequestPackage request = RequestPackageFactory.createNoteAddingRequest(id, note, this);
        ResponseAsyncTask task = new ResponseAsyncTask(this);
        task.execute(request);
    }

    @Override
    public void onGetResponse(Response response) {
        if (response.getStatus() != Response.RESPONSE_SUCCESS)
            Toast.makeText(this, R.string.try_later_problem_connecting, Toast.LENGTH_SHORT)
                    .show();
    }
}
