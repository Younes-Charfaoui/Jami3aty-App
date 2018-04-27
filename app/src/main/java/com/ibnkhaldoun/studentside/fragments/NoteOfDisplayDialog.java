/*
 * Copyright (c) 2018. This code was created by Younes Charfaoui in the process of Graduation Project for the year of  2018 , which is about creating a platform  for students and professors to help them in the communication and the get known of the university information and so on.
 */

package com.ibnkhaldoun.studentside.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.ibnkhaldoun.studentside.R;

public class NoteOfDisplayDialog extends DialogFragment {

    public static final int EDIT = 1;
    public static final int ADD = 0;
    public static final String KEY_ID = "keyId";
    public static final String KEY_TYPE = "keyType";
    public static final String KEY_NOTE = "keyNote";
    private static INoteOfDisplay mNoteInterface;
    private TextInputEditText mNoteEditText;
    private long mPostId;

    public static NoteOfDisplayDialog newInstance(long id, int type) {
        Bundle args = new Bundle();
        args.putLong(KEY_ID, id);
        args.putInt(KEY_TYPE, type);
        NoteOfDisplayDialog fragment = new NoteOfDisplayDialog();
        fragment.setArguments(args);
        return fragment;
    }

    public static NoteOfDisplayDialog newInstance(long id, int type, String note) {
        Bundle args = new Bundle();
        args.putLong(KEY_ID, id);
        args.putInt(KEY_TYPE, type);
        args.putString(KEY_NOTE, note);
        NoteOfDisplayDialog fragment = new NoteOfDisplayDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mNoteInterface = (INoteOfDisplay) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.new_note);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View noteView = inflater.inflate(R.layout.note_of_display_dialog, null, false);
        mNoteEditText = noteView.findViewById(R.id.note_dialog_edit_text);
        String note = null;
        if (getArguments() != null) {
            note = getArguments().getString(KEY_NOTE);
        }
        mNoteEditText.setText(note);
        builder.setView(noteView);

        builder.setPositiveButton(android.R.string.yes, (dialog, which) ->
        {
            if (getArguments() != null) {
                if (!mNoteEditText.getText().toString().equals(getArguments().getString(KEY_NOTE))){

                    String noteC = mNoteEditText.getText().toString().trim();
                    if(!TextUtils.isEmpty(noteC)){

                        mNoteInterface.OnFinishNoting(noteC, getArguments().getLong(KEY_ID), getArguments().getInt(KEY_TYPE));
                    }else {
                        Toast.makeText(getActivity(), "Please Add something", Toast.LENGTH_SHORT).show();
                    }
                }
                else dismiss();
            }
        });
        builder.setNegativeButton(android.R.string.no, null);

        return builder.create();

    }

    public interface INoteOfDisplay {
        void OnFinishNoting(String note, long id, int type);
    }
}
