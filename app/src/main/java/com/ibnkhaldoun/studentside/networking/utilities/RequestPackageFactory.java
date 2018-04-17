/*
 * Copyright (c) 2018. This code was created by Younes Charfaoui in the process of Graduation Project for the year of  2018 , which is about creating a platform  for students and professors to help them in the communication and the get known of the university information and so on.
 */

package com.ibnkhaldoun.studentside.networking.utilities;

import android.content.Context;

import com.ibnkhaldoun.studentside.Utilities.PreferencesManager;
import com.ibnkhaldoun.studentside.networking.models.RequestPackage;
import com.ibnkhaldoun.studentside.providers.EndPointsProvider;
import com.ibnkhaldoun.studentside.providers.KeyDataProvider;

import static com.ibnkhaldoun.studentside.Utilities.PreferencesManager.STUDENT;
import static com.ibnkhaldoun.studentside.networking.models.RequestPackage.POST;

public class RequestPackageFactory {

    public static RequestPackage createNoteAddingRequest(long id, String note, Context context) {
        return new RequestPackage.Builder()
                .addEndPoint(EndPointsProvider.getAddCommentsEndpoint())
                .addMethod(POST)
                .addParams(KeyDataProvider.KEY_NOTE, note)
                .addParams(KeyDataProvider.JSON_POST_ID2, String.valueOf(id))
                .addParams(KeyDataProvider.JSON_STUDENT_ID, new PreferencesManager(context, STUDENT).getId())
                .addParams(KeyDataProvider.JSON_USER_NAME, new PreferencesManager(context, STUDENT).getFullName())
                .create();
    }

    public static RequestPackage createNoteEditingRequest(long id, String note) {
        return new RequestPackage.Builder()
                .addMethod(POST)
                .addEndPoint(EndPointsProvider.getModifyCommentsEndpoint())
                .addParams(KeyDataProvider.JSON_COMMENT_EDITED, note)
                .addParams(KeyDataProvider.JSON_COMMENT_ID2, String.valueOf(id))
                .create();
    }
}
