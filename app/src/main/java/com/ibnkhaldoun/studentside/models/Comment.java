package com.ibnkhaldoun.studentside.models;


import android.os.Parcel;
import android.os.Parcelable;

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

    public void setIdComment(long idComment) {
        this.idComment = idComment;
    }

    public long getIdPost() {
        return idPost;
    }

    public void setIdPost(long idPost) {
        this.idPost = idPost;
    }

    public long getIdCommenter() {
        return idCommenter;
    }

    public void setIdCommenter(long idCommenter) {
        this.idCommenter = idCommenter;
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
