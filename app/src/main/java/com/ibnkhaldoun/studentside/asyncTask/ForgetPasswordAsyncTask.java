package com.ibnkhaldoun.studentside.asyncTask;

import android.os.AsyncTask;

import com.ibnkhaldoun.studentside.interfaces.TaskListener;
import com.ibnkhaldoun.studentside.networking.models.ForgetPasswordResponse;
import com.ibnkhaldoun.studentside.networking.models.RequestPackage;
import com.ibnkhaldoun.studentside.networking.utilities.HttpUtilities;
import com.ibnkhaldoun.studentside.networking.utilities.JsonUtilities;

import java.io.IOException;


public class ForgetPasswordAsyncTask extends AsyncTask<RequestPackage, Void, ForgetPasswordResponse> {

    private TaskListener mTaskListener;

    public ForgetPasswordAsyncTask(TaskListener listener) {
        this.mTaskListener = listener;
    }

    @Override
    protected void onPostExecute(ForgetPasswordResponse forgetResponse) {
        mTaskListener.onForgetPasswordCompletionListener(forgetResponse);
    }

    @Override
    protected ForgetPasswordResponse doInBackground(RequestPackage... requestPackages) {
        try {
            String response = HttpUtilities.getData(requestPackages[0]);
            return JsonUtilities.getForgetPasswordResponse(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
