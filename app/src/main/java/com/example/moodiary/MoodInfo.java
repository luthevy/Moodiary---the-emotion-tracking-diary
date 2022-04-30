package com.example.moodiary;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MoodInfo {

    public static Integer[][] moods_thumbnail = {
            {R.drawable.mood_amazing, R.drawable.mood_02, R.drawable.mood_14},
            {R.drawable.mood_happy, R.drawable.mood_00},
            {R.drawable.mood_ok},
            {R.drawable.mood_sad},
            {R.drawable.mood_awful, R.drawable.mood_13, R.drawable.mood_03}
    };


    public static String[][] moods_type = {
            {"Amazing", "Medium", "Tinker"},
            {"Happy", "xin"},
            {"Ok"},
            {"Sad"},
            {"Awful", "Sầu", "angry"}
    };

    public static Integer[] new_moods = {R.drawable.mood_01, R.drawable.mood_04, R.drawable.mood_05,
            R.drawable.mood_06, R.drawable.mood_07, R.drawable.mood_08, R.drawable.mood_09, R.drawable.mood_10,
            R.drawable.mood_11, R.drawable.mood_12, R.drawable.mood_13};

    public static String[] moods_color = {"#90DA6E", "#5CEF93", "#45D9FF", "#F5CC67", "#FC6C79"};

    public static int[] moods_base_color = {R.color.mc1, R.color.mc2, R.color.mc3, R.color.mc4, R.color.mc5};

    public static String[] activity_type = {"cleaning", "cook", "date", "drawing",
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

    public static void addToDatabase() {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference arrayRef = rootRef.child("moods_thumbnails");
        for (int i = 0; i < moods_thumbnail.length; i++) {
            DatabaseReference arraySend = arrayRef.child(String.valueOf(i));
            arraySend.setValue(Arrays.asList(moods_thumbnail[i]));
        }

        arrayRef = rootRef.child("moods_type");
        for (int i = 0; i < moods_type.length; i++) {
            DatabaseReference arraySend = arrayRef.child(String.valueOf(i));
            arraySend.setValue(Arrays.asList(moods_type[i]));
        }

        arrayRef = rootRef.child("new_moods");
        arrayRef.setValue(Arrays.asList(new_moods));


        arrayRef = rootRef.child("activity_type");
        arrayRef.setValue(Arrays.asList(activity_type));


        arrayRef = rootRef.child("activity_thumbnail");
        arrayRef.setValue(Arrays.asList(activity_thumbnail));
    }


    public static void retrieveData() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("activity_thumbnail");
        ArrayList<Integer> activity_thumb_list = new ArrayList<>();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Integer temp = dataSnapshot.getValue(Integer.class);
                    activity_thumb_list.add(temp);
                }

                activity_thumbnail = new Integer[activity_thumb_list.size()];
                activity_thumbnail = activity_thumb_list.toArray(activity_thumbnail);

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        ref = FirebaseDatabase.getInstance().getReference("activity_type");
        ArrayList<String> activity_type_list = new ArrayList<>();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String temp = dataSnapshot.getValue(String.class);
                    activity_type_list.add(temp);
                }

                activity_type = new String[activity_type_list.size()];
                activity_type = activity_type_list.toArray(activity_type);

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        ref = FirebaseDatabase.getInstance().getReference("new_moods");
        ArrayList<Integer> new_moods_list = new ArrayList<>();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Integer temp = dataSnapshot.getValue(Integer.class);
                    new_moods_list.add(temp);
                }

                new_moods = new Integer[new_moods_list.size()];
                new_moods = new_moods_list.toArray(new_moods);

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        ArrayList<Integer[]> moods_thumbnails_list = new ArrayList<>();
        ref = FirebaseDatabase.getInstance().getReference("moods_thumbnails");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    GenericTypeIndicator<List<Integer>> t = new GenericTypeIndicator<List<Integer>>() {};
                    List temp = (List)dataSnapshot.getValue(t);
                    Integer[] foo = new Integer[temp.size()];
                    moods_thumbnails_list.add((Integer[]) temp.toArray(foo));
                }

                moods_thumbnail = moods_thumbnails_list.toArray(moods_thumbnail);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        ArrayList<String[]> moods_type_list = new ArrayList<>();
        ref = FirebaseDatabase.getInstance().getReference("moods_type");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    GenericTypeIndicator<List<String>> t = new GenericTypeIndicator<List<String>>() {};
                    List temp = (List)dataSnapshot.getValue(t);
                    String[] foo = new String[temp.size()];
                    moods_type_list.add((String[]) temp.toArray(foo));
                }
                for (int i = 0; i < moods_type_list.size(); i++) {
                    System.out.println(moods_type_list.get(i) + "--------");
                }
                moods_type = new String[4][];
                moods_type = moods_type_list.toArray(moods_type);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }

    public static void addMood(int new_mood_index, int type, String name) {
        ArrayList<Integer> arrList = new ArrayList<Integer>(Arrays.asList(moods_thumbnail[type]));
        ArrayList<String> arrList2 = new ArrayList<String>(Arrays.asList(moods_type[type]));

        arrList.add(new_moods[new_mood_index]);
        arrList2.add(name);

        moods_type[type] = new String[arrList2.size()];
        moods_type[type] = arrList2.toArray(moods_type[type]);

        moods_thumbnail[type] = new Integer[arrList.size()];
        moods_thumbnail[type] = arrList.toArray(moods_thumbnail[type]);


        removeCur(new_mood_index);
        addToDatabase();


    }


    private static void removeCur(int new_mood_index) {
        Integer[] list = new Integer[new_moods.length - 1];
        int j = 0;
        for (int i = 0; i < new_moods.length; i++) {
            if (i != new_mood_index) {
                list[j] = new_moods[i];
                j++;
            }
        }
        new_moods = list;
    }
}
