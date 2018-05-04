/*------------------------------------------------------------------------------
 - Copyright (c) 2018. This code was created by Younes Charfaoui in the process of Graduation Project for the year of  2018 , which is about creating a platform  for students and professors to help them in the communication and the get known of the university information and so on.
 -----------------------------------------------------------------------------*/

package com.ibnkhaldoun.studentside.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;

import com.ibnkhaldoun.studentside.R;
import com.ibnkhaldoun.studentside.Utilities.ActivityUtilities;
import com.ibnkhaldoun.studentside.activities.LoginActivity;
import com.ibnkhaldoun.studentside.activities.StudentMainActivity;


public class SettingFragment extends PreferenceFragment
        implements Preference.OnPreferenceChangeListener,
        SharedPreferences.OnSharedPreferenceChangeListener {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting_pref);
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

        Preference preference = getPreferenceScreen().findPreference(getString(R.string.key_sign_out));
        preference.setOnPreferenceClickListener(p -> {
            new AlertDialog.Builder(getActivity())
                    .setTitle(R.string.sign_out)
                    .setMessage(R.string.logout_confirmation)
                    .setPositiveButton(R.string.yes, (dialog, which) -> {
                        StudentMainActivity.ACTIVITY.finish();
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                        getActivity().finish();
                        ActivityUtilities.logOut(getActivity());
                    })
                    .setNegativeButton(android.R.string.no, null)
                    .show();
            return true;
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        //todo code to check the field that they are well inputted
        return false;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

    }

}
