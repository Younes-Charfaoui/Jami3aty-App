package com.ibnkhaldoun.studentside.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Display implements Parcelable {

    public static final Parcelable.Creator<Display> CREATOR = new Parcelable.Creator<Display>() {
        @Override
        public Display createFromParcel(Parcel source) {
            return new Display(source);
        }

        @Override
        public Display[] newArray(int size) {
            return new Display[size];
        }
    };

    private long id;
    private Professor professor;
    private String date, text;
    private int type;


    public Display(long id, Professor professor, String date, String text, int type) {
        this.id = id;
        this.professor = professor;
        this.date = date;
        this.text = text;
        this.type = type;
    }

    protected Display(Parcel in) {
        this.id = in.readLong();
        this.professor = in.readParcelable(Professor.class.getClassLoader());
        this.date = in.readString();
        this.text = in.readString();
        this.type = in.readInt();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getCommentList() {
        return type;
    }

    public void setCommentList(int type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeParcelable(this.professor, flags);
        dest.writeString(this.date);
        dest.writeString(this.text);
        dest.writeInt(this.type);
    }
}
