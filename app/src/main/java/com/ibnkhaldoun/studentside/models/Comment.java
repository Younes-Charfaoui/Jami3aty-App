/*------------------------------------------------------------------------------
 - Copyright (c) 2018. This code was created by Younes Charfaoui in the process of Graduation Project for the year of  2018 , which is about creating a platform  for students and professors to help them in the communication and the get known of the university information and so on.
 -----------------------------------------------------------------------------*/

package com.ibnkhaldoun.studentside.models;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * @definition this class will model the comment of like called in the application
 * the note.
 */

public class Comment implements Parcelable {
    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel source) {
            return new Comment(source);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };
    private String name;
    private String comment, date;
    private long idComment;
    private long idPost;
    private long idCommenter;

    public Comment(String name, String comment, String date, long idComment, long idPost, long idCommenter) {
        this.name = name;
        this.comment = comment;
        this.date = date;
        this.idComment = idComment;
        this.idPost = idPost;
        this.idCommenter = idCommenter;
    }

    public Comment(String mCommenter, String mComment, String mDate) {

        this.name = mCommenter;
        this.comment = mComment;
        this.date = mDate;
    }

    protected Comment(Parcel in) {
        this.name = in.readString();
        this.comment = in.readString();
        this.date = in.readString();
        this.idComment = in.readLong();
        this.idPost = in.readLong();
        this.idCommenter = in.readLong();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getIdComment() {
        return idComment;
    }


    public long getIdCommenter() {
        return idCommenter;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String mDate) {
        this.date = mDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String mComment) {
        this.comment = mComment;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.comment);
        dest.writeString(this.date);
        dest.writeLong(this.idComment);
        dest.writeLong(this.idPost);
        dest.writeLong(this.idCommenter);
    }
}
