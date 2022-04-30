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

public class AddNewMood extends AppCompatActivity implements View.OnClickListener {

    private TextInputEditText newMoodName;
    private GridView extraIcons;
    private ImageView color1, color2, color3, color4, color5;
    private ImageButton btnSave;

    private int chosenColor = -1;
    private int chosenIcon;
    private int hasSelectedmood, previousSelectedMoodPosition = -1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_mood);

        hasSelectedmood = 0;
        newMoodName = findViewById(R.id.newMoodName);
        extraIcons = findViewById(R.id.moodAdd);
        color1 = findViewById(R.id.color1);
        color2 = findViewById(R.id.color2);
        color3 = findViewById(R.id.color3);
        color4 = findViewById(R.id.color4);
        color5 = findViewById(R.id.color5);
        btnSave = findViewById(R.id.btnSaveInAddMood);

        color1.setOnClickListener(this);
        color2.setOnClickListener(this);
        color3.setOnClickListener(this);
        color4.setOnClickListener(this);
        color5.setOnClickListener(this);
        btnSave.setOnClickListener(this);

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
                System.out.println("HE:LLLL");
                if (TextUtils.isEmpty(newMoodName.getText().toString()))
                    Toast.makeText(AddNewMood.this, "Please enter NAME for new mood", Toast.LENGTH_LONG).show();
                else if (chosenColor == -1)
                    Toast.makeText(AddNewMood.this, "Please choose COLOR of new mood", Toast.LENGTH_LONG).show();
                else if (previousSelectedMoodPosition == -1)
                    Toast.makeText(AddNewMood.this, "Please choose ICON new mood", Toast.LENGTH_LONG).show();
                else {
                    MoodInfo.addMood(previousSelectedMoodPosition, chosenColor - 1, newMoodName.getText().toString());
                    AlertDialog dialog = new AlertDialog.Builder(AddNewMood.this)
                            .setTitle("Add Mood")
                            .setMessage("Add Success")
                            .setPositiveButton("OK", (dialogInterface, i) -> {
                                startActivity(new Intent(AddNewMood.this, ShowEntriesActivity.class));
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
//            case R.id.btnSaveInAddMood:
//                if(newMoodName.getText().equals(""))
//                    Toast.makeText(this, "Please enter NAME for new mood", Toast.LENGTH_LONG);
//                else if(chosenColor == -1)
//                    Toast.makeText(this, "Please choose COLOR of new mood", Toast.LENGTH_LONG);
//                else if (previousSelectedMoodPosition == -1)
//                    Toast.makeText(this, "Please choose ICON new mood", Toast.LENGTH_LONG);
//                else{
//                    MoodInfo.addMood(previousSelectedMoodPosition, chosenColor-1);
//                    Toast.makeText(this, "Add Success", Toast.LENGTH_LONG);
//                }
//
//                break;
        }
    }

    private void refreshChoosenColor() {
        color1.setImageResource(R.drawable.circular_shape_1);
        color2.setImageResource(R.drawable.circular_shape_2);
        color3.setImageResource(R.drawable.circular_shape_3);
        color4.setImageResource(R.drawable.circular_shape_4);
        color5.setImageResource(R.drawable.circular_shape_5);
    }


}
