package com.example.moodiary.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.moodiary.EntriesAdapter;
import com.example.moodiary.Entry;
import com.example.moodiary.Fragment.CalendarFragment;
import com.example.moodiary.Fragment.SettingsFragment;
import com.example.moodiary.Fragment.StatsFragment;
import com.example.moodiary.MoodInfo;
import com.example.moodiary.R;
import com.example.moodiary.UploaddtbService;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;

public class ShowEntriesActivity extends AppCompatActivity {


    public        Integer[]              mood_thumbnails =
            {R.drawable.mood_amazing, R.drawable.mood_happy, R.drawable.mood_ok, R.drawable.mood_sad, R.drawable.mood_awful};
    public static HashMap<Entry, String> keyOfEntry;

    private ListView listbyDate, listsameDate;
    private       BottomNavigationView bottom_navigation_menu;
    private       FloatingActionButton fabMain;
    private final Boolean              isFabOpen = false;
    private FirebaseDatabase database;
    DatabaseReference ref;
    private ArrayList<Entry> listEntry;

    private ArrayList<ArrayList<Entry>> listOfEntryInDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_entries);
        bottom_navigation_menu = findViewById(R.id.bottom_navigation_menu);
        bottom_navigation_menu.setBackground(null);
        bottom_navigation_menu.setItemIconTintList(null);
        bottom_navigation_menu.setOnNavigationItemSelectedListener(navListener);
//---------------------Decor Sub Menu-------------------------------
        fabMain = findViewById(R.id.fabMain);
        fabMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddEntry_ChooseMoodsActivity.class));
            }
        });

        //reset data
        //MoodInfo.addToDatabase();

        if(MoodInfo.needUpload == 1) {
            //startService(new Intent(this, UploaddtbService.class));
            MoodInfo.addToDatabase();
            MoodInfo.needUpload = 0;
            MoodInfo.hasRetrieveData = 0;
        }

        int needdo = 0;
        if(MoodInfo.retrieveEntry == 1){
            //MoodInfo.updateEntryAfterEditMood(MoodInfo.new_old[0], MoodInfo.new_old[1]);
            MoodInfo.addToDatabase();
            needdo = 1;
            MoodInfo.retrieveEntry = 0;
            MoodInfo.hasRetrieveData = 0;
        }

        if(needdo == 1)
        {
            MoodInfo.updateEntryAfterEditMood(MoodInfo.new_old[0], MoodInfo.new_old[1]);
        }

        if(MoodInfo.hasRetrieveData == 0) {
            MoodInfo.retrieveData();
            MoodInfo.hasRetrieveData = 1;
        }



//--------------------------------Get infor from database-------
        ref        = FirebaseDatabase.getInstance().getReference("Entry");
        listEntry  = new ArrayList<>();
        keyOfEntry = new HashMap<Entry, String>();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Entry entry = dataSnapshot.getValue(Entry.class);
                    keyOfEntry.put(entry, dataSnapshot.getKey());
                    listEntry.add(entry);
                }

                // --------------Sort date by new to old-------------
                Collections.sort(listEntry, new Comparator<Entry>() {
                    @Override
                    public int compare(Entry e1, Entry e2) {
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                        try {
                            Date date1 = formatter.parse(e1.getDateOfMood());
                            Date date2 = formatter.parse(e2.getDateOfMood());

                            return date1.compareTo(date2);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        return 0;
                    }
                });
                Collections.reverse(listEntry);
                divideEntry();


                listbyDate = findViewById(R.id.list_different_date);
                //listsameDate = findViewById(R.id.list_different_date);

                EntriesAdapter aa = new EntriesAdapter(ShowEntriesActivity.this, R.layout.custom_row_entries, listOfEntryInDate, mood_thumbnails);
                //CustomOneEntry aa=new CustomOneEntry(ShowEntriesActivity.this, R.layout.custom_show_one_entries, listEntry, mood_thumbnails);

                listbyDate.setAdapter(aa);
                //listsameDate.setAdapter(aa);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("ERR read");
            }
        });


//---------------------------List view-------------------------------
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()) {
                        case R.id.navSettings:
                            selectedFragment = new SettingsFragment();
                            break;
                        case R.id.navEntries:
                            selectedFragment = new Fragment();
                            break;
                        case R.id.navCalendar:
                            selectedFragment = new CalendarFragment();
                            break;
                        case R.id.navStats:
                            selectedFragment = new StatsFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.navDisplay, selectedFragment).commit();
                    return true;
                }


//-------------------------------Show sub menu-----------------------------
            };
//    private void showFabMenu(){
//        isFabOpen=true;
//        fabToday.setVisibility(View.VISIBLE);
//
//    }
//
//    private void closeFabMenu(){
//        isFabOpen=false;
//        fabToday.setVisibility(View.INVISIBLE);
//    }

    //-------------Divide entries that have the same day---------
    private void divideEntry() {
        listOfEntryInDate = new ArrayList<ArrayList<Entry>>();
        ArrayList<Entry> all_entries = (ArrayList) listEntry.clone();
        ArrayList<Entry> temp_list   = new ArrayList<>();

        int len = listEntry.size();

        while (len > 0) {
            int   i           = 0;
            Entry first_entry = all_entries.get(0);

            temp_list.add(first_entry);

            all_entries.remove(0);
            len--;

            if (all_entries.size() > 0) {
                while (i < all_entries.size()) {
                    if (first_entry.getDayOfmood().equals(all_entries.get(i).getDayOfmood())) {
                        temp_list.add(all_entries.get(i));
                        all_entries.remove(all_entries.remove(i));
                        i = 0;
                        len--;
                    } else
                        i++;
                }
            }
            listOfEntryInDate.add((ArrayList) temp_list.clone());
            //System.out.println("new size 1"+ listOfEntryInDate.get(0).get(0).getDayOfmood());

            temp_list.clear();
        }

    }
}
