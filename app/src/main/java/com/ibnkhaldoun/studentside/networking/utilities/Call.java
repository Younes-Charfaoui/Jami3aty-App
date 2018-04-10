/*
 * Copyright (c) 2018. This code was created by Younes Charfaoui in the process of Graduation Project for the year of  2018 , which is about creating a platform  for students and professors to help them in the communication and the get known of the university information and so on.
 */

package com.ibnkhaldoun.studentside.networking.utilities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.ibnkhaldoun.studentside.models.Display;
import com.ibnkhaldoun.studentside.networking.models.RequestPackage;
import com.ibnkhaldoun.studentside.services.LoadDataService;

import java.io.IOException;
import java.util.ArrayList;

public class Call<D> {

    /*
    public void doCall(RequestPackage requestPackage, Context context, String action) throws IOException {
        String response = HttpUtilities.getData(requestPackage);

        ArrayList<D> list = getDataList(action, response);
        Intent intent = new Intent(action);
        intent.putParcelableArrayListExtra(action, list);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }


    private ArrayList<D> getDataList(String action, String response) {
        switch (action) {
            case LoadDataService.DISPLAY_ACTION:
                return JsonUtilities.getDisplaysList(response);
        }
        return null;
    }*/


}
