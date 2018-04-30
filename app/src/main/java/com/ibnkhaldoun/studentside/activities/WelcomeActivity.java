package com.ibnkhaldoun.studentside.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.ibnkhaldoun.studentside.R;
import com.ibnkhaldoun.studentside.Utilities.ActivityUtilities;
import com.ibnkhaldoun.studentside.Utilities.PreferencesManager;
import com.ibnkhaldoun.studentside.adapters.IntroPagerAdapter;

public class WelcomeActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    //the needed attributes
    private PreferencesManager mPreferencesManager;
    private int[] mLayouts;
    private ViewPager mViewPager;
    private Button mNextButton, mSkipButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPreferencesManager = new PreferencesManager(this,PreferencesManager.CONFIG);
        if (mPreferencesManager.isNotFirstTimeLaunched()) {
            launchMainScreen();
        }

        //checking if the sdk is grate than Lollipop
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        setContentView(R.layout.activity_welcome);

        //getting reference to the views
        mViewPager = findViewById(R.id.welcome_view_pager);
        mSkipButton = findViewById(R.id.skip_button);
        mNextButton = findViewById(R.id.next_button);

        //getting our layout to displays
        mLayouts = new int[]{
                R.layout.welcome_slide_one,
                R.layout.welcome_slide_two,
                R.layout.welcome_slide_three,
                R.layout.welcome_slide_four};

        //changing the color of the status bar to transparent
        ActivityUtilities.changeStatusBarColorToTransparent(getWindow());
        IntroPagerAdapter mPageAdapter = new IntroPagerAdapter(this, mLayouts);
        mViewPager.setAdapter(mPageAdapter);
        mViewPager.addOnPageChangeListener(this);

        //when the skip button is Pressed
        mSkipButton.setOnClickListener(v -> launchMainScreen());

        //when the next button is pressed
        mNextButton.setOnClickListener(v -> {

            int current = getItem();
            if (current < mLayouts.length) {
                mViewPager.setCurrentItem(current);
            } else {
                launchMainScreen();
            }
        });

    }

    private void launchMainScreen() {
        mPreferencesManager.setFirstTimeLaunched();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }


    private int getItem() {
        return mViewPager.getCurrentItem() + 1;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        if (position == mLayouts.length - 1) {
            mNextButton.setText(getResources().getString(R.string.got_it));
            mSkipButton.setVisibility(View.GONE);
        } else {
            mNextButton.setText(getResources().getString(R.string.next));
            mSkipButton.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

}
