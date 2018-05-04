/*------------------------------------------------------------------------------
 - Copyright (c) 2018. This code was created by Younes Charfaoui in the process of Graduation Project for the year of  2018 , which is about creating a platform  for students and professors to help them in the communication and the get known of the university information and so on.
 -----------------------------------------------------------------------------*/

package com.ibnkhaldoun.studentside.models;


import android.os.Parcel;
import android.os.Parcelable;

//this class model the message between student and professor.

public class Message implements Parcelable {
    public static final Creator<Message> CREATOR = new Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel source) {
            return new Message(source);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };
    private long id, idStudent, idProfessor;
    private boolean in;
    private String subject, text, date, professor , student;

    public Message(long id, long idStudent, long idProfessor, boolean in, String subject, String text, String date, String professor, String student) {

        this.id = id;
        this.idStudent = idStudent;
        this.idProfessor = idProfessor;
        this.in = in;
        this.subject = subject;
        this.text = text;
        this.date = date;
        this.professor = professor;
        this.student = student;
    }

    protected Message(Parcel in) {
        this.id = in.readLong();
        this.idStudent = in.readLong();
        this.idProfessor = in.readLong();
        this.in = in.readByte() != 0;
        this.subject = in.readString();
        this.text = in.readString();
        this.date = in.readString();
        this.professor = in.readString();
        this.student = in.readString();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdStudent() {
        return idStudent;
    }

    public long getIdProfessor() {
        return idProfessor;
    }

    public boolean isIn() {
        return in;
    }

    public void setIn(boolean in) {
        this.in = in;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeLong(this.idStudent);
        dest.writeLong(this.idProfessor);
        dest.writeByte(this.in ? (byte) 1 : (byte) 0);
        dest.writeString(this.subject);
        dest.writeString(this.text);
        dest.writeString(this.date);
        dest.writeString(this.professor);
        dest.writeString(this.student);
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }
}
