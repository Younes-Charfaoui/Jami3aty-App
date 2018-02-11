package com.ibnkhaldoun.studentside;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SignUpActivity extends AppCompatActivity {

    private EditText mEmailEditText, mPasswordEditText, mCardNumberEditText, mBirthDayEditText;
    private TextInputLayout mEmailWrapper, mCardNumberWrapper, mPasswordWrapper, mBirthDayWrapper;
    private Calendar mCalendar;
    private Menu mMenu;

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
        mBirthDayWrapper = findViewById(R.id.date_wrapper_sign_up);


        mCardNumberEditText = findViewById(R.id.input_card_number_sign_up);
        mEmailEditText = findViewById(R.id.input_email_sign_up);
        mPasswordEditText = findViewById(R.id.input_password_sign_up);
        mBirthDayEditText = findViewById(R.id.input_date_of_birth_sign_up);

        mCalendar = Calendar.getInstance();

        mBirthDayEditText.setOnClickListener(v -> {
            new DatePickerDialog(SignUpActivity.this,
                    (view, year, month, dayOfMonth) -> {
                        mCalendar.set(Calendar.YEAR, year);
                        mCalendar.set(Calendar.MONTH, month);
                        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateDateValue();
                    },
                    mCalendar.get(Calendar.YEAR),
                    mCalendar.get(Calendar.MONTH),
                    mCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });
        final ProgressBar loading = findViewById(R.id.loading_progressBar_signUp);
        final Button signUpButton = findViewById(R.id.sign_up_button);
        signUpButton.setOnClickListener(v -> {
            if (validate()) {
                hideKeyboard();
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                
                mCardNumberWrapper.setEnabled(false);
                mEmailWrapper.setEnabled(false);
                mPasswordWrapper.setEnabled(false);
                mBirthDayWrapper.setEnabled(false);
                mMenu.findItem(R.id.action_help).setEnabled(false);
                getSupportActionBar().setHomeButtonEnabled(false);
                signUpButton.setVisibility(View.GONE);
                loading.setVisibility(View.VISIBLE);

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
                new AlertDialog.Builder(this)
                        .setTitle("Cancel Account Creation")
                        .setMessage("Are you sure you want to cancel account creation? this will discard any information you've entered so far.")
                        .setPositiveButton("Yes", (dialog, which) -> finish())
                        .setNegativeButton("No", null)
                        .create().show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean validate() {

        boolean validate = true;
        String cardNumber = mCardNumberEditText.getText().toString();
        String email = mEmailEditText.getText().toString();
        String password = mPasswordEditText.getText().toString();
        String date = mBirthDayEditText.getText().toString();

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

        if (date.isEmpty()) {
            mBirthDayWrapper.setError(getResources().getString(R.string.date_empty));
            validate = false;
        } else {
            mBirthDayWrapper.setError(null);
        }
        return validate;

    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Cancel Account Creation")
                .setMessage("Are you sure you want to cancel account creation? this will discard any information you've entered so far.")
                .setPositiveButton("Yes", (dialog, which) -> finish())
                .setNegativeButton("No", null)
                .create().show();
    }

    private void updateDateValue() {
        String dateFormat = "dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.FRANCE);
        mBirthDayEditText.setText(simpleDateFormat.format(mCalendar.getTime()));
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
}
