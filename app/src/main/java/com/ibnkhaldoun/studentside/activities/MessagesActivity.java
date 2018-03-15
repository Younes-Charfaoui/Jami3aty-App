package com.ibnkhaldoun.studentside.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.ibnkhaldoun.studentside.R;
import com.ibnkhaldoun.studentside.adapters.MessagesAdapter;
import com.ibnkhaldoun.studentside.models.Mail;
import com.ibnkhaldoun.studentside.models.Message;

import java.util.List;

public class MessagesActivity extends AppCompatActivity {

    public static final String KEY_MESSAGES = "keyMessages";
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
        Mail mail = intent.getParcelableExtra(KEY_MESSAGES);
        assert getSupportActionBar() != null;
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
            Intent intentNewMessages = new Intent(MessagesActivity.this,
                    NewMessageActivity.class);
            intentNewMessages.putExtra(NewMessageActivity.KEY_PROFESSOR, mail.getProfessor());
            intentNewMessages.putExtra(NewMessageActivity.KEY_LAUNCHER, NewMessageActivity.MESSAGE);
            startActivity(intentNewMessages);
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
