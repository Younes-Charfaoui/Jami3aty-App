package com.ibnkhaldoun.studentside.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.ibnkhaldoun.studentside.enums.UnityTypes;

import java.util.ArrayList;
import java.util.List;

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
    private UnityTypes unityTypes;
    private int level;
    private List<ScheduleItem> scheduleList = new ArrayList<>();

    public Subject() {
    }

    public Subject(String title, String shortTitle, String summary, String content, String credit, String coefficient, UnityTypes unityTypes, int level) {
        this.title = title;
        this.shortTitle = shortTitle;
        this.summary = summary;
        this.content = content;
        this.credit = credit;
        this.coefficient = coefficient;
        this.unityTypes = unityTypes;
        this.level = level;
    }

    public Subject(String title, String shortTitle, String summary, String content, String coefficient, String credit, int level, UnityTypes unityTypes, String tdProfessor, String tpProfessor, String courseProfessor) {
        this.title = title;
        this.shortTitle = shortTitle;
        this.summary = summary;
        this.content = content;
        this.tdProfessor = tdProfessor;
        this.tpProfessor = tpProfessor;
        this.courseProfessor = courseProfessor;
        this.unityTypes = unityTypes;
        this.level = level;
        this.coefficient = coefficient;
        this.credit = credit;
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
        int tmpUnityTypes = in.readInt();
        this.unityTypes = tmpUnityTypes == -1 ? null : UnityTypes.values()[tmpUnityTypes];
        this.level = in.readInt();
        this.scheduleList = in.createTypedArrayList(ScheduleItem.CREATOR);
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

    public List<ScheduleItem> getScheduleList() {
        return this.scheduleList;
    }

    public void setScheduleList(List<ScheduleItem> scheduleList) {
        this.scheduleList = scheduleList;
    }

    public void addScheduleItem(ScheduleItem item) {
        scheduleList.add(item);
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

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }

    public boolean itHasTd() {
        return this.tdProfessor != null;
    }

    public boolean itHasTp() {
        return this.tpProfessor != null;
    }

    public boolean itHasCourse() {
        return this.courseProfessor != null;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UnityTypes getUnityTypes() {
        return unityTypes;
    }

    public void setUnityTypes(UnityTypes unityTypes) {
        this.unityTypes = unityTypes;
    }

    public String getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(String coefficient) {
        this.coefficient = coefficient;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
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
        dest.writeInt(this.unityTypes == null ? -1 : this.unityTypes.ordinal());
        dest.writeInt(this.level);
        dest.writeTypedList(this.scheduleList);
    }
}
