package com.ibnkhaldoun.studentside.models;


import android.os.Parcel;
import android.os.Parcelable;


public class ScheduleItem implements Parcelable {

    public static final Parcelable.Creator<ScheduleItem> CREATOR = new Parcelable.Creator<ScheduleItem>() {
        @Override
        public ScheduleItem createFromParcel(Parcel source) {
            return new ScheduleItem(source);
        }

        @Override
        public ScheduleItem[] newArray(int size) {
            return new ScheduleItem[size];
        }
    };
    private int time;
    private String subject, location;
    private String professor;
    private int type;

    public ScheduleItem(int time, String location, String subject, String professor, int type) {
        this.time = time;
        this.location = location;
        this.subject = subject;
        this.professor = professor;
        this.type = type;
    }


    protected ScheduleItem(Parcel in) {
        this.time = in.readInt();
        this.location = in.readString();
        this.subject = in.readString();
        this.professor = in.readString();
        this.type = in.readInt();
    }

    public String getProfessor() {
        return professor;
    }

    public int getTime() {
        return time;
    }

    public String getLocation() {
        return location;
    }

    public boolean isPassed() {
        return false;
    }

    public String getSubject() {
        return subject;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.time);
        dest.writeString(this.location);
        dest.writeString(this.subject);
        dest.writeString(this.professor);
        dest.writeInt(this.type);
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type){
        this.type = type;
    }
}
