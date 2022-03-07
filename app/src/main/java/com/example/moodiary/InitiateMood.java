package com.example.moodiary;

import android.app.AppComponentFactory;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class InitiateMood extends AppCompatActivity {
    private TextView chooseDay, chooseTime;
    DatePickerDialog.OnDateSetListener SetDate;
    TimePickerDialog.OnTimeSetListener SetTime;
    int tHour, tMinute;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beginchoosemood);

        chooseDay = findViewById(R.id.chooseDay);
        chooseTime = findViewById(R.id.chooseTime);

        final Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        SimpleDateFormat dateFormatTemp = new SimpleDateFormat("dd/MM/yyyy");
        String curdate = dateFormatTemp.format(new Date());
        chooseDay.setText(curdate);

        SimpleDateFormat timeFormatTemp = new SimpleDateFormat("HH:ss");
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
        //Choose Time

//        chooseTime.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                TimePickerDialog timePicker = new TimePickerDialog(InitiateMood.this, SetTime, 10,20 , true);
//                timePicker.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                timePicker.show();
//            }
//        });
//        SetTime = new TimePickerDialog.OnTimeSetListener() {
//            @Override
//            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
//                chooseTime.setText(hourOfDay+":"+minute);
//            }
//        };
    }
}
