package com.ibnkhaldoun.studentside.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout;
import com.ibnkhaldoun.studentside.R;
import com.ibnkhaldoun.studentside.asyncTask.SignUpAsyncTask;
import com.ibnkhaldoun.studentside.interfaces.TaskListener;
import com.ibnkhaldoun.studentside.networking.models.ForgetPasswordResponse;
import com.ibnkhaldoun.studentside.networking.models.LoginResponse;
import com.ibnkhaldoun.studentside.networking.models.RequestPackage;
import com.ibnkhaldoun.studentside.networking.models.SignUpResponse;
import com.ibnkhaldoun.studentside.networking.utilities.NetworkUtilities;
import com.ibnkhaldoun.studentside.providers.EndPointsProvider;

import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.*;
import static com.ibnkhaldoun.studentside.networking.models.Response.*;

public class SignUpActivity extends AppCompatActivity
        implements View.OnFocusChangeListener, TaskListener {

    private EditText mEmailEditText, mPasswordEditText, mCardNumberEditText, mBacAverageEditText;
    private TextInputLayout mEmailWrapper, mCardNumberWrapper, mPasswordWrapper, mBacAverageWrapper;
    private Menu mMenu;
    private ProgressBar mLoadingProgressBar;
    private Button mSignUpButton;
    private TextView mTitleTextView , mSubTitleTextView;
    private LinearLayout mFieldLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white);


        mCardNumberWrapper = findViewById(R.id.card_number_wrapper_sign_up);
        mEmailWrapper = findViewById(R.id.email_wrapper_sign_up);
        mPasswordWrapper = findViewById(R.id.password_wrapper_sign_up);
        mBacAverageWrapper = findViewById(R.id.bac_average_wrapper_sign_up);


        mCardNumberEditText = findViewById(R.id.input_card_number_sign_up);
        mBacAverageEditText = findViewById(R.id.input_bac_average_sign_up);
        mEmailEditText = findViewById(R.id.input_email_sign_up);
        mPasswordEditText = findViewById(R.id.input_password_sign_up);

        mCardNumberEditText.setOnFocusChangeListener(this);
        mBacAverageEditText.setOnFocusChangeListener(this);
        mEmailEditText.setOnFocusChangeListener(this);
        mPasswordEditText.setOnFocusChangeListener(this);
        mLoadingProgressBar = findViewById(R.id.loading_progressBar_signUp);
        mSignUpButton = findViewById(R.id.sign_up_button);

        mTitleTextView = findViewById(R.id.sing_up_title_text_view);
        mSubTitleTextView = findViewById(R.id.sing_up_subtitle_text_view);

        mFieldLinearLayout = findViewById(R.id.sign_up_field_linear_layout);

        mSignUpButton.setOnClickListener(v -> {
            if (validate()) {
                hideKeyboard();

                if (!NetworkUtilities.isConnected(this)) {
                    Toast.makeText(this, R.string.no_internet_connection_string, Toast.LENGTH_SHORT).show();
                } else {
                    mCardNumberWrapper.setEnabled(false);
                    mEmailWrapper.setEnabled(false);
                    mPasswordWrapper.setEnabled(false);
                    mBacAverageWrapper.setEnabled(false);
                    mMenu.findItem(R.id.action_help).setEnabled(false);
                    mMenu.findItem(android.R.id.home).setEnabled(false);
                    getSupportActionBar().setHomeButtonEnabled(false);

                    RequestPackage requestPackage = new RequestPackage();
                    requestPackage.setEndPoint(EndPointsProvider.getSignUpEndpoint());
                    requestPackage.setMethod(RequestPackage.POST);
                    requestPackage.addParams(KEY_ANDROID, KEY_ANDROID);
                    requestPackage.addParams(KEY_BAC, mBacAverageEditText.getText().toString());
                    requestPackage.addParams(KEY_CARD_NUMBER, mCardNumberEditText.getText().toString());
                    requestPackage.addParams(KEY_EMAIL, mEmailEditText.getText().toString());
                    requestPackage.addParams(KEY_PASSWORD, mPasswordEditText.getText().toString());

                    SignUpAsyncTask signUpAsyncTask = new SignUpAsyncTask(this);
                    signUpAsyncTask.execute(requestPackage);

                    mSignUpButton.setVisibility(View.GONE);
                    mLoadingProgressBar.setVisibility(View.VISIBLE);
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sign_up, menu);
        mMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_help:
                startActivity(new Intent(this, HelpActivity.class));
                break;
            case android.R.id.home:
                if (isEmpty()) {
                    finish();
                } else {
                    new AlertDialog.Builder(this)
                            .setTitle("Cancel Account Creation")
                            .setMessage("Are you sure you want to cancel account creation? this will discard any information you've entered so far.")
                            .setPositiveButton("Yes", (dialog, which) -> finish())
                            .setNegativeButton("No", null)
                            .create().show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * validate a set of inputs with specificthings
     *
     * @return a boolean
     */
    private boolean validate() {
        boolean validate = true;
        String cardNumber = mCardNumberEditText.getText().toString();
        String email = mEmailEditText.getText().toString();
        String password = mPasswordEditText.getText().toString();
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

        if (password.isEmpty()) {
            mPasswordWrapper.setError(getResources().getString(R.string.password_empty));
            validate = false;
        } else if (password.length() < 8) {
            mPasswordWrapper.setError(getResources().getString(R.string.valid_password));
            validate = false;
        } else {
            mPasswordWrapper.setError(null);
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

    @Override
    public void onBackPressed() {
        if (isEmpty()) {
            super.onBackPressed();
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Cancel Account Creation")
                    .setMessage("Are you sure you want to cancel account creation? this will discard any information you've entered so far.")
                    .setPositiveButton("Yes", (dialog, which) -> finish())
                    .setNegativeButton("No", null)
                    .create().show();
        }
    }

    /**
     * this takes an String average and check if it is
     * a valid average for bac
     *
     * @param average
     * @return boolean
     */
    private boolean validAverage(String average) {
        double averageDouble = Double.parseDouble(average);
        return !(averageDouble < 10 || averageDouble >= 20);
    }

    /**
     * this is a helper method will be used to hide keyboard after
     * click on the login button
     */
    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager manager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            assert manager != null;
            manager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * this will check if the fields are all empty
     *
     * @return
     */
    private boolean isEmpty() {
        String cardNumber = mCardNumberEditText.getText().toString();
        String email = mEmailEditText.getText().toString();
        String password = mPasswordEditText.getText().toString();
        String bacAverage = mBacAverageEditText.getText().toString();
        return cardNumber.isEmpty() && email.isEmpty() && password.isEmpty() && bacAverage.isEmpty();
    }

    /**
     * @param v
     * @param hasFocus
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
            switch (v.getId()) {
                case R.id.input_card_number_sign_up:
                    if (!mCardNumberEditText.getText().toString().isEmpty()) {
                        if (mCardNumberEditText.getText().toString().length() != 8) {
                            mCardNumberWrapper.setError(getResources().getString(R.string.valid_card_number));
                        } else {
                            mCardNumberWrapper.setError(null);
                        }
                    }
                    break;
                case R.id.input_email_sign_up:
                    if (!mEmailEditText.getText().toString().isEmpty()) {
                        if (!Patterns.EMAIL_ADDRESS.matcher(mEmailEditText.getText().toString()).matches()) {
                            mEmailWrapper.setError(getResources().getString(R.string.valid_email));
                        } else {
                            mEmailWrapper.setError(null);
                        }
                    }
                    break;
                case R.id.input_bac_average_sign_up:
                    if (!mBacAverageEditText.getText().toString().isEmpty()) {
                        if (!validAverage(mBacAverageEditText.getText().toString())) {
                            mBacAverageWrapper.setError(getResources().getString(R.string.bac_average_invalid));
                        } else {
                            mBacAverageWrapper.setError(null);
                        }
                    }
                    break;
                case R.id.input_password_sign_up:
                    if (!mPasswordEditText.getText().toString().isEmpty()) {
                        if (mPasswordEditText.getText().toString().length() < 8) {
                            mPasswordWrapper.setError(getResources().getString(R.string.valid_password));
                        } else {
                            mPasswordWrapper.setError(null);
                        }
                    }
                    break;
            }
        }
    }

    @Override
    public void onLoginCompletionListener(LoginResponse response) {
        //do nothing here
    }

    @Override
    public void onSignUpCompletionListener(SignUpResponse response) {
        switch (response.getStatus()){
            case RESPONSE_SUCCESS:
                mSubTitleTextView.setText(R.string.account_created_succes_string);
                mTitleTextView.setText(R.string.completed);
                mSignUpButton.setOnClickListener(v-> finish());
                mSignUpButton.setText(R.string.done);
                break;
            case RESPONSE_ACCOUNT_EXISTS :
                mCardNumberWrapper.setError("This account of this card number already used.");

                break;
            case RESPONSE_AVERAGE_ERROR:
                mBacAverageWrapper.setError("This average is not correct for this card number");
                break;
        }
    }

    @Override
    public void onForgetPasswordCompletionListener(ForgetPasswordResponse response) {
        //do nothing here
    }
}
