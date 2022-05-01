package com.example.moodiary.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moodiary.CustomMoodInEdit;
import com.example.moodiary.MoodInfo;
import com.example.moodiary.R;

import java.util.ArrayList;

public class EditMood extends AppCompatActivity {

    ListView listEditMood;
    ImageButton btnBack;
    Button addNewMood;

    private ArrayList<String>editMoodAList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_mood);
        btnBack = findViewById(R.id.btnBack);
        listEditMood = findViewById(R.id.listEditMood);
        addNewMood = findViewById(R.id.addNewMood);

        addNewMood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EditMood.this, AddNewMood.class));
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        editMoodAList = new ArrayList<>();
        for(int i = 0; i< MoodInfo.moods_type.length; i++)
            for(int j = 0; j<MoodInfo.moods_type[i].length; j++)
                if(j!=0)
                    editMoodAList.add(MoodInfo.moods_type[i][j]);


        CustomMoodInEdit eAdapter = new CustomMoodInEdit(this, editMoodAList);

        listEditMood.setAdapter(eAdapter);

        listEditMood.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(EditMood.this, UpdateMood.class);
                intent.putExtra("Mood_Edit_Name", editMoodAList.get(i));
                startActivity(intent);
            }
        });

    }
}
