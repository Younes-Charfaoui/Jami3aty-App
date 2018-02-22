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
import com.ibnkhaldoun.studentside.Utilities.Utils;
import com.ibnkhaldoun.studentside.models.Message;

public class MessageDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);
        assert getSupportActionBar() != null;

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Message message = getIntent().getParcelableExtra("Key");
        getSupportActionBar().setTitle(message.getSubject());
        toolbar.setTitle(message.getSubject());

        TextView subjectTextView = findViewById(R.id.message_detail_subject);
        subjectTextView.setText(message.getSubject());

        TextView senderShortNameTextView = findViewById(R.id.message_detail_sender_short_name);
        senderShortNameTextView.setText(message.getSender().getShortName());


        GradientDrawable circle = (GradientDrawable) senderShortNameTextView.getBackground();
        circle.setColor(Utils.getCircleColor(message.getSender().getShortName().charAt(0), this));
        TextView senderNameTextView = findViewById(R.id.message_detail_sender_name);
        senderNameTextView.setText(message.getSender().getFullName());

        TextView receiverTextView = findViewById(R.id.message_detail_receiver);
        receiverTextView.setText(String.format("to %s", message.getReceiver().getFullName()));

        TextView textTextView = findViewById(R.id.message_detail_text);
        textTextView.setText(message.getText());

        TextView dateTextView = findViewById(R.id.message_detail_date);
        dateTextView.setText(message.getDate());

        TextView timeTextView = findViewById(R.id.message_detail_time);
        timeTextView.setText("17:15");

        findViewById(R.id.message_detail_reply_button).setOnClickListener(v -> {
            //todo add code to handle replying messages
            Toast.makeText(this, "Replay Button", Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.message_detail_forward_button).setOnClickListener(v -> {
            //todo add code to handle forwarding messages
            Toast.makeText(this, "Forward Button", Toast.LENGTH_SHORT).show();
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
            case R.id.message_detail_forward_menu:
                //todo add code to handle forwarding messages
                Toast.makeText(this, "Forward Action", Toast.LENGTH_SHORT).show();
                break;

            case R.id.message_detail_reply_menu:
                //todo add code to handle replying messages
                Toast.makeText(this, "Reply Action", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }
}
