package com.ibnkhaldoun.studentside.activities;

import android.content.Intent;
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
import android.widget.Toast;

import com.ibnkhaldoun.studentside.R;
import com.ibnkhaldoun.studentside.Utilities.ActivityUtilities;
import com.ibnkhaldoun.studentside.Utilities.PreferencesManager;
import com.ibnkhaldoun.studentside.asyncTask.LoginAsyncTask;
import com.ibnkhaldoun.studentside.enums.Levels;
import com.ibnkhaldoun.studentside.interfaces.TaskListener;
import com.ibnkhaldoun.studentside.networking.models.ForgetPasswordResponse;
import com.ibnkhaldoun.studentside.networking.models.LoginResponse;
import com.ibnkhaldoun.studentside.networking.models.RequestPackage;
import com.ibnkhaldoun.studentside.networking.models.SignUpResponse;
import com.ibnkhaldoun.studentside.networking.utilities.NetworkUtilities;
import com.ibnkhaldoun.studentside.providers.EndPointsProvider;
import com.ibnkhaldoun.studentside.providers.KeyDataProvider;
import com.ibnkhaldoun.studentside.services.FcmTokenService;

import static com.ibnkhaldoun.studentside.networking.models.Response.*;

public class LoginActivity extends AppCompatActivity implements TaskListener {

    private final static int LOGIN_ID_LOADER = 126;
    private EditText mEmailEditText, mPasswordEditText;
    private TextInputLayout mEmailWrapper, mPasswordWrapper;
    private ProgressBar mLoadingProgressBar;
    private LinearLayout mButtonsLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        ActivityUtilities.changeStatusBarColorToTransparent(getWindow());
        mEmailEditText = findViewById(R.id.input_email);
        mEmailEditText.setText("ismail@gmail.com");
        mPasswordEditText = findViewById(R.id.input_password);
        mPasswordEditText.setText("hellohello");
        mPasswordWrapper = findViewById(R.id.password_wrapper);
        mEmailWrapper = findViewById(R.id.email_wrapper);
        mLoadingProgressBar = findViewById(R.id.progress_bar);
        mButtonsLinearLayout = findViewById(R.id.buttons_linearLayout);
        findViewById(R.id.login_button).setOnClickListener(v -> {
            if (validate()) {
                hideKeyboard();
                if (NetworkUtilities.isConnected(this)) {

                    //setting the request parameters
                    RequestPackage request = new RequestPackage();
                    request.setMethod(RequestPackage.POST);
                    request.setEndPoint(EndPointsProvider.getLoginEndpoint());
                    String email = mEmailEditText.getText().toString();
                    String password = mPasswordEditText.getText().toString();

                    request.addParams(KeyDataProvider.KEY_ANDROID, KeyDataProvider.KEY_ANDROID);
                    request.addParams(KeyDataProvider.KEY_EMAIL, email);
                    request.addParams(KeyDataProvider.KEY_PASSWORD, password);
                    request.addParams(KeyDataProvider.KEY_TOKEN_FIREBASE , FcmTokenService.getCurrentToken());

                    LoginAsyncTask loginTask = new LoginAsyncTask(this);
                    loginTask.execute(request);
                    mPasswordWrapper.setEnabled(false);
                    mEmailWrapper.setEnabled(false);
                    mLoadingProgressBar.setVisibility(View.VISIBLE);
                    mButtonsLinearLayout.setVisibility(View.GONE);
                } else {
                    Toast.makeText(this, R.string.no_internet_connection_string, Toast.LENGTH_SHORT).show();
                }
            }
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


    @Override
    public void onLoginCompletionListener(LoginResponse response) {

        switch (response.getStatus()) {
            case RESPONSE_SUCCESS:
                PreferencesManager manager = new PreferencesManager(this);
                if (response.isStudent()) {
                    manager.setLogin(response.getStudent().getId()
                            , response.getStudent().getFullName()
                            , Levels.getLevelString(Levels.getLevel(response.getStudent().getLevel())));
                    startActivity(new Intent(this, StudentMainActivity.class));
                    finish();
                } else {
                    Toast.makeText(this, "Login for professor", Toast.LENGTH_SHORT).show();
                }
                break;
            case RESPONSE_EMAIL_ERROR:
                mEmailWrapper.setError(getString(R.string.email_does_not_exits_string));
                mPasswordWrapper.setEnabled(true);
                mEmailWrapper.setEnabled(true);
                mPasswordEditText.setText(null);
                mLoadingProgressBar.setVisibility(View.GONE);
                mButtonsLinearLayout.setVisibility(View.VISIBLE);
                break;
            case RESPONSE_PASSWORD_ERROR:
                mPasswordWrapper.setError(getString(R.string.error_in_password_string));
                mPasswordWrapper.setEnabled(true);
                mEmailWrapper.setEnabled(true);
                mPasswordEditText.setText(null);
                mLoadingProgressBar.setVisibility(View.GONE);
                mButtonsLinearLayout.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onSignUpCompletionListener(SignUpResponse response) {
        // do nothing here
    }

    @Override
    public void onForgetPasswordCompletionListener(ForgetPasswordResponse response) {
        // do nothing here
    }
}
