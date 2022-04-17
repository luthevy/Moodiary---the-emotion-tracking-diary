package com.example.moodiary.Fragment;

import androidx.fragment.app.Fragment;
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
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;


public class CalendarFragment extends Fragment {
    private MaterialCalendarView calendarView;
    private ArrayList<String> listEntry;
    DatabaseReference ref;
    TextView amazing,happy,ok,sad,awful;
    Calendar c;

    int selectedMonth = 0;

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
        c = Calendar.getInstance();
        selectedMonth = c.get(Calendar.MONTH) + 1;

        DatabaseReference ref =  FirebaseDatabase.getInstance().getReference("Entry");
        listEntry=new ArrayList<String>();

        countMoodinMonth(ref, selectedMonth, listEntry);


        calendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                listEntry.clear();
                selectedMonth  = date.getMonth() + 1;
                countMoodinMonth(ref, selectedMonth, listEntry);
            }
        });
        return v;
    }
    private void countMoodinMonth(DatabaseReference ref, int selectedMonth, ArrayList<String> listEntry){
        //                Get entry from Firebase
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
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
                    if (currentMonth == selectedMonth) {
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
}


