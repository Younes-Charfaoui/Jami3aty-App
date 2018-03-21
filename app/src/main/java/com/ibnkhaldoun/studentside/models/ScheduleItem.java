package com.ibnkhaldoun.studentside.models;


import android.os.Parcel;
import android.os.Parcelable;

import com.ibnkhaldoun.studentside.enums.ClassTypes;


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
    private String time, location;
    private String subject;
    private int classType;
    private Professor professor;

    public ScheduleItem(String time, String location, String subject, int classType, Professor profesor) {
        this.time = time;
        this.location = location;
        this.subject = subject;
        this.classType = classType;
        this.professor = profesor;
    }


    public ScheduleItem(String time, String location, String subject, int classType) {
        this.time = time;
        this.location = location;
        this.subject = subject;
        this.classType = classType;
    }

    protected ScheduleItem(Parcel in) {
        this.time = in.readString();
        this.location = in.readString();
        this.subject = in.readString();
        this.classType = in.readInt();
        this.professor = in.readParcelable(Professor.class.getClassLoader());
    }

    public Professor getProfessor() {
        return professor;
    }

    public String getTime() {
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

    public int getClassType() {
        return classType;
    }

    @Override
    public String toString() {
        return this.getTime() + ", " +
                ClassTypes.getStringFormat(this.classType) +
                " at" + this.getLocation();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.time);
        dest.writeString(this.location);
        dest.writeString(this.subject);
        dest.writeInt(this.classType);
        dest.writeParcelable(this.professor, flags);
    }
}
