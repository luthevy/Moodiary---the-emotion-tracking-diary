package com.example.moodiary;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Entry {
    private String moodID;
    private String note;
    private String dateOfmood;

    public Entry(){}
    public Entry(String moods, String noteSth){

        moodID = moods;
        note = noteSth;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        dateOfmood=formatter.format(date).toString();
    }

    public String getMoodID() {
        return moodID;
    }

    public String getNote() {
        return note;
    }

    public void setMoodID(String moodID) {
        this.moodID = moodID;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDateOfmood() {
        return dateOfmood;
    }

    public void setDateOfmood(String dateOfmood) {
        this.dateOfmood = dateOfmood;
    }
}
