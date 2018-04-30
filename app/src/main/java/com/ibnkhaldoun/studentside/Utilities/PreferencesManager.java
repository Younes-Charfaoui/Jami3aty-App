package com.ibnkhaldoun.studentside.Utilities;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class PreferencesManager {

    public static final int CONFIG = 1;
    public static final int STUDENT = 2;
    public static final int SUBJECT = 3;
    public static final int UNIVERSITY = 4;
    public static final int PROFESSOR = 5;
    public static final String KEY_MAIL_STUDENT_MAIL = "keyMailStudentMain";
    private static final String KEY_MENU_NOTE = "keyMenuNote";
    private static final String KEY_HAMBURGER_STUDENT_MAIN = "keyHamburgerStudentMain";
    private static final String TAG = "manager";
    private static final int PRIVATE_MODE = 0;
    private static final String PREFERENCE_CONFIGURATION_NAME = "configuration";
    private static final String PREFERENCE_STUDENT_NAME = "student";
    private static final String PREFERENCE_SUBJECTS_NAME = "subjects";
    private static final String PREFERENCE_UNIVERSITY_NAME = "university";
    private static final String PREFERENCE_PROFESSOR_NAME = "professor";
    private static final String FIRST_TIME = "isFirstTime";
    private static final String LOGIN = "login";
    private static final String FULL_NAME = "fullName";
    private static final String GRADE = "grade";
    private static final String ID = "id";
    private static final String LEVEL = "level";
    private static final String GROUP = "group";
    private static final String SECTION = "section";
    private static final String SUBJECTS = "subjects";
    private static final String KEY_ADD_NOTE = "keyNote";
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    @SuppressLint("CommitPrefEdits")
    public PreferencesManager(Context mContext, int type) {
        mPreferences = mContext.getSharedPreferences(getSharedPreferencesFile(type), PRIVATE_MODE);
        mEditor = mPreferences.edit();
    }

    private String getSharedPreferencesFile(int type) {
        switch (type) {
            case CONFIG:
                return PREFERENCE_CONFIGURATION_NAME;
            case STUDENT:
                return PREFERENCE_STUDENT_NAME;
            case SUBJECT:
                return PREFERENCE_SUBJECTS_NAME;
            case UNIVERSITY:
                return PREFERENCE_UNIVERSITY_NAME;
            case PROFESSOR:
                return PREFERENCE_PROFESSOR_NAME;
            default:
                return PREFERENCE_CONFIGURATION_NAME;
        }
    }

    public boolean isNotFirstTimeLaunched() {
        return !mPreferences.getBoolean(FIRST_TIME, true);
    }

    public void setFirstTimeLaunched() {
        mEditor.putBoolean(FIRST_TIME, false);
        mEditor.commit();
    }

    public boolean isLogin() {
        return mPreferences.getBoolean(LOGIN, false);
    }

    public void setLoginProfessor(String id, String fullName, String degree) {
        mEditor.putString(FULL_NAME, fullName);
        mEditor.putString(GRADE, degree);
        mEditor.putBoolean(LOGIN, true);
        mEditor.putString(ID, id);
        mEditor.apply();
    }

    public void setLoginStudent(String id, String fullName, String grade,
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

    public String getIdUser() {
        return mPreferences.getString(ID, null);
    }

    public String getGroupStudent() {
        return mPreferences.getString(GROUP, null);
    }

    public String getSectionStudent() {
        return mPreferences.getString(SECTION, null);
    }

    public String getLevelStudent() {
        return mPreferences.getString(LEVEL, null);
    }

    public String getGradeUser() {
        return mPreferences.getString(GRADE, null);
    }

    public String getFullNameUser() {
        return mPreferences.getString(FULL_NAME, null);
    }

    public void addSubjects(ArrayList<String> subjects) {
        Set<String> setOfSubjects = new HashSet<>(subjects);
        mEditor.putStringSet(SUBJECTS, setOfSubjects);
        mEditor.commit();
    }

    @SuppressWarnings("ConstantConditions")
    public ArrayList<String> getSubjects() {
        Set<String> set = mPreferences.getStringSet(SUBJECTS, null);
        return new ArrayList<>(set);
    }

    public boolean isSubjectsExists() {
        return mPreferences.getStringSet(SUBJECTS, null) != null;
    }

    public void setAddNoteFirstTime() {
        mEditor.putBoolean(KEY_ADD_NOTE, false);
        mEditor.apply();
    }

    public boolean isAddNoteFirstTime() {
        return mPreferences.getBoolean(KEY_ADD_NOTE, true);
    }

    public void setMenuNoteFirstTime() {
        mEditor.putBoolean(KEY_MENU_NOTE, false);
        mEditor.apply();
    }

    public boolean isMenuNoteFirstTime() {
        return mPreferences.getBoolean(KEY_MENU_NOTE, true);
    }

    public void setStudentMainFirstTime() {
        mEditor.putBoolean(KEY_HAMBURGER_STUDENT_MAIN, false);
        mEditor.apply();
    }

    public boolean isStudentMainFirstTime() {
        return mPreferences.getBoolean(KEY_HAMBURGER_STUDENT_MAIN, true);
    }

    public void setStudentMainMailFirstTime() {
        mEditor.putBoolean(KEY_MAIL_STUDENT_MAIL, false);
        mEditor.apply();
    }

    public boolean isStudentMainMailFirstTime() {
        return mPreferences.getBoolean(KEY_MAIL_STUDENT_MAIL, true);
    }

    void removePreference() {
        mEditor.clear().commit();
    }
}
