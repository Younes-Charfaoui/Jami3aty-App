package com.ibnkhaldoun.studentside.models;


import android.os.Parcel;
import android.os.Parcelable;

public class Message implements Parcelable {

    private boolean in;
    private String subject, text, date, professor;

    public boolean isIn() {
        return in;
    }

    public void setIn(boolean in) {
        this.in = in;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public Message(boolean in, String subject, String text, String date, String professor) {

        this.in = in;
        this.subject = subject;
        this.text = text;
        this.date = date;
        this.professor = professor;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.in ? (byte) 1 : (byte) 0);
        dest.writeString(this.subject);
        dest.writeString(this.text);
        dest.writeString(this.date);
        dest.writeString(this.professor);
    }

    protected Message(Parcel in) {
        this.in = in.readByte() != 0;
        this.subject = in.readString();
        this.text = in.readString();
        this.date = in.readString();
        this.professor = in.readString();
    }

    public static final Parcelable.Creator<Message> CREATOR = new Parcelable.Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel source) {
            return new Message(source);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };
}
