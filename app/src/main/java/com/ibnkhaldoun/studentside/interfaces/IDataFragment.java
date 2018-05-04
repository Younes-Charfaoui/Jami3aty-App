/*------------------------------------------------------------------------------
 - Copyright (c) 2018. This code was created by Younes Charfaoui in the process of Graduation Project for the year of  2018 , which is about creating a platform  for students and professors to help them in the communication and the get known of the university information and so on.
 -----------------------------------------------------------------------------*/

package com.ibnkhaldoun.studentside.interfaces;


import com.ibnkhaldoun.studentside.fragments.DisplaysFragment;
import com.ibnkhaldoun.studentside.fragments.MessageFragment;
import com.ibnkhaldoun.studentside.fragments.NotificationFragment;

/**
 * this interface is for notifying the holder activity that
 * one of the child fragments need data or they have been attached,
 * by knowing this situation we will have a way to pass data to the
 * fragments.
 */
public interface IDataFragment {

    void onNeedData(DisplaysFragment displaysFragment);

    void onNeedData(MessageFragment messageFragment);

    void onNeedData(NotificationFragment notificationFragment);

    void onAttach(DisplaysFragment displaysFragment);

    void onAttach(MessageFragment messageFragment);

    void onAttach(NotificationFragment notificationFragment);
}
