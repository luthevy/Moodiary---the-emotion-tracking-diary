package com.example.moodiary;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ShowEntriesActivity extends ListActivity {

    String[] items={"hello","Ok"};
    Integer[] thumbnails={R.drawable.ic_baseline_add_circle,R.drawable.box};
    BottomNavigationView bottom_navigation_menu;
    FloatingActionButton fabMain, fabToday;
    Boolean isFabOpen=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showentries);
        bottom_navigation_menu=(BottomNavigationView)findViewById(R.id.bottom_navigation_menu);
        bottom_navigation_menu.setBackground(null);
        bottom_navigation_menu.setItemIconTintList(null);

          fabMain=(FloatingActionButton)findViewById(R.id.fabMain);
          fabToday=(FloatingActionButton)findViewById(R.id.fabToday);
          fabToday.setVisibility(View.INVISIBLE);

        fabMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isFabOpen)
                    showFabMenu();
                else
                    closeFabMenu();
            }
        });

        fabToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), InitiateMood.class));

            }
        });
        CustomEntriesList aa=new CustomEntriesList(this, R.layout.custom_row_entries, items, thumbnails);
        setListAdapter(aa);
    }

    private void showFabMenu(){
        isFabOpen=true;
        fabToday.setVisibility(View.VISIBLE);

    }

    private void closeFabMenu(){
        isFabOpen=false;
        fabToday.setVisibility(View.INVISIBLE);
    }
}
