package com.ibnkhaldoun.studentside.networking.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * @definition this class have common method of the network state
 * and some kind of that.
 */
public final class NetworkUtilities {

    //this method get the state of the network : connected of not.
    public static boolean isConnected(Context context) {
        ConnectivityManager manager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        try {
            if (manager != null) {
                NetworkInfo info = manager.getActiveNetworkInfo();
                return info != null && info.isConnectedOrConnecting();
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
