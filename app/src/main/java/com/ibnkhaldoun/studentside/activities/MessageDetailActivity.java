package com.ibnkhaldoun.studentside.activities;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.ibnkhaldoun.studentside.R;
import com.ibnkhaldoun.studentside.Utilities.PreferencesManager;
import com.ibnkhaldoun.studentside.Utilities.Utilities;
import com.ibnkhaldoun.studentside.models.Message;
import com.ibnkhaldoun.studentside.models.Professor;

import static com.ibnkhaldoun.studentside.Utilities.PreferencesManager.STUDENT;

public class MessageDetailActivity extends AppCompatActivity {

    public static final String KEY_MESSAGE = "keyMessage";
    public static final String KEY_PROFESSOR = "keyProfessor";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if( getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Message message = getIntent().getParcelableExtra(KEY_MESSAGE);
        Professor professor = getIntent().getParcelableExtra(KEY_PROFESSOR);
        getSupportActionBar().setTitle(message.getSubject());
        toolbar.setTitle(message.getSubject());

        TextView subjectTextView = findViewById(R.id.message_detail_subject);
        subjectTextView.setText(message.getSubject());
        PreferencesManager manager = new PreferencesManager(this,STUDENT);

        TextView senderShortNameTextView = findViewById(R.id.message_detail_sender_short_name);
        GradientDrawable circle = (GradientDrawable) senderShortNameTextView.getBackground();

        TextView senderNameTextView = findViewById(R.id.message_detail_sender_name);
        TextView receiverTextView = findViewById(R.id.message_detail_receiver);

        if (message.isIn()) {
            senderShortNameTextView.setText(professor.getShortName());
            circle.setColor(Utilities.getCircleColor(professor.getShortName().charAt(0), this));
            senderNameTextView.setText(professor.getFullName());

            receiverTextView.setText(String.format("to %s", manager.getFullName()));
        } else {

            senderShortNameTextView.setText(manager.getFullName());
            circle.setColor(Utilities.getCircleColor(manager.getFullName().charAt(0), this));
            senderNameTextView.setText(manager.getFullName());
            receiverTextView.setText(String.format("to %s", professor.getFullName()));
        }


        TextView textTextView = findViewById(R.id.message_detail_text);
        textTextView.setText(message.getText());

        TextView dateTextView = findViewById(R.id.message_detail_date);
        dateTextView.setText(message.getDate());

        TextView timeTextView = findViewById(R.id.message_detail_time);
        timeTextView.setText(Utilities.getDateFormat(message.getDate()));

        findViewById(R.id.message_detail_reply_button).setOnClickListener(v -> {
            //todo add code to handle replying messages
            Toast.makeText(this, "Replay Button", Toast.LENGTH_SHORT).show();
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.message_detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.message_detail_delete_menu:
                //todo add code to handle deleting messages
                Toast.makeText(this, "Delete Action", Toast.LENGTH_SHORT).show();
                break;

            case R.id.message_detail_reply_menu:
                //todo add code to handle replying messages
                Toast.makeText(this, "Reply Action", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }
}
