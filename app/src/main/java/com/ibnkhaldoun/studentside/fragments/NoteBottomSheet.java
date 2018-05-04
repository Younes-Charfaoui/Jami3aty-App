/*------------------------------------------------------------------------------
 - Copyright (c) 2018. This code was created by Younes Charfaoui in the process of Graduation Project for the year of  2018 , which is about creating a platform  for students and professors to help them in the communication and the get known of the university information and so on.
 -----------------------------------------------------------------------------*/

package com.ibnkhaldoun.studentside.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ibnkhaldoun.studentside.R;

public class NoteBottomSheet extends BottomSheetDialogFragment {

    private INoteBottomSheet mInterface;
    private  static final String KEY_ID = "keyId";
    private  static final String KEY_NOTE = "keyNote";

    public static NoteBottomSheet newInstance(long id , String note) {
        Bundle args = new Bundle();
        args.putLong(KEY_ID, id);
        args.putString(KEY_NOTE, note);
        NoteBottomSheet fragment = new NoteBottomSheet();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mInterface = (INoteBottomSheet) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_more_option_note_of_display, container, false);
        view.findViewById(R.id.bottom_sheet_note_modify).setOnClickListener(v -> {
            if (getArguments() != null) {
                mInterface.OnEdit(getArguments().getLong(KEY_ID) , getArguments().getString(KEY_NOTE));
            }
            dismiss();
        });

        view.findViewById(R.id.bottom_sheet_note_remove).setOnClickListener(v -> {
            if (getArguments() != null) {
                mInterface.OnRemove(getArguments().getLong(KEY_ID));
            }
            dismiss();
        });
        return view;
    }

    public interface INoteBottomSheet {
        void OnEdit(long id , String note);

        void OnRemove(long id);
    }
}
