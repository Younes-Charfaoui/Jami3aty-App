package com.ibnkhaldoun.studentside.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

/**
 * @definition this service will do the operation of
 * adding some data in the database , like that we are doing this
 * operation in the background thread.
 */

public class DatabaseService extends IntentService {


    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     *  Used to name the worker thread, important only for debugging.
     */
    public DatabaseService() {
        super("Database Service");
    }

    //todo add the schedule and the subjects to the database
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }
}
