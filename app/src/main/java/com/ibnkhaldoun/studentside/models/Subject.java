/*------------------------------------------------------------------------------
 - Copyright (c) 2018. This code was created by Younes Charfaoui in the process of Graduation Project for the year of  2018 , which is about creating a platform  for students and professors to help them in the communication and the get known of the university information and so on.
 -----------------------------------------------------------------------------*/

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
    private int level;

    public Subject() {
    }

    public Subject(@NonNull String title, @NonNull String shortTitle,
                   @NonNull String summary, @NonNull String content, @NonNull String credit,
                   @NonNull String coefficient, int unityTypes ,int level) {
        this.title = title;
        this.shortTitle = shortTitle;
        this.summary = summary;
        this.content = content;
        this.credit = credit;
        this.coefficient = coefficient;
        this.unityTypes = unityTypes;
        this.level = level;
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
        this.level = in.readInt();
    }

    public int getLevel() {
        return level;
    }

    public Subject(String title, String shortTitle, String summary, String content, String credit, String coefficient, String tdProfessor, String tpProfessor, String courseProfessor, int unityTypes , int level) {

        this.title = title;
        this.shortTitle = shortTitle;
        this.summary = summary;
        this.content = content;
        this.credit = credit;
        this.coefficient = coefficient;
        this.tdProfessor = tdProfessor;
        this.tpProfessor = tpProfessor;
        this.courseProfessor = courseProfessor;
        this.unityTypes = unityTypes;
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
        dest.writeInt(this.level);
    }

    @Override
    public String toString() {
        return credit + " " + coefficient+ " "+level + courseProfessor ;
    }
}
