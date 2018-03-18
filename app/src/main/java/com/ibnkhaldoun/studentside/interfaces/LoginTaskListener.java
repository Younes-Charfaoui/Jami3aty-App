package com.ibnkhaldoun.studentside.interfaces;


import com.ibnkhaldoun.studentside.networking.models.LoginResponse;

public interface LoginTaskListener {
    void onLoginCompletionListener(LoginResponse response);

}
