package com.ibnkhaldoun.studentside.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.ibnkhaldoun.studentside.R;
import com.ibnkhaldoun.studentside.models.Professor;

public class NewMessageActivity extends AppCompatActivity {

    public static final String KEY_PROFESSOR = "keyProfessor";
    public static final String KEY_LAUNCHER = "keyLauncher";

    public static final int DRAFT = 2;
    public static final int MESSAGE = 1;


    private Professor mProfessor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_message);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        int type = getIntent().getIntExtra(KEY_LAUNCHER, -1);
        switch (type) {
            case MESSAGE:

                break;
            case DRAFT:
                break;
            default:
                finish();
        }

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_new_message, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_new_message_send:
                // TODO: 15/03/2018 add the code to send the message
                break;
        }
        return true;
    }
}
