package com.ibnkhaldoun.studentside.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.ibnkhaldoun.studentside.R;
import com.ibnkhaldoun.studentside.Utilities.ActivityUtilities;
import com.ibnkhaldoun.studentside.Utilities.PreferencesManager;
import com.ibnkhaldoun.studentside.networking.utilities.HttpUtilities;
import com.ibnkhaldoun.studentside.networking.models.RequestPackage;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity {

    private final static int LOGIN_ID_LOADER = 126;
    private EditText mEmailEditText, mPasswordEditText;
    private TextInputLayout mEmailWrapper, mPasswordWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        ActivityUtilities.changeStatusBarColorToTransparent(getWindow());
        mEmailEditText = findViewById(R.id.input_email);
        mPasswordEditText = findViewById(R.id.input_password);
        mPasswordWrapper = findViewById(R.id.password_wrapper);
        mEmailWrapper = findViewById(R.id.email_wrapper);
        final ProgressBar loadingProgressBar = findViewById(R.id.progress_bar);
        final LinearLayout buttonsLinearLayout = findViewById(R.id.buttons_linearLayout);

        findViewById(R.id.login_button).setOnClickListener(v -> {
            //TODO getting the information from the server
            PreferencesManager manager = new PreferencesManager(LoginActivity.this);
            manager.setLogin("11", mEmailEditText.getText().toString(), "L3", "Computer Science");
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
            /*if (validate()) {
                hideKeyboard();

                //setting the request parameters
                RequestPackage request = new RequestPackage();
                request.setMethod(RequestPackage.POST);
                request.setEndPoint(EndPointsProvider.getLoginEndpoint());
                String email = mEmailEditText.getText().toString();
                String password = mPasswordEditText.getText().toString();
                request.addParams("email", CryptographyUtilities.encryptEmail(email));
                request.addParams("password", CryptographyUtilities.hashingPassword(password));
                LoginAsyncTask loginTask = new LoginAsyncTask();
                loginTask.execute(request);
                mPasswordWrapper.setEnabled(false);
                mEmailWrapper.setEnabled(false);
                loadingProgressBar.setVisibility(View.VISIBLE);
                buttonsLinearLayout.setVisibility(View.GONE);
            }*/
        });

        findViewById(R.id.create_account_button).setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, SignUpActivity.class)));


        findViewById(R.id.forget_password_textView).setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, ForgetPasswordActivity.class)));
    }

    /**
     * a method that return if the user enter correctly
     * the information's
     *
     * @return if the user make mistake
     */
    private boolean validate() {

        boolean validate = true;
        String usernameEmail = mEmailEditText.getText().toString();
        String password = mPasswordEditText.getText().toString();

        if (usernameEmail.isEmpty()) {
            mEmailWrapper.setError(getResources().getString(R.string.email_empty));
            validate = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(usernameEmail).matches()) {
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

    private class LoginAsyncTask extends AsyncTask<RequestPackage, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            // TODO: 09/03/2018 add code to handle the response from the server
        }

        @Override
        protected String doInBackground(RequestPackage... requests) {
            try {
                return HttpUtilities.getData(requests[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
