package com.ibnkhaldoun.studentside.models;


import android.os.Parcel;
import android.os.Parcelable;

import com.ibnkhaldoun.studentside.enums.ClassTypes;


public class ScheduleItem implements Parcelable {
    public static final Creator<ScheduleItem> CREATOR = new Creator<ScheduleItem>() {
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
    private ClassTypes classType;

    public Professor getProfessor() {
        return professor;
    }

    //todo the variable added below for just testing purpose
    private Professor professor;

    public ScheduleItem(String time, String location, String subject, ClassTypes classType, Professor profesor) {
        this.time = time;
        this.location = location;
        this.subject = subject;
        this.classType = classType;
        this.professor = profesor;
    }

    protected ScheduleItem(Parcel in) {
        this.time = in.readString();
        this.location = in.readString();
        this.subject = in.readString();
        int tmpClassType = in.readInt();
        this.classType = tmpClassType == -1 ? null : ClassTypes.values()[tmpClassType];
        this.professor = in.readParcelable(Professor.class.getClassLoader());
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

    public ClassTypes getClassType() {
        return classType;
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
        dest.writeInt(this.classType == null ? -1 : this.classType.ordinal());
        dest.writeParcelable(this.professor, flags);
    }
}
