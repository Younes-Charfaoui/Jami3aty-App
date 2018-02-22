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
import com.ibnkhaldoun.studentside.adapters.DisplaysAdapter;
import com.ibnkhaldoun.studentside.data_providers.DataProviders;
import com.ibnkhaldoun.studentside.models.Display;

import java.util.ArrayList;
import java.util.List;


public class DisplaysFragment extends Fragment {

    public static DisplaysFragment newInstance(List<Display> list) {

        Bundle args = new Bundle();
        args.putParcelableArrayList("Key", (ArrayList<? extends Parcelable>) list);
        DisplaysFragment fragment = new DisplaysFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_displays, container, false);

        assert getArguments() != null;
        List<Display> displays = getArguments().getParcelableArrayList("Key");
        RecyclerView recyclerView = view.findViewById(R.id.displays_recycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        DisplaysAdapter adapter = new DisplaysAdapter(getContext(), displays);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        return view;
    }
}
