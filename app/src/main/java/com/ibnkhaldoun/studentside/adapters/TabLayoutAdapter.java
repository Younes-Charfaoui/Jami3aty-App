package com.ibnkhaldoun.studentside.adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ibnkhaldoun.studentside.fragments.DisplaysFragment;
import com.ibnkhaldoun.studentside.fragments.MessageFragment;
import com.ibnkhaldoun.studentside.fragments.NotificationFragment;

public class TabLayoutAdapter extends FragmentPagerAdapter {

    private int mNumbersOfTabs;


    public TabLayoutAdapter(FragmentManager fm, int numbersOfTab) {
        super(fm);
        this.mNumbersOfTabs = numbersOfTab;
    }

    @Override
    public Fragment getItem(int position) {

        switch (mNumbersOfTabs) {
            case 3:
                switch (position) {
                    case 0:
                        return new DisplaysFragment();
                    case 1:
                        return new NotificationFragment();
                    case 2:
                        return new MessageFragment();
                    default:
                        return null;
                }
            case 2:
                switch (position) {
                    case 0:
                        return new DisplaysFragment();
                    case 1:
                        return new MessageFragment();
                    default:
                        return null;
                }
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
