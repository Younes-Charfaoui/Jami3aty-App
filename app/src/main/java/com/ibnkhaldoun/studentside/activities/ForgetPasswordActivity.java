package com.ibnkhaldoun.studentside.activities;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ibnkhaldoun.studentside.R;

public class ForgetPasswordActivity extends AppCompatActivity {

    private TextView mAccountPleaseTextView;
    private Button mFindAccountButton;
    private EditText mEmailEditText;
    private TextInputLayout mEmailWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mFindAccountButton = findViewById(R.id.find_account);
        mAccountPleaseTextView = findViewById(R.id.account_search_textView);
        mEmailEditText = findViewById(R.id.input_email_forget_password);
        mEmailWrapper = findViewById(R.id.email_wrapper_forget_password);

        mEmailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    mAccountPleaseTextView.setVisibility(View.VISIBLE);
                    mFindAccountButton.setVisibility(View.GONE);
                } else {
                    mAccountPleaseTextView.setVisibility(View.GONE);
                    mFindAccountButton.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mFindAccountButton.setOnClickListener(v -> {
            if (validate()) {
                Toast.makeText(this, "Going to get......", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validate() {
        if (!Patterns.EMAIL_ADDRESS.matcher(mEmailEditText.getText().toString()).matches()) {
            mEmailWrapper.setError(getResources().getString(R.string.valid_email));
            return false;
        } else {
            mEmailWrapper.setError(null);
        }
        return true;
    }
}
