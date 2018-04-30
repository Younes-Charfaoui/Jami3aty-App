/*
 * Copyright (c) 2018. This code was created by Younes Charfaoui in the process of Graduation Project for the year of  2018 , which is about creating a platform  for students and professors to help them in the communication and the get known of the university information and so on.
 */

package com.ibnkhaldoun.studentside.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;

public class ProfessorInfo implements Parcelable {

    public static final Parcelable.Creator<ProfessorInfo> CREATOR = new Parcelable.Creator<ProfessorInfo>() {
        @Override
        public ProfessorInfo createFromParcel(Parcel source) {
            return new ProfessorInfo(source);
        }

        @Override
        public ProfessorInfo[] newArray(int size) {
            return new ProfessorInfo[size];
        }
    };
    private String subjectId, subjectTitle;
    private int level;
    private HashMap<Integer, ArrayList<Integer>> sectionAndGroups;

    public ProfessorInfo(String subjectId, String subjectTitle, int level, HashMap<Integer, ArrayList<Integer>> sectionAndGroups) {
        this.subjectId = subjectId;
        this.subjectTitle = subjectTitle;
        this.level = level;
        this.sectionAndGroups = sectionAndGroups;
    }

    protected ProfessorInfo(Parcel in) {
        this.subjectId = in.readString();
        this.subjectTitle = in.readString();
        this.level = in.readInt();
        this.sectionAndGroups = (HashMap<Integer, ArrayList<Integer>>) in.readSerializable();
    }

    public String getSubjectId() {

        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectTitle() {
        return subjectTitle;
    }

    public void setSubjectTitle(String subjectTitle) {
        this.subjectTitle = subjectTitle;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public HashMap<Integer, ArrayList<Integer>> getSectionAndGroups() {
        return sectionAndGroups;
    }

    public void setSectionAndGroups(HashMap<Integer, ArrayList<Integer>> sectionAndGroups) {
        this.sectionAndGroups = sectionAndGroups;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.subjectId);
        dest.writeString(this.subjectTitle);
        dest.writeInt(this.level);
        dest.writeSerializable(this.sectionAndGroups);
    }
}
