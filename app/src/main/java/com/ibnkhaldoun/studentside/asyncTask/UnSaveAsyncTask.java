package com.ibnkhaldoun.studentside.asyncTask;

import android.os.AsyncTask;

import com.ibnkhaldoun.studentside.interfaces.UnsaveListener;
import com.ibnkhaldoun.studentside.networking.models.RequestPackage;
import com.ibnkhaldoun.studentside.networking.models.Response;
import com.ibnkhaldoun.studentside.networking.utilities.HttpUtilities;
import com.ibnkhaldoun.studentside.networking.utilities.JsonUtilities;

import org.json.JSONException;

import java.io.IOException;

import static com.ibnkhaldoun.studentside.networking.models.Response.IO_EXCEPTION;
import static com.ibnkhaldoun.studentside.networking.models.Response.JSON_EXCEPTION;


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
            return JsonUtilities.getUnsaveResponse(responseString);
        } catch (IOException e) {
            return new Response(IO_EXCEPTION);
        } catch (JSONException e) {
            return new Response(JSON_EXCEPTION);
        }
    }
}
