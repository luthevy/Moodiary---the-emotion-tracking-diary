package com.example.moodiary.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.moodiary.Entry;
import com.example.moodiary.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;


public class CalendarFragment extends Fragment {
    private MaterialCalendarView calendarView;
    private ArrayList<String> listEntry;
    DatabaseReference ref;
    TextView amazing,happy,ok,sad,awful;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.activity_calendar, container, false);
        calendarView = v.findViewById(R.id.calendarView);
        amazing = v.findViewById(R.id.amazingStat);
        happy = v.findViewById(R.id.happyStat);
        ok = v.findViewById(R.id.okStat);
        sad = v.findViewById(R.id.sadStat);
        awful = v.findViewById(R.id.awfulStat);
        calendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            int selectedDate = 0;
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                // Do something here
                selectedDate  = date.getMonth() + 1;
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Entry");
                ref = FirebaseDatabase.getInstance().getReference("Entry");
//                Get entry from Firebase
                listEntry=new ArrayList<String>();
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        listEntry.clear();
                        for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                            Entry            entry     = dataSnapshot.getValue(Entry.class);
                            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                            Date date1 = null;
                            try {
                                date1 = formatter.parse(entry.getDateOfMood());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            int currentMonth = date1.getMonth()+1;
                            if (currentMonth == selectedDate) {
                                listEntry.add(entry.getMoodType());
                            }

                        }
                        int setAmazingStat = Collections.frequency(listEntry,"Amazing") +
                                          Collections.frequency(listEntry,"Medium") +
                                          Collections.frequency(listEntry,"Tinker");
                        int setHappyStat = Collections.frequency(listEntry,"Happy");
                        int setAwfulStat = Collections.frequency(listEntry,"angry") +
                                Collections.frequency(listEntry,"Sầu")+
                                Collections.frequency(listEntry,"Awful");;
                        amazing.setText(String.valueOf(setAmazingStat));
                        happy.setText(String.valueOf(setHappyStat));
                        ok.setText(String.valueOf(Collections.frequency(listEntry,"Ok")));
                        sad.setText(String.valueOf(Collections.frequency(listEntry,"Sad")));
                        awful.setText(String.valueOf(setAwfulStat));
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        System.out.println("ERR read");
                    }
                });

            }
        });
        return v;
    }
}


