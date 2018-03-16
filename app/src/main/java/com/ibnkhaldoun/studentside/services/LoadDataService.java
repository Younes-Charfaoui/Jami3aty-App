package com.ibnkhaldoun.studentside.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.ibnkhaldoun.studentside.activities.MarkActivity;
import com.ibnkhaldoun.studentside.activities.StudentMainActivity;
import com.ibnkhaldoun.studentside.activities.SubjectsActivity;
import com.ibnkhaldoun.studentside.models.Mail;
import com.ibnkhaldoun.studentside.models.Mark;
import com.ibnkhaldoun.studentside.models.Subject;
import com.ibnkhaldoun.studentside.networking.models.RequestPackage;
import com.ibnkhaldoun.studentside.networking.utilities.HttpUtilities;
import com.ibnkhaldoun.studentside.networking.utilities.JsonUtilities;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @definition this service will basically do the network operation
 * to get the data from the server and broadcast it to
 * specific activity or fragment that are waiting for it.
 */

public class LoadDataService extends IntentService {

    public static final String KEY_REQUEST = "request";

    public static final String DISPLAY_ACTION = "displayAction";
    public static final String MAIL_ACTION = "mailAction";
    public static final String SCHEDULE_ACTION = "scheduleAction";
    public static final String MARK_ACTION = "markAction";
    public static final String SUBJECT_ACTION = "subjectAction";
    public static final String NOTIFICATION_ACTION = "notificationAction";

    public static final int DISPLAY_TYPE = 1;
    public static final int MAIL_TYPE = 2;
    public static final int SCHEDULE_TYPE = 3;
    public static final int MARK_TYPE = 4;
    public static final int SUBJECT_TYPE = 5;
    public static final int ALL_TYPE = 6;
    public static final int NOTIFICATION_TYPE = 7;

    public static final String KEY_ACTION = "Action";
    public static final String KEY_DATA = "data";

    public LoadDataService() {
        super("LoadDataService");
    }

    @Override
    public void onHandleIntent(Intent intent) {
        int type = intent.getIntExtra(KEY_ACTION, -1);
        RequestPackage request = intent.getParcelableExtra(KEY_REQUEST);
        switch (type) {
            case MAIL_TYPE:
                mailCall(request, this);
                break;
            case SCHEDULE_TYPE:
                break;
            case DISPLAY_TYPE:
                break;
            case MARK_TYPE:
                markCall(request, this);
                break;
            case SUBJECT_TYPE:
                subjectCall(request, this);
                break;
        }
    }

    private void subjectCall(RequestPackage request, Context context) {
        try {
            String response = HttpUtilities.getData(request);
            ArrayList<Subject> subjectList = JsonUtilities.getSubjectList(response);
            Intent intentSubject = new Intent(SUBJECT_ACTION);
            intentSubject.putExtra(SubjectsActivity.KEY_SUBJECTS, subjectList);
            LocalBroadcastManager.getInstance(this).sendBroadcast(intentSubject);
            Intent intentDatabase = new Intent(context, DatabaseService.class);
            intentDatabase.putExtra(DatabaseService.KEY_CONTENT_DATA, subjectList);
            intentDatabase.putExtra(KEY_ACTION, SUBJECT_TYPE);
            startService(intentDatabase);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mailCall(RequestPackage requestPackage, Context context) {
        try {
            String response = HttpUtilities.getData(requestPackage);
            ArrayList<Mail> mailList = JsonUtilities.getMailList(response);
            Intent intentToMails = new Intent(MAIL_ACTION);
            intentToMails.putExtra(StudentMainActivity.KEY_MAILS, mailList);
            LocalBroadcastManager.getInstance(context).sendBroadcast(intentToMails);
            Intent intentToDatabase = new Intent(context, DatabaseService.class);
            intentToDatabase.putExtra(DatabaseService.KEY_CONTENT_DATA, mailList);
            startService(intentToDatabase);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void markCall(RequestPackage request, Context context) {
        try {
            String response = HttpUtilities.getData(request);
            ArrayList<Mark> markList = JsonUtilities.getMarkList(response);
            Intent intentMark = new Intent(MARK_ACTION);
            intentMark.putExtra(MarkActivity.KEY_MARKS, markList);
            LocalBroadcastManager.getInstance(this).sendBroadcast(intentMark);
            Intent intentDatabase = new Intent(context, DatabaseService.class);
            intentDatabase.putExtra(DatabaseService.KEY_CONTENT_DATA, markList);
            intentDatabase.putExtra(KEY_ACTION, MARK_TYPE);
            startService(intentDatabase);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
