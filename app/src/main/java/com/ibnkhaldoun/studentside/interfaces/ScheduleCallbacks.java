/*------------------------------------------------------------------------------
 - Copyright (c) 2018. This code was created by Younes Charfaoui in the process of Graduation Project for the year of  2018 , which is about creating a platform  for students and professors to help them in the communication and the get known of the university information and so on.
 -----------------------------------------------------------------------------*/

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
