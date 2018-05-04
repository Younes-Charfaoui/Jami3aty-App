/*------------------------------------------------------------------------------
 - Copyright (c) 2018. This code was created by Younes Charfaoui in the process of Graduation Project for the year of  2018 , which is about creating a platform  for students and professors to help them in the communication and the get known of the university information and so on.
 -----------------------------------------------------------------------------*/

package com.ibnkhaldoun.studentside.fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ibnkhaldoun.studentside.R;
import com.ibnkhaldoun.studentside.adapters.NotificationAdapter;
import com.ibnkhaldoun.studentside.interfaces.IDataFragment;
import com.ibnkhaldoun.studentside.models.Notification;

import java.util.ArrayList;
import java.util.List;

import static com.ibnkhaldoun.studentside.activities.StudentMainActivity.NOTIFICATION_TYPE;


public class NotificationFragment extends BaseMainFragment<Notification> implements SwipeRefreshLayout.OnRefreshListener {
    public static final String KEY_INNER_DATA = "keyInnerData";
    private RecyclerView mNotificationRecyclerView;
    private NotificationAdapter mAdapter;
    private ProgressBar mLoadingProgressBar;
    private LinearLayout mEmptyLayout;
    private IDataFragment mInterface;
    private SwipeRefreshLayout mNotificationSwipe;

    public static NotificationFragment newInstance(List<Notification> notificationList) {
        Bundle args = new Bundle();
        args.putParcelableArrayList(KEY_INNER_DATA, (ArrayList<? extends Parcelable>) notificationList);
        NotificationFragment fragment = new NotificationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mInterface = (IDataFragment) context;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);

        mNotificationRecyclerView = view.findViewById(R.id.notification_recycler_view);
        mLoadingProgressBar = view.findViewById(R.id.notification_progress_bar);
        mEmptyLayout = view.findViewById(R.id.notification_empty_view);
        mNotificationSwipe = view.findViewById(R.id.notification_swipe);

        mNotificationSwipe.setOnRefreshListener(this);
        mAdapter = new NotificationAdapter(getContext());

        mInterface.onNeedData(this);

        mEmptyLayout.setOnClickListener(v -> mInterface.onNeedData(this));

        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mNotificationRecyclerView.setLayoutManager(manager);
        mNotificationRecyclerView.setAdapter(mAdapter);
        mNotificationRecyclerView.setHasFixedSize(true);

        mInterface.onAttach(this);
        return view;
    }

    @Override
    public void onNetworkLoadedSucceed(List<Notification> list) {
        if (mNotificationSwipe.isRefreshing())
            mNotificationSwipe.setRefreshing(false);
        mLoadingProgressBar.setVisibility(View.GONE);
        if (list.size() != 0) {
            mAdapter.swapList(list);
            mEmptyLayout.setVisibility(View.GONE);
            mNotificationRecyclerView.setVisibility(View.VISIBLE);
        } else {
            mEmptyLayout.setVisibility(View.VISIBLE);
            mNotificationRecyclerView.setVisibility(View.GONE);
        }

    }

    @Override
    public void onNetworkStartLoading() {

        mLoadingProgressBar.setVisibility(View.VISIBLE);
        mNotificationRecyclerView.setVisibility(View.GONE);
        mEmptyLayout.setVisibility(View.GONE);

    }

    @Override
    public void onNetworkLoadingFailed(int errorType) {
        if (mNotificationSwipe.isRefreshing())
            mNotificationSwipe.setRefreshing(false);
        switch (errorType) {
            case INTERNET_ERROR:
                Toast.makeText(getContext(),
                        R.string.no_internet_connection_string,
                        Toast.LENGTH_SHORT).show();
                break;
        }

    }

    @Override
    public void onDatabaseLoadingFinished(Cursor cursor) {

    }

    @Override
    public void onDatabaseStartLoading() {
        mLoadingProgressBar.setVisibility(View.VISIBLE);
        mNotificationRecyclerView.setVisibility(View.GONE);
        mEmptyLayout.setVisibility(View.GONE);
    }

    @Override
    public int getBaseType() {
        return NOTIFICATION_TYPE;
    }

    @Override
    public void onRefresh() {
        mInterface.onNeedData(this);
    }
}
