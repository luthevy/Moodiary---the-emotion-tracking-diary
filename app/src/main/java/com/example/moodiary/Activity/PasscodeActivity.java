package com.example.moodiary.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moodiary.R;
import com.hanks.passcodeview.PasscodeView;


public class PasscodeActivity extends AppCompatActivity {
    PasscodeView passcodeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passcode);

        String passcode = getApplicationContext()
                .getSharedPreferences("PIN", MODE_PRIVATE)
                .getString("PIN", "");

        if(passcode.equals("")){
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }

        passcodeView = (PasscodeView) findViewById(R.id.passcodeView);
        passcodeView.setLocalPasscode(passcode);

        passcodeView.setListener(new PasscodeView.PasscodeViewListener() {

            @Override
            public void onFail() {
                Toast.makeText(getApplication(), "Incorrect PIN", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(String number) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });
    }
}