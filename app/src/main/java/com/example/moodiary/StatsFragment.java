package com.example.moodiary;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.app.Fragment;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;

public class StatsFragment extends Fragment {
    private LineChart moodLineChart;
    private PieChart pieChartCountMood;
    private TextView currentMonthStatic;
    private Button toYearStatics;

    private ArrayList<Entry>listEntry;
    private HashMap<String,Integer> countMood;
    private int currentMonth, currentYear;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_month_statistics, container, false);
        moodLineChart = v.findViewById(R.id.moodLineChart);
        pieChartCountMood = v.findViewById(R.id.pieChartCountMood);
        currentMonthStatic = v.findViewById(R.id.currentMonthStatistic);
        toYearStatics = v.findViewById(R.id.toYearStatic);

        listEntry = new ArrayList<>();
        countMood = new HashMap<>();
        Date nowdate = new Date();
        currentMonth = nowdate.getMonth()+1;
        currentYear = nowdate.getYear()+1900;
        currentMonthStatic.setText(currentMonth+"/"+currentYear);

        toYearStatics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),YearStatistic.class));
            }
        });

        getChart(currentMonth);

        return v;
    }

    private void getChart(int month){
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
                    if (date.getMonth()+1 == currentMonth)
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

                //---------- Get last mood if a day has >1 mood
                for(int first = 0; first < listEntry.size()-1; first++)
                    for(int second = first + 1; second < listEntry.size(); second++)
                        if(listEntry.get(first).getDayOfmood().equals(listEntry.get(second).getDayOfmood())) {
                            listEntry.remove(second);
                            second--;
                        }

                //------------------Add data to line chart--------------
                LineDataSet lineDataSet = new LineDataSet(lineChartDataSet(),"data set");

                ArrayList<ILineDataSet> iLineDataSets=new ArrayList<>();
                iLineDataSets.add(lineDataSet);

                LineData lineData = new LineData(iLineDataSets);
                moodLineChart.setData(lineData);
                moodLineChart.invalidate();

                loadPieChartData();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private ArrayList<com.github.mikephil.charting.data.Entry>lineChartDataSet(){
        ArrayList<com.github.mikephil.charting.data.Entry> dataSet = new ArrayList<>();
//        for (Entry e:listEntry){
//            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
//            try {
//                Date date = formatter.parse(e.getDateOfMood());
//                System.out.println(date.getDate()+" "+getMoodNum(e));
//                dataSet.add(new com.github.mikephil.charting.data.Entry(date.getDate(),getMoodNum(e)));
//            } catch (ParseException parseException) {
//                parseException.printStackTrace();
//            }
//
//        }
        dataSet.add(new com.github.mikephil.charting.data.Entry(0,10));
        dataSet.add(new com.github.mikephil.charting.data.Entry(1,20));
        dataSet.add(new com.github.mikephil.charting.data.Entry(2,30));
        dataSet.add(new com.github.mikephil.charting.data.Entry(4,40));

        return dataSet;
    }

    private void loadPieChartData (){
        ArrayList<PieEntry> entries = new ArrayList<>();
        for (String key: countMood.keySet() )
            entries.add(new PieEntry(countMood.get(key),key));


        PieDataSet dataSet = new PieDataSet(entries,"Moods");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setValueTextSize(12);

        PieData data = new PieData(dataSet);
        //data.setDrawValues(true);
        data.setValueTextSize(10);
        data.setValueTextColor(Color.WHITE);

        //data.setValueTextColors(spam);


        pieChartCountMood.setDrawHoleEnabled(true);
        pieChartCountMood.setEntryLabelTextSize(12);
        pieChartCountMood.setEntryLabelColor(Color.WHITE);
        pieChartCountMood.getDescription().setEnabled(false);

        Legend l = pieChartCountMood.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setTextSize(12);
        l.setDrawInside(false);
        l.setEnabled(true);

        pieChartCountMood.setData(data);
        pieChartCountMood.invalidate();
        pieChartCountMood.animateY(1300, Easing.EaseInOutQuad);

    }

    private int getMoodNum(Entry e) {
        for (int i = 0; i < MoodInfo.moods_type.length; i++) {
            for (int j = 0; j < MoodInfo.moods_type[i].length; j++) {
                if (e.getMoodType().equals(MoodInfo.moods_type[i][j])) {
                    return 5-i;
                }
            }
        }
        return 0;
    }

    private ArrayList<Integer>getColorChart(ArrayList<Entry> a){
        ArrayList<Integer>colors = new ArrayList<>();
        for(Entry e: a){
            for (int i = 0; i < MoodInfo.moods_type.length; i++) {
                for (int j = 0; j < MoodInfo.moods_type[i].length; j++) {
                    if (a.equals(MoodInfo.moods_type[i][j])) {
                        colors.add(Color.parseColor(MoodInfo.moods_color[i]));
                    }
                }
            }
        }

        return colors;
    }
}
