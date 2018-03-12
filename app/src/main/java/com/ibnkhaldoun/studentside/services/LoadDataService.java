package com.ibnkhaldoun.studentside.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

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
    public static final String MARK_ACTION = "displayMark";
    public static final String SUBJECT_ACTION = "subject";

    public static final int DISPLAY_TYPE = 1;
    public static final int MAIL_TYPE = 2;
    public static final int SCHEDULE_TYPE = 3;
    public static final int MARK_TYPE = 4;
    public static final int SUBJECT_TYPE = 5;

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
                break;
            case SCHEDULE_TYPE:
                break;
            case DISPLAY_TYPE:
                break;
            case MARK_TYPE:
                break;
            case SUBJECT_TYPE:

                break;
            default:
                throw new IllegalStateException();
        }
    }

    private void subjectCall(RequestPackage request, Context context) {
        try {
            String response = HttpUtilities.getData(request);
            ArrayList<Subject> subjectList = JsonUtilities.getSubjectList(response);
            Intent intent = new Intent(SUBJECT_ACTION);
            intent.putExtra(KEY_DATA, subjectList);
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            Intent intentDatabase = new Intent(context, DatabaseService.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
