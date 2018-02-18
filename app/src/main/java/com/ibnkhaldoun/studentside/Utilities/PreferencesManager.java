package com.ibnkhaldoun.studentside.Utilities;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesManager {

    private static final int PRIVATE_MODE = 0;
    private static final String PREFERENCE_NAME = "sliders";
    private static final String FIRST_TIME = "isFirstTime";
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    @SuppressLint("CommitPrefEdits")
    public PreferencesManager(Context mContext) {
        mPreferences = mContext.getSharedPreferences(PREFERENCE_NAME, PRIVATE_MODE);
        mEditor = mPreferences.edit();
    }

    public boolean isFirstTimeLaunched() {
        return mPreferences.getBoolean(FIRST_TIME, true);
    }

    public void setFirstTimeLaunched() {
        mEditor.putBoolean(FIRST_TIME, false);
        mEditor.commit();
    }
}
