package com.example.moodiary;

import android.app.Activity;
import android.app.AppComponentFactory;
import android.app.DatePickerDialog;
import android.app.ListActivity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ImageViewCompat;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;



public class InitiateMood extends AppCompatActivity {
    private MoodInfor moodInfor;

//    Integer[][] moods_thumbnail=infor.moods_thumbnail;
//
//    String [][] moods_type = infor.moods_type;
//    String [] moods_color = infor.moods_color;

    private TextView chooseDay, chooseTime;
    private ImageView chooseAmazing, chooseHappy, chooseOk, chooseSad, chooseAwful;
    private ImageView chooseAmazingBg, chooseHappyBg, chooseOkBg, chooseSadBg, chooseAwfulBg;
    private TextView textAmazing, textHappy, textOk, textSad, textAwful;
    DatePickerDialog.OnDateSetListener SetDate;
    TimePickerDialog.OnTimeSetListener SetTime;
    int tHour, tMinute;
    private ImageButton btnNext, btnExit;
    private String moodType = "Amazing";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beginchoosemood);


        chooseAmazing = findViewById(R.id.chooseAmazing);
        chooseHappy = findViewById(R.id.chooseHappy);
        chooseOk = findViewById(R.id.chooseOk);
        chooseSad = findViewById(R.id.chooseSad);
        chooseAwful = findViewById(R.id.chooseAwful);

        chooseAmazingBg = findViewById(R.id.chooseAmazingBg);
        chooseHappyBg = findViewById(R.id.chooseHappyBg);
        chooseSadBg = findViewById(R.id.chooseSadBg);
        chooseOkBg = findViewById(R.id.chooseOkBg);
        chooseAwfulBg = findViewById(R.id.chooseAwfulBg);

        textAmazing = findViewById(R.id.textAmazing);
        textHappy = findViewById(R.id.textHappy);
        textOk = findViewById(R.id.textOk);
        textSad = findViewById(R.id.textOk);
        textAwful = findViewById(R.id.textAwful);

        if(moodInfor.moods_type[0].length > 1){
            textAmazing.setText("...");
        }
        if(moodInfor.moods_type[1].length > 1){
            textHappy.setText("...");
        }
        if(moodInfor.moods_type[2].length > 1){
            textOk.setText("...");
        }
        if(moodInfor.moods_type[3].length > 1){
            textSad.setText("...");
        }
        if(moodInfor.moods_type[4].length > 1){
            textAwful.setText("...");
        }


            chooseDay = findViewById(R.id.chooseDay);
        chooseTime = findViewById(R.id.chooseTime);


        //----------------------------------------Day Time Setting---------------------------------------------------------------
        final Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        SimpleDateFormat dateFormatTemp = new SimpleDateFormat("dd/MM/yyyy");
        String curdate = dateFormatTemp.format(new Date());
        chooseDay.setText(curdate);

        SimpleDateFormat timeFormatTemp = new SimpleDateFormat("HH:mm");
        String curtime = timeFormatTemp.format(new Date());
        chooseTime.setText(curtime);

        //Choose day
        chooseDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePicker = new DatePickerDialog(InitiateMood.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, SetDate, year, month, day);
                datePicker.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePicker.show();
            }
        });
        SetDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month += 1;
                String date="";
                String newDay="";
                if(day<10)
                    newDay="0"+day;
                else
                    newDay=""+day;
                if(month>=10)
                    date = newDay+"/"+month+"/"+year;
                else
                    date = newDay+"/0"+month+"/"+year;
                chooseDay.setText(date);
            }
        };


        chooseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePicker = new TimePickerDialog(InitiateMood.this, android.R.style.Theme_Holo_Dialog_MinWidth,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hourOfDay, int Minute) {
                                tHour = hourOfDay;
                                tMinute = Minute;
                                String time = hourOfDay + ":" +Minute;

                                SimpleDateFormat f24Hours = new SimpleDateFormat("HH:mm");
                                try{
                                    Date date = f24Hours.parse(time);
                                    assert date != null;
                                    chooseTime.setText(f24Hours.format(date));
                                } catch (ParseException e){
                                    e.printStackTrace();
                                }
                            }
                        },12, 0, true);
                timePicker.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePicker.updateTime(tHour,tMinute);
                timePicker.show();
            }
        });




        //----------------------------------CLICK MOOD ICON-----------------------------------------------

        chooseAmazing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDefaultBackground();
                if(moodInfor.moods_type[0].length == 1)
                    moodType = "Amazing";
                else{
                    chooseAmazing.setVisibility(View.INVISIBLE);
                    textAmazing.setVisibility(View.INVISIBLE);
                    LayoutInflater inflater = ((Activity)InitiateMood.this).getLayoutInflater();
                    RelativeLayout rl = findViewById(R.id.amazingRL);
                    View extra_list = inflater.inflate(R.layout.list_view_layout, null);
                    ListView extra_mood = extra_list.findViewById(R.id.extra_mood);
                    CustomExtraMoods adapter = new CustomExtraMoods(InitiateMood.this,R.layout.custom_extra_mood,moodInfor.moods_thumbnail[0],
                            moodInfor.moods_type[0],moodInfor.moods_color[0]);
                    extra_mood.setAdapter(adapter);
                    extra_mood.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                            chooseAmazing.setVisibility(View.VISIBLE);
                            textAmazing.setVisibility(View.VISIBLE);
                            chooseAmazing.setImageResource(moodInfor.moods_thumbnail[0][position]);
                            textAmazing.setText(moodInfor.moods_type[0][position]);
                            moodType = moodInfor.moods_type[0][position];
                            rl.removeView(extra_list);
                        }
                    });
                    rl.addView(extra_list);
                }
                chooseAmazingBg.setImageTintList(ColorStateList.valueOf(Color.parseColor("#90DA6E")));
            }
        });

        chooseHappy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDefaultBackground();
                if((moodInfor.moods_type[1]).length == 1)
                    moodType = "Happy";
                else{
                    chooseHappy.setVisibility(View.INVISIBLE);
                    textHappy.setVisibility(View.INVISIBLE);
                    LayoutInflater inflater = ((Activity)InitiateMood.this).getLayoutInflater();
                    RelativeLayout rl = findViewById(R.id.happyRL);
                    View extra_list = inflater.inflate(R.layout.list_view_layout, null);
                    ListView extra_mood = extra_list.findViewById(R.id.extra_mood);
                    CustomExtraMoods adapter = new CustomExtraMoods(InitiateMood.this,R.layout.custom_extra_mood,moodInfor.moods_thumbnail[1],
                            moodInfor.moods_type[1],moodInfor.moods_color[1]);
                    extra_mood.setAdapter(adapter);
                    extra_mood.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                            chooseHappy.setVisibility(View.VISIBLE);
                            textHappy.setVisibility(View.VISIBLE);
                            chooseHappy.setImageResource(moodInfor.moods_thumbnail[1][position]);
                            textHappy.setText(moodInfor.moods_type[1][position]);
                            moodType = moodInfor.moods_type[1][position];
                            rl.removeView(extra_list);
                        }
                    });
                    rl.addView(extra_list);
                }
                chooseHappyBg.setImageTintList(ColorStateList.valueOf(Color.parseColor("#5CEF93")));
            }
        });

        chooseOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDefaultBackground();
                if(moodInfor.moods_type[2].length == 1)
                    moodType = "Amazing";
                else{
                    chooseOk.setVisibility(View.INVISIBLE);
                    textOk.setVisibility(View.INVISIBLE);
                    LayoutInflater inflater = ((Activity)InitiateMood.this).getLayoutInflater();
                    RelativeLayout rl = findViewById(R.id.okRL);
                    View extra_list = inflater.inflate(R.layout.list_view_layout, null);
                    ListView extra_mood = extra_list.findViewById(R.id.extra_mood);
                    CustomExtraMoods adapter = new CustomExtraMoods(InitiateMood.this,R.layout.custom_extra_mood,moodInfor.moods_thumbnail[2],
                            moodInfor.moods_type[2],moodInfor.moods_color[2]);
                    extra_mood.setAdapter(adapter);
                    extra_mood.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                            chooseOk.setVisibility(View.VISIBLE);
                            textOk.setVisibility(View.VISIBLE);
                            chooseOk.setImageResource(moodInfor.moods_thumbnail[2][position]);
                            textOk.setText(moodInfor.moods_type[2][position]);
                            moodType = moodInfor.moods_type[2][position];
                            rl.removeView(extra_list);
                        }
                    });
                    rl.addView(extra_list);
                }
                chooseOkBg.setImageTintList(ColorStateList.valueOf(Color.parseColor("#45D9FF")));
            }
        });

        chooseSad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDefaultBackground();
                if(moodInfor.moods_type[3].length == 1)
                    moodType = "Sad";
                else{
                    chooseSad.setVisibility(View.INVISIBLE);
                    textSad.setVisibility(View.INVISIBLE);
                    LayoutInflater inflater = ((Activity)InitiateMood.this).getLayoutInflater();
                    RelativeLayout rl = findViewById(R.id.sadRL);
                    View extra_list = inflater.inflate(R.layout.list_view_layout, null);
                    ListView extra_mood = extra_list.findViewById(R.id.extra_mood);
                    CustomExtraMoods adapter = new CustomExtraMoods(InitiateMood.this,R.layout.custom_extra_mood,moodInfor.moods_thumbnail[3],
                            moodInfor.moods_type[3],moodInfor.moods_color[3]);
                    extra_mood.setAdapter(adapter);
                    extra_mood.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                            chooseSad.setVisibility(View.VISIBLE);
                            textSad.setVisibility(View.VISIBLE);
                            chooseSad.setImageResource(moodInfor.moods_thumbnail[3][position]);
                            textSad.setText(moodInfor.moods_type[3][position]);
                            moodType = moodInfor.moods_type[3][position];
                            rl.removeView(extra_list);
                        }
                    });
                    rl.addView(extra_list);
                }
                chooseSadBg.setImageTintList(ColorStateList.valueOf(Color.parseColor("#F5CC67")));
            }
        });

        chooseAwful.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDefaultBackground();
                if(moodInfor.moods_type[4].length == 1)
                    moodType = "Awful";
                else{
                    chooseAwful.setVisibility(View.INVISIBLE);
                    textAwful.setVisibility(View.INVISIBLE);
                    LayoutInflater inflater = ((Activity)InitiateMood.this).getLayoutInflater();
                    RelativeLayout rl = findViewById(R.id.awfulRL);
                    View extra_list = inflater.inflate(R.layout.list_view_layout, null);
                    ListView extra_mood = extra_list.findViewById(R.id.extra_mood);
                    CustomExtraMoods adapter = new CustomExtraMoods(InitiateMood.this,R.layout.custom_extra_mood,
                            moodInfor.moods_thumbnail[4],moodInfor.moods_type[4],moodInfor.moods_color[4]);
                    extra_mood.setAdapter(adapter);
                    extra_mood.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                            chooseAwful.setVisibility(View.VISIBLE);
                            textAwful.setVisibility(View.VISIBLE);
                            chooseAwful.setImageResource(moodInfor.moods_thumbnail[3][position]);
                            textAwful.setText(moodInfor.moods_type[3][position]);
                            moodType = moodInfor.moods_type[3][position];
                            rl.removeView(extra_list);
                        }
                    });
                    rl.addView(extra_list);
                }                chooseAwfulBg.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FC6C79")));
            }
        });


        //--------------------------------------------------------------------------------------------

        btnNext = findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddEntryActivity.class);
                intent.putExtra("currentChoosenMood",moodType);
                intent.putExtra("dayOfMood",chooseDay.getText().toString());
                intent.putExtra("timeOfMood",chooseTime.getText().toString());
                startActivity(intent);
            }
        });
        btnExit = findViewById(R.id.imgbtnExit);
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ShowEntriesActivity.class));
            }
        });
    }

    public void setDefaultBackground(){
        chooseAmazingBg.setImageTintList(ColorStateList.valueOf(Color.parseColor("#ffffff")));
        chooseHappyBg.setImageTintList(ColorStateList.valueOf(Color.parseColor("#ffffff")));
        chooseOkBg.setImageTintList(ColorStateList.valueOf(Color.parseColor("#ffffff")));
        chooseSadBg.setImageTintList(ColorStateList.valueOf(Color.parseColor("#ffffff")));
        chooseAwfulBg.setImageTintList(ColorStateList.valueOf(Color.parseColor("#ffffff")));

    }
}
