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
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.ibnkhaldoun.studentside.R;
import com.ibnkhaldoun.studentside.Utilities.PreferencesManager;
import com.ibnkhaldoun.studentside.adapters.SubjectSpinnerAdapter;
import com.ibnkhaldoun.studentside.asyncTask.ResponseAsyncTask;
import com.ibnkhaldoun.studentside.models.ProfessorInfo;
import com.ibnkhaldoun.studentside.networking.models.RequestPackage;
import com.ibnkhaldoun.studentside.networking.models.Response;
import com.ibnkhaldoun.studentside.networking.utilities.JsonUtilities;
import com.ibnkhaldoun.studentside.providers.EndPointsProvider;

import java.util.ArrayList;
import java.util.Set;

import static com.ibnkhaldoun.studentside.Utilities.PreferencesManager.PROFESSOR;
import static com.ibnkhaldoun.studentside.networking.models.RequestPackage.POST;

public class AddPostActivity extends AppCompatActivity implements ResponseAsyncTask.IResponseListener {

    private ArrayList<ProfessorInfo> mProfessorInformation;
    private ScrollView mScroll;
    private ProgressBar mLoadingBar;

    private Spinner mSubjectSpinner, mSectionSpinner, mGroupSpinner;
    private String subject;
    private int section;
    private int group;
    private int level;

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
                .addParams("id_professor", new PreferencesManager(this, PROFESSOR).getIdUser())
                .create();

        ResponseAsyncTask task = new ResponseAsyncTask(this);
        task.execute(request);

        mSubjectSpinner = findViewById(R.id.add_post_subject_spinner);
        mGroupSpinner = findViewById(R.id.add_post_group_spinner);
        mSectionSpinner = findViewById(R.id.add_post_section_spinner);
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
                // TODO: 28/04/2018 send post to server
                Toast.makeText(this, "Add Post", Toast.LENGTH_SHORT).show();
                break;
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
                    mScroll.setVisibility(View.VISIBLE);
                    setupSubjectSpinner();
                    Log.i("Tag", "onReceive: the list is not empty");
                } else {
                    Log.i("Tag", "onReceive: the list is empty");
                    Toast.makeText(this, "You can't post anything for the moment, try later.", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            case Response.IO_EXCEPTION:
                Toast.makeText(this, "Probelm when connecting, try later", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case Response.JSON_EXCEPTION:
                Toast.makeText(this, "Probelm when getting response from server, try later", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
    }

    private void toastAll() {
        Toast.makeText(this, level + " " + subject + " " + section + " " + group + " ", Toast.LENGTH_SHORT).show();
    }

    private void setupSubjectSpinner() {

        ArrayAdapter<String> sectionAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        sectionAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mSectionSpinner.setAdapter(sectionAdapter);

        ArrayAdapter<String> groupAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        groupAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mGroupSpinner.setAdapter(groupAdapter);

        mGroupSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                group = Integer.parseInt(groupAdapter.getItem(position).replace("Group ", ""));
                toastAll();
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
                section = Integer.parseInt(sectionAdapter.getItem(position).replace("Section ", ""));
                toastAll();
                groupAdapter.clear();
                ArrayList<Integer> groupSet = mProfessorInformation.get(mSubjectSpinner.getSelectedItemPosition()).getSectionAndGroups().get(position);
                for (int group : groupSet) {
                    groupAdapter.add("Group " + (group));
                }
                group = Integer.parseInt(groupAdapter.getItem(0).replace("Group ", ""));
                groupAdapter.notifyDataSetChanged();
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

                for (int section : sections) {
                    sectionAdapter.add("Section " + (section + 1));

                }
                section = Integer.parseInt(sectionAdapter.getItem(0).replace("Section ", ""));
                sectionAdapter.notifyDataSetChanged();

                groupAdapter.clear();
                ArrayList<Integer> groupSet = mProfessorInformation.get(position).getSectionAndGroups().get(0);
                for (int group : groupSet) {

                    groupAdapter.add("Group " + (group));
                }
                group = Integer.parseInt(groupAdapter.getItem(0).replace("Group ", ""));
                groupAdapter.notifyDataSetChanged();
                toastAll();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}
