package com.ibnkhaldoun.studentside.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.ibnkhaldoun.studentside.R;
import com.ibnkhaldoun.studentside.adapters.MessagesAdapter;
import com.ibnkhaldoun.studentside.models.Mail;
import com.ibnkhaldoun.studentside.models.Message;

import java.util.List;

public class MessagesActivity extends AppCompatActivity {

    private FloatingActionButton mAddMessageFab;
    private RecyclerView mRecyclerView;
    private MessagesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        Toolbar toolbar = findViewById(R.id.messages_toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        Mail mail = intent.getParcelableExtra("Key");

        getSupportActionBar().setTitle(mail.getProfessor().getFullName());
        toolbar.setTitle(mail.getProfessor().getFullName());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        List<Message> list = mail.getMessages();

        mAddMessageFab = findViewById(R.id.messages_fab);
        mRecyclerView = findViewById(R.id.messages_recycler_view);
        mAdapter = new MessagesAdapter(this, list);
        mRecyclerView.setAdapter(mAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setHasFixedSize(true);

        mAddMessageFab.setOnClickListener(v -> {

        });
    }

}
