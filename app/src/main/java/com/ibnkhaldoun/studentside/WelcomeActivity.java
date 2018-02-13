package com.ibnkhaldoun.studentside;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ibnkhaldoun.studentside.Utilities.PreferencesManager;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        PreferencesManager preferencesManager = new PreferencesManager(this);
        if (!preferencesManager.isFirstTimeLaunched()) {

            finish();
        }
    }
}
