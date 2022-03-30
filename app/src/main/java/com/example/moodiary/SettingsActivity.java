package com.example.moodiary;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SettingsActivity extends AppCompatActivity {
    private FirebaseUser fbaseUser;
    private TextView userName;
    private Button btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        userName = findViewById(R.id.name);
        btnExit = findViewById(R.id.btnLogout);
        btnExit.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            finish();
        });
        fbaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (fbaseUser!=null) {
            userName.setText(fbaseUser.getDisplayName());
        }

    }

}