package com.ibnkhaldoun.studentside.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Display implements Parcelable {

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
    //todo : add code to use the display object
    private long id;
    private Professor professor;
    private String date, text;
    private List<Comment> commentList;


    public Display(long id, Professor professor, String date, String text, List<Comment> commentList) {
        this.id = id;
        this.professor = professor;
        this.date = date;
        this.text = text;
        this.commentList = commentList;
    }

    protected Display(Parcel in) {
        this.id = in.readLong();
        this.professor = in.readParcelable(Professor.class.getClassLoader());
        this.date = in.readString();
        this.text = in.readString();
        this.commentList = in.createTypedArrayList(Comment.CREATOR);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeParcelable(this.professor, flags);
        dest.writeString(this.date);
        dest.writeString(this.text);
        dest.writeTypedList(this.commentList);
    }
}
