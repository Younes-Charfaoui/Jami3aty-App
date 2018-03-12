package com.ibnkhaldoun.studentside.asyncTask;

import android.os.AsyncTask;

import com.ibnkhaldoun.studentside.interfaces.TaskListener;
import com.ibnkhaldoun.studentside.networking.models.LoginResponse;
import com.ibnkhaldoun.studentside.networking.models.RequestPackage;
import com.ibnkhaldoun.studentside.networking.utilities.HttpUtilities;
import com.ibnkhaldoun.studentside.networking.utilities.JsonUtilities;

import java.io.IOException;


public class LoginAsyncTask extends AsyncTask<RequestPackage, Void, LoginResponse> {

    private TaskListener mTaskListener;

    public LoginAsyncTask(TaskListener listener) {
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
            e.printStackTrace();
        }
        return null;
    }
}
