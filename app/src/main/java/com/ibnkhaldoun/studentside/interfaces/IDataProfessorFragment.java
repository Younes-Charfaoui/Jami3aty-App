/*
 * Copyright (c) 2018. This code was created by Younes Charfaoui in the process of Graduation Project for the year of  2018 , which is about creating a platform  for students and professors to help them in the communication and the get known of the university information and so on.
 */

package com.ibnkhaldoun.studentside.interfaces;


import com.ibnkhaldoun.studentside.fragments.DisplaysFragment;
import com.ibnkhaldoun.studentside.fragments.MessageFragment;
import com.ibnkhaldoun.studentside.fragments.NotificationFragment;

public interface IDataProfessorFragment {

    void onNeedData(DisplaysFragment displaysFragment);

    void onNeedData(MessageFragment messageFragment);


    void onAttach(DisplaysFragment displaysFragment);

    void onAttach(MessageFragment messageFragment);

}
