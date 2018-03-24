package com.ibnkhaldoun.studentside.fragments;


import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.ibnkhaldoun.studentside.R;
import com.ibnkhaldoun.studentside.adapters.ScheduleAdapter;
import com.ibnkhaldoun.studentside.interfaces.ScheduleCallbacks;
import com.ibnkhaldoun.studentside.models.ScheduleItem;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class DayScheduleFragment extends Fragment {

    private static final String KEY_INNER_DATA = "keyInnerData";
    private static int mDay;
    private ScheduleAdapter mAdapter;
    private RecyclerView mDayScheduleRecyclerView;
    private ProgressBar mLoadingProgressbar;
    private LinearLayout mEmptyLayout;

    public static DayScheduleFragment newInstance(List<ScheduleItem> scheduleItemList, int day) {
        mDay = day;
        return new DayScheduleFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ScheduleCallbacks scheduleListener = (ScheduleCallbacks) context;
        scheduleListener.onAttach(this, mDay);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.day_schedule_fragment, container, false);

        mDayScheduleRecyclerView = view.findViewById(R.id.day_schedule_recycler);
        mLoadingProgressbar = view.findViewById(R.id.day_schedule_progress);
        mEmptyLayout = view.findViewById(R.id.schedule_empty_view);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        mAdapter = new ScheduleAdapter(getContext());
        mDayScheduleRecyclerView.setAdapter(mAdapter);
        mDayScheduleRecyclerView.setLayoutManager(manager);
        mDayScheduleRecyclerView.setHasFixedSize(true);
        return view;
    }

    public void setScheduleList(List<ScheduleItem> list) {
        mLoadingProgressbar.setVisibility(GONE);
        if (list.size() != 0) {
            mAdapter.setList(list);
            mDayScheduleRecyclerView.setVisibility(VISIBLE);
        } else {
            mEmptyLayout.setVisibility(VISIBLE);
        }
    }
}
