/*------------------------------------------------------------------------------
 - Copyright (c) 2018. This code was created by Younes Charfaoui in the process of Graduation Project for the year of  2018 , which is about creating a platform  for students and professors to help them in the communication and the get known of the university information and so on.
 -----------------------------------------------------------------------------*/

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

/**
 * @definition: This activity is responsible for the UI and the background logic
 * for the posting activity of the professor, in which there are spinners and editText
 * to help professor customize the post and then publish it to the student to see it.
 */

public class AddPostActivity extends AppCompatActivity implements ResponseAsyncTask.IResponseListener {

    //array list for holding the professor info gotten via the internet.
    private ArrayList<ProfessorInfo> mProfessorInformation;
    private ScrollView mScroll;
    private ProgressBar mLoadingBar;

    //spinners to display the information to the professor.
    private Spinner mSubjectSpinner, mSectionSpinner, mGroupSpinner, mTypeSpinner;

    //some variables to hold the actual selection to send to the server later.
    private String subject;
    private long section, group;
    private int level, type;

    //edit text for the file and the text that will be posted by the professor.
    private EditText mTextEdit, mFileEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //getting reference to the views
        mScroll = findViewById(R.id.add_post_scroll);
        mLoadingBar = findViewById(R.id.add_post_progress);
        mSubjectSpinner = findViewById(R.id.add_post_subject_spinner);
        mGroupSpinner = findViewById(R.id.add_post_group_spinner);
        mSectionSpinner = findViewById(R.id.add_post_section_spinner);
        mTypeSpinner = findViewById(R.id.add_post_type_spinner);
        mFileEdit = findViewById(R.id.add_post_file_edit);
        mTextEdit = findViewById(R.id.add_post_text_post);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //the request package to get the professor information.
        RequestPackage request = new RequestPackage.Builder()
                .addMethod(POST)
                .addEndPoint(EndPointsProvider.getDisplaysProfessorInfo())
                .addParams(JSON_MAIL_ID_PROFESSOR, new PreferencesManager(this, PROFESSOR).getIdUser())
                .create();

        //creating and executing the task.
        ResponseAsyncTask task = new ResponseAsyncTask(this);
        task.execute(request);
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
                //check if there is connection to the internet first.
                if (NetworkUtilities.isConnected(this)) {
                    //check if the information are well formed via the method {validate}.
                    if (validate()) {
                        //if they are correct we create a package and launch it to add the post to the database.

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
                            //getting the response from the server about how the situation of our post.
                            if (response.getStatus() == 200)
                                Toast.makeText(this, R.string.sucess_post, Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(this, R.string.try_later_problem_connecting, Toast.LENGTH_SHORT).show();
                        });
                        task.execute(request);
                        Toast.makeText(this, R.string.posting, Toast.LENGTH_SHORT).show();
                        // finishing the activity.
                        finish();
                    }
                } else {
                    // in the case of the no internet we display a simple message.
                    Toast.makeText(this, R.string.no_internet_connection_string, Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return true;
    }


    /**
     * this method check if the information are well formed and display a toast message if the
     * information are not well formed.
     *
     * @return the boolean that indicate if the information are well formed.
     */
    private boolean validate() {
        String text = mTextEdit.getText().toString().trim();
        if (text.length() == 0) {
            Toast.makeText(this, R.string.please_add_text, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    /**
     * this method was from the interface {@link ResponseAsyncTask.IResponseListener}
     * to make callbacks when the information of the professor are gotten.
     *
     * @param response
     */
    @Override
    public void onGetResponse(Response response) {
        switch (response.getStatus()) {
            case Response.RESPONSE_SUCCESS:
                //if the response was succeeded
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

    /**
     * this method is a helper to initialize the spinner and the
     * appropriate adapter and to track the value of each spinner.
     */
    private void setupSubjectSpinner() {

        GroupSpinnerAdapter groupAdapter = new GroupSpinnerAdapter(this, android.R.layout.simple_spinner_item);
        groupAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mGroupSpinner.setAdapter(groupAdapter);

        //set callback when the value of spinner changed
        mGroupSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                group = id;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        SectionSpinnerAdapter sectionAdapter = new SectionSpinnerAdapter(this, android.R.layout.simple_spinner_item);
        sectionAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mSectionSpinner.setAdapter(sectionAdapter);


        //set callback when the value of spinner changed
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

        SubjectSpinnerAdapter subjectSpinnerAdapter = new SubjectSpinnerAdapter(this, android.R.layout.simple_spinner_item,
                mProfessorInformation);
        subjectSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        //set the subject spinner to the subjectSpinnerAdapter
        mSubjectSpinner.setAdapter(subjectSpinnerAdapter);

        //set callback when the value of spinner changed
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

        //list to hold the types value
        List<PostTypes> list = new ArrayList<>();

        list.add(new PostTypes(PostTypes.AVIS_TYPE));
        list.add(new PostTypes(PostTypes.CONSULTATION_TYPE));
        list.add(new PostTypes(PostTypes.MARK_TYPE));

        //the type spinner hold the Types of the post {AVIS_TYPE , CONSULTATION_TYPE , MARK_TYPE}
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
