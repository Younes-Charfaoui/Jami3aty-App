package com.ibnkhaldoun.studentside.models;


import android.os.Parcel;
import android.os.Parcelable;

public class Comment implements Parcelable {
    private Person mCommenter;
    private String mComment, mDate;

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    public Person getmCommenter() {

        return mCommenter;
    }

    public void setmCommenter(Person mCommenter) {
        this.mCommenter = mCommenter;
    }

    public String getmComment() {
        return mComment;
    }

    public void setmComment(String mComment) {
        this.mComment = mComment;
    }

    public Comment(Person mCommenter, String mComment, String mDate) {

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
        dest.writeParcelable(this.mCommenter, flags);
        dest.writeString(this.mComment);
        dest.writeString(this.mDate);
    }

    protected Comment(Parcel in) {
        this.mCommenter = in.readParcelable(Person.class.getClassLoader());
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
