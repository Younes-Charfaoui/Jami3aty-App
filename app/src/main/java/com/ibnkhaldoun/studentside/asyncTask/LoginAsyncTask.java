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