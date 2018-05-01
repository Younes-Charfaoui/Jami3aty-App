package com.ibnkhaldoun.studentside.activities;

import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.StringRes;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.ibnkhaldoun.studentside.R;
import com.ibnkhaldoun.studentside.Utilities.ActivityUtilities;
import com.ibnkhaldoun.studentside.Utilities.PreferencesManager;
import com.ibnkhaldoun.studentside.asyncTask.ResponseAsyncTask;
import com.ibnkhaldoun.studentside.models.Message;
import com.ibnkhaldoun.studentside.networking.models.RequestPackage;
import com.ibnkhaldoun.studentside.providers.EndPointsProvider;
import com.ibnkhaldoun.studentside.providers.KeyDataProvider;

import static com.ibnkhaldoun.studentside.networking.models.RequestPackage.POST;

public class NewMessageActivity extends AppCompatActivity {

    public static final String KEY_PROFESSOR = "keyProfessor";
    public static final String KEY_LAUNCHER = "keyLauncher";
    public static final String KEY_ID = "keyId ";
    public static final String MESSAGE = "message";

    private TextInputEditText mSubjectEditText, mMessageEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_message);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mSubjectEditText = findViewById(R.id.new_message_text_edit_subject);
        mMessageEditText = findViewById(R.id.new_message_text_edit_message);
        TextInputLayout messageWrapper = findViewById(R.id.new_message_text_input_message);
        TextInputLayout subjectWrapper = findViewById(R.id.new_message_text_input_subject);
        if (getIntent().hasExtra(MESSAGE)) {
            Message message = getIntent().getParcelableExtra(MESSAGE);
            String subject = "RE: " + message.getSubject();
            mSubjectEditText.setText(subject);
        }
        if (ActivityUtilities.whoIsUsing(this) == MessageDetailActivity.PROFESSOR) {
            subjectWrapper.setCounterEnabled(false);
            mSubjectEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(9999)});
            messageWrapper.setCounterEnabled(false);
            mMessageEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(9999)});
        }
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
                if (validate()) {

                    RequestPackage request = null;
                    if (ActivityUtilities.whoIsUsing(this) == MessageDetailActivity.STUDENT) {
                        //send tp professor
                        if (getIntent().hasExtra(MESSAGE)) {
                            Message message = getIntent().getParcelableExtra(MESSAGE);
                            request = new RequestPackage.Builder()
                                    .addMethod(POST)
                                    .addEndPoint(EndPointsProvider.getAddMailEndPoint())
                                    .addParams(KeyDataProvider.KEY_AJAX, KeyDataProvider.KEY_ANDROID)
                                    .addParams(KeyDataProvider.JSON_MAIL_MESSAGE, mMessageEditText.getText().toString().trim())
                                    .addParams(KeyDataProvider.JSON_MAIL_SUBJECT, mSubjectEditText.getText().toString().trim())
                                    .addParams(KeyDataProvider.JSON_MAIL_SENDER, "0")
                                    .addParams(KeyDataProvider.JSON_STUDENT_ID, String.valueOf(message.getIdStudent()))
                                    .addParams(KeyDataProvider.JSON_MAIL_ID_PROFESSOR, String.valueOf(message.getIdProfessor()))
                                    .create();
                        } else {
                            request = new RequestPackage.Builder()
                                    .addMethod(POST)
                                    .addEndPoint(EndPointsProvider.getAddMailEndPoint())
                                    .addParams(KeyDataProvider.KEY_AJAX, KeyDataProvider.KEY_ANDROID)
                                    .addParams(KeyDataProvider.JSON_MAIL_MESSAGE, mMessageEditText.getText().toString().trim())
                                    .addParams(KeyDataProvider.JSON_MAIL_SUBJECT, mSubjectEditText.getText().toString().trim())
                                    .addParams(KeyDataProvider.JSON_MAIL_SENDER, "0")
                                    .addParams(KeyDataProvider.JSON_STUDENT_ID, new PreferencesManager(this, PreferencesManager.STUDENT).getIdUser())
                                    .addParams(KeyDataProvider.JSON_MAIL_ID_PROFESSOR, getIntent().getStringExtra(KEY_ID))
                                    .create();
                        }
                    } else {
                        //send to student
                        if (getIntent().hasExtra(MESSAGE)) {
                            Message message = getIntent().getParcelableExtra(MESSAGE);
                            request = new RequestPackage.Builder()
                                    .addMethod(POST)
                                    .addEndPoint(EndPointsProvider.getAddMailEndPoint())
                                    .addParams(KeyDataProvider.KEY_AJAX, KeyDataProvider.KEY_ANDROID)
                                    .addParams(KeyDataProvider.JSON_MAIL_MESSAGE, mMessageEditText.getText().toString())
                                    .addParams(KeyDataProvider.JSON_MAIL_SUBJECT, mSubjectEditText.getText().toString())
                                    .addParams(KeyDataProvider.JSON_MAIL_SENDER, "1")
                                    .addParams(KeyDataProvider.JSON_STUDENT_ID, String.valueOf(message.getIdStudent()))
                                    .addParams(KeyDataProvider.JSON_MAIL_ID_PROFESSOR, String.valueOf(message.getIdProfessor()))
                                    .create();
                        }
                    }

                    ResponseAsyncTask task = new ResponseAsyncTask(response -> {
                        if (response.getStatus() == 200)
                            Toast.makeText(this, "Message was send.", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(this, "Message was not send, try later.", Toast.LENGTH_SHORT).show();
                    });
                    task.execute(request);
                    Toast.makeText(this, "Sending....", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    private boolean validate() {
        String message = mMessageEditText.getText().toString().trim();
        String subject = mSubjectEditText.getText().toString().trim();

        if (TextUtils.isEmpty(subject)) {
            Toast.makeText(this, "Please add a subject.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(message)) {
            Toast.makeText(this, "Please add a message.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
