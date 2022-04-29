package com.example.moodiary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.moodiary.Activity.ShowEntriesActivity;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;

public class YearStatistic extends Activity {
    LinearLayout maxDayInMonth;
    LinearLayout[] MonthOfYear;
    String[] month_brief = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    TextView currentYearStatistic;
    ImageButton backBtn;
    int currentYear;
    private BarChart barChartCountMood;
    private ArrayList<Entry> listEntry;
    private HashMap<String, Integer> countMood;
    ArrayList barList = new ArrayList();
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_year_statistics);
        currentYearStatistic = findViewById(R.id.currentYearStatistic);
        currentYear = Integer.parseInt(currentYearStatistic.getText().toString());



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
        backBtn = findViewById(R.id.backBtnInYear);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(YearStatistic.this, ShowEntriesActivity.class));
            }
        });
        listEntry = new ArrayList<>();

        DatabaseReference dtb = FirebaseDatabase.getInstance().getReference("Entry");
        dtb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Entry entry = dataSnapshot.getValue(Entry.class);
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    Date date = null;
                    try {
                        date = formatter.parse(entry.getDateOfMood());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if (date.getYear() + 1900 == currentYear)
                        listEntry.add(entry);
                }

                Collections.sort(listEntry, new Comparator<Entry>() {
                    @Override
                    public int compare(Entry e1, Entry e2) {
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                        try {
                            Date date1 = formatter.parse(e1.getDateOfMood());
                            Date date2 = formatter.parse(e2.getDateOfMood());
                            return date1.compareTo(date2);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        return 0;
                    }
                });
                Collections.reverse(listEntry);

                countMood = new HashMap<>();
                for (Entry e : listEntry) {
                    if (countMood.containsKey(e.getMoodType()))
                        countMood.put(e.getMoodType(), countMood.get(e.getMoodType()) + 1);
                    else
                        countMood.put(e.getMoodType(), 1);
                }

                for (String key : countMood.keySet()) {
                    System.out.println(key + " " + countMood.get(key));
                }


                //---------- Get last mood if a day has >1 mood
                for (int first = 0; first < listEntry.size() - 1; first++)
                    for (int second = first + 1; second < listEntry.size(); second++)
                        if (listEntry.get(first).getDayOfmood().equals(listEntry.get(second).getDayOfmood())) {
                            listEntry.remove(second);
                            second--;
                        }


                //------------------Day of month-------------------
                for (int i = 0; i <= 31; i++) {
                    TextView txtday = new TextView(context);
                    txtday.setText(Integer.toString(i));
                    if (i == 0)
                        txtday.setText("");
                    txtday.setTextSize(13);
                    maxDayInMonth.addView(txtday);
                }

                //--------------------Show mood icon in each day-----------------------
                for (int curMonth = 1; curMonth <= 12; curMonth++) {
                    for (int curDay = 0; curDay <= 31; curDay++) {
                        int isSet = 0;
                        if (curDay == 0) {
                            //-----------Set month tag-----------
                            TextView txtday = new TextView(context);
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            params.setMargins(-2, 0, 0, 0);
                            txtday.setLayoutParams(params);
                            txtday.setText(month_brief[curMonth - 1]);
                            txtday.setTextSize(13);
                            MonthOfYear[curMonth].addView(txtday);
                        } else {
                            if (curMonth == 2 && curDay == 30)
                                break;
                            if ((curMonth == 4 || curMonth == 6 || curMonth == 9) || curMonth == 11)
                                if (curDay == 31)
                                    break;

                            for (Entry e : listEntry) {
                                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                                try {
                                    Date date1 = formatter.parse(e.getDateOfMood());
                                    if (date1.getMonth() + 1 == curMonth && date1.getDate() == curDay) {
                                        ImageView imgIcon = new ImageView(context);
                                        setMoodThumb(imgIcon, e.getMoodType());
                                        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(62, 62);
                                        imgIcon.setLayoutParams(parms);
                                        MonthOfYear[curMonth].addView(imgIcon);
                                        isSet = 1;
                                    }
                                } catch (ParseException parseException) {
                                    parseException.printStackTrace();
                                }
                            }

                            if (isSet == 0) {
                                ImageView imgIcon = new ImageView(context);
                                imgIcon.setImageResource(R.drawable.circular_shape_none);
                                LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(62, 62);
                                imgIcon.setLayoutParams(parms);
                                MonthOfYear[curMonth].addView(imgIcon);
                            }
                        }
                    }
                }

                //------------------------Count mood chart------------------------
                barChartCountMood = findViewById(R.id.barChartCountMood);
                getData();
                ArrayList xVals = new ArrayList();
                ArrayList<Integer> barColor = new ArrayList<>();

                xVals.add("");
                for (String key2 : countMood.keySet()) {
                    xVals.add(key2);
                    for (int i = 0; i < MoodInfo.moods_type.length; i++)
                        for (int j = 0; j < MoodInfo.moods_type[i].length; j++)
                            if (key2.equals(MoodInfo.moods_type[i][j]))
                                barColor.add(Color.parseColor(MoodInfo.moods_color[i]));
                }


                BarDataSet barDataSet = new BarDataSet(barList, "");
                BarData barData = new BarData(barDataSet);
                barData.setValueFormatter(new ValueFormatter() {
                    @Override
                    public String getFormattedValue(float value) {
                        return "" + ((int) value);
                    }
                });
                barChartCountMood.setData(barData);

                //barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                barDataSet.setColors(barColor);
                barDataSet.setValueTextColors(barColor);
                barDataSet.setValueTextSize(16f);

                Legend l = barChartCountMood.getLegend(); l.setEnabled(false);

                barChartCountMood.getDescription().setEnabled(false);
                barChartCountMood.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xVals));
                barChartCountMood.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
                barChartCountMood.getXAxis().setDrawGridLines(false);
                barChartCountMood.getXAxis().setDrawAxisLine(false);
                barChartCountMood.getXAxis().setGranularity(1);
                barChartCountMood.getXAxis().setLabelCount(xVals.size());
                barChartCountMood.getAxisLeft().setEnabled(false);
                barChartCountMood.getAxisRight().setEnabled(false);
                barChartCountMood.invalidate();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                System.out.println("Error reading year statistics");
            }
        });
    }

    private void getData() {
        int c = 1;
        for (String key : countMood.keySet()) {
            barList.add(new BarEntry(c, countMood.get(key)));
            c++;
        }

    }

    private void setMoodThumb(ImageView img, String mood) {

        for (int i = 0; i < MoodInfo.moods_type.length; i++) {
            for (int j = 0; j < MoodInfo.moods_type[i].length; j++) {
                if (mood.equals(MoodInfo.moods_type[i][j])) {
                    img.setImageResource(MoodInfo.moods_thumbnail[i][j]);
                    img.setImageTintList(ColorStateList.valueOf(Color.parseColor(MoodInfo.moods_color[i])));
                }
            }
        }
    }
}
