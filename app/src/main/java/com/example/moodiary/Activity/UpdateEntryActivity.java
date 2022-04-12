package com.example.moodiary.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moodiary.Entry;
import com.example.moodiary.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateEntryActivity extends AppCompatActivity {

    private ImageButton btnUpdate, btnBack;
    DatabaseReference thisEntryRef;
    Entry             thisEntry;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_entry);

        btnBack   = findViewById(R.id.btnBack1);
        btnUpdate = findViewById(R.id.btnUpdate);

        btnBack.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), ShowEntriesActivity.class)));


        String e = getIntent().getStringExtra("EntryKey");
        System.out.println(e);

        thisEntryRef = FirebaseDatabase
                .getInstance()
                .getReference("Entry")
                .child(e);

        thisEntry = thisEntryRef.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {

            }
        })
                .getResult()
                .getValue(Entry.class);

        btnUpdate.setOnClickListener(view -> {
            thisEntryRef.setValue(thisEntry).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    startActivity(new Intent(getApplicationContext(), ShowEntriesActivity.class));
                }
            });
        });
    }
}
