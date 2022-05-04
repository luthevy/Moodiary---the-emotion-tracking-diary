package com.example.moodiary.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moodiary.ActivityAdapter;
import com.example.moodiary.EntryActivitiesAdapter;
import com.example.moodiary.EntryActivity;
import com.example.moodiary.MoodInfo;
import com.example.moodiary.R;

import java.util.ArrayList;

public class EditActivities  extends AppCompatActivity {

    public static final String TAG = "ListViewExample";

    ListView listEditActivities;
    ImageButton btnBack;
    Button btnSave;

    private ArrayList<String> editMoodAList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_activities);

        this.listEditActivities = (ListView)findViewById(R.id.listEditActivities);
        this.btnSave = (Button)findViewById(R.id.saveActivities);
        this.btnBack = (ImageButton)findViewById(R.id.btnBackActivity);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        this.listEditActivities.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        this.listEditActivities.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG, "onItemClick: " +position);
                CheckedTextView v = (CheckedTextView) view;
                boolean currentCheck = v.isChecked();
                EntryActivity activity = (EntryActivity) listEditActivities.getItemAtPosition(position);
                activity.setActive(!currentCheck);
            }
        });

        ArrayList<EntryActivity> activityArrayList = new ArrayList<EntryActivity>();
        for (int i = 0; i < MoodInfo.activity_type.length; i++) {
            activityArrayList.add(new EntryActivity(MoodInfo.activity_type[i], MoodInfo.activity_thumbnail[i],MoodInfo.activity_active[i]));
        }
        ArrayAdapter<EntryActivity> arrayAdapter = new ArrayAdapter<EntryActivity>(this, android.R.layout.simple_list_item_checked, activityArrayList);
        this.listEditActivities.setAdapter(arrayAdapter);

        for(int i=0;i < activityArrayList.size(); i++ )  {
            this.listEditActivities.setItemChecked(i, activityArrayList.get(i).isActive());
        }

        this.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                MoodInfo.updateActivities(mood_name, previousSelectedMoodPosition, chosenColor - 1, newMoodName.getText().toString());
//                AlertDialog dialog = new AlertDialog.Builder(EditActivities.this)
//                        .setTitle("Edit Activities")
//                        .setMessage("Update Success")
//                        .setPositiveButton("OK", (dialogInterface, i) -> {
//                            MoodInfo.retrieveEntry = 1;
//                            startActivity(new Intent(EditActivities.this, ShowEntriesActivity.class));
//                            //finish();
//                        })
//                        .show();
//
//                dialog.getButton(Dialog.BUTTON_POSITIVE).setTextSize(18);
//                dialog.getButton(Dialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#0000ff"));
            }
        });
    }

}
