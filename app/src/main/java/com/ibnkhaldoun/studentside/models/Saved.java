/*------------------------------------------------------------------------------
 - Copyright (c) 2018. This code was created by Younes Charfaoui in the process of Graduation Project for the year of  2018 , which is about creating a platform  for students and professors to help them in the communication and the get known of the university information and so on.
 -----------------------------------------------------------------------------*/

package com.ibnkhaldoun.studentside.models;


import android.os.Parcel;
import android.os.Parcelable;

public class Saved implements Parcelable {

    private long id;
    private String professor, text, date, filePath, subjectTitle;
    private int type ;

    public Saved(long id, String professor, String text, String date) {
        this.id = id;
        this.professor = professor;
        this.text = text;
        this.date = date;
    }



    public long getId() {
        return this.id;
    }

    public String getText() {
        return this.text;
    }

    public String getProfessor() {
        return this.professor;
    }

    public String getDate() {
        return this.date;
    }


    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getSubjectTitle() {
        return subjectTitle;
    }

    public void setSubjectTitle(String subjectTitle) {
        this.subjectTitle = subjectTitle;
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
        dest.writeString(this.professor);
        dest.writeString(this.text);
        dest.writeString(this.date);
        dest.writeString(this.filePath);
        dest.writeString(this.subjectTitle);
        dest.writeInt(this.type);
    }

    protected Saved(Parcel in) {
        this.id = in.readLong();
        this.professor = in.readString();
        this.text = in.readString();
        this.date = in.readString();
        this.filePath = in.readString();
        this.subjectTitle = in.readString();
        this.type = in.readInt();
    }

    public static final Parcelable.Creator<Saved> CREATOR = new Parcelable.Creator<Saved>() {
        @Override
        public Saved createFromParcel(Parcel source) {
            return new Saved(source);
        }

        @Override
        public Saved[] newArray(int size) {
            return new Saved[size];
        }
    };
}
