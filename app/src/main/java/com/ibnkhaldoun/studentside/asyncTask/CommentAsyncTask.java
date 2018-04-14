/*
 * Copyright (c) 2018. This code was created by Younes Charfaoui in the process of Graduation Project for the year of  2018 , which is about creating a platform  for students and professors to help them in the communication and the get known of the university information and so on.
 */

package com.ibnkhaldoun.studentside.asyncTask;

import android.os.AsyncTask;

import com.ibnkhaldoun.studentside.models.Comment;
import com.ibnkhaldoun.studentside.networking.models.RequestPackage;
import com.ibnkhaldoun.studentside.networking.utilities.HttpUtilities;
import com.ibnkhaldoun.studentside.networking.utilities.JsonUtilities;

import java.io.IOException;
import java.util.List;

public class CommentAsyncTask extends AsyncTask<RequestPackage, Void, List<Comment>> {

    private ICommentListener mListener;

    public CommentAsyncTask(ICommentListener mListener) {
        this.mListener = mListener;
    }

    @Override
    protected List<Comment> doInBackground(RequestPackage... requests) {

        try {
            String response = HttpUtilities.getData(requests[0]);
            return JsonUtilities.getCommentsList(response);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Comment> comments) {
        mListener.OnFinishedLoading(comments);
    }

    @Override
    protected void onPreExecute() {
        mListener.OnStartLoading();
    }

    public interface ICommentListener {
        void OnStartLoading();

        void OnFinishedLoading(List<Comment> list);
    }
}
