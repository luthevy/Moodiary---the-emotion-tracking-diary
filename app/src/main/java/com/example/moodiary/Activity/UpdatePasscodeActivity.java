package com.example.moodiary.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moodiary.R;


public class UpdatePasscodeActivity extends AppCompatActivity {

    private EditText currentPIN, newPIN, newPINRetype;
    private Button btnRemove, btnUpdate;
    private SharedPreferences prefs;
    private SharedPreferences.Editor prefsEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_passcode);

        prefs     = getApplicationContext().getSharedPreferences("PIN", MODE_PRIVATE);
        prefsEdit = prefs.edit();

        currentPIN   = findViewById(R.id.currentPIN);
        newPIN       = findViewById(R.id.newPIN);
        newPINRetype = findViewById(R.id.newPINRetype);
        btnRemove    = findViewById(R.id.btnRemove);
        btnUpdate    = findViewById(R.id.btnChange);

        btnRemove.setOnClickListener(view -> {
            AlertDialog dialog = new AlertDialog.Builder(UpdatePasscodeActivity.this)
                    .setTitle("Remove PIN")
                    .setMessage("This action will reset your PIN to empty. Are you sure?")
                    .setPositiveButton("Yes", (dialogInterface, i) -> {

                        String cp = currentPIN.getText().toString();
                        if (!cp.equals(prefs.getString("PIN", null))) {
                            Toast.makeText(getApplicationContext(), "Please type your current PIN into Current PIN field first", Toast.LENGTH_SHORT).show();
                        } else {
                            prefsEdit.putString("PIN", "").apply();
                        }

                        Toast.makeText(getApplicationContext(), "Removed PIN", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), ShowEntriesActivity.class));
                        finish();
                    })
                    .setNegativeButton("No", null)
                    .setIcon(R.drawable.warning1)
                    .show();

            dialog.getButton(Dialog.BUTTON_POSITIVE).setTextSize(18);
            dialog.getButton(Dialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#ff999999"));
            dialog.getButton(Dialog.BUTTON_NEGATIVE).setTextSize(18);

            ((TextView) dialog.findViewById(android.R.id.message)).setTextSize(20);
        });

        btnUpdate.setOnClickListener(view -> {
            String cp = currentPIN.getText().toString(),
                    np = newPIN.getText().toString(),
                    npr = newPINRetype.getText().toString(),
                    oldPassword = prefs.getString("PIN", "");

            System.out.println("a");

            if ((!oldPassword.equals("")) && (!cp.equals(oldPassword))) {
                Toast.makeText(getApplicationContext(), "Current PIN is incorrect", Toast.LENGTH_SHORT).show();
                System.out.println("b");
            } else if (np.length() != 4) {
                Toast.makeText(getApplicationContext(), "New PIN must be 4 numbers only", Toast.LENGTH_SHORT).show();
                System.out.println("c");
            }else if (!npr.equals(np)) {
                Toast.makeText(getApplicationContext(), "Retyped New PIN does not match with New PIN", Toast.LENGTH_SHORT).show();
                System.out.println("d");
            }else {
                prefsEdit.putString("PIN", np).apply();
                Toast.makeText(getApplicationContext(), "Updated Successful!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), ShowEntriesActivity.class));
                finish();
            }
        });
    }
}