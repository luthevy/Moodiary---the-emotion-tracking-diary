package com.example.moodiary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomOneEntry extends ArrayAdapter<Entry> {
    Context context;
    Integer[] thumbnails;
    ArrayList<Entry> items;

    public CustomOneEntry(Context context, int layoutToBeInflated, ArrayList<Entry> items, Integer[] thumbnails) {
        super(context, R.layout.custom_show_one_entries, items);
        this.context = context;
        this.thumbnails = thumbnails;
        this.items = (ArrayList) items.clone();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View row = inflater.inflate(R.layout.custom_show_one_entries, null);
        System.out.println("cusstom 2  "+ items.get(position));

        ImageView moodIcon = row.findViewById(R.id.moodIcon);
        ImageView actIcon = row.findViewById(R.id.actIcon);
        TextView curMood = row.findViewById(R.id.curMood);
        TextView timeText = row.findViewById(R.id.timeText);
        TextView actText = row.findViewById(R.id.actText);
        TextView descText = row.findViewById(R.id.descText);


        curMood.setText(items.get(position).getMoodType());
        timeText.setText(items.get(position).getTimeOfmood());
        descText.setText(items.get(position).getNote());
        return(row);
    }


}
