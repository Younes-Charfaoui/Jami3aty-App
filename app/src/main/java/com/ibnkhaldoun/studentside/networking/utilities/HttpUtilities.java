package com.ibnkhaldoun.studentside.networking.utilities;


import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import com.ibnkhaldoun.studentside.networking.models.RequestPackage;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

/**
 * @definition this class have the methods of
 * accessing the end point and getting data from the
 * internet of course , it base on the method of call
 * that are defined in the request package.
 */
public class HttpUtilities {

    /**
     * thi method will check if the package contain a post or a get method
     * and base on this choice it will make a call and return a string response
     *
     * @param requestPackage requestPackage
     * @return String
     * @throws IOException
     */
    public static String getData(RequestPackage requestPackage) throws IOException {
        String address = requestPackage.getEndPoint();

        //if the method is get so we create a url with hte appropriate params.
        if (requestPackage.getMethod().equals(RequestPackage.GET)) {
            address = createGetUrl(requestPackage.getParams(),
                    requestPackage.getEndPoint());
        }

        //creating the client that will make the call.
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        clientBuilder.connectTimeout(15000, TimeUnit.MILLISECONDS);

        OkHttpClient client = clientBuilder.build();

        //make a point to the end point.
        Request.Builder requestBuilder = new Request.Builder().url(address);


        //if the method is post we need to add the params in the multipart builder.
        if (requestPackage.getMethod().equals(RequestPackage.POST)) {
            MultipartBody.Builder builder = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM);
            for (String key : requestPackage.getParams().keySet()) {
                builder.addFormDataPart(key, requestPackage.getParams().get(key));
            }
            RequestBody requestBody = builder.build();
            requestBuilder.method(RequestPackage.POST, requestBody);
        }

        //creating the request based on what has been passed.
        Request request = requestBuilder.build();

        client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onFailure(WebSocket webSocket, Throwable t, @Nullable Response response) {
                super.onFailure(webSocket, t, response);
                Log.i("Fuck", "onFailure: the request was failed");
            }
        });

        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            //this will get true only if the response code was 200 from the http.

            return response.body().string();
        } else {
            //the response code was not 200.
            throw new IOException("Response code is: " + response.code());
        }
    }

    /**
     * this method can create a url with the %s=%s pattern for the get request
     *
     * @param params
     * @param endPoint
     * @return
     */
    private static String createGetUrl(Map<String, String> params, String endPoint) {
        Uri.Builder uriBuilder = Uri.parse(endPoint).buildUpon();
        for (String key : params.keySet()) {
            uriBuilder.appendQueryParameter(key, params.get(key));
        }
        return uriBuilder.build().toString();
    }
}
