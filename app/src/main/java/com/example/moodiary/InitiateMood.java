package com.example.moodiary;

import android.app.AppComponentFactory;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
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
    private TextView chooseDay, chooseTime;
    ImageView chooseAmazing, chooseHappy, chooseOk, chooseSad, chooseAwful;
    ImageView chooseAmazingBg, chooseHappyBg, chooseOkBg, chooseSadBg, chooseAwfulBg;
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


        chooseDay = findViewById(R.id.chooseDay);
        chooseTime = findViewById(R.id.chooseTime);


        //-------------------------------------------------------------------------------------------------------
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

        //---------------------------------------------------------------------------------

        chooseAmazing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDefaultBackground();
                moodType = "Amazing";
                chooseAmazingBg.setImageTintList(ColorStateList.valueOf(Color.parseColor("#90DA6E")));
            }
        });

        chooseHappy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDefaultBackground();
                moodType = "Happy";
                chooseHappyBg.setImageTintList(ColorStateList.valueOf(Color.parseColor("#5CEF93")));
            }
        });

        chooseOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDefaultBackground();
                moodType = "Ok";
                chooseOkBg.setImageTintList(ColorStateList.valueOf(Color.parseColor("#45D9FF")));
            }
        });

        chooseSad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDefaultBackground();
                moodType = "Sad";
                chooseSadBg.setImageTintList(ColorStateList.valueOf(Color.parseColor("#F5CC67")));
            }
        });

        chooseAwful.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDefaultBackground();
                moodType = "Awful";
                chooseAwfulBg.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FC6C79")));
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
