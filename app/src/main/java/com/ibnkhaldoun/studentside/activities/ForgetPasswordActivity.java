package com.ibnkhaldoun.studentside.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ibnkhaldoun.studentside.R;
import com.ibnkhaldoun.studentside.asyncTask.ForgetPasswordAsyncTask;
import com.ibnkhaldoun.studentside.interfaces.TaskListener;
import com.ibnkhaldoun.studentside.networking.models.ForgetPasswordResponse;
import com.ibnkhaldoun.studentside.networking.models.LoginResponse;
import com.ibnkhaldoun.studentside.networking.models.RequestPackage;
import com.ibnkhaldoun.studentside.networking.models.SignUpResponse;
import com.ibnkhaldoun.studentside.networking.utilities.NetworkUtilities;
import com.ibnkhaldoun.studentside.providers.EndPointsProvider;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.KEY_ANDROID;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.KEY_BAC;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.KEY_CARD_NUMBER;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.KEY_EMAIL;
import static com.ibnkhaldoun.studentside.networking.models.Response.*;

public class ForgetPasswordActivity extends AppCompatActivity implements TaskListener {

    private TextView mAccountPleaseTextView;
    private Button mFindAccountButton;
    private EditText mEmailEditText, mCardNumberEditText, mBacAverageEditText;
    private TextInputLayout mEmailWrapper, mCardNumberWrapper, mBacAverageWrapper;
    private ProgressBar mProgressBar;

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
        mCardNumberEditText = findViewById(R.id.input_card_number_forget_password);
        mCardNumberWrapper = findViewById(R.id.card_number_wrapper_forget_password);
        mBacAverageEditText = findViewById(R.id.input_bac_forget_password);
        mBacAverageWrapper = findViewById(R.id.bac_wrapper_forget_password);
        mProgressBar = findViewById(R.id.forget_password_progress_bar);


        mFindAccountButton.setOnClickListener(v -> {
            if (validate()) {
                if (NetworkUtilities.isConnected(ForgetPasswordActivity.this)) {

                    RequestPackage requestPackage = new RequestPackage();
                    requestPackage.setEndPoint(EndPointsProvider.getForgetPasswordEndpoint());
                    requestPackage.setMethod(RequestPackage.POST);
                    requestPackage.addParams(KEY_ANDROID, KEY_ANDROID);
                    requestPackage.addParams(KEY_EMAIL, mEmailEditText.getText().toString());
                    requestPackage.addParams(KEY_CARD_NUMBER, mCardNumberEditText.getText().toString());
                    requestPackage.addParams(KEY_BAC, mBacAverageEditText.getText().toString());

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
        String cardNumber = mCardNumberEditText.getText().toString();
        String email = mEmailEditText.getText().toString();
        String bacAverage = mBacAverageEditText.getText().toString();

        if (cardNumber.isEmpty()) {
            mCardNumberWrapper.setError(getResources().getString(R.string.card_number_empty));
            validate = false;
        } else if (cardNumber.length() != 8) {
            mCardNumberWrapper.setError(getResources().getString(R.string.valid_card_number));
            validate = false;
        } else {
            mCardNumberWrapper.setError(null);
        }

        if (email.isEmpty()) {
            mEmailWrapper.setError(getResources().getString(R.string.email_empty));
            validate = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmailWrapper.setError(getResources().getString(R.string.valid_email));
            validate = false;
        } else {
            mEmailWrapper.setError(null);
        }

        if (bacAverage.isEmpty()) {
            mBacAverageWrapper.setError(getResources().getString(R.string.bac_average_empty));
            validate = false;
        } else if (!validAverage(bacAverage)) {
            mBacAverageWrapper.setError(getResources().getString(R.string.bac_average_invalid));
            validate = false;
        } else {
            mBacAverageWrapper.setError(null);
        }
        return validate;
    }

    private boolean validAverage(String average) {
        double averageDouble = Double.parseDouble(average);
        return !(averageDouble < 10 || averageDouble >= 20);
    }

    @Override
    public void onLoginCompletionListener(LoginResponse response) {
        // do nothing here
    }

    @Override
    public void onSignUpCompletionListener(SignUpResponse response) {
        // do nothing here
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
                mEmailWrapper.setVisibility(GONE);
                mBacAverageWrapper.setVisibility(GONE);
                mCardNumberWrapper.setVisibility(GONE);
                break;
            case RESPONSE_EMAIL_ERROR:
                mProgressBar.setVisibility(GONE);
                mFindAccountButton.setVisibility(VISIBLE);
                mAccountPleaseTextView.setVisibility(VISIBLE);
                mAccountPleaseTextView.setText("The information provided can't lead to an email");
                mAccountPleaseTextView.setTextColor(Color.RED);
                mEmailWrapper.setEnabled(true);
                mBacAverageWrapper.setEnabled(true);
                mCardNumberWrapper.setEnabled(true);
                break;
        }
    }
}
