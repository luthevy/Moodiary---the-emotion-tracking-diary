package com.example.moodiary;

import android.app.Activity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Entry {
    private String activity;
    private String note;
    private String dateOfmood;
    private String moodType;

    public Entry(){}
    public Entry(String activities, String noteSth, String type){

        activity = activities;
        note = noteSth;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        dateOfmood=formatter.format(date).toString();
        moodType = type;
    }

    public String getActivity() {
        return activity;
    }

    public String getNote() {
        return note;
    }

    public void setActivity(String activity) { this.activity = activity; }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDateOfmood() {
        return dateOfmood;
    }

    public void setDateOfmood(String dateOfmood) {
        this.dateOfmood = dateOfmood;
    }

    public String getMoodType() { return moodType; }

    public void setMoodType(String moodType) { this.moodType = moodType; }
}
