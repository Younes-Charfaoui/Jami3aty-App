package com.ibnkhaldoun.studentside.fragments;


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
import android.widget.ProgressBar;

import com.ibnkhaldoun.studentside.R;
import com.ibnkhaldoun.studentside.adapters.ScheduleAdapter;
import com.ibnkhaldoun.studentside.models.ScheduleItem;

import java.util.ArrayList;
import java.util.List;

public class DayScheduleFragment extends Fragment {

    private static final String KEY_INNER_DATA = "keyInnerData";

    private ScheduleAdapter mAdapter;
    private RecyclerView mDayScheduleRecyclerView;
    private ProgressBar mLoadingProgressbar;

    public static DayScheduleFragment newInstance(List<ScheduleItem> scheduleItemList) {
        Bundle args = new Bundle();
        args.putParcelableArrayList(KEY_INNER_DATA, (ArrayList<? extends Parcelable>) scheduleItemList);
        DayScheduleFragment fragment = new DayScheduleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.day_schedule_fragment, container, false);
        assert getArguments() != null;
        mDayScheduleRecyclerView = view.findViewById(R.id.day_schedule_recycler);
        mLoadingProgressbar = view.findViewById(R.id.day_schedule_progress);

        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        if (getArguments() != null) {
            if (getArguments().getParcelableArrayList(KEY_INNER_DATA) != null) {

            }
        }
        mAdapter = new ScheduleAdapter(getContext());
        mDayScheduleRecyclerView.setAdapter(mAdapter);
        mDayScheduleRecyclerView.setLayoutManager(manager);
        mDayScheduleRecyclerView.setHasFixedSize(true);
        return view;
    }
}
