/*
 * Copyright (c) 2018. This code was created by Younes Charfaoui in the process of Graduation Project for the year of  2018 , which is about creating a platform  for students and professors to help them in the communication and the get known of the university information and so on.
 */

package com.ibnkhaldoun.studentside.asyncTask;

import android.os.AsyncTask;
import android.util.Log;

import com.ibnkhaldoun.studentside.networking.models.RequestPackage;
import com.ibnkhaldoun.studentside.networking.models.Response;
import com.ibnkhaldoun.studentside.networking.utilities.HttpUtilities;
import com.ibnkhaldoun.studentside.networking.utilities.JsonUtilities;

import org.json.JSONException;

import java.io.IOException;

import static com.ibnkhaldoun.studentside.networking.models.Response.IO_EXCEPTION;
import static com.ibnkhaldoun.studentside.networking.models.Response.JSON_EXCEPTION;
import static com.ibnkhaldoun.studentside.networking.models.Response.RESPONSE_SUCCESS;

public class ResponseAsyncTask extends AsyncTask<RequestPackage, Void, Response> {

    private IResponseListener mListener;

    public ResponseAsyncTask(IResponseListener mListener) {
        this.mListener = mListener;
    }

    @Override
    protected void onPostExecute(Response response) {
        super.onPostExecute(response);
        mListener.onGetResponse(response);
    }

    @Override
    protected Response doInBackground(RequestPackage... requestPackages) {
        try {
            String response = HttpUtilities.getData(requestPackages[0]);
            int codeResponse = JsonUtilities.getStatusCode(response);

            Log.i("Save", "doInBackground: " + response);
            return new Response(codeResponse);
        } catch (IOException e) {
            Log.i("Save", "doInBackground: " + e.getMessage());
            return new Response(IO_EXCEPTION);
        } catch (JSONException e) {
            Log.i("Save", "doInBackground: " + e.getMessage() );
            e.printStackTrace();
            return new Response(JSON_EXCEPTION);
        }
    }

    public interface IResponseListener {
        void onGetResponse(Response response);
    }
}
