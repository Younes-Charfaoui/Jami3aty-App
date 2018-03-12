package com.ibnkhaldoun.studentside.asyncTask;

import android.os.AsyncTask;

import com.ibnkhaldoun.studentside.interfaces.TaskListener;
import com.ibnkhaldoun.studentside.networking.models.RequestPackage;
import com.ibnkhaldoun.studentside.networking.models.SignUpResponse;
import com.ibnkhaldoun.studentside.networking.utilities.HttpUtilities;
import com.ibnkhaldoun.studentside.networking.utilities.JsonUtilities;

import java.io.IOException;


public class SignUpAsyncTask extends AsyncTask<RequestPackage, Void, SignUpResponse> {

    private TaskListener mTaskListener;

    public SignUpAsyncTask(TaskListener listener) {
        this.mTaskListener = listener;
    }

    @Override
    protected void onPostExecute(SignUpResponse signUpResponse) {
        mTaskListener.onSignUpCompletionListener(signUpResponse);
    }

    @Override
    protected SignUpResponse doInBackground(RequestPackage... requestPackages) {
        try {
            String response = HttpUtilities.getData(requestPackages[0]);
            return JsonUtilities.getSignUpResponse(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
