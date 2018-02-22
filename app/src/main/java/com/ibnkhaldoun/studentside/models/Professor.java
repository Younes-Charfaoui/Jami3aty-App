package com.ibnkhaldoun.studentside.models;


import android.os.Parcel;

public class Professor extends Person {


    private String mGrade;


    public Professor(String grade, String mFirstName, String mLastName) {
        super(mFirstName, mLastName);
        this.mGrade = grade;
    }

    protected Professor(Parcel in) {
        super(in);
        this.mGrade = in.readString();
    }

    public static final Creator<Professor> CREATOR = new Creator<Professor>() {
        @Override
        public Professor createFromParcel(Parcel in) {
            return new Professor(in);
        }

        @Override
        public Professor[] newArray(int size) {
            return new Professor[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.mGrade);
    }

    public int describeContents() {
        return 0;
    }
}
