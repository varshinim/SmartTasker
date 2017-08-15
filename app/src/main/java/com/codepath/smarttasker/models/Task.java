package com.codepath.smarttasker.models;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Task implements Parcelable {

    private String text;
    private int id;
    private Date dueDate;
    private int priority;

    public Task()
    {
        this.text = null;
    }
    public Task(String text) {
        super();
        this.text = text;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

    public Date getDueDate() {
        return dueDate;
    }
    public void setDueDate (Date dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(id);
        out.writeString(text);
        out.writeSerializable(dueDate);
    }
    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Task> CREATOR = new Parcelable.Creator<Task>() {
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private Task(Parcel in) {
        id = in.readInt();
        text = in.readString();
        dueDate = (java.util.Date) in.readSerializable();
    }
}
