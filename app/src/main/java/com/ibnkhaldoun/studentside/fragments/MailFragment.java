package com.ibnkhaldoun.studentside.fragments;

import android.content.Context;
import android.database.Cursor;
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
import android.widget.Toast;

import com.ibnkhaldoun.studentside.R;
import com.ibnkhaldoun.studentside.Utilities.Utilities;
import com.ibnkhaldoun.studentside.adapters.MailAdapter;
import com.ibnkhaldoun.studentside.database.DatabaseContract;
import com.ibnkhaldoun.studentside.interfaces.DataFragmentInterface;
import com.ibnkhaldoun.studentside.interfaces.LoadingDataCallbacks;
import com.ibnkhaldoun.studentside.models.Mail;
import com.ibnkhaldoun.studentside.models.Message;
import com.ibnkhaldoun.studentside.models.Professor;

import java.util.ArrayList;
import java.util.List;

import static com.ibnkhaldoun.studentside.activities.StudentMainActivity.MAIL_TYPE;


public class MailFragment extends Fragment implements LoadingDataCallbacks {

    private LinearLayout mEmptyLayout;
    private RecyclerView mMailRecyclerView;
    private MailAdapter mAdapter;
    private ProgressBar mLoadingProgressBar;

    private DataFragmentInterface mailInterface;

    public static MailFragment newInstance(List<Mail> mails) {
        Bundle args = new Bundle();
        args.putParcelableArrayList("Key", (ArrayList<? extends Parcelable>) mails);
        MailFragment fragment = new MailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mailInterface = (DataFragmentInterface) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mail, container, false);
        mMailRecyclerView = view.findViewById(R.id.mail_recycler_view);
        mLoadingProgressBar = view.findViewById(R.id.mail_progress_bar);
        mEmptyLayout = view.findViewById(R.id.mail_empty_view);

        mEmptyLayout.setOnClickListener(v -> mailInterface.onNeedData(MAIL_TYPE));

        assert getArguments() != null;
        List<Mail> mailList = getArguments().getParcelableArrayList("Key");

        mAdapter = new MailAdapter(getContext());
        mAdapter.setMailList(mailList);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mMailRecyclerView.setAdapter(mAdapter);
        mMailRecyclerView.setLayoutManager(manager);
        mMailRecyclerView.setHasFixedSize(true);

        return view;
    }


    @Override
    public void onNetworkLoadedSucceed(int type, List list) {
        if (type == MAIL_TYPE) {
            mLoadingProgressBar.setVisibility(View.GONE);
            if (list.size() != 0) {
                mAdapter.setMailList(list);
                mEmptyLayout.setVisibility(View.GONE);
                mMailRecyclerView.setVisibility(View.VISIBLE);
            } else {
                mEmptyLayout.setVisibility(View.VISIBLE);
                mMailRecyclerView.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onNetworkStartLoading(int type) {
        if (type == MAIL_TYPE) {
            mEmptyLayout.setVisibility(View.GONE);
            mMailRecyclerView.setVisibility(View.GONE);
            mLoadingProgressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onNetworkLoadingFailed(int type, int errorType) {
        if (type == MAIL_TYPE) {
            switch (errorType) {
                case INTERNET_ERROR:
                    Toast.makeText(getContext(),
                            R.string.no_internet_connection_string,
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    @Override
    public void onDatabaseLoadingFinished(int type, Cursor cursor) {
        if (type == MAIL_TYPE) {
            mLoadingProgressBar.setVisibility(View.GONE);
            if (cursor.getCount() != 0) {
                mAdapter.setMailList(getMailsFromCursor(cursor));
                mMailRecyclerView.setVisibility(View.VISIBLE);
            } else {
                mEmptyLayout.setVisibility(View.VISIBLE);
            }
        }
    }

    private List<Mail> getMailsFromCursor(Cursor cursor) {

        List<Mail> list = new ArrayList<>();
        long professorId = -1;
        List<Message> messages = new ArrayList<>();
        Professor professor = new Professor();

        while (cursor.moveToNext()) {
            String professorName = cursor.getString(cursor.getColumnIndex(DatabaseContract.MailEntry.COLUMN_PROFESSOR_NAME));
            long professorId2 = cursor.getLong(cursor.getColumnIndex(DatabaseContract.MailEntry.COLUMN_PROFESSOR_ID));
            String subject = cursor.getString(cursor.getColumnIndex(DatabaseContract.MailEntry.COLUMN_MESSAGE_SUBJECT));
            String text = cursor.getString(cursor.getColumnIndex(DatabaseContract.MailEntry.COLUMN_MESSAGE_SUBJECT));
            String date = cursor.getString(cursor.getColumnIndex(DatabaseContract.MailEntry.COLUMN_MESSAGE_SUBJECT));
            int in = cursor.getInt(cursor.getColumnIndex(DatabaseContract.MailEntry.COLUMN_MESSAGE_SUBJECT));
            Message message = new Message(subject, text, date, in == 1);
            if (professorId != professorId2 && professorId != -1) {
                list.add(new Mail(professor, messages));
                professor = new Professor();
                professor.setFirstName(Utilities.getFirstName(professorName));
                professor.setLastName(Utilities.getLastName(professorName));
                professor.setId(String.valueOf(professorId));
                messages = new ArrayList<>();
            }
            messages.add(message);
            professorId = professorId2;
        }
        return list;
    }

    @Override
    public void onDatabaseStartLoading(int type) {
        if (type == MAIL_TYPE) {
            mLoadingProgressBar.setVisibility(View.VISIBLE);
            mMailRecyclerView.setVisibility(View.GONE);
            mEmptyLayout.setVisibility(View.GONE);
        }
    }
}
