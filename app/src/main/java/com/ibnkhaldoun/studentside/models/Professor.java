package com.ibnkhaldoun.studentside.models;


import android.os.Parcel;
import android.os.Parcelable;

public class Professor implements Parcelable {

    public static final Parcelable.Creator<Professor> CREATOR = new Parcelable.Creator<Professor>() {
        @Override
        public Professor createFromParcel(Parcel source) {
            return new Professor(source);
        }

        @Override
        public Professor[] newArray(int size) {
            return new Professor[size];
        }
    };
    private String mFirstName, mLastName, mShortName;

    public Professor(String mFirstName, String mLastName) {
        this.mFirstName = mFirstName;
        this.mLastName = mLastName;
        mShortName = String.valueOf(
                Character.toString(mFirstName.charAt(0)) +
                        Character.toString(mLastName.charAt(0)))
                .toUpperCase();

    }

    protected Professor(Parcel in) {
        this.mFirstName = in.readString();
        this.mLastName = in.readString();
        this.mShortName = in.readString();
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String mFirstName) {
        this.mFirstName = mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String mLastName) {
        this.mLastName = mLastName;
    }

    public String getShortName() {
        return mShortName;
    }

    public void setShortName(String mShortName) {
        this.mShortName = mShortName;
    }

    public String getFullName() {
        return this.mFirstName + " " + this.mLastName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mFirstName);
        dest.writeString(this.mLastName);
        dest.writeString(this.mShortName);
    }
}
