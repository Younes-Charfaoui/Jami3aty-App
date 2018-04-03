package com.ibnkhaldoun.studentside.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Display implements Parcelable {


    private long id;
    private String date, professor, text , file , subject;
    private int type;

    public void setId(long id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getId() {

        return id;
    }

    public String getDate() {
        return date;
    }

    public String getProfessor() {
        return professor;
    }

    public String getText() {
        return text;
    }

    public String getFile() {
        return file;
    }

    public String getSubject() {
        return subject;
    }

    public int getType() {
        return type;
    }

    public Display(long id, String date, String professor, String text, String file, String subject, int type) {

        this.id = id;
        this.date = date;
        this.professor = professor;
        this.text = text;
        this.file = file;
        this.subject = subject;
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
    }

    protected Display(Parcel in) {
        this.id = in.readLong();
        this.date = in.readString();
        this.professor = in.readString();
        this.text = in.readString();
        this.file = in.readString();
        this.subject = in.readString();
        this.type = in.readInt();
    }

    public static final Parcelable.Creator<Display> CREATOR = new Parcelable.Creator<Display>() {
        @Override
        public Display createFromParcel(Parcel source) {
            return new Display(source);
        }

        @Override
        public Display[] newArray(int size) {
            return new Display[size];
        }
    };
}
