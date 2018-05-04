/*------------------------------------------------------------------------------
 - Copyright (c) 2018. This code was created by Younes Charfaoui in the process of Graduation Project for the year of  2018 , which is about creating a platform  for students and professors to help them in the communication and the get known of the university information and so on.
 -----------------------------------------------------------------------------*/

package com.ibnkhaldoun.studentside.asyncTask;

import android.os.AsyncTask;
import android.util.Log;

import com.ibnkhaldoun.studentside.interfaces.UnsaveListener;
import com.ibnkhaldoun.studentside.networking.models.RequestPackage;
import com.ibnkhaldoun.studentside.networking.models.Response;
import com.ibnkhaldoun.studentside.networking.utilities.HttpUtilities;

import java.io.IOException;

import static com.ibnkhaldoun.studentside.networking.models.Response.IO_EXCEPTION;


public class UnSaveAsyncTask extends AsyncTask<RequestPackage, Void, Response> {

    private UnsaveListener mUnsaveListener;
    private int position;

    public UnSaveAsyncTask(UnsaveListener unsaveListener, int position) {
        this.mUnsaveListener = unsaveListener;
        this.position = position;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Response response) {
        super.onPostExecute(response);
        mUnsaveListener.OnUnsaveFinished(response, position);
    }

    @Override
    protected Response doInBackground(RequestPackage... requestPackages) {
        try {
            String responseString = HttpUtilities.getData(requestPackages[0]);
            Log.i("Something", "doInBackground: " +  responseString);
            return new Response(200);
        } catch (IOException e) {
            return new Response(IO_EXCEPTION);
        }
    }
}
