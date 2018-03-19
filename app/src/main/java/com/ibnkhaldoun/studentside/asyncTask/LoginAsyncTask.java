package com.ibnkhaldoun.studentside.asyncTask;

import android.os.AsyncTask;
import android.util.Log;

import com.ibnkhaldoun.studentside.interfaces.LoginTaskListener;
import com.ibnkhaldoun.studentside.networking.models.LoginResponse;
import com.ibnkhaldoun.studentside.networking.models.RequestPackage;
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
        Log.i("Test", "Background");
        try {
            Log.i("End", "doInBackground: the end point " + requestPackages[0].getEndPoint());
            Log.i("Ismail", " Before Executing ");
            String response = HttpUtilities.getData(requestPackages[0]);
            Log.i("Data", "doInBackground: " + response);
            return JsonUtilities.getLoginResponse(response);
        } catch (IOException e) {
            Log.i("Ismail", "doInBackground: it was an IOException");
            e.printStackTrace();
        }
        return null;
    }
}