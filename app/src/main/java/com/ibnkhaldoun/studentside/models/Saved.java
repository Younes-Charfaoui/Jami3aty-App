package com.ibnkhaldoun.studentside.models;


import android.os.Parcel;
import android.os.Parcelable;

public class Saved implements Parcelable {
    //todo : add code to use the saved object
    private long id;
    private String professor,text,date;

    public Saved(long id, String professor , String text , String date){
        this.id = id;
        this.professor = professor;
        this.text = text;
        this.date = date;
    }

    public long getId(){
        return this.id;
    }

    public String getText(){
        return this.text;
    }

    public String getProfessor(){
        return this.professor;
    }

    public String getDate(){
        return this.date;
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
    }

    protected Saved(Parcel in) {
        this.id = in.readLong();
        this.professor = in.readString();
        this.text = in.readString();
        this.date = in.readString();
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
