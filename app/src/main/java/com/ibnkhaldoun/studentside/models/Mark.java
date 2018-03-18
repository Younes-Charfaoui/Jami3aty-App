package com.ibnkhaldoun.studentside.models;


import android.os.Parcel;
import android.os.Parcelable;

public class Mark implements Parcelable {

    private String subjectName, shortSubjectName;
    private float mExam, mTD, mTP;
    private String courseProfessor;
    private String tdProfessor;
    private String tpProfessor;
    private long subjectId;

    public Mark(long subjectId ,String subjectName, String shortSubjectName, float mExam, float mTD, float mTP, String courseProfessor, String tdProfessor, String tpProfessor) {
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



    public String getCourseProfessor() {
        return courseProfessor;
    }

    public void setCourseProfessor(String courseProfessor) {
        this.courseProfessor = courseProfessor;
    }

    public String getTdProfessor() {
        return tdProfessor;
    }

    public void setTdProfessor(String tdProfessor) {
        this.tdProfessor = tdProfessor;
    }

    public String getTpProfessor() {
        return tpProfessor;
    }

    public void setTpProfessor(String tpProfessor) {
        this.tpProfessor = tpProfessor;
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

    public long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(long subjectId) {
        this.subjectId = subjectId;
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
}
