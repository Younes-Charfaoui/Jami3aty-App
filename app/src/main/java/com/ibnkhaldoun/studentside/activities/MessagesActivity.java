package com.ibnkhaldoun.studentside.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.ibnkhaldoun.studentside.R;
import com.ibnkhaldoun.studentside.models.Mail;

public class MessagesActivity extends AppCompatActivity {

    private FloatingActionButton mAddMessageFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mAddMessageFab = findViewById(R.id.messages_fab);
        Intent intent = getIntent();
        if (intent != null) {
            Mail mail = intent.getParcelableExtra("Key");
            toolbar.setTitle(mail.getProfessor().getFullName());
        }
        //todo get the intent with the mails and professor name and set the title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
