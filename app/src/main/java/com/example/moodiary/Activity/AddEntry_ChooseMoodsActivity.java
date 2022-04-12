package com.example.moodiary.Activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moodiary.CustomExtraMoods;
import com.example.moodiary.MoodInfo;
import com.example.moodiary.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class AddEntry_ChooseMoodsActivity extends AppCompatActivity {

    private TextView chooseDay, chooseTime;
    DatePickerDialog.OnDateSetListener SetDate;
    TimePickerDialog.OnTimeSetListener SetTime;
    int                                tHour, tMinute;
    private ImageButton btnNext, btnBack;

    private final String[]    mtypes = {"Amazing", "Happy", "Ok", "Sad", "Awful"};
    private       ImageView[] ms, mbgs;
    private TextView[]       mtexts;
    private RelativeLayout[] mrls;

    private int[] chosenMood = {0, 0};


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry_choose_moods);

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


        //----------------------------------------Day Time Setting---------------------------------------------------------------
        final Calendar calendar = Calendar.getInstance();
        int            day      = calendar.get(Calendar.DAY_OF_MONTH);
        int            month    = calendar.get(Calendar.MONTH);
        int            year     = calendar.get(Calendar.YEAR);

        SimpleDateFormat dateFormatTemp = new SimpleDateFormat("dd/MM/yyyy");
        String           curdate        = dateFormatTemp.format(new Date());
        chooseDay.setText(curdate);

        SimpleDateFormat timeFormatTemp = new SimpleDateFormat("HH:mm");
        String           curtime        = timeFormatTemp.format(new Date());
        chooseTime.setText(curtime);

        //Choose day
        chooseDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePicker = new DatePickerDialog(AddEntry_ChooseMoodsActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, SetDate, year, month, day);
                datePicker.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePicker.show();
            }
        });
        SetDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month += 1;
                String date   = "";
                String newDay = "";
                if (day < 10)
                    newDay = "0" + day;
                else
                    newDay = "" + day;
                if (month >= 10)
                    date = newDay + "/" + month + "/" + year;
                else
                    date = newDay + "/0" + month + "/" + year;
                chooseDay.setText(date);
            }
        };


        chooseTime.setOnClickListener(view -> {
            TimePickerDialog timePicker = new TimePickerDialog(AddEntry_ChooseMoodsActivity.this, android.R.style.Theme_Holo_Dialog_MinWidth,
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


        //----------------------------------CLICK MOOD ICON-----------------------------------------------

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


                    LayoutInflater inflater   = AddEntry_ChooseMoodsActivity.this.getLayoutInflater();
                    RelativeLayout rl         = mrls[i];
                    View           extra_list = inflater.inflate(R.layout.list_view_layout, null);
                    ListView       extra_mood = extra_list.findViewById(R.id.extra_mood);
                    CustomExtraMoods adapter = new CustomExtraMoods(
                            AddEntry_ChooseMoodsActivity.this,
                            R.layout.custom_extra_mood,
                            MoodInfo.moods_thumbnail[i],
                            MoodInfo.moods_type[i],
                            MoodInfo.moods_color[i]);

                    extra_mood.setAdapter(adapter);
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

                    DisplayMetrics displayMetrics = new DisplayMetrics();
                    getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                    int height = displayMetrics.heightPixels;
                    int width  = displayMetrics.widthPixels;

                    TranslateAnimation trans = new TranslateAnimation(
                            width * i * 1f / 5,
                            width * i * 1f / 5,
                            2 * height * 1f / 3,
                            height);

                    trans.setInterpolator(new LinearInterpolator());
                    trans.setDuration(250);

                    rl.addView(extra_list);

                    ImageView rec = findViewById(R.id.whiteRec);
                    rec.setVisibility(View.VISIBLE);
                    rec.startAnimation(trans);
                    rec.setVisibility(View.INVISIBLE);
                }
            });
        }

        //--------------------------------------------------------------------------------------------

        btnNext = findViewById(R.id.btnNext);
        btnNext.setOnClickListener((View view) -> {
                    Intent intent = new Intent(getApplicationContext(), AddEntry_ChooseActivitiesActivity.class);
                    intent.putExtra("chosenMood", chosenMood);
                    intent.putExtra("dayOfMood", chooseDay.getText().toString());
                    intent.putExtra("timeOfMood", chooseTime.getText().toString());
                    startActivity(intent);
                }
        );
        btnBack = findViewById(R.id.btnBack1);
        btnBack.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), ShowEntriesActivity.class)));
    }

    public void resetBackground() {
        for (int i = 0; i < ms.length; ++i) {
            mbgs[i].setVisibility(View.INVISIBLE);
            // TO RESET THE CHOSEN MOOD UPON CLICKING ANOTHER TYPE
//            if (MoodInfor.moods_type[i].length > 1){
//                mtexts[i].setText("...");
//                mtexts[i].setTextSize(30);
//                mtexts[i].setTypeface(null, Typeface.BOLD);
//                mtexts[i].setPaddingRelative(0,-45,0,0);
//            }
//            else mtexts[i].setText(MoodInfor.moods_type[i][0]);
//
//            ms[i].setImageResource(MoodInfor.moods_thumbnail[i][0]);
        }
    }
}
