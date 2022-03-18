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
        View row = inflater.inflate(R.layout.custom_row_entries, null);
        ListView listsameDate = row.findViewById(R.id.list_same_date);
        System.out.println("In custom  "+ items.get(position));

        CustomOneEntry custom = new CustomOneEntry(row.getContext(), R.layout.custom_row_entries, items.get(position), thumbnails);
        listsameDate.setAdapter(custom);

        return(row);
    }
}
