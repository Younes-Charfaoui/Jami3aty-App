/*------------------------------------------------------------------------------
 - Copyright (c) 2018. This code was created by Younes Charfaoui in the process of Graduation Project for the year of  2018 , which is about creating a platform  for students and professors to help them in the communication and the get known of the university information and so on.
 -----------------------------------------------------------------------------*/

package com.ibnkhaldoun.studentside.models;


import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;

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
    private int level;

    public int getLevel() {
        return level;
    }

    public int getSection() {
        return section;
    }

    public int getGroup() {
        return group;
    }

    private int section;
    private int group;

    public Schedule(int dayOfSchedule,
                    int level, int section, int group) {
        this.dayOfSchedule = dayOfSchedule;
        this.scheduleItemList = new ArrayList<>();
        this.level = level;
        this.section = section;
        this.group = group;
    }


    protected Schedule(Parcel in) {
        this.dayOfSchedule = in.readInt();
        this.scheduleItemList = in.createTypedArrayList(ScheduleItem.CREATOR);
        this.level = in.readInt();
        this.section = in.readInt();
        this.group = in.readInt();
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
        dest.writeInt(this.level);
        dest.writeInt(this.section);
        dest.writeInt(this.group);
    }

    public static ArrayList<Schedule> getScheduleList(SparseArray<Schedule> sparse) {
        ArrayList<Schedule> returnedList = new ArrayList<>();
        for (int i = 0; i < sparse.size(); i++) {
            returnedList.add(sparse.get(sparse.keyAt(i)));
        }
        return returnedList;
    }
}
