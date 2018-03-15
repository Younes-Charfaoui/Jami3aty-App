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
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.ibnkhaldoun.studentside.R;
import com.ibnkhaldoun.studentside.adapters.MailAdapter;
import com.ibnkhaldoun.studentside.models.Mail;

import java.util.ArrayList;
import java.util.List;


public class MailFragment extends Fragment {

    private LinearLayout mEmptyLayout;
    private RecyclerView mMailRecyclerView;
    private MailAdapter mAdapter;
    private ProgressBar mLoadingProgressBar;

    public static MailFragment newInstance(List<Mail> mails) {
        Bundle args = new Bundle();
        args.putParcelableArrayList("Key", (ArrayList<? extends Parcelable>) mails);
        MailFragment fragment = new MailFragment();
        fragment.setArguments(args);

        return fragment;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mail, container, false);
        mMailRecyclerView = view.findViewById(R.id.mail_recycler_view);
        mLoadingProgressBar = view.findViewById(R.id.mail_progress_bar);
        assert getArguments() != null;
        List<Mail> mailList = getArguments().getParcelableArrayList("Key");
        mAdapter = new MailAdapter(getContext());
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mMailRecyclerView.setAdapter(mAdapter);
        mMailRecyclerView.setLayoutManager(manager);
        mMailRecyclerView.setHasFixedSize(true);
        return view;
    }
}
