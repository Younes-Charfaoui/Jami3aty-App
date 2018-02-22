package com.ibnkhaldoun.studentside.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.ibnkhaldoun.studentside.enums.PostTypes;

public class Notification implements Parcelable {

    private Professor professor;
    private String date, text;
    private PostTypes type;

    public Notification(Professor professor, String date, String text, PostTypes type) {
        this.professor = professor;
        this.date = date;
        this.text = text;
        this.type = type;
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

    public PostTypes getType() {
        return type;
    }

    public void setType(PostTypes type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.professor, flags);
        dest.writeString(this.date);
        dest.writeString(this.text);
        dest.writeInt(this.type == null ? -1 : this.type.ordinal());
    }

    protected Notification(Parcel in) {
        this.professor = in.readParcelable(Professor.class.getClassLoader());
        this.date = in.readString();
        this.text = in.readString();
        int tmpType = in.readInt();
        this.type = tmpType == -1 ? null : PostTypes.values()[tmpType];
    }

    public static final Parcelable.Creator<Notification> CREATOR = new Parcelable.Creator<Notification>() {
        @Override
        public Notification createFromParcel(Parcel source) {
            return new Notification(source);
        }

        @Override
        public Notification[] newArray(int size) {
            return new Notification[size];
        }
    };
}
