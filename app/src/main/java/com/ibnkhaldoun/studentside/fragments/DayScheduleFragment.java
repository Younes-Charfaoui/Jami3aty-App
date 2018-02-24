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

import com.ibnkhaldoun.studentside.R;
import com.ibnkhaldoun.studentside.adapters.ScheduleAdapter;
import com.ibnkhaldoun.studentside.models.ScheduleItem;

import java.util.ArrayList;
import java.util.List;

public class DayScheduleFragment extends Fragment {

    public static DayScheduleFragment newInstance(List<ScheduleItem> scheduleItemList) {

        Bundle args = new Bundle();
        args.putParcelableArrayList("Key", (ArrayList<? extends Parcelable>) scheduleItemList);
        DayScheduleFragment fragment = new DayScheduleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.day_schedule_fragment, container, false);
        assert getArguments() != null;
        RecyclerView dayScheduleRecyclerView = view.findViewById(R.id.day_schedule_recycler);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        ScheduleAdapter adapter = new ScheduleAdapter(getContext(), getArguments().getParcelableArrayList("Key"));
        dayScheduleRecyclerView.setAdapter(adapter);
        dayScheduleRecyclerView.setLayoutManager(manager);
        dayScheduleRecyclerView.setHasFixedSize(true);
        return view;
    }
}
