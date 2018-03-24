package com.ibnkhaldoun.studentside.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.ibnkhaldoun.studentside.R;


public class LevelListFragment extends DialogFragment {

    public static final int stateLevel = 1;
    public static final int stateGroup = 2;
    public static final int stateSection = 3;
    public static final int stateSpeciality = 4;
    private String[] mLevels = getActivity().getResources().getStringArray(R.array.levels_array_string);
    private String[] mSpeciality = getContext().getResources().getStringArray(R.array.speciality_array_string);

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        assert getActivity() != null;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());;

        return builder.create();
    }
}
