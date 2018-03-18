package com.ibnkhaldoun.studentside.fragments;

import android.database.Cursor;
import android.support.v4.app.Fragment;

import java.util.List;


public abstract class BaseMainFragment<M> extends Fragment {

    public static final int INTERNET_ERROR = 100;

    public abstract void onNetworkLoadedSucceed(List<M> list);

    public abstract void onNetworkStartLoading();

    public abstract void onNetworkLoadingFailed(int errorType);

    public abstract void onDatabaseLoadingFinished(Cursor cursor);

    public abstract void onDatabaseStartLoading();

    public abstract int getBaseType();
}
