package com.example.moodiary;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.ArrayList;

public class CustomEntriesList extends ArrayAdapter<ArrayList<Entry>> {
    Context context;
    Integer[] thumbnails;
    ArrayList<ArrayList<Entry>> items;

    public CustomEntriesList(Context context, int layoutToBeInflated, ArrayList<ArrayList<Entry>> items, Integer[] thumbnails) {
        super(context, R.layout.custom_row_entries, items);
        this.context = context;
        this.thumbnails = thumbnails;
        this.items = (ArrayList)items.clone();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        LinearLayout row = (LinearLayout) inflater.inflate(R.layout.custom_row_entries, null);
//        ListView listsameDate = row.findViewById(R.id.list_same_date);
        System.out.println("In custom  "+ items.get(position));
        for(Entry e :items.get(position)){
            View child = inflater.inflate(R.layout.custom_show_one_entries, null);
            ImageView moodIcon = child.findViewById(R.id.moodIcon);
            ImageView actIcon = child.findViewById(R.id.actIcon);
            TextView dateText = child.findViewById(R.id.dateText);
            TextView curMood = child.findViewById(R.id.curMood);
            TextView timeText = child.findViewById(R.id.timeText);
            TextView actText = child.findViewById(R.id.actText);
            TextView descText = child.findViewById(R.id.descText);

            dateText.setText(e.getDayOfmood());
            curMood.setText(e.getMoodType());
            timeText.setText(e.getTimeOfmood());
            descText.setText(e.getNote());
            row.addView(child);
        }

//        CustomOneEntry custom = new CustomOneEntry(listsameDate.getContext(), R.layout.custom_show_one_entries, items.get(position), thumbnails);
//        listsameDate.setAdapter(custom);

        return(row);
    }
}
