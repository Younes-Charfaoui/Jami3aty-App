package com.ibnkhaldoun.studentside.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

public class Subject implements Parcelable {

    public static final Parcelable.Creator<Subject> CREATOR = new Parcelable.Creator<Subject>() {
        @Override
        public Subject createFromParcel(Parcel source) {
            return new Subject(source);
        }

        @Override
        public Subject[] newArray(int size) {
            return new Subject[size];
        }
    };

    private long id;
    private String title, shortTitle;
    private String summary, content, credit, coefficient;
    private String tdProfessor;
    private String tpProfessor;
    private String courseProfessor;
    private int unityTypes;


    public Subject() {
    }

    public Subject(@NonNull String title, @NonNull String shortTitle,
                   @NonNull String summary, @NonNull String content, @NonNull String credit,
                   @NonNull String coefficient, int unityTypes) {
        this.title = title;
        this.shortTitle = shortTitle;
        this.summary = summary;
        this.content = content;
        this.credit = credit;
        this.coefficient = coefficient;
        this.unityTypes = unityTypes;
    }


    protected Subject(Parcel in) {
        this.id = in.readLong();
        this.title = in.readString();
        this.shortTitle = in.readString();
        this.summary = in.readString();
        this.content = in.readString();
        this.credit = in.readString();
        this.coefficient = in.readString();
        this.tdProfessor = in.readString();
        this.tpProfessor = in.readString();
        this.courseProfessor = in.readString();
        this.unityTypes = in.readInt();
    }

    public String getTdProfessor() {
        return tdProfessor;
    }

    public void setTdProfessor(String tdProfessor) {
        this.tdProfessor = tdProfessor;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTpProfessor() {
        return tpProfessor;
    }

    public void setTpProfessor(String tpProfessor) {
        this.tpProfessor = tpProfessor;
    }

    public String getCourseProfessor() {
        return courseProfessor;
    }

    public void setCourseProfessor(String courseProfessor) {
        this.courseProfessor = courseProfessor;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortTitle() {
        return shortTitle;
    }

    public String getSummary() {
        return summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getUnityTypes() {
        return unityTypes;
    }

    public String getCoefficient() {
        return coefficient;
    }

    public String getCredit() {
        return credit;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.title);
        dest.writeString(this.shortTitle);
        dest.writeString(this.summary);
        dest.writeString(this.content);
        dest.writeString(this.credit);
        dest.writeString(this.coefficient);
        dest.writeString(this.tdProfessor);
        dest.writeString(this.tpProfessor);
        dest.writeString(this.courseProfessor);
        dest.writeInt(this.unityTypes);
    }
}
