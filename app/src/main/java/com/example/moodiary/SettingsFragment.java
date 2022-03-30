package com.example.moodiary;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SettingsFragment extends Fragment{
    private Button btnExit;
    private FirebaseUser fbaseUser;
    private TextView userName;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_settings, container, false);
        userName = v.findViewById(R.id.name);
        btnExit = v.findViewById(R.id.btnLogout);
        btnExit.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(v.getContext(),LoginActivity.class));
        });
        fbaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (fbaseUser!=null) {
            userName.setText(fbaseUser.getDisplayName());
        }
        // Inflate the layout for this fragment
        return v;
    }
}
