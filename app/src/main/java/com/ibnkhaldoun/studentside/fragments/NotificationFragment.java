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
import com.ibnkhaldoun.studentside.adapters.NotificationAdapter;
import com.ibnkhaldoun.studentside.models.Notification;

import java.util.ArrayList;
import java.util.List;


public class NotificationFragment extends Fragment {

    public static NotificationFragment newInstance(List<Notification> notificationList) {

        Bundle args = new Bundle();
        args.putParcelableArrayList("Key", (ArrayList<? extends Parcelable>) notificationList);
        NotificationFragment fragment = new NotificationFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.notification_recycler_view);
        assert getArguments() != null ;
        NotificationAdapter adapter = new NotificationAdapter(getContext(), getArguments().getParcelableArrayList("Key"));
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        return view;
    }
}
