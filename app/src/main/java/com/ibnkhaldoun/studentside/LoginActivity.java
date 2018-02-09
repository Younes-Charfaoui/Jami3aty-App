package com.ibnkhaldoun.studentside;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText mUsernameEmailEditText, mPasswordEditText;
    private TextInputLayout passwordWrapper;
    private TextInputLayout emailUsernameWrapper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mUsernameEmailEditText = findViewById(R.id.input_email_username);
        mPasswordEditText = findViewById(R.id.input_password);
        passwordWrapper = findViewById(R.id.password_wrapper);
        emailUsernameWrapper = findViewById(R.id.email_username_wrapper);

        findViewById(R.id.login_button).setOnClickListener(v -> {
            hideKeyboard();
            if (validate()) {
                Toast.makeText(LoginActivity.this, "You Logged In", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.sign_up_button).setOnClickListener(v -> {

        });
    }

    /**
     * a method that return if the user enter correctly
     * the information's
     *
     * @return if the user make mistake
     */
    private boolean validate() {

        boolean validate = true;
        String usernameEmail = mUsernameEmailEditText.getText().toString();
        String password = mPasswordEditText.getText().toString();

        if (usernameEmail.isEmpty()) {
            emailUsernameWrapper.setError(getResources().getString(R.string.email_empty));
            validate = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(usernameEmail).matches()) {
            emailUsernameWrapper.setError(getResources().getString(R.string.valid_email));
            validate = false;
        } else {
            emailUsernameWrapper.setError(null);
        }

        if (password.isEmpty()) {
            passwordWrapper.setError(getResources().getString(R.string.password_empty));
            validate = false;
        } else if (password.length() < 8) {
            passwordWrapper.setError(getResources().getString(R.string.valid_password));
            validate = false;
        } else {
            passwordWrapper.setError(null);
        }
        return validate;
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
