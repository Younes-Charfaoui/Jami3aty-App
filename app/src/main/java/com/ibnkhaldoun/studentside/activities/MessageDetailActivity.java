package com.ibnkhaldoun.studentside.activities;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.ibnkhaldoun.studentside.R;
import com.ibnkhaldoun.studentside.Utilities.PreferencesManager;
import com.ibnkhaldoun.studentside.Utilities.Utilities;
import com.ibnkhaldoun.studentside.asyncTask.ResponseAsyncTask;
import com.ibnkhaldoun.studentside.models.Message;
import com.ibnkhaldoun.studentside.networking.models.RequestPackage;
import com.ibnkhaldoun.studentside.networking.utilities.NetworkUtilities;
import com.ibnkhaldoun.studentside.providers.EndPointsProvider;

import static com.ibnkhaldoun.studentside.Utilities.PreferencesManager.STUDENT;
import static com.ibnkhaldoun.studentside.networking.models.RequestPackage.POST;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.JSON_MAIL_ID2;

public class MessageDetailActivity extends AppCompatActivity {

    public static final String KEY_MESSAGE = "keyMessage";
    public static final String KEY_PROFESSOR = "keyProfessor";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Message message = getIntent().getParcelableExtra(KEY_MESSAGE);

        getSupportActionBar().setTitle(message.getSubject());
        toolbar.setTitle(message.getSubject());

        TextView subjectTextView = findViewById(R.id.message_detail_subject);
        subjectTextView.setText(message.getSubject());
        PreferencesManager manager = new PreferencesManager(this, STUDENT);

        TextView senderShortNameTextView = findViewById(R.id.message_detail_sender_short_name);
        GradientDrawable circle = (GradientDrawable) senderShortNameTextView.getBackground();

        TextView senderNameTextView = findViewById(R.id.message_detail_sender_name);
        TextView receiverTextView = findViewById(R.id.message_detail_receiver);

        if (message.isIn()) {
            senderShortNameTextView.setText(Utilities.getProfessorShortName(message.getProfessor()));
            circle.setColor(Utilities.getCircleColor(this));
            senderNameTextView.setText(message.getProfessor());
            receiverTextView.setText(String.format("to %s", manager.getFullName()));
        } else {

            senderShortNameTextView.setText(Utilities.getStudentShortName(manager.getFullName()));
            circle.setColor(Utilities.getCircleColor(this));
            senderNameTextView.setText(manager.getFullName());
            receiverTextView.setText(String.format("to %s", message.getProfessor()));
        }


        TextView textTextView = findViewById(R.id.message_detail_text);
        textTextView.setText(message.getText());

        TextView timeTextView = findViewById(R.id.message_detail_date);
        timeTextView.setText(Utilities.getDateFormat(message.getDate()));

    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.message_detail_menu, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
/*
            case R.id.message_detail_delete_menu:
                if (NetworkUtilities.isConnected(this)) {
                    Message message = getIntent().getParcelableExtra(KEY_MESSAGE);
                    new AlertDialog.Builder(this)
                            .setTitle("Delete Mail")
                            .setMessage("Are you sure that you want to delete this email ?")
                            .setPositiveButton(R.string.delete_string, (dialog, which) -> {
                                RequestPackage request = new RequestPackage.Builder()
                                        .addMethod(POST)
                                        .addEndPoint(EndPointsProvider.getRemoveMailEndPoint())
                                        .addParams(JSON_MAIL_ID2, String.valueOf(message.getId()))
                                        .create();
                                ResponseAsyncTask task = new ResponseAsyncTask(response -> {
                                    if (response.getStatus() == 200) {
                                        Toast.makeText(this, "Mail was deleted", Toast.LENGTH_SHORT).show();
                                        finish();
                                    } else {
                                        Toast.makeText(this, "An error ocurred", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                task.execute(request);
                            })
                            .setNegativeButton(android.R.string.no, null)
                            .show();
                } else {
                    Toast.makeText(this, R.string.no_internet_connection_string, Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.message_detail_reply_menu:
                //todo add code to handle replying messages
                Toast.makeText(this, "Reply Action", Toast.LENGTH_SHORT).show();
                break;
*/
        }
        return true;
    }
}
