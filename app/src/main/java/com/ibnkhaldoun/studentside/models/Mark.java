/*------------------------------------------------------------------------------
 - Copyright (c) 2018. This code was created by Younes Charfaoui in the process of Graduation Project for the year of  2018 , which is about creating a platform  for students and professors to help them in the communication and the get known of the university information and so on.
 -----------------------------------------------------------------------------*/

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
    private String courseProfessor;
    private String tdProfessor;
    private String tpProfessor;
    private long subjectId;


    public Mark(long subjectId, String subjectName, String shortSubjectName, float mExam, float mTD, float mTP, String courseProfessor, String tdProfessor, String tpProfessor) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.shortSubjectName = shortSubjectName;
        this.mExam = mExam;
        this.mTD = mTD;
        this.mTP = mTP;
        this.courseProfessor = courseProfessor;
        this.tdProfessor = tdProfessor;
        this.tpProfessor = tpProfessor;
    }

    protected Mark(Parcel in) {
        this.subjectName = in.readString();
        this.shortSubjectName = in.readString();
        this.mExam = in.readFloat();
        this.mTD = in.readFloat();
        this.mTP = in.readFloat();
        this.courseProfessor = in.readString();
        this.tdProfessor = in.readString();
        this.tpProfessor = in.readString();
        this.subjectId = in.readLong();
    }


    public String getSubjectName() {
        return subjectName;
    }

    public String getShortSubjectName() {
        return shortSubjectName;
    }

    public float getExam() {
        return mExam;
    }

    public float getTD() {
        return mTD;
    }

    public float getTP() {
        return mTP;
    }

    public long getSubjectId() {
        return subjectId;
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
        dest.writeString(this.courseProfessor);
        dest.writeString(this.tdProfessor);
        dest.writeString(this.tpProfessor);
        dest.writeLong(this.subjectId);
    }
}
