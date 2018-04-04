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
import android.view.View;

import com.ibnkhaldoun.studentside.R;

public class NoteOfDisplayDialog extends DialogFragment {

    private TextInputEditText mNoteEditText;
    private static INoteOfDisplay mNoteInterface;
    private long mPostId;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mNoteInterface = (INoteOfDisplay) context;
    }

    public static NoteOfDisplayDialog newInstance(long id) {
        Bundle args = new Bundle();
        args.putLong("Key",id);
        NoteOfDisplayDialog fragment = new NoteOfDisplayDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle(R.string.new_note);
        View noteView = getLayoutInflater().inflate(R.layout.note_of_display_dialog, null);
        mNoteEditText = noteView.findViewById(R.id.note_dialog_edit_text);
        builder.setView(noteView);

        builder.setPositiveButton(android.R.string.yes, (dialog, which) ->
                mNoteInterface.OnFinishNoting(mNoteEditText.getText().toString(),getArguments().getLong("Key")));
        builder.setNegativeButton(android.R.string.no, null);

        return builder.create();
    }

    public interface INoteOfDisplay {
        void OnFinishNoting(String note , long id);
    }
}
