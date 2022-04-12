package com.example.moodiary.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import androidx.fragment.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.moodiary.Activity.LoginActivity;
import com.example.moodiary.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SettingsFragment extends Fragment {
    private TextView     btnExit;
    private FirebaseUser fbaseUser;
    private TextView     userName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_settings, container, false);
        userName = v.findViewById(R.id.name);
        btnExit  = v.findViewById(R.id.btnLogout);
        btnExit.setOnClickListener(view -> {
            AlertDialog dialog = new AlertDialog.Builder(v.getContext())
                    .setTitle("Logout")
                    .setMessage("Are you sure?")
                    .setPositiveButton("Yes", (dialogInterface, i) -> {
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(v.getContext(), LoginActivity.class));
                    })
                    .setNegativeButton("No", null)
                    .setIcon(R.drawable.warning1)
                    .show();

            dialog.getButton(Dialog.BUTTON_POSITIVE).setTextSize(18);
            dialog.getButton(Dialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#ff999999"));
            dialog.getButton(Dialog.BUTTON_NEGATIVE).setTextSize(18);

            ((TextView) dialog.findViewById(android.R.id.message)).setTextSize(20);
        });
        fbaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (fbaseUser != null) {
            userName.setText(fbaseUser.getDisplayName());
        }
        // Inflate the layout for this fragment
        return v;
    }
}
