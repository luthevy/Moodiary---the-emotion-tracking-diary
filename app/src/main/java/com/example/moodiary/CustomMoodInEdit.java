package com.example.moodiary;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomMoodInEdit extends ArrayAdapter<String> {
    Context context;
    ArrayList<String> names;
    String                      actColor;
    public CustomMoodInEdit(Context context, ArrayList<String> names) {
        super(context, R.layout.custom_list_edit_mood, names);
        this.context    = context;
        this.names      = (ArrayList) names.clone();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View row = convertView;
        if(row == null)
            row      = inflater.inflate(R.layout.custom_list_edit_mood, null);

        ImageView icons = row.findViewById(R.id.editIconMood);
        ImageView editbtn = row.findViewById(R.id.editMoodBtn);
        TextView moodname = row.findViewById(R.id.nameEditMood);
        setMoodThumb(icons, names.get(position), moodname);
        return row;
    }

    private void setMoodThumb(ImageView img, String mood, TextView curMood) {

        for (int i = 0; i < MoodInfo.moods_type.length; i++) {
            for (int j = 0; j < MoodInfo.moods_type[i].length; j++) {
                if (mood.equals(MoodInfo.moods_type[i][j])) {
                    if(i<5) {
                        img.setImageResource(MoodInfo.moods_thumbnail[i][j]);
                        img.setImageTintList(ColorStateList.valueOf(Color.parseColor(MoodInfo.moods_color[i])));
                        curMood.setText(mood);
                        curMood.setTextColor(ColorStateList.valueOf(Color.parseColor(MoodInfo.moods_color[i])));
                    }
                }
            }
        }
    }
}
