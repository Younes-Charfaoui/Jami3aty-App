/*
 * Copyright (c) 2018. This code was created by Younes Charfaoui in the process of Graduation Project for the year of  2018 , which is about creating a platform  for students and professors to help them in the communication and the get known of the university information and so on.
 */

package com.ibnkhaldoun.studentside.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.ibnkhaldoun.studentside.models.ProfessorInfo;

import java.util.ArrayList;

public class ProfessorInfoDialog extends DialogFragment {

    public static final String KEY_LIST = "keyList";
    public static final String KEY_TYPE = "keyType";
    public static final int TYPE_SUBJECT = 1;
    public static final int TYPE_SECTION = 2;
    public static final int TYPE_GROUP = 3;

    public static ProfessorInfoDialog newInstanceSubject(ArrayList<ProfessorInfo> list) {

        Bundle args = new Bundle();
        args.putParcelableArrayList(KEY_LIST, list);
        args.putInt(KEY_TYPE, TYPE_SUBJECT);
        ProfessorInfoDialog fragment = new ProfessorInfoDialog();
        fragment.setArguments(args);
        return fragment;
    }

    public static ProfessorInfoDialog newInstanceGroup(ArrayList<ProfessorInfo> list, int id) {

        Bundle args = new Bundle();
        args.putParcelableArrayList(KEY_LIST, list);
        args.putInt(KEY_TYPE, TYPE_GROUP);
        ProfessorInfoDialog fragment = new ProfessorInfoDialog();
        fragment.setArguments(args);
        return fragment;
    }

    public static ProfessorInfoDialog newInstanceSection(ArrayList<ProfessorInfo> list, int id) {

        Bundle args = new Bundle();
        args.putParcelableArrayList(KEY_LIST, list);
        args.putInt(KEY_TYPE, TYPE_GROUP);
        ProfessorInfoDialog fragment = new ProfessorInfoDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        int type = getArguments().getInt(KEY_TYPE);
        switch (type) {
            case TYPE_GROUP:
                builder.setTitle("Choose group");

                break;
        }
        return builder.create();
    }
}
