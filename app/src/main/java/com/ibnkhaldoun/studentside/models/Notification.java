package com.ibnkhaldoun.studentside.models;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.ibnkhaldoun.studentside.enums.PostTypes;

public class Notification implements Parcelable {

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
    private String professor, date, subjectTitle;
    private int type;
    private long id;
    private boolean seen;

    public Notification(String professor, String date, String subjectTitle, int type, long id, boolean seen) {

        this.professor = professor;
        this.date = date;
        this.subjectTitle = subjectTitle;
        this.type = type;
        this.id = id;
        this.seen = seen;
    }

    protected Notification(Parcel in) {
        this.professor = in.readString();
        this.date = in.readString();
        this.subjectTitle = in.readString();
        this.type = in.readInt();
        this.id = in.readLong();
        this.seen = in.readByte() != 0;
    }

    public String createText(Context context) {
        return this.professor + " Has publish an " + context.getString(PostTypes.getType(type)) + " about "
                + subjectTitle;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSubjectTitle() {
        return subjectTitle;
    }

    public void setSubjectTitle(String subjectTitle) {
        this.subjectTitle = subjectTitle;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.professor);
        dest.writeString(this.date);
        dest.writeString(this.subjectTitle);
        dest.writeInt(this.type);
        dest.writeLong(this.id);
        dest.writeByte(this.seen ? (byte) 1 : (byte) 0);
    }
}
