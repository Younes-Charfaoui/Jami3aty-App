package com.ibnkhaldoun.studentside.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.ibnkhaldoun.studentside.R;
import com.ibnkhaldoun.studentside.models.Message;

public class MessageDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("FUCK", "onCreate: Fucking before");
        setContentView(R.layout.activity_message_detail);
        Log.i("FUCK", "onCreate: Fucking after");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Message message = getIntent().getParcelableExtra("Key");
        getSupportActionBar().setTitle(message.getSubject());
        toolbar.setTitle(message.getSubject());


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
