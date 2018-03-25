package com.ibnkhaldoun.studentside.interfaces;


import com.ibnkhaldoun.studentside.fragments.DayMondayFragment;
import com.ibnkhaldoun.studentside.fragments.DaySundayFragment;
import com.ibnkhaldoun.studentside.fragments.DayThursdayFragment;
import com.ibnkhaldoun.studentside.fragments.DayTuesdayFragment;
import com.ibnkhaldoun.studentside.fragments.DayWednesdayFragment;

public interface ScheduleCallbacks {
    void onAttach(DaySundayFragment fragment);

    void onAttach(DayMondayFragment fragment);

    void onAttach(DayTuesdayFragment fragment);

    void onAttach(DayWednesdayFragment fragment);

    void onAttach(DayThursdayFragment fragment);
}
