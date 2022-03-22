package com.example.moodiary;

public class MoodInfor {
    public static Integer[][] moods_thumbnail={
            {R.drawable.mood_amazing, R.drawable.mood_02, R.drawable.mood_14},
            {R.drawable.mood_happy},
            {R.drawable.mood_ok},
            {R.drawable.mood_sad},
            {R.drawable.mood_awful}
    };

    public static String [][] moods_type = {
            {"Amazing", "Medium", "Tinker"},
            {"Happy"},
            {"Ok"},
            {"Sad"},
            {"Awful"}
    };

    public static String [] moods_color ={"#90DA6E", "#5CEF93", "#45D9FF", "#F5CC67", "#FC6C79"};

    public static String [] activity_type ={"drawing", "TV", "eat", "sleep", "walk", "date", "swim", "friend", "work"};

    public static Integer[] activity_thumbnail = {R.drawable.activity_drawing, R.drawable.activity_tv, R.drawable.activity_eat,
            R.drawable.activity_sleep, R.drawable.activity_walk, R.drawable.activity_date, R.drawable.activity_swim,
            R.drawable.activity_friend, R.drawable.activity_work};

    public void addMood(){

    }
}
