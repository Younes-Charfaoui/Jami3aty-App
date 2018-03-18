package com.ibnkhaldoun.studentside.interfaces;


import com.ibnkhaldoun.studentside.networking.models.SignUpResponse;

public interface SignUpTaskListener {
    void onSignUpCompletionListener(SignUpResponse response);

}
