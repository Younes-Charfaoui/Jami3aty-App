package com.ibnkhaldoun.studentside.networking.models;



public class ForgetPasswordResponse {
    private int status;

    public ForgetPasswordResponse(int status){
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

}
