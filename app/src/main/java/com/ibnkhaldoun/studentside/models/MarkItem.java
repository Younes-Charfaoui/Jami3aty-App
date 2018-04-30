/*
 * Copyright (c) 2018. This code was created by Younes Charfaoui in the process of Graduation Project for the year of  2018 , which is about creating a platform  for students and professors to help them in the communication and the get known of the university information and so on.
 */

package com.ibnkhaldoun.studentside.models;

import android.os.Parcel;
import android.os.Parcelable;

public class MarkItem implements Parcelable {

    private String subjectName, shortSubjectName;
    private float mExam, mTD, mTP;

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getShortSubjectName() {
        return shortSubjectName;
    }

    public void setShortSubjectName(String shortSubjectName) {
        this.shortSubjectName = shortSubjectName;
    }

    public float getExam() {
        return mExam;
    }

    public void setExam(float mExam) {
        this.mExam = mExam;
    }

    public float getTD() {
        return mTD;
    }

    public void setTD(float mTD) {
        this.mTD = mTD;
    }

    public float getTP() {
        return mTP;
    }

    public void setTP(float mTP) {
        this.mTP = mTP;
    }

    public long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(long subjectId) {
        this.subjectId = subjectId;
    }

    public MarkItem(String subjectName, String shortSubjectName, float mExam, float mTD, float mTP, long subjectId) {

        this.subjectName = subjectName;
        this.shortSubjectName = shortSubjectName;
        this.mExam = mExam;
        this.mTD = mTD;
        this.mTP = mTP;
        this.subjectId = subjectId;
    }

    private long subjectId;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.subjectName);
        dest.writeString(this.shortSubjectName);
        dest.writeFloat(this.mExam);
        dest.writeFloat(this.mTD);
        dest.writeFloat(this.mTP);
        dest.writeLong(this.subjectId);
    }

    protected MarkItem(Parcel in) {
        this.subjectName = in.readString();
        this.shortSubjectName = in.readString();
        this.mExam = in.readFloat();
        this.mTD = in.readFloat();
        this.mTP = in.readFloat();
        this.subjectId = in.readLong();
    }

    public static final Parcelable.Creator<MarkItem> CREATOR = new Parcelable.Creator<MarkItem>() {
        @Override
        public MarkItem createFromParcel(Parcel source) {
            return new MarkItem(source);
        }

        @Override
        public MarkItem[] newArray(int size) {
            return new MarkItem[size];
        }
    };
}
