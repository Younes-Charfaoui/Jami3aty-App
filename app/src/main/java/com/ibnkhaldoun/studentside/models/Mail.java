package com.ibnkhaldoun.studentside.models;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Mail implements Parcelable {

    private Professor mProfessor;
    private List<Message> mMessages;

    public Mail(Professor mProfessor , List<Message> messages) {
        this.mProfessor = mProfessor;
        this.mMessages = messages;
    }

    public Professor getProfessor() {
        return mProfessor;
    }

    public void setProfessor(Professor mProfessor) {
        this.mProfessor = mProfessor;
    }


    public List<Message> getMessages() {
        return mMessages;
    }

    public void setMessages(List<Message> mMessages) {
        this.mMessages = mMessages;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.mProfessor, flags);
        dest.writeTypedList(this.mMessages);
    }

    protected Mail(Parcel in) {
        this.mProfessor = in.readParcelable(Professor.class.getClassLoader());
        this.mMessages = in.createTypedArrayList(Message.CREATOR);
    }

    public static final Parcelable.Creator<Mail> CREATOR = new Parcelable.Creator<Mail>() {
        @Override
        public Mail createFromParcel(Parcel source) {
            return new Mail(source);
        }

        @Override
        public Mail[] newArray(int size) {
            return new Mail[size];
        }
    };
}
