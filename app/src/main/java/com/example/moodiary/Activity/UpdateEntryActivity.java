package com.example.moodiary.Activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moodiary.Entry;
import com.example.moodiary.EntryActivitiesAdapter;
import com.example.moodiary.EntryActivity;
import com.example.moodiary.MoodInfo;
import com.example.moodiary.MoodsAdapter;
import com.example.moodiary.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class UpdateEntryActivity extends AppCompatActivity {

    private ImageButton btnUpdate, btnBack;
    DatabaseReference thisEntryRef;
    Entry             thisEntry;

    private TextView chooseDay, chooseTime;
    DatePickerDialog.OnDateSetListener SetDate;
    int                                tHour, tMinute;

    private ImageView[] ms, mbgs;
    private TextView[]       mtexts;
    private RelativeLayout[] mrls;

    //private ImageView rec;

    private int[] chosenMood = {0, 0};

    private String[]  activityDefault_type      = {
            "cleaning", "cook", "date", "drawing",
            "eat", "family", "festival", "friend",
            "game", "gift", "music", "party",
            "reading", "relax", "shopping", "sleep",
            "sport", "study", "swim", "tv",
            "walk", "work"
    };
    private Integer[] activityDefault_thumbnail = {
            R.drawable.activity_cleaning, R.drawable.activity_cook, R.drawable.activity_date, R.drawable.activity_drawing,
            R.drawable.activity_eat, R.drawable.activity_family, R.drawable.activity_festival, R.drawable.activity_friend,
            R.drawable.activity_game, R.drawable.activity_gift, R.drawable.activity_music, R.drawable.activity_party,
            R.drawable.activity_reading, R.drawable.activity_relax, R.drawable.activity_shopping, R.drawable.activity_sleep,
            R.drawable.activity_sport, R.drawable.activity_study, R.drawable.activity_swim, R.drawable.activity_tv,
            R.drawable.activity_walk, R.drawable.activity_work
    };
    private boolean[] chooseStatus              = new boolean[activityDefault_type.length];

    private GridView activitiesGridView;
    private EditText notes;
    private Button   btnAddPhoto;
    private Uri      selectedImage;
    String listAct = "";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_entry);

        ms = new ImageView[]{
                findViewById(R.id.chooseAmazing),
                findViewById(R.id.chooseHappy),
                findViewById(R.id.chooseOk),
                findViewById(R.id.chooseSad),
                findViewById(R.id.chooseAwful)
        };

        mbgs = new ImageView[]{
                findViewById(R.id.chooseAmazingBg),
                findViewById(R.id.chooseHappyBg),
                findViewById(R.id.chooseOkBg),
                findViewById(R.id.chooseSadBg),
                findViewById(R.id.chooseAwfulBg)
        };

        mtexts = new TextView[]{
                findViewById(R.id.textAmazing),
                findViewById(R.id.textHappy),
                findViewById(R.id.textOk),
                findViewById(R.id.textSad),
                findViewById(R.id.textAwful)
        };

        mrls = new RelativeLayout[]{
                findViewById(R.id.amazingRL),
                findViewById(R.id.happyRL),
                findViewById(R.id.okRL),
                findViewById(R.id.sadRL),
                findViewById(R.id.awfulRL)
        };

        chooseDay  = findViewById(R.id.chooseDay);
        chooseTime = findViewById(R.id.chooseTime);

        btnBack   = findViewById(R.id.btnBack1);
        btnUpdate = findViewById(R.id.btnUpdate);

        //rec = findViewById(R.id.fullWhiteRec);

        activitiesGridView = findViewById(R.id.activitiesGridView);
        notes              = findViewById(R.id.edtxt_Notes);
        btnAddPhoto        = findViewById(R.id.btnAddPhoto);

        thisEntryRef = FirebaseDatabase
                .getInstance()
                .getReference("Entry")
                .child(getIntent().getStringExtra("EntryKey"));

        thisEntryRef.get().addOnSuccessListener(dataSnapshot -> {

            thisEntry = dataSnapshot.getValue(Entry.class);

            // SET DAY AND TIME
            chooseDay.setText(thisEntry.getDayOfmood());
            chooseTime.setText(thisEntry.getTimeOfmood());

            String thisEntryMoodType = thisEntry.getMoodType();

            // SET MOOD
            for (int i = 0; i < MoodInfo.moods_type.length; i++) {
                for (int j = 0; j < MoodInfo.moods_type[i].length; j++) {
                    if (thisEntryMoodType.equals(MoodInfo.moods_type[i][j])) {

                        mbgs[i].setVisibility(View.VISIBLE);
                        ms[i].setImageResource(MoodInfo.moods_thumbnail[i][j]);
                        mtexts[i].setText(MoodInfo.moods_type[i][j]);
                        mtexts[i].setTextSize(15);
                        mtexts[i].setTypeface(null, Typeface.NORMAL);
                        mtexts[i].setPaddingRelative(0, 0, 0, 0);

                        chosenMood[0] = i;
                        chosenMood[1] = j;
                    }
                }
            }

            // SET ACTIVITIES
            for (String activity : thisEntry.getActivity().split(" "))
                chooseStatus[Integer.parseInt(activity) - 1] = true;

            // SET NOTE
            notes.setText(thisEntry.getNote());

            // SET IMAGE
            if (!thisEntry.getImgLink().isEmpty()) {
                btnAddPhoto.setText("Edit Photo");

                //show or change selected image
                ((ImageView) findViewById(R.id.selectedImage)).setImageURI(
                        Uri.parse(thisEntry.getImgLink()));
            }

        });

        final Calendar calendar = Calendar.getInstance();
        int            day      = calendar.get(Calendar.DAY_OF_MONTH);
        int            month    = calendar.get(Calendar.MONTH);
        int            year     = calendar.get(Calendar.YEAR);

        chooseDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePicker = new DatePickerDialog(UpdateEntryActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth, SetDate, year, month, day);
                datePicker.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                datePicker.show();
            }
        });

        SetDate = (datePicker, year1, month1, day1) ->
                chooseDay.setText(String.format("%02d/%02d/%d", day1, month1 + 1, year1));

        chooseTime.setOnClickListener(view -> {
            TimePickerDialog timePicker = new TimePickerDialog(UpdateEntryActivity.this,
                    android.R.style.Theme_Holo_Dialog_MinWidth,
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int hourOfDay, int Minute) {
                            tHour   = hourOfDay;
                            tMinute = Minute;
                            String time = hourOfDay + ":" + Minute;

                            SimpleDateFormat f24Hours = new SimpleDateFormat("HH:mm");
                            try {
                                Date date = f24Hours.parse(time);
                                assert date != null;
                                chooseTime.setText(f24Hours.format(date));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }, 12, 0, true);
            timePicker.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            timePicker.updateTime(tHour, tMinute);
            timePicker.show();
        });


        for (int j = 0; j < ms.length; ++j) {
            int i = j;

            if (MoodInfo.moods_type[i].length > 1) {
                mtexts[i].setText("...");
                mtexts[i].setTextSize(30);
                mtexts[i].setTypeface(null, Typeface.BOLD);
                mtexts[i].setPaddingRelative(0, -45, 0, 0);
            }

            ms[i].setOnClickListener((View view) -> {
                resetBackground();

                mbgs[i].setVisibility(View.VISIBLE);
                if (MoodInfo.moods_type[i].length == 1) {
                    chosenMood[0] = i;
                    chosenMood[1] = 0;
                } else {
                    mtexts[i].setVisibility(View.INVISIBLE);
                    ms[i].setVisibility(View.INVISIBLE);
                    mbgs[i].setVisibility(View.INVISIBLE);

                    LayoutInflater inflater   = UpdateEntryActivity.this.getLayoutInflater();
                    RelativeLayout rl         = mrls[i];
                    View           extra_list = inflater.inflate(R.layout.list_view_layout, null);
                    ListView       extra_mood = extra_list.findViewById(R.id.extra_mood);
                    MoodsAdapter adapter = new MoodsAdapter(
                            UpdateEntryActivity.this,
                            R.layout.custom_extra_mood,
                            MoodInfo.moods_thumbnail[i],
                            MoodInfo.moods_type[i],
                            MoodInfo.moods_color[i]);

                    extra_mood.setAdapter(adapter);
                    setListViewHeightBasedOnItems(extra_mood);

                    extra_mood.setOnItemClickListener((AdapterView<?> adapterView, View view_, int position, long id) -> {

                        resetBackground();
                        mtexts[i].setVisibility(View.VISIBLE);
                        mbgs[i].setVisibility(View.VISIBLE);
                        ms[i].setVisibility(View.VISIBLE);

                        ms[i].setImageResource(MoodInfo.moods_thumbnail[i][position]);
                        mtexts[i].setText(MoodInfo.moods_type[i][position]);
                        mtexts[i].setTextSize(15);
                        mtexts[i].setTypeface(null, Typeface.NORMAL);
                        mtexts[i].setPaddingRelative(0, 0, 0, 0);
                        chosenMood[0] = i;
                        chosenMood[1] = position;
                        rl.removeView(extra_list);
                    });

                    rl.addView(extra_list);
                }
            });
        }

        ArrayList<EntryActivity> activityArrayList = new ArrayList<>();
        for (int i = 0; i < activityDefault_type.length; i++) {
            activityArrayList.add(new EntryActivity(activityDefault_type[i], activityDefault_thumbnail[i]));
        }
        EntryActivitiesAdapter adapter = new EntryActivitiesAdapter(this, activityArrayList, chooseStatus);
        activitiesGridView.setAdapter(adapter);

        btnAddPhoto.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 3);
        });

        btnBack.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), ShowEntriesActivity.class)));

        btnUpdate.setOnClickListener(view -> {

            for (int i = 0; i < chooseStatus.length; i++) {
                if (chooseStatus[i])
                    listAct += i + 1 + " ";
            }

            thisEntry.setActivity(listAct);
            thisEntry.setNote(notes.getText().toString());
            thisEntry.setDayOfmood(chooseDay.getText().toString());
            thisEntry.setTimeOfmood(chooseTime.getText().toString());
            thisEntry.setMoodType(MoodInfo.moods_type[chosenMood[0]][chosenMood[1]]);

            thisEntryRef.setValue(thisEntry)
                    .addOnSuccessListener(unused ->
                            startActivity(new Intent(getApplicationContext(), ShowEntriesActivity.class)));
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            btnAddPhoto.setText("Edit Photo");

            //show or change selected image
            selectedImage = data.getData();
            ((ImageView) findViewById(R.id.selectedImage)).setImageURI(selectedImage);
        }
    }

    public void resetBackground() {
        for (int i = 0; i < ms.length; ++i) {
            mbgs[i].setVisibility(View.INVISIBLE);
        }
    }
    public static boolean setListViewHeightBasedOnItems(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                item.measure(0, 0);
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems - 1);

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight;
            listView.setLayoutParams(params);
            listView.requestLayout();

            return true;

        } else {
            return false;
        }
    }
}
