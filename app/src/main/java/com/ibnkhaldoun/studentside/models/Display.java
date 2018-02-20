package com.ibnkhaldoun.studentside.models;

public class Display {
    //todo : add code to use the display object
    private long id;
    private Professor professor;
    private String date, text;

    public Display(long id, Professor professor, String date, String text) {
        this.id = id;
        this.professor = professor;
        this.date = date;
        this.text = text;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
