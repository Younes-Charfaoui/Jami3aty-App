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
import com.ibnkhaldoun.studentside.adapters.MailAdapter;
import com.ibnkhaldoun.studentside.models.Mail;

import java.security.Security;
import java.util.ArrayList;
import java.util.List;


public class MailFragment extends Fragment {


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
        RecyclerView recyclerView = view.findViewById(R.id.mail_recycler_view);
        assert getArguments() != null;
        List<Mail> mailList = getArguments().getParcelableArrayList("Key");
        MailAdapter adapter = new MailAdapter(getContext(), mailList);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        return view;
    }
}
