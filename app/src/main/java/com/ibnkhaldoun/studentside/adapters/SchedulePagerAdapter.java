package com.ibnkhaldoun.studentside.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ibnkhaldoun.studentside.enums.Day;
import com.ibnkhaldoun.studentside.fragments.DayScheduleFragment;
import com.ibnkhaldoun.studentside.providers.DataProviders;


public class SchedulePagerAdapter extends FragmentPagerAdapter {

    public SchedulePagerAdapter(FragmentManager fm ) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return DayScheduleFragment.newInstance(DataProviders.getSundayList(), Day.SUNDAY);
            case 1:
                return DayScheduleFragment.newInstance(DataProviders.getMondayList(), Day.MONDAY);
            case 2:
                return DayScheduleFragment.newInstance(DataProviders.getTuesdayList(), Day.TUESDAY);
            case 3:
                return DayScheduleFragment.newInstance(DataProviders.getWednesdayList(), Day.WEDNESDAY);
            case 4:
                return DayScheduleFragment.newInstance(DataProviders.getThursdayList(), Day.THURSDAY);
            default:
                return DayScheduleFragment.newInstance(DataProviders.getMondayList(), Day.TUESDAY);
        }
    }

    @Override
    public int getCount() {
        return 5;
    }
}
