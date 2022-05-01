package com.example.moodiary;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomActivitiesOT extends BaseAdapter {
    Context context;
    ArrayList<Integer> items;
    ArrayList<Integer>count;

    public CustomActivitiesOT(Context context, ArrayList<Integer> items, ArrayList<Integer> count) {
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
        View row = inflater.inflate(R.layout.custom_gridview_ot, null);


        ImageView actImg = row.findViewById(R.id.activityCurIcon);
        TextView actCount = row.findViewById(R.id.activitiesCurCount);
        TextView actName = row.findViewById(R.id.activityCurName);

        if(items.size() > 0) {
            actImg.setImageResource(MoodInfo.activity_thumbnail[items.get(position)]);
            actCount.setText(String.valueOf(count.get(position)));
            actName.setText(MoodInfo.activity_type[items.get(position)]);
        }
        return(row);
    }

}
