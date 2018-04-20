package com.ibnkhaldoun.studentside.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.ibnkhaldoun.studentside.Utilities.PreferencesManager;
import com.ibnkhaldoun.studentside.activities.MarkActivity;
import com.ibnkhaldoun.studentside.activities.SavedActivity;
import com.ibnkhaldoun.studentside.activities.ScheduleActivity;
import com.ibnkhaldoun.studentside.activities.SubjectsActivity;
import com.ibnkhaldoun.studentside.models.Comment;
import com.ibnkhaldoun.studentside.models.Display;
import com.ibnkhaldoun.studentside.models.Mail;
import com.ibnkhaldoun.studentside.models.Mark;
import com.ibnkhaldoun.studentside.models.Message;
import com.ibnkhaldoun.studentside.models.Notification;
import com.ibnkhaldoun.studentside.models.Saved;
import com.ibnkhaldoun.studentside.models.Subject;
import com.ibnkhaldoun.studentside.networking.models.RequestPackage;
import com.ibnkhaldoun.studentside.networking.utilities.HttpUtilities;
import com.ibnkhaldoun.studentside.networking.utilities.JsonUtilities;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import static com.ibnkhaldoun.studentside.networking.models.Response.IO_EXCEPTION;
import static com.ibnkhaldoun.studentside.networking.models.Response.JSON_EXCEPTION;
import static com.ibnkhaldoun.studentside.services.DatabaseService.KEY_CONTENT_DATA;

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
    public static final String SUBJECT_INNER_ACTION = "subjectAction";
    public static final String NOTIFICATION_ACTION = "notificationAction";
    public static final String COMMENTS_ACTION = "commentsAction";

    public static final int DISPLAY_TYPE = 1;
    public static final int MAIL_TYPE = 2;
    public static final int SCHEDULE_TYPE = 3;
    public static final int MARK_TYPE = 4;
    public static final int SUBJECT_TYPE = 5;
    public static final int ALL_TYPE = 6;
    public static final int NOTIFICATION_TYPE = 7;
    public static final int SAVED_TYPE = 8;
    public static final int SUBJECT_IN_TYPE = 9;
    public static final int SCHEDULE_IN_TYPE = 10;
    public static final int COMMENT_TYPE = 11;

    public static final String KEY_ACTION = "Action";
    public static final String KEY_DATA = "data";
    public static final String KEY_ERROR = "error";
    public static final String SAVED_ACTION = "savedAction";

    public static final String ACTION_ERROR = "errorAction";

    public LoadDataService() {
        super("LoadDataService");
    }

    @Override
    public void onHandleIntent(Intent intent) {
        int type = intent.getIntExtra(KEY_ACTION, -1);
        RequestPackage request = intent.getParcelableExtra(KEY_REQUEST);
        switch (type) {
            case MAIL_TYPE:
                mailCall(request);
                break;
            case SCHEDULE_TYPE:
                scheduleCall(request);
                break;
            case DISPLAY_TYPE:
                displayCall(request);
                break;
            case MARK_TYPE:
                markCall(request);
                break;
            case SUBJECT_TYPE:
                subjectCall(request);
                break;
            case SUBJECT_IN_TYPE:
                subjectInnerCall(request);
                break;
            case NOTIFICATION_TYPE:
                notificationCall(request);
                break;
            case SAVED_TYPE:
                savedCall(request);
                break;
            case SCHEDULE_IN_TYPE:
                scheduleInnerCall(request);
                break;
            case COMMENT_TYPE:
                commentsCall(request);
                break;
        }
    }

    private void displayCall(RequestPackage request) {
        try {
            String response = HttpUtilities.getData(request);
            ArrayList<Display> displayList = JsonUtilities.getDisplaysList(response);
            Intent intent = new Intent(DISPLAY_ACTION);
            intent.putExtra(KEY_DATA, displayList);
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void notificationCall(RequestPackage request) {
        try {
            String response = HttpUtilities.getData(request);
            Log.i("Notif", "notificationCall: " + response);
            ArrayList<Notification> notificationList = JsonUtilities.getNotificationList(response);
            Intent intent = new Intent(NOTIFICATION_ACTION);
            intent.putExtra(KEY_DATA, notificationList);
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        } catch (IOException e) {
            e.printStackTrace();
            Log.i("Tag", "notificationCall: IO");
        } catch (JSONException e) {
            e.printStackTrace();
            Log.i("Tag", "notificationCall: JSON");
        }
    }

    private void commentsCall(RequestPackage request) {
        try {
            String response = HttpUtilities.getData(request);
            ArrayList<Comment> commentsList = JsonUtilities.getCommentsList(response);
            Intent intent = new Intent(COMMENTS_ACTION);
            intent.putExtra(KEY_DATA, commentsList);
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void scheduleInnerCall(RequestPackage request) {
        try {
            String response = HttpUtilities.getData(request);
            Intent intentDatabase = new Intent(this, DatabaseService.class);
            intentDatabase.putExtra(DatabaseService.KEY_CONTENT_DATA, response);
            intentDatabase.putExtra(LoadDataService.KEY_ACTION, LoadDataService.SCHEDULE_TYPE);
            startService(intentDatabase);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void subjectInnerCall(RequestPackage request) {
        try {
            String response = HttpUtilities.getData(request);

            ArrayList<Subject> subjectList = JsonUtilities.getSubjectList(response);
            ArrayList<String> subjectsStrings = new ArrayList<>();
            if (subjectList != null) for (Subject subject : subjectList) {
                subjectsStrings.add(subject.getTitle());
            }
            PreferencesManager manager = new
                    PreferencesManager(this, PreferencesManager.SUBJECT);
            manager.addSubjects(subjectsStrings);
            Intent intentDatabase = new Intent(this, DatabaseService.class);
            intentDatabase.putExtra(KEY_CONTENT_DATA, subjectList);
            intentDatabase.putExtra(KEY_ACTION, SUBJECT_TYPE);
            startService(intentDatabase);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void scheduleCall(RequestPackage request) {
        try {
            String response = HttpUtilities.getData(request);

            Intent intentSchedule = new Intent(SCHEDULE_ACTION);
            intentSchedule.putExtra(ScheduleActivity.KEY_SCHEDULE, response);
            LocalBroadcastManager.getInstance(this).sendBroadcast(intentSchedule);
            Intent intentDatabase = new Intent(this, DatabaseService.class);
            intentDatabase.putExtra(KEY_ACTION, SCHEDULE_TYPE);
            intentDatabase.putExtra(KEY_CONTENT_DATA, response);
            startService(intentDatabase);
        } catch (IOException ex) {
            ex.printStackTrace();
            LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(ACTION_ERROR).putExtra(KEY_ERROR, IO_EXCEPTION));
        }
    }

    private void savedCall(RequestPackage request) {
        try {
            String response = HttpUtilities.getData(request);
            ArrayList<Saved> savedList = JsonUtilities.getSavedList(response);
            Intent intentSaved = new Intent(SAVED_ACTION);
            intentSaved.putParcelableArrayListExtra(SavedActivity.KEY_SAVED, savedList);
            LocalBroadcastManager.getInstance(this).sendBroadcast(intentSaved);
            Intent intentDatabase = new Intent(this, DatabaseService.class);
            intentDatabase.putExtra(KEY_ACTION, SAVED_TYPE);
            intentDatabase.putParcelableArrayListExtra(KEY_CONTENT_DATA, savedList);
            startService(intentDatabase);
        } catch (IOException exception) {
            exception.printStackTrace();
            LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(ACTION_ERROR).putExtra(KEY_ERROR, IO_EXCEPTION));
        } catch (JSONException e) {
            e.printStackTrace();
            LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(ACTION_ERROR).putExtra(KEY_ERROR, JSON_EXCEPTION));
        }
    }

    private void subjectCall(RequestPackage request) {
        try {
            String response = HttpUtilities.getData(request);
            ArrayList<Subject> subjectList = JsonUtilities.getSubjectList(response);
            Intent intentSubject = new Intent(SUBJECT_ACTION);
            intentSubject.putExtra(SubjectsActivity.KEY_SUBJECTS, subjectList);
            LocalBroadcastManager.getInstance(this).sendBroadcast(intentSubject);
            Intent intentDatabase = new Intent(this, DatabaseService.class);
            intentDatabase.putExtra(KEY_CONTENT_DATA, subjectList);
            intentDatabase.putExtra(KEY_ACTION, SUBJECT_TYPE);
            startService(intentDatabase);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mailCall(RequestPackage requestPackage) {
        try {
            Log.i("MailCall", "mailCall: request start");
            String response = HttpUtilities.getData(requestPackage);
            ArrayList<Message> mailList = JsonUtilities.getMailList(response);
            Intent intentToMails = new Intent(MAIL_ACTION);
            intentToMails.putExtra(KEY_DATA, mailList);
            LocalBroadcastManager.getInstance(this).sendBroadcast(intentToMails);
//            Intent intentToDatabase = new Intent(this, DatabaseService.class);
//            intentToDatabase.putExtra(KEY_CONTENT_DATA, mailList);
//            startService(intentToDatabase);
        } catch (IOException e) {
            Log.i("MailCall", "mailCall: it was Io");
            e.printStackTrace();
        }
    }

    private void markCall(RequestPackage request) {
        try {
            String response = HttpUtilities.getData(request);
            ArrayList<Mark> markList = JsonUtilities.getMarkList(response);
            Intent intentMark = new Intent(MARK_ACTION);
            intentMark.putExtra(MarkActivity.KEY_MARKS, markList);
            LocalBroadcastManager.getInstance(this).sendBroadcast(intentMark);
            Intent intentDatabase = new Intent(this, DatabaseService.class);
            intentDatabase.putExtra(KEY_CONTENT_DATA, markList);
            intentDatabase.putExtra(KEY_ACTION, MARK_TYPE);
            startService(intentDatabase);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
