package com.example.moodiary;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.ImageViewCompat;

import java.util.ArrayList;

public class ActivityAdapter extends ArrayAdapter<EntryActivity> {
    public ActivityAdapter(@NonNull Context context, ArrayList<EntryActivity> activityArrayList) {
        super(context, 0, activityArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.custom_activity_layout, parent, false);
        }

        EntryActivity actModel = getItem(position);
        ImageView     actIcon  = listitemView.findViewById(R.id.actIcon);
        TextView      actTxt   = listitemView.findViewById(R.id.actText);

        actIcon.setImageResource(actModel.getAct_icon());
        actTxt.setText(actModel.getAct_name());
        return listitemView;
    }
}
