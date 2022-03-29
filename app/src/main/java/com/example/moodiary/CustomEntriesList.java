package com.example.moodiary;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CustomEntriesList extends ArrayAdapter<ArrayList<Entry>> {
    Context context;
    Integer[] thumbnails;
    ArrayList<ArrayList<Entry>> items;
    String actColor;

//-----------------
//    Integer[] moods_thumbnail={R.drawable.mood_amazing,R.drawable.mood_happy, R.drawable.mood_ok, R.drawable.mood_sad,
//            R.drawable.mood_awful};
//    Integer[] activity_thumbnail = {R.drawable.activity_drawing, R.drawable.activity_tv, R.drawable.activity_eat,
//            R.drawable.activity_sleep, R.drawable.activity_walk, R.drawable.activity_date, R.drawable.activity_swim,
//            R.drawable.activity_friend, R.drawable.activity_work};
//    String [] moods_type = {"Amazing", "Happy","Ok", "Sad", "Awful"};
//    String [] moods_color ={"#90DA6E", "#5CEF93", "#45D9FF", "#F5CC67", "#FC6C79"};
//    String [] activity_type ={"drawing", "TV", "eat", "sleep", "walk", "date", "swim", "friend", "work"};

    MoodInfor moodInfor;
//-----------------------------------------

    public CustomEntriesList(Context context, int layoutToBeInflated, ArrayList<ArrayList<Entry>> items, Integer[] thumbnails) {
        super(context, R.layout.custom_row_entries, items);
        this.context = context;
        this.thumbnails = thumbnails;
        this.items = (ArrayList)items.clone();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        LinearLayout row = (LinearLayout) inflater.inflate(R.layout.custom_row_entries, null);
        row.removeAllViews();
        System.out.println("In custom  "+ items.get(position));
        for(Entry e :items.get(position)){
            View child = inflater.inflate(R.layout.custom_show_one_entries, null);
            ImageView moodIcon = child.findViewById(R.id.moodIcon);
            TextView curMood = child.findViewById(R.id.curMood);
            TextView timeText = child.findViewById(R.id.timeText);

            TextView descText = child.findViewById(R.id.descText);

            curMood.setText(e.getMoodType());

            timeText.setText(e.getTimeOfmood());
            descText.setText(e.getNote());
            setMoodThumb(moodIcon, e.getMoodType(), curMood);

            if(!e.getImgLink().equals("")) {
                //Picasso
                LinearLayout entryImgLayout = child.findViewById(R.id.entryImgLayout);
                View entryImgView = inflater.inflate(R.layout.custom_image_layout, null);
                ImageView entryImg = entryImgView.findViewById(R.id.entryImg);
                Picasso.with(entryImgView.getContext()).load(e.getImgLink()).into(entryImg);
                entryImgLayout.addView(entryImgView);
            }

            // SET LAYOUT CHO 1 BOX CUNG NGAY
            LinearLayout act_linear = child.findViewById(R.id.act_linear);
            String []parts = e.getActivity().split(" ");
            for(int i = 0; i< parts.length; i++){
                View small_act_layout = inflater.inflate(R.layout.custom_action_row, null);
                ImageView actIcon = small_act_layout.findViewById(R.id.actIcon);
                TextView textView = small_act_layout.findViewById(R.id.actText);

                actIcon.setImageResource(moodInfor.activity_thumbnail[Integer.parseInt(parts[i])-1]);
                actIcon.setImageTintList(ColorStateList.valueOf(Color.parseColor(actColor)));

                textView.setText(moodInfor.activity_type[Integer.parseInt(parts[i])-1]);
                act_linear.addView(small_act_layout);
            }

            row.addView(child);
        }
        return(row);
    }

    private void setMoodThumb(ImageView img, String mood, TextView curMood){

        for (int i=0; i< moodInfor.moods_type.length; i++) {
            for (int j = 0; j < moodInfor.moods_type[i].length;j++){
                if (mood.equals(moodInfor.moods_type[i][j])) {
                    img.setImageResource(moodInfor.moods_thumbnail[i][j]);
                    img.setImageTintList(ColorStateList.valueOf(Color.parseColor(moodInfor.moods_color[i])));
                    actColor = moodInfor.moods_color[i];
                    curMood.setTextColor(ColorStateList.valueOf(Color.parseColor(moodInfor.moods_color[i])));
                }
            }
        }
    }
}
