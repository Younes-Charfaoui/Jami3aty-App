package com.ibnkhaldoun.studentside.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.ibnkhaldoun.studentside.R;
import com.ibnkhaldoun.studentside.Utilities.ActivityUtilities;
import com.ibnkhaldoun.studentside.adapters.SubjectProfessorDialogAdapter;
import com.ibnkhaldoun.studentside.interfaces.IProfessorDialog;
import com.ibnkhaldoun.studentside.models.MailProfessor;

import java.util.ArrayList;
import java.util.List;


public class ProfessorListFragment extends DialogFragment {

    public static final String KEY_PROFESSOR = "keyProfessor";
    public IProfessorDialog mInterface;

    public static ProfessorListFragment newInstance( List<MailProfessor> list) {

        Bundle args = new Bundle();
        args.putParcelableArrayList(KEY_PROFESSOR, (ArrayList<? extends Parcelable>) list);
        ProfessorListFragment fragment = new ProfessorListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mInterface = (IProfessorDialog) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        assert getActivity() != null && getArguments() != null;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        ArrayList<MailProfessor> professorList = getArguments().getParcelableArrayList(KEY_PROFESSOR);
        builder.setTitle(R.string.choose_professor);
        ArrayList<String> list = new ArrayList<>();

            for (MailProfessor professor : professorList) {
                list.add(professor.getName());
            }


        assert getContext() != null;
        SubjectProfessorDialogAdapter adapter = new SubjectProfessorDialogAdapter(getContext(), R
                .layout.subject_professor_dialog_list_item, list);
        builder.setAdapter(adapter, (dialog, which) ->
                mInterface.onProfessorChosen(professorList.get(which).getName(),
                        professorList.get(which).getId()));
        return builder.create();
    }
}
