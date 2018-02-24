package com.ibnkhaldoun.studentside.models;


import android.os.Parcel;
import android.os.Parcelable;

public class Mark implements Parcelable {
    public static final Parcelable.Creator<Mark> CREATOR = new Parcelable.Creator<Mark>() {
        @Override
        public Mark createFromParcel(Parcel source) {
            return new Mark(source);
        }

        @Override
        public Mark[] newArray(int size) {
            return new Mark[size];
        }
    };
    private String subjectName, shortSubjectName;
    private float mExam, mTD, mTP;

    public Mark(String subjectName, String shortSubjectName, float mExam) {
        this.subjectName = subjectName;
        this.shortSubjectName = shortSubjectName;
        this.mExam = mExam;
    }

    public Mark(String shortSubjectName, String subjectName, float mExam, float mTD, float mTP) {
        this.subjectName = subjectName;
        this.shortSubjectName = shortSubjectName;
        this.mExam = mExam;
        this.mTD = mTD;
        this.mTP = mTP;
    }

    public Mark(String subjectName, String shortSubjectName, float mExam, float mTD) {
        this.subjectName = subjectName;
        this.shortSubjectName = shortSubjectName;
        this.mExam = mExam;
        this.mTD = mTD;
    }

    protected Mark(Parcel in) {
        this.subjectName = in.readString();
        this.shortSubjectName = in.readString();
        this.mExam = in.readFloat();
        this.mTD = in.readFloat();
        this.mTP = in.readFloat();
    }


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
    }
}
