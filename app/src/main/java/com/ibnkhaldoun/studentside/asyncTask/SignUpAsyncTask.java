package com.ibnkhaldoun.studentside.asyncTask;

import android.os.AsyncTask;
import android.support.v4.util.ArrayMap;
import android.util.Log;

import com.ibnkhaldoun.studentside.interfaces.SignUpTaskListener;
import com.ibnkhaldoun.studentside.networking.models.RequestPackage;
import com.ibnkhaldoun.studentside.networking.models.SignUpResponse;
import com.ibnkhaldoun.studentside.networking.utilities.HttpUtilities;
import com.ibnkhaldoun.studentside.networking.utilities.JsonUtilities;

import java.io.IOException;


public class SignUpAsyncTask extends AsyncTask<RequestPackage, Void, SignUpResponse> {

    private SignUpTaskListener mTaskListener;

    public SignUpAsyncTask(SignUpTaskListener listener) {
        this.mTaskListener = listener;
    }

    @Override
    protected void onPostExecute(SignUpResponse signUpResponse) {
        mTaskListener.onSignUpCompletionListener(signUpResponse);
    }

    @Override
    protected SignUpResponse doInBackground(RequestPackage... requestPackages) {
        try {ArrayMap<String, String> map = new ArrayMap<>();
            Log.i("sign", "doInBackground: the background stared");
            String response = HttpUtilities.getData(requestPackages[0]);
            Log.i("sign", "doInBackground: the response " + response);
            return JsonUtilities.getSignUpResponse(response);
        } catch (IOException e) {
            e.printStackTrace();
            Log.i("sign", "doInBackground: it was IOEXception");
        }
        return null;
    }
}
