package com.example.moodiary.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.moodiary.Activity.LoginActivity;
import com.example.moodiary.Activity.PasscodeActivity;
import com.example.moodiary.Activity.UpdatePasscodeActivity;
import com.example.moodiary.Activity.UpdatePasswordActivity;
import com.example.moodiary.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SettingsFragment extends Fragment {
    private TextView btnSetGoal, btnEditMoods, btnEditActivities, btnSetPin, btnChangePassword, btnLogout;
    private FirebaseUser fbaseUser;
    private TextView     userName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_settings, container, false);
        userName = v.findViewById(R.id.name);

        btnSetGoal        = v.findViewById(R.id.btnSetGoal);
        btnEditMoods      = v.findViewById(R.id.btnSettingEditMoods);
        btnEditActivities = v.findViewById(R.id.btnSettingEditActivities);
        btnSetPin         = v.findViewById(R.id.btnSetPin);
        btnChangePassword = v.findViewById(R.id.btnChangePassword);
        btnLogout         = v.findViewById(R.id.btnLogout);


        btnChangePassword.setOnClickListener(view ->
                startActivity(new Intent(v.getContext(), UpdatePasswordActivity.class)));

        btnSetPin.setOnClickListener(view ->
                startActivity(new Intent(v.getContext(), UpdatePasscodeActivity.class)));

        btnLogout.setOnClickListener(view -> {
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
