package com.ibnkhaldoun.studentside.models;


public class Note {
    private String note;
    private String subject;
    private long id;

    public Note(long id, String subject, String note) {
        this.id = id;
        this.note = note;
        this.subject = subject;
    }

    public String getNote() {
        return note;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }
}
