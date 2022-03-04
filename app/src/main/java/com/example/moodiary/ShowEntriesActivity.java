package com.example.moodiary;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ShowEntriesActivity extends ListActivity {

    String[] items={"hello","Ok"};
    Integer[] thumbnails={R.drawable.ic_baseline_add_circle,R.drawable.box};
    BottomNavigationView bottom_navigation_menu;
//    FloatingActionButton fabMain, fabToday;
//    Boolean isFabOpen=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showentries);
        bottom_navigation_menu=(BottomNavigationView)findViewById(R.id.bottom_navigation_menu);
        bottom_navigation_menu.setBackground(null);
        bottom_navigation_menu.setItemIconTintList(null);

//        fabMain=(FloatingActionButton)findViewById(R.id.fabMain);
//        fabToday=(FloatingActionButton)findViewById(R.id.fabToday);
//
//        fabMain.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(!isFabOpen)
//                    showFabMenu();
//                else
//                    closeFabMenu();
//            }
//        });
//
        CustomEntriesList aa=new CustomEntriesList(this, R.layout.custom_row_entries, items, thumbnails);
        setListAdapter(aa);
    }
//
//    private void showFabMenu(){
//        isFabOpen=true;
//        fabToday.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
//
//    }
//
//    private void closeFabMenu(){
//        isFabOpen=false;
//        fabToday.animate().translationY(0);
//    }
}
