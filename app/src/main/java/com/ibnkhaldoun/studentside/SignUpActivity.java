package com.ibnkhaldoun.studentside;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    private EditText mEmailEditText, mPasswordEditText, mCardNumberEditText;
    private TextInputLayout mEmailWrapper, mCardNumberWrapper, mPasswordWrapper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white);

        mCardNumberWrapper = findViewById(R.id.card_number_wrapper_sign_up);
        mEmailWrapper = findViewById(R.id.email_wrapper_sign_up);
        mPasswordWrapper = findViewById(R.id.password_wrapper_sign_up);

        mCardNumberEditText = findViewById(R.id.input_card_number_sign_up);
        mEmailEditText = findViewById(R.id.input_email_sign_up);
        mPasswordEditText = findViewById(R.id.input_password_sign_up);

        findViewById(R.id.sign_up_button).setOnClickListener(v -> {
            if (validate()) {
                Toast.makeText(this, "Sign up......", Toast.LENGTH_SHORT).show();
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
}
