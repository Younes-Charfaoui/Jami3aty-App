package com.ibnkhaldoun.studentside.interfaces;

import com.ibnkhaldoun.studentside.networking.models.ForgetPasswordResponse;
import com.ibnkhaldoun.studentside.networking.models.LoginResponse;
import com.ibnkhaldoun.studentside.networking.models.SignUpResponse;


public interface TaskListener {

    void onLoginCompletionListener(LoginResponse response);

    void onSignUpCompletionListener(SignUpResponse response);

    void onForgetPasswordCompletionListener(ForgetPasswordResponse response);
}

