package com.ibnkhaldoun.studentside.models;


public class Note {
    private String note;
    private Subject subject;

    public Note(String note, Subject subject) {

        this.note = note;
        this.subject = subject;
    }

    public String getNote() {
        return note;
    }

    public Subject getSubject() {
        return subject;
    }
}
