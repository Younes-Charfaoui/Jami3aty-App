package com.ibnkhaldoun.studentside.fragments;

import android.os.Bundle;
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
import com.ibnkhaldoun.studentside.data_providers.DataProviders;


public class NotificationFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.notification_recycler_view);
        NotificationAdapter adapter = new NotificationAdapter(getContext(), DataProviders.getNotificationList());
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        return view;
    }
}
