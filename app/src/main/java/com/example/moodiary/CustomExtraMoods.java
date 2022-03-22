package com.example.moodiary;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.*;
import android.widget.LinearLayout;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CustomExtraMoods extends ArrayAdapter<String> {
    Context context;
    Integer[] thumbnails;
    String[] names;
    String color;

    public CustomExtraMoods(Context context, int layoutToBeInflated, Integer[] thumbnails, String[] names, String color) {
        super(context, R.layout.custom_extra_mood, names);
        this.context = context;
        this.thumbnails = thumbnails;
        this.names = names;
        this.color = color;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View row = inflater.inflate(R.layout.custom_extra_mood, null);
        ImageView chooseMoodExtra = row.findViewById(R.id.chooseMoodExtra);
        TextView textMoodExtra = row.findViewById(R.id.textMoodExtra);
        chooseMoodExtra.setImageResource(thumbnails[position]);
        chooseMoodExtra.setImageTintList(ColorStateList.valueOf(Color.parseColor(color)));
        textMoodExtra.setText(names[position]);
        textMoodExtra.setTextColor(ColorStateList.valueOf(Color.parseColor(color)));
        return row;
    }
}
