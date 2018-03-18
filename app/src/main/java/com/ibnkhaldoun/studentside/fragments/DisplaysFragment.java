package com.ibnkhaldoun.studentside.fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ibnkhaldoun.studentside.R;
import com.ibnkhaldoun.studentside.adapters.DisplaysAdapter;
import com.ibnkhaldoun.studentside.interfaces.DataFragmentInterface;
import com.ibnkhaldoun.studentside.models.Display;

import java.util.ArrayList;
import java.util.List;

import static com.ibnkhaldoun.studentside.activities.StudentMainActivity.DISPLAY_TYPE;


public class DisplaysFragment extends BaseMainFragment<Display> {

    private ProgressBar mLoadingProgressBar;
    private LinearLayout mEmptyLayout;
    private RecyclerView mDisplaysRecyclerView;
    private DisplaysAdapter mAdapter;
    private DataFragmentInterface mInterface;

    public static DisplaysFragment newInstance(List<Display> list) {
        Bundle args = new Bundle();
        args.putParcelableArrayList("Key", (ArrayList<? extends Parcelable>) list);
        DisplaysFragment fragment = new DisplaysFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mInterface = (DataFragmentInterface) context;
        mInterface.onAttach(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_displays, container, false);
        mLoadingProgressBar = view.findViewById(R.id.display_progress_bar);
        mDisplaysRecyclerView = view.findViewById(R.id.displays_recycler_view);
        mEmptyLayout = view.findViewById(R.id.display_empty_view);

        assert getArguments() != null;
        List<Display> displays = getArguments().getParcelableArrayList("Key");

        mEmptyLayout.setOnClickListener(v -> mInterface.onNeedData(this));

        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mAdapter = new DisplaysAdapter(getContext());
        mAdapter.swapList(displays);
        mDisplaysRecyclerView.setLayoutManager(manager);
        mDisplaysRecyclerView.setAdapter(mAdapter);
        mDisplaysRecyclerView.setHasFixedSize(true);

        return view;
    }

    @Override
    public void onNetworkLoadedSucceed(List<Display> list) {
        mLoadingProgressBar.setVisibility(View.GONE);
        if (list.size() != 0) {
            mAdapter.swapList(list);
            mEmptyLayout.setVisibility(View.GONE);
            mDisplaysRecyclerView.setVisibility(View.VISIBLE);
        } else {
            mEmptyLayout.setVisibility(View.VISIBLE);
            mDisplaysRecyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onNetworkStartLoading() {
        mLoadingProgressBar.setVisibility(View.VISIBLE);
        mDisplaysRecyclerView.setVisibility(View.GONE);
        mEmptyLayout.setVisibility(View.GONE);
    }

    @Override
    public void onNetworkLoadingFailed(int errorType) {
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
        mDisplaysRecyclerView.setVisibility(View.GONE);
        mEmptyLayout.setVisibility(View.GONE);
    }

    @Override
    public int getBaseType() {
        return DISPLAY_TYPE;
    }
}
