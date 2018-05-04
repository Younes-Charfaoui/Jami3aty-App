/*------------------------------------------------------------------------------
 - Copyright (c) 2018. This code was created by Younes Charfaoui in the process of Graduation Project for the year of  2018 , which is about creating a platform  for students and professors to help them in the communication and the get known of the university information and so on.
 -----------------------------------------------------------------------------*/

package com.ibnkhaldoun.studentside.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.ibnkhaldoun.studentside.R;
import com.ibnkhaldoun.studentside.Utilities.ActivityUtilities;
import com.ibnkhaldoun.studentside.Utilities.PreferencesManager;

import static com.ibnkhaldoun.studentside.Utilities.PreferencesManager.STUDENT;

public class SplashActivity extends AppCompatActivity {

    private static final long SPLASH_TIME = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slpash);
        Class classes = WelcomeActivity.class;
        ActivityUtilities.changeStatusBarColorToTransparent(getWindow());
        PreferencesManager manager = new PreferencesManager(this, STUDENT);
        if (manager.isLogin()) {
            classes = StudentMainActivity.class;
        } else if (new PreferencesManager(this, PreferencesManager.PROFESSOR).isLogin()) {
            classes = ProfessorMainActivity.class;
        } else if (new PreferencesManager(this, PreferencesManager.CONFIG).isNotFirstTimeLaunched()) {
            classes = LoginActivity.class;
        }

        Class finalClasses = classes;
        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, finalClasses));
            finish();
        }, SPLASH_TIME);
    }
}
