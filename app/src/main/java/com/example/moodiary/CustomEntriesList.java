package com.example.moodiary;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

public class CustomEntriesList extends ArrayAdapter<String> {
    Context context;
    Integer[] thumbnails;
    String[] items;

    public CustomEntriesList(Context context, int layoutToBeInflated, String[] items, Integer[] thumbnails) {
        super(context, R.layout.custom_row_entries, items);
        this.context = context;
        this.thumbnails = thumbnails;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View row = inflater.inflate(R.layout.custom_row_entries, null);
        return(row);
    }
}
