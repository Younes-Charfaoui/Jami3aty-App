package com.ibnkhaldoun.studentside.activities;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.ibnkhaldoun.studentside.R;
import com.ibnkhaldoun.studentside.Utilities.PreferencesManager;
import com.ibnkhaldoun.studentside.asyncTask.ResponseAsyncTask;
import com.ibnkhaldoun.studentside.models.Professor;
import com.ibnkhaldoun.studentside.networking.models.RequestPackage;
import com.ibnkhaldoun.studentside.providers.EndPointsProvider;
import com.ibnkhaldoun.studentside.providers.KeyDataProvider;

import static com.ibnkhaldoun.studentside.networking.models.RequestPackage.POST;

public class NewMessageActivity extends AppCompatActivity {

    public static final String KEY_PROFESSOR = "keyProfessor";
    public static final String KEY_LAUNCHER = "keyLauncher";
    public static final String KEY_ID = "keyId ";

    private Professor mProfessor;
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

        String professor = getIntent().getStringExtra(KEY_PROFESSOR);
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
                    RequestPackage request = new RequestPackage.Builder()
                            .addMethod(POST)
                            .addEndPoint(EndPointsProvider.getAddMailEndPoint())
                            .addParams(KeyDataProvider.KEY_AJAX, KeyDataProvider.KEY_ANDROID)
                            .addParams(KeyDataProvider.JSON_MAIL_MESSAGE, mMessageEditText.getText().toString())
                            .addParams(KeyDataProvider.JSON_MAIL_SUBJECT, mSubjectEditText.getText().toString())
                            .addParams(KeyDataProvider.JSON_STUDENT_ID, new PreferencesManager(this, PreferencesManager.STUDENT).getId())
                            .addParams(KeyDataProvider.JSON_MAIL_ID_PROFESSOR, getIntent().getStringExtra(KEY_ID))
                            .create();

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
        }
        return true;
    }

    private boolean validate() {
        String message = mMessageEditText.getText().toString();
        String subject = mSubjectEditText.getText().toString();

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
