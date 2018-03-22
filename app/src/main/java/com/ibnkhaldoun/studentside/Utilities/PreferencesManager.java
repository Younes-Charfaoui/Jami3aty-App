package com.ibnkhaldoun.studentside.Utilities;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class PreferencesManager {
    private static final String TAG = "manager";

    private static final int PRIVATE_MODE = 0;
    private static final String PREFERENCE_NAME = "sliders";
    private static final String FIRST_TIME = "isFirstTime";
    private static final String LOGIN = "login";
    private static final String FULL_NAME = "fullName";
    private static final String GRADE = "grade";
    private static final String ID = "id";

    private static final String LEVEL = "level";
    private static final String GROUP = "group";
    private static final String SECTION = "section";

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

    public boolean isLogin() {
        return mPreferences.getBoolean(LOGIN, false);
    }

    public void setLogin(String id, String fullName, String grade,
                         String group, String section, String level) {
        mEditor.putBoolean(LOGIN, true);
        mEditor.putString(ID, id);
        mEditor.putString(FULL_NAME, fullName);
        mEditor.putString(GRADE, grade);
        mEditor.putString(LEVEL, level);
        mEditor.putString(SECTION, section);
        mEditor.putString(GROUP, group);
        mEditor.commit();
    }

    public String getId() {
        return mPreferences.getString(ID, null);
    }

    public String getGroup() {
        String hello = mPreferences.getString(GROUP, null);
        Log.i(TAG, "getGroup: " + hello);
        return hello;
    }

    public String getSection() {
        String hello = mPreferences.getString(SECTION, null);
        Log.i(TAG, "getSection: " + hello);
        return hello;
    }

    public String getLevel() {
        String hello = mPreferences.getString(LEVEL, null);
        Log.i(TAG, "getLevel: " + hello);
        return hello;
    }

    public String getGrade() {
        return mPreferences.getString(GRADE, null);
    }

    public String getFullName() {
        return mPreferences.getString(FULL_NAME, null);
    }
}
