package com.ibnkhaldoun.studentside;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ForgetPasswordActivity extends AppCompatActivity {

    private TextView mAccountPleaseTextView;
    private Button mFindAccount;
    private EditText mEmailEditText;
    private TextInputLayout mEmailWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mFindAccount = findViewById(R.id.find_account);
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
                    mFindAccount.setVisibility(View.GONE);
                } else {
                    mAccountPleaseTextView.setVisibility(View.GONE);
                    mFindAccount.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

}
