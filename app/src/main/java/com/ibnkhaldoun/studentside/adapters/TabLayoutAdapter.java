package com.ibnkhaldoun.studentside.adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ibnkhaldoun.studentside.providers.DataProviders;
import com.ibnkhaldoun.studentside.fragments.DisplaysFragment;
import com.ibnkhaldoun.studentside.fragments.MailFragment;
import com.ibnkhaldoun.studentside.fragments.NotificationFragment;

public class TabLayoutAdapter extends FragmentPagerAdapter {

    private int mNumbersOfTabs;
    private int CurrentState;

    public TabLayoutAdapter(FragmentManager fm, int numbersOfTab) {
        super(fm);
        this.mNumbersOfTabs = numbersOfTab;
    }

    public void setCurrentState(int currentState) {
        CurrentState = currentState;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return DisplaysFragment.newInstance(DataProviders.getDisplayList());
            case 1:
                return NotificationFragment.newInstance(DataProviders.getNotificationList());
            case 2:
                return MailFragment.newInstance(DataProviders.getMailList());
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumbersOfTabs;
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }
}
