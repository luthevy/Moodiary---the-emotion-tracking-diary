package com.example.moodiary.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moodiary.CustomMoodInAdding;
import com.example.moodiary.MoodInfo;
import com.example.moodiary.R;
import com.google.android.material.textfield.TextInputEditText;

public class UpdateMood extends AppCompatActivity implements View.OnClickListener {

    private TextInputEditText newMoodName;
    private GridView extraIcons;
    private ImageView color1, color2, color3, color4, color5, currentMoodIcon;
    private ImageButton btnSave;

    private int chosenColor = -1;
    private int chosenIcon;
    private int hasSelectedmood, previousSelectedMoodPosition = -1;
    private String mood_name;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_mood);
        mood_name= getIntent().getStringExtra("Mood_Edit_Name");

        hasSelectedmood = 0;
        newMoodName = findViewById(R.id.newMoodName);
        extraIcons = findViewById(R.id.moodAdd);
        color1 = findViewById(R.id.color1);
        color2 = findViewById(R.id.color2);
        color3 = findViewById(R.id.color3);
        color4 = findViewById(R.id.color4);
        color5 = findViewById(R.id.color5);
        currentMoodIcon = findViewById(R.id.currentMoodInUpdate);
        btnSave = findViewById(R.id.btnSaveInAddMood);

        color1.setOnClickListener(this);
        color2.setOnClickListener(this);
        color3.setOnClickListener(this);
        color4.setOnClickListener(this);
        color5.setOnClickListener(this);
        btnSave.setOnClickListener(this);

        for(int i = 0; i<MoodInfo.moods_type.length; i++){
            for(int j = 0; j<MoodInfo.moods_type[i].length; j++){
                if(MoodInfo.moods_type[i][j].equals(mood_name)){
                    if(i<5) {
                        newMoodName.setText(mood_name);
                        getClicked(i);
                        currentMoodIcon.setImageResource(MoodInfo.moods_thumbnail[i][j]);
                        currentMoodIcon.setImageTintList(ColorStateList.valueOf(Color.parseColor(MoodInfo.moods_color[i])));
                    }
                }
            }
        }

        CustomMoodInAdding aAdapter = new CustomMoodInAdding(this, MoodInfo.new_moods);
        extraIcons.setAdapter(aAdapter);


        extraIcons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                LinearLayout currentLayout = (LinearLayout) extraIcons.getChildAt(i);
                ImageView currentPosition = (ImageView) currentLayout.getChildAt(0);
                if (hasSelectedmood == 1) {
                    LinearLayout previousLayout = (LinearLayout) extraIcons.getChildAt(previousSelectedMoodPosition);
                    ImageView previousPos = (ImageView) previousLayout.getChildAt(0);
                    previousPos.setImageTintList(ColorStateList.valueOf(Color.parseColor("#000000")));
                }
                if (chosenColor == -1)
                    currentPosition.setImageTintList(ColorStateList.valueOf(Color.parseColor(MoodInfo.moods_color[0])));
                else
                    currentPosition.setImageTintList(ColorStateList.valueOf(Color.parseColor(MoodInfo.moods_color[chosenColor - 1])));
                previousSelectedMoodPosition = i;
                hasSelectedmood = 1;
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(newMoodName.getText().toString()))
                    Toast.makeText(UpdateMood.this, "Please enter NAME for new mood", Toast.LENGTH_LONG).show();
                else if (chosenColor == -1)
                    Toast.makeText(UpdateMood.this, "Please choose COLOR of new mood", Toast.LENGTH_LONG).show();
                else {
                    MoodInfo.updateMood(mood_name, previousSelectedMoodPosition, chosenColor - 1, newMoodName.getText().toString());
                    AlertDialog dialog = new AlertDialog.Builder(UpdateMood.this)
                            .setTitle("Edit Mood")
                            .setMessage("Update Success")
                            .setPositiveButton("OK", (dialogInterface, i) -> {
                                MoodInfo.retrieveEntry = 1;
                                startActivity(new Intent(UpdateMood.this, ShowEntriesActivity.class));
                                //finish();
                            })
                            .show();

                    dialog.getButton(Dialog.BUTTON_POSITIVE).setTextSize(18);
                    dialog.getButton(Dialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#0000ff"));
                }
            }


        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.color1:
                refreshChoosenColor();
                color1.setImageResource(R.drawable.circular_shape_1_clicked);
                chosenColor = 1;
                break;
            case R.id.color2:
                refreshChoosenColor();
                color2.setImageResource(R.drawable.circular_shape_2_clicked);
                chosenColor = 2;
                break;
            case R.id.color3:
                refreshChoosenColor();
                color3.setImageResource(R.drawable.circular_shape_3_clicked);
                chosenColor = 3;
                break;
            case R.id.color4:
                refreshChoosenColor();
                color4.setImageResource(R.drawable.circular_shape_4_clicked);
                chosenColor = 4;
                break;
            case R.id.color5:
                refreshChoosenColor();
                color5.setImageResource(R.drawable.circular_shape_5_clicked);
                chosenColor = 5;
                break;
        }
    }

    private void refreshChoosenColor() {
        color1.setImageResource(R.drawable.circular_shape_1);
        color2.setImageResource(R.drawable.circular_shape_2);
        color3.setImageResource(R.drawable.circular_shape_3);
        color4.setImageResource(R.drawable.circular_shape_4);
        color5.setImageResource(R.drawable.circular_shape_5);
    }

    private void getClicked(int color){
        switch (color){
            case 0:
                refreshChoosenColor();
                color1.setImageResource(R.drawable.circular_shape_1_clicked);
                chosenColor = 1;
                break;
            case 1:
                refreshChoosenColor();
                color2.setImageResource(R.drawable.circular_shape_2_clicked);
                chosenColor = 2;
                break;
            case 2:
                refreshChoosenColor();
                color3.setImageResource(R.drawable.circular_shape_3_clicked);
                chosenColor = 3;
                break;
            case 3:
                refreshChoosenColor();
                color4.setImageResource(R.drawable.circular_shape_4_clicked);
                chosenColor = 4;
                break;
            case 4:
                refreshChoosenColor();
                color5.setImageResource(R.drawable.circular_shape_5_clicked);
                chosenColor = 5;
                break;
        }
    }

}
