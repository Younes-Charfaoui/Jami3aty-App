/*------------------------------------------------------------------------------
 - Copyright (c) 2018. This code was created by Younes Charfaoui in the process of Graduation Project for the year of  2018 , which is about creating a platform  for students and professors to help them in the communication and the get known of the university information and so on.
 -----------------------------------------------------------------------------*/

package com.ibnkhaldoun.studentside.models;

/**
 * @definition this class model the note that the users have on their
 * phone to remember doing things.
 */

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
