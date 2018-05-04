/*------------------------------------------------------------------------------
 - Copyright (c) 2018. This code was created by Younes Charfaoui in the process of Graduation Project for the year of  2018 , which is about creating a platform  for students and professors to help them in the communication and the get known of the university information and so on.
 -----------------------------------------------------------------------------*/

package com.ibnkhaldoun.studentside.fragments;

import android.database.Cursor;
import android.support.v4.app.Fragment;

import java.util.List;


public abstract class BaseMainFragment<DataType> extends Fragment {

    public static final int INTERNET_ERROR = 100;

    public abstract void onNetworkLoadedSucceed(List<DataType> list);

    public abstract void onNetworkStartLoading();

    public abstract void onNetworkLoadingFailed(int errorType);

    public abstract void onDatabaseLoadingFinished(Cursor cursor);

    public abstract void onDatabaseStartLoading();

    public abstract int getBaseType();
}
