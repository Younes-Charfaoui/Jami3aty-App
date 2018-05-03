/*
 * Copyright (c) 2018. This code was created by Younes Charfaoui in the process of Graduation
 * Project for the year of  2018 , which is about creating a platform  for students and professors
 * to help them in the communication and the get known of the university information and so on.
 */

package com.ibnkhaldoun.studentside.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.ibnkhaldoun.studentside.R;
import com.ibnkhaldoun.studentside.Utilities.PreferencesManager;
import com.ibnkhaldoun.studentside.adapters.GroupSpinnerAdapter;
import com.ibnkhaldoun.studentside.adapters.SectionSpinnerAdapter;
import com.ibnkhaldoun.studentside.adapters.SubjectSpinnerAdapter;
import com.ibnkhaldoun.studentside.adapters.TypeSpinnerAdapter;
import com.ibnkhaldoun.studentside.asyncTask.ResponseAsyncTask;
import com.ibnkhaldoun.studentside.enums.PostTypes;
import com.ibnkhaldoun.studentside.models.ProfessorInfo;
import com.ibnkhaldoun.studentside.networking.models.RequestPackage;
import com.ibnkhaldoun.studentside.networking.models.Response;
import com.ibnkhaldoun.studentside.networking.utilities.JsonUtilities;
import com.ibnkhaldoun.studentside.networking.utilities.NetworkUtilities;
import com.ibnkhaldoun.studentside.providers.EndPointsProvider;
import com.ibnkhaldoun.studentside.providers.KeyDataProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.ibnkhaldoun.studentside.Utilities.PreferencesManager.PROFESSOR;
import static com.ibnkhaldoun.studentside.networking.models.RequestPackage.POST;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.JSON_MAIL_ID_PROFESSOR;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.JSON_POST_FILE;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.JSON_POST_TEXT;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.JSON_STUDENT_GROUP;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.JSON_STUDENT_LEVEL;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.JSON_STUDENT_SECTION;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.JSON_SUBJECT_ID2;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.KEY_AJAX;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.KEY_ANDROID;

public class AddPostActivity extends AppCompatActivity implements ResponseAsyncTask.IResponseListener {

    private ArrayList<ProfessorInfo> mProfessorInformation;
    private ScrollView mScroll;
    private ProgressBar mLoadingBar;

    private Spinner mSubjectSpinner, mSectionSpinner, mGroupSpinner, mTypeSpinner;
    private String subject;
    private long section;
    private long group;
    private int level;
    private int type;


    private EditText mTextEdit, mFileEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mScroll = findViewById(R.id.add_post_scroll);
        mLoadingBar = findViewById(R.id.add_post_progress);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RequestPackage request = new RequestPackage.Builder()
                .addMethod(POST)
                .addEndPoint(EndPointsProvider.getDisplaysProfessorInfo())
                .addParams(JSON_MAIL_ID_PROFESSOR, new PreferencesManager(this, PROFESSOR).getIdUser())
                .create();

        ResponseAsyncTask task = new ResponseAsyncTask(this);
        task.execute(request);

        mSubjectSpinner = findViewById(R.id.add_post_subject_spinner);
        mGroupSpinner = findViewById(R.id.add_post_group_spinner);
        mSectionSpinner = findViewById(R.id.add_post_section_spinner);
        mTypeSpinner = findViewById(R.id.add_post_type_spinner);
        mFileEdit = findViewById(R.id.add_post_file_edit);
        mTextEdit = findViewById(R.id.add_post_text_post);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_post, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.add_post_action:
                if (NetworkUtilities.isConnected(this)) {

                    if (validate()) {
                        RequestPackage request = new RequestPackage.Builder()
                                .addEndPoint(EndPointsProvider.getDisplayEndpointProfessorAdd())
                                .addMethod(POST)
                                .addParams(KEY_AJAX, KEY_ANDROID)
                                .addParams(JSON_MAIL_ID_PROFESSOR, new PreferencesManager(this, PROFESSOR).getIdUser())
                                .addParams(JSON_SUBJECT_ID2, subject)
                                .addParams(JSON_STUDENT_LEVEL, String.valueOf(level))
                                .addParams(JSON_STUDENT_SECTION, String.valueOf(section))
                                .addParams(JSON_STUDENT_GROUP, String.valueOf(group))
                                .addParams(KeyDataProvider.JSON_POST_TYPE, String.valueOf(type))
                                .addParams(JSON_POST_TEXT, mTextEdit.getText().toString())
                                .addParams(JSON_POST_FILE, mFileEdit.getText().toString())
                                .create();

                        Log.i("Tag", "onOptionsItemSelected: " + request.getParams());
                        ResponseAsyncTask task = new ResponseAsyncTask(response -> {
                            if (response.getStatus() == 200)
                                Toast.makeText(this, R.string.sucess_post, Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(this, R.string.try_later_problem_connecting, Toast.LENGTH_SHORT).show();
                        });
                        task.execute(request);
                        Toast.makeText(this, R.string.posting, Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } else {
                    Toast.makeText(this, R.string.no_internet_connection_string, Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return true;
    }


    private boolean validate() {
        String text = mTextEdit.getText().toString().trim();
        if (text.length() == 0) {
            Toast.makeText(this, R.string.please_add_text, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    @Override
    public void onGetResponse(Response response) {
        switch (response.getStatus()) {
            case Response.RESPONSE_SUCCESS:
                mProfessorInformation = JsonUtilities.getProfessorInfo(response.getData());
                mLoadingBar.setVisibility(View.GONE);
                if (mProfessorInformation.size() != 0) {
                    setupSubjectSpinner();
                    mScroll.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(this, R.string.you_cant_post_at_moment, Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            case Response.IO_EXCEPTION:
                Toast.makeText(this, R.string.try_later_problem_connecting, Toast.LENGTH_SHORT).show();
                finish();
                break;
            case Response.JSON_EXCEPTION:
                Toast.makeText(this, R.string.error_json, Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
    }

    private void setupSubjectSpinner() {

        SectionSpinnerAdapter sectionAdapter = new SectionSpinnerAdapter(this, android.R.layout.simple_spinner_item);
        sectionAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mSectionSpinner.setAdapter(sectionAdapter);

        GroupSpinnerAdapter groupAdapter = new GroupSpinnerAdapter(this, android.R.layout.simple_spinner_item);
        groupAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mGroupSpinner.setAdapter(groupAdapter);

        mGroupSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                group = id;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        SubjectSpinnerAdapter subjectSpinnerAdapter = new SubjectSpinnerAdapter(this, android.R.layout.simple_spinner_item,
                mProfessorInformation);
        subjectSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        mSectionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                section = id;


                ArrayList<Integer> groupSet = mProfessorInformation.get(mSubjectSpinner.getSelectedItemPosition()).getSectionAndGroups().get(position);
                groupAdapter.setList(groupSet);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mSubjectSpinner.setAdapter(subjectSpinnerAdapter);
        mSubjectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                subject = mProfessorInformation.get(position).getSubjectId();
                level = mProfessorInformation.get(position).getLevel();

                sectionAdapter.clear();
                Set<Integer> sections = mProfessorInformation.get(position).getSectionAndGroups().keySet();
                sectionAdapter.setList(new ArrayList<>(sections));


                groupAdapter.clear();
                ArrayList<Integer> groupSet = mProfessorInformation.get(position).getSectionAndGroups().get(0);
                groupAdapter.setList(groupSet);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        List<PostTypes> list = new ArrayList<>();

        list.add(new PostTypes(PostTypes.AVIS_TYPE));
        list.add(new PostTypes(PostTypes.CONSULTATION_TYPE));
        list.add(new PostTypes(PostTypes.MARK_TYPE));

        TypeSpinnerAdapter typeAdapter = new TypeSpinnerAdapter(this, android.R.layout.simple_spinner_item, list);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        mTypeSpinner.setAdapter(typeAdapter);
        mTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type = (int) id;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
