package com.ibnkhaldoun.studentside.models;

import com.ibnkhaldoun.studentside.enums.UnityTypes;

public class Subject {
    private String title, shortTitle;
    private boolean hasTd, hasTP;
    private String summary, content , credit , coefficient;
    private Professor tdProfessor, tpProfessor, courseProfessor;
    private UnityTypes unityTypes;
    private int level;

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

    public Subject(String title, String shortTitle, boolean hasTd, boolean hasTP, String summary, String content, Professor tdProfessor, Professor tpProfessor, Professor courseProfessor, UnityTypes unityTypes) {
        this.title = title;
        this.shortTitle = shortTitle;
        this.hasTd = hasTd;
        this.hasTP = hasTP;
        this.summary = summary;
        this.content = content;
        this.tdProfessor = tdProfessor;
        this.tpProfessor = tpProfessor;
        this.courseProfessor = courseProfessor;
        this.unityTypes = unityTypes;
    }

    public void setHasTd(boolean hasTd) {
        this.hasTd = hasTd;
    }

    public void setHasTP(boolean hasTP) {
        this.hasTP = hasTP;
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
        return hasTd;
    }

    public boolean itHasTP() {
        return hasTP;
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

    public Professor getTdProfessor() {
        return tdProfessor;
    }

    public void setTdProfessor(Professor tdProfessor) {
        this.tdProfessor = tdProfessor;
    }

    public Professor getTpProfessor() {
        return tpProfessor;
    }

    public void setTpProfessor(Professor tpProfessor) {
        this.tpProfessor = tpProfessor;
    }

    public Professor getCourseProfessor() {
        return courseProfessor;
    }

    public void setCourseProfessor(Professor courseProfessor) {
        this.courseProfessor = courseProfessor;
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
}
