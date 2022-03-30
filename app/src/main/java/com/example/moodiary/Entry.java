package com.example.moodiary;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Entry {
    private String activity;
    private String note;
    private String dayOfmood;
    private String timeOfmood;
    private String moodType;
    private String dateOfMood;
    private String imgLink;

    public Entry(){}
    public Entry(String activities, String noteSth, String day, String time, String type){

        activity = activities;
        note = noteSth;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        dayOfmood = day;
        timeOfmood = time;
        moodType = type;
        dateOfMood = dayOfmood + " " +timeOfmood;
        imgLink="";
    }

    public Entry(String activities, String noteSth, String day, String time, String type, String linkImg){

        activity = activities;
        note = noteSth;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        dayOfmood = day;
        timeOfmood = time;
        moodType = type;
        dateOfMood = dayOfmood + " " +timeOfmood;
        imgLink=linkImg;
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

    public String getDayOfmood() { return dayOfmood; }

    public String getTimeOfmood() { return timeOfmood; }

    public void setDayOfmood(String dayOfmood) { this.dayOfmood = dayOfmood; }

    public void setTimeOfmood(String timeOfmood) { this.timeOfmood = timeOfmood; }

    public String getMoodType() { return moodType; }

    public void setMoodType(String moodType) { this.moodType = moodType; }

    public String getDateOfMood() { return dateOfMood; }

    public void setDateOfMood(String dateOfMood) { this.dateOfMood = dateOfMood; }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    public String getImgLink() {
        return imgLink;
    }
}
