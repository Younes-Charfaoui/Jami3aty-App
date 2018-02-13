package com.ibnkhaldoun.studentside.Activities;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
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

import com.ibnkhaldoun.studentside.R;

public class SignUpActivity extends AppCompatActivity implements View.OnFocusChangeListener {

    private EditText mEmailEditText, mPasswordEditText, mCardNumberEditText, mBacAverageEditText;
    private TextInputLayout mEmailWrapper, mCardNumberWrapper, mPasswordWrapper, mBacAverageWrapper;

    //private Menu mMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
        final ProgressBar loading = findViewById(R.id.loading_progressBar_signUp);
        final Button signUpButton = findViewById(R.id.sign_up_button);

        signUpButton.setOnClickListener(v -> {
            if (validate()) {
                hideKeyboard();
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (!(networkInfo != null && networkInfo.isConnected())) {
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.coordinator_view), "Sorry, no connection !", Snackbar.LENGTH_SHORT);
                    snackbar.setAction("Try again", v1 -> {
                        if (!(networkInfo != null && networkInfo.isConnected())) snackbar.show();
                    });
                    snackbar.setActionTextColor(getResources().getColor(R.color.deep_red));
                    snackbar.show();
                } else {
                    mCardNumberWrapper.setEnabled(false);
                    mEmailWrapper.setEnabled(false);
                    mPasswordWrapper.setEnabled(false);
                    mBacAverageWrapper.setEnabled(false);
                }
                /*mMenu.findItem(R.id.action_help).setEnabled(false);
                getSupportActionBar().setHomeButtonEnabled(false);
                signUpButton.setVisibility(View.GONE);
                loading.setVisibility(View.VISIBLE);*/

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sign_up, menu);

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
}
