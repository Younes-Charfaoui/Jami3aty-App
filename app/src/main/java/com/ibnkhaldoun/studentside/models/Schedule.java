package com.ibnkhaldoun.studentside.models;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Schedule implements Parcelable {
    public static final Parcelable.Creator<Schedule> CREATOR = new Parcelable.Creator<Schedule>() {
        @Override
        public Schedule createFromParcel(Parcel source) {
            return new Schedule(source);
        }

        @Override
        public Schedule[] newArray(int size) {
            return new Schedule[size];
        }
    };
    private int dayOfSchedule;
    private List<ScheduleItem> scheduleItemList;

    public Schedule(int dayOfSchedule, List<ScheduleItem> scheduleItemList) {
        this.dayOfSchedule = dayOfSchedule;
        this.scheduleItemList = scheduleItemList;
    }

    public Schedule(int dayOfSchedule) {
        this.dayOfSchedule = dayOfSchedule;
        this.scheduleItemList = new ArrayList<>();
    }

    protected Schedule(Parcel in) {
        this.dayOfSchedule = in.readInt();
        this.scheduleItemList = in.createTypedArrayList(ScheduleItem.CREATOR);
    }

    public int getDayOfSchedule() {
        return dayOfSchedule;
    }

    public List<ScheduleItem> getScheduleItemList() {
        return scheduleItemList;
    }

    public void setScheduleItemList(List<ScheduleItem> scheduleItemList) {
        this.scheduleItemList = scheduleItemList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.dayOfSchedule);
        dest.writeTypedList(this.scheduleItemList);
    }
}
