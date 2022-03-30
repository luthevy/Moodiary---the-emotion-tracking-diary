package com.example.moodiary;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class ShowEntriesActivity extends Activity {


    public Integer[] mood_thumbnails={R.drawable.mood_amazing,R.drawable.mood_happy, R.drawable.mood_ok, R.drawable.mood_sad,
                            R.drawable.mood_awful};
    ListView listbyDate, listsameDate;

    private BottomNavigationView bottom_navigation_menu;
    private       FloatingActionButton fabMain;
    private final Boolean              isFabOpen =false;

    private FirebaseDatabase database;
    DatabaseReference ref;
    private ArrayList<Entry> listEntry;

    private ArrayList<ArrayList<Entry>> listOfEntryInDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showentries);
        bottom_navigation_menu= findViewById(R.id.bottom_navigation_menu);
        bottom_navigation_menu.setBackground(null);
        bottom_navigation_menu.setItemIconTintList(null);
        bottom_navigation_menu.setOnNavigationItemSelectedListener(navListener);



//---------------------Decor Sub Menu-------------------------------
          fabMain= findViewById(R.id.fabMain);
//          fabToday=(FloatingActionButton)findViewById(R.id.fabToday);
//          fabToday.setVisibility(View.INVISIBLE);

//        fabMain.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(!isFabOpen)
//                    showFabMenu();
//                else
//                    closeFabMenu();
//            }
//        });

        fabMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), InitiateMood.class));

            }
        });


//--------------------------------Get infor from database-------
        ref = FirebaseDatabase.getInstance().getReference("Entry");
        listEntry=new ArrayList<>();

        System.out.println("ERR read");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Entry entry = dataSnapshot.getValue(Entry.class);
                    System.out.println("Read " + entry.getTimeOfmood());
                    listEntry.add(entry);
                }
                Collections.reverse(listEntry);
                divideEntry();


                listbyDate = findViewById(R.id.list_different_date);
                //listsameDate = findViewById(R.id.list_different_date);

                CustomEntriesList aa=new CustomEntriesList(ShowEntriesActivity.this, R.layout.custom_row_entries, listOfEntryInDate, mood_thumbnails);
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
                    switch (item.getItemId()){
                        case R.id.navSettings:
                            selectedFragment = new SettingsFragment();
                            break;
                        case R.id.navEntries:
                            selectedFragment = new EntriesFragment();
                            break;
                    }

                    getFragmentManager().beginTransaction().replace(R.id.navDisplay,selectedFragment).commit();
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
    private void divideEntry(){
        listOfEntryInDate = new ArrayList<ArrayList<Entry>>();
        ArrayList<Entry>all_entries = (ArrayList)listEntry.clone();
        ArrayList<Entry>temp_list = new ArrayList<>();

        int len = listEntry.size();

        while(len > 0) {
            int i = 0;
            Entry first_entry = all_entries.get(0);

            temp_list.add(first_entry);

            all_entries.remove(0);
            len--;

            if(all_entries.size()>0) {
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
            listOfEntryInDate.add((ArrayList)temp_list.clone());
            //System.out.println("new size 1"+ listOfEntryInDate.get(0).get(0).getDayOfmood());

            temp_list.clear();
        }
        
    }
}
