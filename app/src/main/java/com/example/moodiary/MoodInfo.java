package com.example.moodiary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MoodInfo {
    public static Integer[][] moods_thumbnail={
            {R.drawable.mood_amazing, R.drawable.mood_02, R.drawable.mood_14},
            {R.drawable.mood_happy,R.drawable.mood_00},
            {R.drawable.mood_ok},
            {R.drawable.mood_sad},
            {R.drawable.mood_awful,R.drawable.mood_13,R.drawable.mood_03}
    };

    public static String [][] moods_type = {
            {"Amazing", "Medium", "Tinker"},
            {"Happy","xin"},
            {"Ok"},
            {"Sad"},
            {"Awful","Sầu","angry"}
    };

    public static Integer[] new_moods = {R.drawable.mood_01, R.drawable.mood_04, R.drawable.mood_05,
            R.drawable.mood_06, R.drawable.mood_07, R.drawable.mood_08, R.drawable.mood_09, R.drawable.mood_10,
            R.drawable.mood_11, R.drawable.mood_12, R.drawable.mood_13};

    public static String [] moods_color ={"#90DA6E", "#5CEF93", "#45D9FF", "#F5CC67", "#FC6C79"};

    public static int [] moods_base_color = {R.color.mc1, R.color.mc2, R.color.mc3, R.color.mc4, R.color.mc5};

    public static String [] activity_type ={"cleaning", "cook", "date", "drawing",
            "eat", "family", "festival", "friend",
            "game", "gift", "music", "party",
            "reading", "relax", "shopping", "sleep",
            "sport", "study", "swim", "tv",
            "walk", "work"};

    public static Integer[] activity_thumbnail = {
            R.drawable.activity_cleaning, R.drawable.activity_cook, R.drawable.activity_date, R.drawable.activity_drawing,
            R.drawable.activity_eat, R.drawable.activity_family, R.drawable.activity_festival, R.drawable.activity_friend,
            R.drawable.activity_game, R.drawable.activity_gift, R.drawable.activity_music, R.drawable.activity_party,
            R.drawable.activity_reading, R.drawable.activity_relax, R.drawable.activity_shopping, R.drawable.activity_sleep,
            R.drawable.activity_sport, R.drawable.activity_study, R.drawable.activity_swim, R.drawable.activity_tv,
            R.drawable.activity_walk, R.drawable.activity_work};

    public static void addMood(int new_mood_index, int type, String name){
        ArrayList<Integer> arrList = new ArrayList<Integer>(Arrays.asList(moods_thumbnail[type]));
        ArrayList<String> arrList2 = new ArrayList<String>(Arrays.asList(moods_type[type]));

        arrList.add(new_moods[new_mood_index]);
        arrList2.add(name);
        moods_thumbnail[type] = arrList.toArray(moods_thumbnail[type]);
        moods_type[type] = arrList2.toArray(moods_type[type]);
        removeCur(new_mood_index);
    }

    private static void removeCur(int new_mood_index){
        //ArrayList<Integer> arrList = new ArrayList<Integer>(Arrays.asList(new_moods));
        //arrList.remove(new_mood_index);
        Integer[]list = new Integer[new_moods.length-1];
        int j=0;
        for(int i=0; i<new_moods.length;i++) {
            if (i != new_mood_index) {
                list[j] = new_moods[i];
                j++;
            }
        }

        //new_moods = arrList.toArray(new_moods);
        new_moods = list;
    }
}
