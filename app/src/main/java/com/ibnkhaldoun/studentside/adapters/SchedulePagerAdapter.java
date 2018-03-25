package com.ibnkhaldoun.studentside.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ibnkhaldoun.studentside.fragments.DayMondayFragment;
import com.ibnkhaldoun.studentside.fragments.DaySundayFragment;
import com.ibnkhaldoun.studentside.fragments.DayThursdayFragment;
import com.ibnkhaldoun.studentside.fragments.DayTuesdayFragment;
import com.ibnkhaldoun.studentside.fragments.DayWednesdayFragment;


public class SchedulePagerAdapter extends FragmentPagerAdapter {

    public SchedulePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new DaySundayFragment();
            case 1:
                return new DayMondayFragment();
            case 2:
                return new DayTuesdayFragment();
            case 3:
                return new DayWednesdayFragment();
            case 4:
                return new DayThursdayFragment();
            default:
                throw new IllegalStateException();
        }
    }

    @Override
    public int getCount() {
        return 5;
    }
}
