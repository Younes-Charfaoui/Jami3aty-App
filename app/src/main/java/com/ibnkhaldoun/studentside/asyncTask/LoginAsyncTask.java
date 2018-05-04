/*------------------------------------------------------------------------------
 - Copyright (c) 2018. This code was created by Younes Charfaoui in the process of Graduation Project for the year of  2018 , which is about creating a platform  for students and professors to help them in the communication and the get known of the university information and so on.
 -----------------------------------------------------------------------------*/

package com.ibnkhaldoun.studentside.asyncTask;

import android.os.AsyncTask;

import com.ibnkhaldoun.studentside.interfaces.LoginTaskListener;
import com.ibnkhaldoun.studentside.networking.models.LoginResponse;
import com.ibnkhaldoun.studentside.networking.models.RequestPackage;
import com.ibnkhaldoun.studentside.networking.models.Response;
import com.ibnkhaldoun.studentside.networking.utilities.HttpUtilities;
import com.ibnkhaldoun.studentside.networking.utilities.JsonUtilities;

import java.io.IOException;


public class LoginAsyncTask extends AsyncTask<RequestPackage, Void, LoginResponse> {

    private LoginTaskListener mTaskListener;

    public LoginAsyncTask(LoginTaskListener listener) {
        this.mTaskListener = listener;
    }

    @Override
    protected void onPostExecute(LoginResponse loginResponse) {
        mTaskListener.onLoginCompletionListener(loginResponse);
    }

    @Override
    protected LoginResponse doInBackground(RequestPackage... requestPackages) {

        try {
            String response = HttpUtilities.getData(requestPackages[0]);
            return JsonUtilities.getLoginResponse(response);
        } catch (IOException e) {
            return new LoginResponse(Response.IO_EXCEPTION);
        }

    }
}