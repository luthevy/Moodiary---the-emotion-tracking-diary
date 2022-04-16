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

public class EntryActivitiesAdapter extends ArrayAdapter<EntryActivity> {


    private final int[] chooseStatus;

    public EntryActivitiesAdapter(@NonNull Context context, ArrayList<EntryActivity> activityArrayList, int[] chooseStatus) {
        super(context, 0, activityArrayList);
        this.chooseStatus = chooseStatus;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.custom_activity_layout, parent, false);
        }

        EntryActivity     actModel      = getItem(position);
        LinearLayout actLayout     = listitemView.findViewById(R.id.activity_layout);
        ImageView    actIcon       = listitemView.findViewById(R.id.activity_icon);
        ImageView    circularShape = listitemView.findViewById(R.id.activity_circular);
        TextView     actTxt        = listitemView.findViewById(R.id.activity_label);
        actLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chooseStatus[position] == 0) {
                    chooseStatus[position] = 1;
                    circularShape.setImageResource(R.drawable.circular_shape);
                    ImageViewCompat.setImageTintList(actIcon, ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                } else if (chooseStatus[position] == 1) {
                    chooseStatus[position] = 0;
                    circularShape.setImageResource(R.drawable.circular_shape_none);
                    ImageViewCompat.setImageTintList(actIcon, ColorStateList.valueOf(Color.parseColor("#97e0bb")));
                }
            }
        });
        actIcon.setImageResource(actModel.getAct_icon());
        actTxt.setText(actModel.getAct_name());
        return listitemView;
    }
}
