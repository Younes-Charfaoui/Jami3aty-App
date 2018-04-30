package com.ibnkhaldoun.studentside.interfaces;

import android.database.Cursor;

import java.util.List;


public interface LoadingDataCallbacks<D>  {
    int INTERNET_ERROR = 100;

    void onNetworkLoadedSucceed(int type, List<D> list);

    void onNetworkStartLoading(int type);

    void onNetworkLoadingFailed(int type, int errorType);

    void onDatabaseLoadingFinished(int type, Cursor cursor);

    void onDatabaseStartLoading(int type);
}
