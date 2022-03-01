package com.example.moodiary;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.*;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ShowEntriesActivity extends ListActivity {

    String[] items={"hello","Ok"};
    Integer[] thumbnails={R.drawable.ic_baseline_add_circle,R.drawable.box};
    BottomNavigationView bottom_navigation_menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showentries);
        bottom_navigation_menu=(BottomNavigationView)findViewById(R.id.bottom_navigation_menu);
        bottom_navigation_menu.setBackground(null);

        CustomEntriesList aa=new CustomEntriesList(this, R.layout.custom_row_entries, items, thumbnails);
        setListAdapter(aa);
    }
}
