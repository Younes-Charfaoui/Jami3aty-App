package com.ibnkhaldoun.studentside.models;


import android.os.Parcel;
import android.os.Parcelable;

import com.ibnkhaldoun.studentside.enums.Levels;

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

    public Schedule(int dayOfSchedule, List<ScheduleItem> scheduleItemList, Levels promo) {
        this.dayOfSchedule = dayOfSchedule;
        this.scheduleItemList = scheduleItemList;

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
