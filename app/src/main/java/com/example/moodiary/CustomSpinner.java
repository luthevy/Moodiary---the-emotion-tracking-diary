package com.example.moodiary;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomSpinner extends BaseAdapter{
    Context context;
    ArrayList<String> items;
    ArrayList<Integer>count;

    public CustomSpinner(Context context, ArrayList<String> items, ArrayList<Integer> count) {
        //super(context, R.layout.custom_show_one_entries, items);
        this.context = context;
        this.items = (ArrayList) items.clone();
        this.count = (ArrayList) count.clone();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View row = inflater.inflate(R.layout.custom_spinner_row, null);
        ImageView moodImg = row.findViewById(R.id.moodOTImg);
        TextView moodName = row.findViewById(R.id.moodOTName);
        TextView moodMultiply = row.findViewById(R.id.moodMultiply);

        setMoodThumb(moodImg, items.get(position), moodName);
        moodMultiply.setText("x"+count.get(position));

        return(row);
    }

    private void setMoodThumb(ImageView img, String mood, TextView curMood) {

        for (int i = 0; i < MoodInfo.moods_type.length; i++) {
            for (int j = 0; j < MoodInfo.moods_type[i].length; j++) {
                if (mood.equals(MoodInfo.moods_type[i][j])) {

                    img.setImageResource(MoodInfo.moods_thumbnail[i][j]);
                    img.setImageTintList(ColorStateList.valueOf(Color.parseColor(MoodInfo.moods_color[i])));
                    curMood.setText(mood);
                    curMood.setTextColor(ColorStateList.valueOf(Color.parseColor(MoodInfo.moods_color[i])));

                }
            }
        }
    }
}
