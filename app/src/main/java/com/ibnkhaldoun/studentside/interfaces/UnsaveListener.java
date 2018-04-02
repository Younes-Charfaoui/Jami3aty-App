package com.ibnkhaldoun.studentside.interfaces;


import com.ibnkhaldoun.studentside.networking.models.Response;

public interface UnsaveListener {

    void OnUnsaveFinished(Response response, int position);

    void OnUnsaveStarted(long id, int position);

    void OnUnsavedEmpty();

}
