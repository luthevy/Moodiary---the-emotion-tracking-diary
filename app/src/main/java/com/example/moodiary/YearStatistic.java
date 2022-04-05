package com.example.moodiary;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.*;

public class YearStatistic extends Activity {
    LinearLayout maxDayInMonth;
    LinearLayout []MonthOfYear;
    String[] month_brief = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_year_statistics);
        maxDayInMonth = findViewById(R.id.maxDayInMonth);
        MonthOfYear = new LinearLayout[13];
        MonthOfYear[1] = findViewById(R.id.Month1);
        MonthOfYear[2] = findViewById(R.id.Month2);
        MonthOfYear[3] = findViewById(R.id.Month3);
        MonthOfYear[4] = findViewById(R.id.Month4);
        MonthOfYear[5] = findViewById(R.id.Month5);
        MonthOfYear[6] = findViewById(R.id.Month6);
        MonthOfYear[7] = findViewById(R.id.Month7);
        MonthOfYear[8] = findViewById(R.id.Month8);
        MonthOfYear[9] = findViewById(R.id.Month9);
        MonthOfYear[10] = findViewById(R.id.Month10);
        MonthOfYear[11] = findViewById(R.id.Month11);
        MonthOfYear[12] = findViewById(R.id.Month12);



        //Day of month
        for(int i = 0; i<=31; i++){
            TextView txtday = new TextView(this);
            txtday.setText(Integer.toString(i));
            if(i==0)
                txtday.setText("");
            txtday.setTextSize(13);
            maxDayInMonth.addView(txtday);
        }

        for(int curMonth = 1; curMonth<=12; curMonth++) {
            //January
            for (int i = 0; i <= 31; i++) {
                if (i == 0) {
                    TextView txtday = new TextView(this);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.setMargins(-2, 0, 0, 0);
                    txtday.setLayoutParams(params);
                    txtday.setText(month_brief[curMonth-1]);
                    txtday.setTextSize(13);
                    MonthOfYear[curMonth].addView(txtday);
                } else {
                    if(curMonth==2 && i==30)
                        break;
                    if((curMonth==4 ||curMonth==6 ||curMonth==9)||curMonth==11)
                        if(i==31)
                            break;
                    ImageView imgIcon = new ImageView(this);
                    imgIcon.setImageResource(R.drawable.circular_shape_none);
                    LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(62, 62);
                    imgIcon.setLayoutParams(parms);
                    MonthOfYear[curMonth].addView(imgIcon);
                }
            }
        }


    }
}
