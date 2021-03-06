package com.example.moodiary;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.moodiary.Activity.ShowEntriesActivity;
import com.example.moodiary.Activity.UpdateEntryActivity;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class EntriesAdapter extends ArrayAdapter<ArrayList<Entry>> {
    Context                     context;
    Integer[]                   thumbnails;
    ArrayList<ArrayList<Entry>> items;
    String                      actColor;

//-----------------
//    Integer[] moods_thumbnail={R.drawable.mood_amazing,R.drawable.mood_happy, R.drawable.mood_ok, R.drawable.mood_sad,
//            R.drawable.mood_awful};
//    Integer[] activity_thumbnail = {R.drawable.activity_drawing, R.drawable.activity_tv, R.drawable.activity_eat,
//            R.drawable.activity_sleep, R.drawable.activity_walk, R.drawable.activity_date, R.drawable.activity_swim,
//            R.drawable.activity_friend, R.drawable.activity_work};
//    String [] moods_type = {"Amazing", "Happy","Ok", "Sad", "Awful"};
//    String [] moods_color ={"#90DA6E", "#5CEF93", "#45D9FF", "#F5CC67", "#FC6C79"};
//    String [] activity_type ={"drawing", "TV", "eat", "sleep", "walk", "date", "swim", "friend", "work"};
//-----------------------------------------

    public EntriesAdapter(Context context, int layoutToBeInflated, ArrayList<ArrayList<Entry>> items, Integer[] thumbnails) {
        super(context, R.layout.custom_row_entries, items);
        this.context    = context;
        this.thumbnails = thumbnails;
        this.items      = (ArrayList) items.clone();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        LinearLayout   row      = (LinearLayout) inflater.inflate(R.layout.custom_row_entries, null);
        row.removeAllViews();
        int get1DateToShow = 0;

        for (Entry e : items.get(position)) {

            View      child       = inflater.inflate(R.layout.custom_show_one_entries, null);
            ImageView moodIcon    = child.findViewById(R.id.moodIcon);
            TextView  curMood     = child.findViewById(R.id.curMood);
            TextView  timeText    = child.findViewById(R.id.timeText);
            TextView  descText    = child.findViewById(R.id.descText);
            ImageView updateEntry = child.findViewById(R.id.updateEntryButton);
            ImageView deleteEntry = child.findViewById(R.id.deleteEntryButton);

            //---------------Set date on the first entry in a day---------------
            if (get1DateToShow == 0) {
                TextView show1Date = new TextView(row.getContext());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        (int) ViewGroup.LayoutParams.MATCH_PARENT, (int) ViewGroup.LayoutParams.WRAP_CONTENT);
                show1Date.setText(e.getDayOfmood());
                show1Date.setTextSize(18);
                show1Date.setLayoutParams(params);
                show1Date.setPadding(70, 25, 0, -20);
                show1Date.setTextColor(Color.parseColor("#97e0bb"));
                show1Date.setTypeface(parent.getResources().getFont(R.font.poppinsmedium));
                show1Date.setTextSize(19);
                show1Date.setGravity(Gravity.CENTER);

                get1DateToShow = 1;
                row.addView(show1Date);
            }

            //----------------Update entry-----------
            updateEntry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, UpdateEntryActivity.class);
                    intent.putExtra("EntryKey", ShowEntriesActivity.keyOfEntry.get(e));
                    context.startActivity(intent);
                }
            });

            deleteEntry.setOnClickListener(view -> {
                AlertDialog dialog = new AlertDialog.Builder(context)
                        .setTitle("Delete")
                        .setMessage("Are you sure?")
                        .setPositiveButton("Yes", (dialogInterface, i) -> {
                            FirebaseDatabase
                                    .getInstance()
                                    .getReference("Entry")
                                    .child(ShowEntriesActivity.keyOfEntry.get(e))
                                    .removeValue();
                            notifyDataSetChanged();
                            context.startActivity(((Activity) context).getIntent());
                            //context.startActivity(new Intent(context.getApplicationContext(), ShowEntriesActivity.class));
                        })
                        .setNegativeButton("No", null)
                        .setIcon(R.drawable.warning1)
                        .show();

                dialog.getButton(Dialog.BUTTON_POSITIVE).setTextSize(18);
                dialog.getButton(Dialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#ff999999"));
                dialog.getButton(Dialog.BUTTON_NEGATIVE).setTextSize(18);

                ((TextView) dialog.findViewById(android.R.id.message)).setTextSize(20);

            });

            curMood.setText(e.getMoodType());
            timeText.setText(e.getTimeOfmood());
            descText.setText(e.getNote());
            setMoodThumb(moodIcon, e.getMoodType(), curMood);


            //------------------------Set image -----------------------------
            if (!e.getImgLink().equals("")) {
                //Picasso
                LinearLayout entryImgLayout = child.findViewById(R.id.entryImgLayout);
                View         entryImgView   = inflater.inflate(R.layout.custom_image_layout, null);
                ImageView    entryImg       = entryImgView.findViewById(R.id.entryImg);
                Picasso.with(entryImgView.getContext()).load(e.getImgLink()).into(entryImg);
                entryImgLayout.addView(entryImgView);
            }

            // --------------------SET LAYOUT CHO 1 BOX CUNG NGAY------------------------
            GridLayout act_grid_layout = child.findViewById(R.id.act_grid_layout);
            String[]   parts           = e.getActivity().split(" ");
//            int total = parts.length;
            int maxColumn = 3;
//            int maxRow = total/maxColumn;
            act_grid_layout.setColumnCount(maxColumn);
//            act_grid_layout.setRowCount(maxRow);
            int c = 0;
            int r = 0;
            for (String part : parts) {

                View      small_act_layout = inflater.inflate(R.layout.custom_action_row, null);
                ImageView actIcon          = small_act_layout.findViewById(R.id.actIcon);
                TextView  textView         = small_act_layout.findViewById(R.id.actText);

                actIcon.setImageResource(MoodInfo.activity_thumbnail[Integer.parseInt(part) - 1]);
                actIcon.setImageTintList(ColorStateList.valueOf(Color.parseColor(actColor)));

                textView.setText(MoodInfo.activity_type[Integer.parseInt(part) - 1]);

                GridLayout.LayoutParams param =new GridLayout.LayoutParams();
                param.height = GridLayout.LayoutParams.WRAP_CONTENT;
                param.width = GridLayout.LayoutParams.WRAP_CONTENT;
                param.columnSpec = GridLayout.spec(c);
                param.rowSpec = GridLayout.spec(r);
                small_act_layout.setLayoutParams(param);

                c++;
                if(c == maxColumn) {
                    c = 0;
                    r++;
                }
                act_grid_layout.addView(small_act_layout);
            }

//            LinearLayout act_linear = child.findViewById(R.id.act_linear);
//            String[]     parts      = e.getActivity().split(" ");
//            for (String part : parts) {
//
//                View      small_act_layout = inflater.inflate(R.layout.custom_action_row, null);
//                ImageView actIcon          = small_act_layout.findViewById(R.id.actIcon);
//                TextView  textView         = small_act_layout.findViewById(R.id.actText);
//
//                actIcon.setImageResource(MoodInfo.activity_thumbnail[Integer.parseInt(part) - 1]);
//                actIcon.setImageTintList(ColorStateList.valueOf(Color.parseColor(actColor)));
//
//                textView.setText(MoodInfo.activity_type[Integer.parseInt(part) - 1]);
//                act_linear.addView(small_act_layout);
//            }

            row.addView(child);
        }
        return (row);
    }

    private void setMoodThumb(ImageView img, String mood, TextView curMood) {

        for (int i = 0; i < MoodInfo.moods_type.length; i++) {
            for (int j = 0; j < MoodInfo.moods_type[i].length; j++) {
                if (mood.equals(MoodInfo.moods_type[i][j])) {
                    if(i<5) {
                    img.setImageResource(MoodInfo.moods_thumbnail[i][j]);
                        img.setImageTintList(ColorStateList.valueOf(Color.parseColor(MoodInfo.moods_color[i])));
                        actColor = MoodInfo.moods_color[i];
                        curMood.setTextColor(ColorStateList.valueOf(Color.parseColor(MoodInfo.moods_color[i])));
                    }
                }
            }
        }
    }
}
