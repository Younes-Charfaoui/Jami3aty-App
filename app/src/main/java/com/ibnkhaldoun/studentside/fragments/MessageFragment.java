package com.ibnkhaldoun.studentside.fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ibnkhaldoun.studentside.R;
import com.ibnkhaldoun.studentside.adapters.MessageAdapter;
import com.ibnkhaldoun.studentside.interfaces.IDataFragment;
import com.ibnkhaldoun.studentside.models.Mail;
import com.ibnkhaldoun.studentside.models.Message;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.ibnkhaldoun.studentside.activities.StudentMainActivity.MAIL_TYPE;


public class MessageFragment extends BaseMainFragment<Message> {

    private LinearLayout mEmptyLayout;
    private RecyclerView mMailRecyclerView;
    private MessageAdapter mAdapter;
    private ProgressBar mLoadingProgressBar;

    private IDataFragment mailInterface;


    public static MessageFragment newInstance(List<Mail> mails) {
        Bundle args = new Bundle();
        args.putParcelableArrayList("Key", (ArrayList<? extends Parcelable>) mails);
        MessageFragment fragment = new MessageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mailInterface = (IDataFragment) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mail, container, false);

        mMailRecyclerView = view.findViewById(R.id.mail_recycler_view);
        mLoadingProgressBar = view.findViewById(R.id.mail_progress_bar);
        mEmptyLayout = view.findViewById(R.id.mail_empty_view);

        mEmptyLayout.setOnClickListener(v -> mailInterface.onNeedData(this));

        mAdapter = new MessageAdapter(getContext());

        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mMailRecyclerView.setAdapter(mAdapter);
        mMailRecyclerView.setLayoutManager(manager);
        mMailRecyclerView.setHasFixedSize(true);
        mailInterface.onAttach(this);
        return view;
    }

    @Override
    public void onNetworkLoadedSucceed(List<Message> list) {

        mLoadingProgressBar.setVisibility(GONE);
        if (list.size() != 0) {
            mAdapter.setMailList(list);
            mEmptyLayout.setVisibility(GONE);
            mMailRecyclerView.setVisibility(VISIBLE);
        } else {
            mEmptyLayout.setVisibility(VISIBLE);
            mMailRecyclerView.setVisibility(GONE);
        }
    }

    @Override
    public void onNetworkStartLoading() {
        mEmptyLayout.setVisibility(GONE);
        mMailRecyclerView.setVisibility(GONE);
        mLoadingProgressBar.setVisibility(VISIBLE);
    }

    @Override
    public void onNetworkLoadingFailed(int errorType) {

        switch (errorType) {
            case INTERNET_ERROR:
                mLoadingProgressBar.setVisibility(GONE);
                mEmptyLayout.setVisibility(VISIBLE);
                mMailRecyclerView.setVisibility(GONE);
                Toast.makeText(getContext(),
                        R.string.no_internet_connection_string,
                        Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onDatabaseLoadingFinished(Cursor cursor) {

        mLoadingProgressBar.setVisibility(GONE);
        if (cursor.getCount() != 0) {
            //mAdapter.setMailList(getMailsFromCursor(cursor));
            mMailRecyclerView.setVisibility(VISIBLE);
        } else {
            mEmptyLayout.setVisibility(VISIBLE);
        }
    }

    @Override
    public void onDatabaseStartLoading() {
        mLoadingProgressBar.setVisibility(VISIBLE);
        mMailRecyclerView.setVisibility(GONE);
        mEmptyLayout.setVisibility(GONE);
    }

    @Override
    public int getBaseType() {
        return MAIL_TYPE;
    }
}
