package com.ibnkhaldoun.studentside.activities;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.ibnkhaldoun.studentside.R;
import com.ibnkhaldoun.studentside.Utilities.ActivityUtilities;
import com.ibnkhaldoun.studentside.Utilities.Utilities;
import com.ibnkhaldoun.studentside.enums.UnityTypes;
import com.ibnkhaldoun.studentside.models.Subject;

import static android.view.View.VISIBLE;

/**
 * @definition this activity will show the detail of the subject
 * to let the student to take some information about the subject
 * and do some basic search of it's content.
 */
public class SubjectsDetailActivity extends AppCompatActivity {

    // the key with which we had take out the subject from the Intent.
    public static final String SUBJECT_KEY = "subjectKey";
    private final static String TAG = "subjectDetail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //taking out the subject to display it's information on the screen.
        Subject subject = getIntent().getParcelableExtra(SUBJECT_KEY);

        // setting up the title of the tool bar and the Action bar.
        toolbar.setTitle(subject.getTitle());

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(subject.getTitle());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        //coloring the circle of the subject with a color.
        int colorOfSubject = Utilities.getCircleColor(subject.getTitle().charAt(0), this);
        ActivityUtilities.changeStatusBarColor(getWindow(), colorOfSubject);

        //setting the color of the tool bar to the color of the current subject.
        toolbar.setBackgroundColor(colorOfSubject);

        //getting reference to the text view and setting up it's color and it text.
        ((TextView) findViewById(R.id.subject_title_title)).setTextColor(colorOfSubject);
        ((TextView) findViewById(R.id.subject_title)).setText(subject.getTitle());

        //getting reference to the text view and setting up it's text.
        ((TextView) findViewById(R.id.subject_coefficient_title)).setTextColor(colorOfSubject);

        //getting reference to the text view and setting up it's color and it text.
        TextView coefficientTv = findViewById(R.id.subject_coefficient);
        coefficientTv.setText(subject.getCoefficient());
        GradientDrawable backCircle = (GradientDrawable) coefficientTv.getBackground();
        backCircle.setColor(colorOfSubject);

        //getting reference to the text view and setting up it's color and it text.
        ((TextView) findViewById(R.id.subject_utility_title)).setTextColor(colorOfSubject);
        ((TextView) findViewById(R.id.subject_utility)).setText(subject.getSummary());

        //getting reference to the text view and setting up it's color and it text.
        if (subject.getCourseProfessor() != null) {
            findViewById(R.id.subject_course_professor_title).setVisibility(VISIBLE);
            ((TextView) findViewById(R.id.subject_course_professor_title)).setTextColor(colorOfSubject);
            findViewById(R.id.subject_course_professor).setVisibility(VISIBLE);
            ((TextView) findViewById(R.id.subject_course_professor)).setText(subject.getCourseProfessor());
        }

        //see if the subject has a td so we must display the td text view.
        if (subject.getTdProfessor() != null) {
            findViewById(R.id.subject_td_professor_title).setVisibility(VISIBLE);
            ((TextView) findViewById(R.id.subject_td_professor_title)).setTextColor(colorOfSubject);
            findViewById(R.id.subject_td_professor).setVisibility(VISIBLE);
            ((TextView) findViewById(R.id.subject_td_professor)).setText(subject.getTdProfessor());
        }

        //see if the subject has a tp so we must display the td text view.
        if (subject.getTpProfessor() != null) {
            findViewById(R.id.subject_tp_professor_title).setVisibility(VISIBLE);
            ((TextView) findViewById(R.id.subject_tp_professor_title)).setTextColor(colorOfSubject);
            findViewById(R.id.subject_tp_professor).setVisibility(VISIBLE);
            ((TextView) findViewById(R.id.subject_tp_professor)).setText(subject.getTpProfessor());
        }

        //setting up the overview of the subject.
        ((TextView) findViewById(R.id.subject_overview_title)).setTextColor(colorOfSubject);
        ((TextView) findViewById(R.id.subject_overview)).setText(subject.getContent());

        //setting up the unity type text view.
        ((TextView) findViewById(R.id.subject_unity_type_title)).setTextColor(colorOfSubject);
        Log.i(TAG, "onCreate: ht e subject type is " + subject.getUnityTypes());
        ((TextView) findViewById(R.id.subject_unity_type)).setText(UnityTypes.getUnitType(subject.getUnityTypes()));

        //setting up the color and the text for the credit
        ((TextView) findViewById(R.id.subject_credit_title)).setTextColor(colorOfSubject);
        TextView textCredit = findViewById(R.id.subject_credit);
        GradientDrawable circleCredit = (GradientDrawable) textCredit.getBackground();
        circleCredit.setColor(colorOfSubject);
        textCredit.setText(subject.getCredit());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }
}