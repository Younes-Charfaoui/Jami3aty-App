package com.ibnkhaldoun.studentside.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ibnkhaldoun.studentside.fragments.DayScheduleFragment;
import com.ibnkhaldoun.studentside.providers.DataProviders;


public class SchedulePagerAdapter extends FragmentPagerAdapter {
    private int numberOfTabs;

    public SchedulePagerAdapter(FragmentManager fm,
                                int numberOfTabs) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return DayScheduleFragment.newInstance(DataProviders.getSundayList());
            case 1:
                return DayScheduleFragment.newInstance(DataProviders.getMondayList());
            case 2:
                return DayScheduleFragment.newInstance(DataProviders.getTuesdayList());
            case 3:
                return DayScheduleFragment.newInstance(DataProviders.getWednesdayList());
            case 4:
                return DayScheduleFragment.newInstance(DataProviders.getThursdayList());
            default:
                return DayScheduleFragment.newInstance(DataProviders.getMondayList());
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
