package com.ibnkhaldoun.studentside.models;


import android.os.Parcel;
import android.os.Parcelable;

public class Comment implements Parcelable {
    private String mCommenter;
    private String mComment, mDate;

    public String getDate() {
        return mDate;
    }

    public void setDate(String mDate) {
        this.mDate = mDate;
    }

    public String getCommenter() {

        return mCommenter;
    }

    public void setCommenter(String mCommenter) {
        this.mCommenter = mCommenter;
    }

    public String getComment() {
        return mComment;
    }

    public void setComment(String mComment) {
        this.mComment = mComment;
    }

    public Comment(String mCommenter, String mComment, String mDate) {

        this.mCommenter = mCommenter;
        this.mComment = mComment;
        this.mDate = mDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mCommenter);
        dest.writeString(this.mComment);
        dest.writeString(this.mDate);
    }

    protected Comment(Parcel in) {
        this.mCommenter = in.readString();
        this.mComment = in.readString();
        this.mDate = in.readString();
    }

    public static final Parcelable.Creator<Comment> CREATOR = new Parcelable.Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel source) {
            return new Comment(source);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };
}
