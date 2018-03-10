package com.ibnkhaldoun.studentside.services;

import android.app.IntentService;
import android.content.Intent;

/**
 * @definition this service will basically do the network operation
 * to get the data from the server and broadcast it to
 * specific activity or fragment that are waiting for it.
 */

public class LoadDataService extends IntentService {

    public static final String DISPLAY_ACTION = "displayAction";
    public static final String MAIL_ACTION = "mailAction";
    public static final String SCHEDULE_ACTION = "scheduleAction";
    public static final String MARK_ACTION = "displayMark";

    public static final int DISPLAY_TYPE = 1;
    public static final int MAIL_TYPE = 2;
    public static final int SCHEDULE_TYPE = 3;
    public static final int MARK_TYPE = 4;


    public LoadDataService() {
        super("LoadDataService");
    }

    @Override
    public void onHandleIntent(Intent intent) {
        int type = intent.getIntExtra("Action", -1);
        switch (type) {
            case MAIL_TYPE:
                break;
            case SCHEDULE_TYPE:
                break;
            case DISPLAY_TYPE:
                break;
            case MARK_TYPE:
                break;
            default:
                throw new IllegalStateException();
        }
    }
}
