package com.ibnkhaldoun.studentside.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ibnkhaldoun.studentside.R;
import com.ibnkhaldoun.studentside.asyncTask.ForgetPasswordAsyncTask;
import com.ibnkhaldoun.studentside.interfaces.ForgetPasswordTaskListener;
import com.ibnkhaldoun.studentside.networking.models.ForgetPasswordResponse;
import com.ibnkhaldoun.studentside.networking.models.RequestPackage;
import com.ibnkhaldoun.studentside.networking.utilities.NetworkUtilities;
import com.ibnkhaldoun.studentside.providers.EndPointsProvider;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.ibnkhaldoun.studentside.networking.models.Response.IO_EXCEPTION;
import static com.ibnkhaldoun.studentside.networking.models.Response.JSON_EXCEPTION;
import static com.ibnkhaldoun.studentside.networking.models.Response.RESPONSE_EMAIL_ERROR;
import static com.ibnkhaldoun.studentside.networking.models.Response.RESPONSE_SUCCESS;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.KEY_ANDROID;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.KEY_EMAIL;

public class ForgetPasswordActivity extends AppCompatActivity implements ForgetPasswordTaskListener {

    private TextView mAccountPleaseTextView;
    private Button mFindAccountButton;
    private EditText mEmailEditText;
    private TextInputLayout mEmailWrapper;
    private ProgressBar mProgressBar;
    private ImageView mEmailImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mFindAccountButton = findViewById(R.id.find_account);
        mAccountPleaseTextView = findViewById(R.id.account_search_textView);
        mEmailEditText = findViewById(R.id.input_email_forget_password);
        mEmailWrapper = findViewById(R.id.email_wrapper_forget_password);
        mEmailImage = findViewById(R.id.email_image_view);
        mProgressBar = findViewById(R.id.forget_password_progress_bar);


        mFindAccountButton.setOnClickListener(v -> {
            if (validate()) {
                if (NetworkUtilities.isConnected(ForgetPasswordActivity.this)) {

                    RequestPackage requestPackage = new RequestPackage();
                    requestPackage.setEndPoint(EndPointsProvider.getForgetPasswordEndpoint());
                    requestPackage.setMethod(RequestPackage.POST);
                    requestPackage.addParams(KEY_ANDROID, KEY_ANDROID);
                    requestPackage.addParams(KEY_EMAIL, mEmailEditText.getText().toString());

                    mProgressBar.setVisibility(VISIBLE);
                    mFindAccountButton.setVisibility(GONE);
                    mAccountPleaseTextView.setVisibility(GONE);
                    mEmailWrapper.setEnabled(false);

                    ForgetPasswordAsyncTask forgetPasswordAsyncTask =
                            new ForgetPasswordAsyncTask(ForgetPasswordActivity.this);
                    forgetPasswordAsyncTask.execute(requestPackage);
                } else {
                    Toast.makeText(this, R.string.no_internet_connection_string, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validate() {

        boolean validate = true;

        String email = mEmailEditText.getText().toString();

        if (email.isEmpty()) {
            mEmailWrapper.setError(getResources().getString(R.string.email_empty));
            validate = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmailWrapper.setError(getResources().getString(R.string.valid_email));
            validate = false;
        } else {
            mEmailWrapper.setError(null);
        }

        return validate;
    }

    @Override
    public void onForgetPasswordCompletionListener(ForgetPasswordResponse response) {
        switch (response.getStatus()) {
            case RESPONSE_SUCCESS:
                mProgressBar.setVisibility(GONE);
                mFindAccountButton.setText(R.string.done);
                mFindAccountButton.setVisibility(VISIBLE);
                mAccountPleaseTextView.setText(R.string.rest_password_string);
                mAccountPleaseTextView.setVisibility(VISIBLE);
                mAccountPleaseTextView.setTextColor(Color.BLACK);
                mFindAccountButton.setOnClickListener(v -> finish());
                mEmailImage.setVisibility(GONE);
                mEmailWrapper.setVisibility(GONE);
                break;
            case RESPONSE_EMAIL_ERROR:
                mProgressBar.setVisibility(GONE);
                mFindAccountButton.setVisibility(VISIBLE);
                mAccountPleaseTextView.setVisibility(VISIBLE);
                mAccountPleaseTextView.setText(R.string.cant_find_email);
                mAccountPleaseTextView.setTextColor(Color.RED);
                mEmailWrapper.setEnabled(true);
                break;
            case JSON_EXCEPTION:
                mProgressBar.setVisibility(GONE);
                mFindAccountButton.setVisibility(VISIBLE);
                mAccountPleaseTextView.setVisibility(VISIBLE);
                mAccountPleaseTextView.setTextColor(Color.RED);
                mEmailWrapper.setEnabled(true);
                Toast.makeText(this, R.string.errior_json, Toast.LENGTH_SHORT).show();
                break;
            case IO_EXCEPTION:
                mProgressBar.setVisibility(GONE);
                mFindAccountButton.setVisibility(VISIBLE);
                mAccountPleaseTextView.setVisibility(VISIBLE);
                mAccountPleaseTextView.setTextColor(Color.RED);
                mEmailWrapper.setEnabled(true);
                Toast.makeText(this, R.string.error_io_exception, Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
