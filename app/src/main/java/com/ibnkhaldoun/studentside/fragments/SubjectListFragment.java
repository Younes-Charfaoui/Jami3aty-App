package com.ibnkhaldoun.studentside.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.ibnkhaldoun.studentside.R;
import com.ibnkhaldoun.studentside.adapters.SubjectProfessorDialogAdapter;
import com.ibnkhaldoun.studentside.interfaces.SubjectDialogInterface;

import java.util.ArrayList;
import java.util.List;


public class SubjectListFragment extends DialogFragment {

    public SubjectDialogInterface mInterface;

    public static SubjectListFragment newInstance(List<String> list) {

        Bundle args = new Bundle();

        args.putStringArrayList("Key", (ArrayList<String>) list);
        SubjectListFragment fragment = new SubjectListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mInterface = (SubjectDialogInterface) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        assert getActivity() != null;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Choose a Subject");

        ArrayList<String> list = getArguments().getStringArrayList("Key");
        assert getContext() != null;
        SubjectProfessorDialogAdapter adapter = new SubjectProfessorDialogAdapter(getContext(), R
                .layout.subject_professor_dialog_list_item, list);
        builder.setAdapter(adapter, (dialog, which) -> mInterface.onSubjectChosen(list.get(which)));
        return builder.create();
    }
}
