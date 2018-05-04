/*------------------------------------------------------------------------------
 - Copyright (c) 2018. This code was created by Younes Charfaoui in the process of Graduation Project for the year of  2018 , which is about creating a platform  for students and professors to help them in the communication and the get known of the university information and so on.
 -----------------------------------------------------------------------------*/

package com.ibnkhaldoun.studentside.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.ibnkhaldoun.studentside.Utilities.Utilities;

/**
 * @definition this class model the post of the professor to their students.
 */

public class Display implements Parcelable {

    public static final Creator<Display> CREATOR = new Creator<Display>() {
        @Override
        public Display createFromParcel(Parcel source) {
            return new Display(source);
        }

        @Override
        public Display[] newArray(int size) {
            return new Display[size];
        }
    };
    private long id;
    private String date, professor, text, file, subject;
    private int type;
    private boolean saved;

    public Display(long id, String date, String professor, String text, String file, String subject, int type) {

        this.id = id;
        this.date = date;
        this.professor = professor;
        this.text = text;
        this.file = file;
        this.subject = subject;
        this.type = type;
    }

    public Display(long id, String date, String professor, String text, String file, String subject, int type, boolean saved) {
        this.saved = saved;
        this.id = id;
        this.date = date;
        this.professor = professor;
        this.text = text;
        this.file = file;
        this.subject = subject;
        this.type = type;
    }

    protected Display(Parcel in) {
        this.id = in.readLong();
        this.date = in.readString();
        this.professor = in.readString();
        this.text = in.readString();
        this.file = in.readString();
        this.subject = in.readString();
        this.type = in.readInt();
        this.saved = in.readByte() != 0;
    }

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    public long getId() {

        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDate() {
        return Utilities.getDateFormat(date);
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.date);
        dest.writeString(this.professor);
        dest.writeString(this.text);
        dest.writeString(this.file);
        dest.writeString(this.subject);
        dest.writeInt(this.type);
        dest.writeByte(this.saved ? (byte) 1 : (byte) 0);
    }
}
