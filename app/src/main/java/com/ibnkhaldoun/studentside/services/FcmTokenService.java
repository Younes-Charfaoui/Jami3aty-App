package com.ibnkhaldoun.studentside.services;


import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class FcmTokenService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        sendRegistrationTokenToServer(refreshedToken);
    }

    private void sendRegistrationTokenToServer(String refreshedToken) {
        /// TODO: add the code to send the token to database.
    }
}
