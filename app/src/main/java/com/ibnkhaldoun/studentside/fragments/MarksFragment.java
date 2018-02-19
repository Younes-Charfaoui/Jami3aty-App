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
import com.ibnkhaldoun.studentside.adapters.MarksAdapter;
import com.ibnkhaldoun.studentside.models.Mark;

import java.util.ArrayList;
import java.util.List;


public class MarksFragment extends Fragment {

    public static MarksFragment newInstance(List<Mark> mListData) {

        Bundle args = new Bundle();
        args.putParcelableArrayList("Key", (ArrayList<? extends Parcelable>) mListData);
        MarksFragment fragment = new MarksFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_marks, container, false);
        assert getArguments() != null;
        List<Mark> marksList = getArguments().getParcelableArrayList("Key");
        RecyclerView recycler = view.findViewById(R.id.marks_recycler_view);
        MarksAdapter adapter = new MarksAdapter(marksList, getContext());
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(manager);
        recycler.setHasFixedSize(true);
        return view;
    }
}