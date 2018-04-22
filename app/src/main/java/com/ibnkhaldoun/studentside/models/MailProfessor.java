/*
 * Copyright (c) 2018. This code was created by Younes Charfaoui in the process of Graduation Project for the year of  2018 , which is about creating a platform  for students and professors to help them in the communication and the get known of the university information and so on.
 */

package com.ibnkhaldoun.studentside.models;

import android.os.Parcel;
import android.os.Parcelable;

public class MailProfessor implements Parcelable {
    private String id , name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MailProfessor(String id, String name) {

        this.id = id;
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
    }

    protected MailProfessor(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<MailProfessor> CREATOR = new Parcelable.Creator<MailProfessor>() {
        @Override
        public MailProfessor createFromParcel(Parcel source) {
            return new MailProfessor(source);
        }

        @Override
        public MailProfessor[] newArray(int size) {
            return new MailProfessor[size];
        }
    };
}
