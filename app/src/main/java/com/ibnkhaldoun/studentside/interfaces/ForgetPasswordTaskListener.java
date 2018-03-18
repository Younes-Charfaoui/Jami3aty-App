package com.ibnkhaldoun.studentside.interfaces;


import com.ibnkhaldoun.studentside.networking.models.ForgetPasswordResponse;

public interface ForgetPasswordTaskListener {

    void onForgetPasswordCompletionListener(ForgetPasswordResponse response);
}
