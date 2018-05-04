/*------------------------------------------------------------------------------
 - Copyright (c) 2018. This code was created by Younes Charfaoui in the process of Graduation Project for the year of  2018 , which is about creating a platform  for students and professors to help them in the communication and the get known of the university information and so on.
 -----------------------------------------------------------------------------*/

package com.ibnkhaldoun.studentside.asyncTask;

import android.os.AsyncTask;

import com.ibnkhaldoun.studentside.interfaces.ResponseTaskListener;
import com.ibnkhaldoun.studentside.networking.models.RequestPackage;
import com.ibnkhaldoun.studentside.networking.models.Response;
import com.ibnkhaldoun.studentside.networking.utilities.HttpUtilities;
import com.ibnkhaldoun.studentside.networking.utilities.JsonUtilities;

import java.io.IOException;


public class SignUpAsyncTask extends AsyncTask<RequestPackage, Void, Response> {

    private ResponseTaskListener mTaskListener;

    public SignUpAsyncTask(ResponseTaskListener listener) {
        this.mTaskListener = listener;
    }

    @Override
    protected void onPostExecute(Response signUpResponse) {
        mTaskListener.onTaskCompletionListener(signUpResponse);
    }

    @Override
    protected Response doInBackground(RequestPackage... requestPackages) {
        try {

            String response = HttpUtilities.getData(requestPackages[0]);

            return JsonUtilities.getSignUpResponse(response);
        } catch (IOException e) {
            e.printStackTrace();

            return new Response(Response.RESPONSE_SUCCESS);
        }
    }
}
