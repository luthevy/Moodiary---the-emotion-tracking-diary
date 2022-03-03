package com.example.moodiary;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ForgotPwActivity extends AppCompatActivity {
    private EditText txtEmail;
    private FirebaseAuth auth;
    private Button sendEmail;
    private String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpw);
        auth = FirebaseAuth.getInstance();

        txtEmail=findViewById(R.id.edtxt_ForgotPwEmail);
        sendEmail = findViewById(R.id.btnGetEmail);

        sendEmail.setOnClickListener(view -> validateData());
    }
    private void validateData(){
        email = txtEmail.getText().toString();
        if (email.isEmpty()){
            Toast.makeText(this, "Fill in email", Toast.LENGTH_SHORT).show();
        }
        else{
            forgetPass();
        }
    }
    private void forgetPass(){
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        startActivity(new Intent(ForgotPwActivity.this,LoginActivity.class));
                        finish();
                        Toast.makeText(ForgotPwActivity.this, "Check your email", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(ForgotPwActivity.this, "Email not found", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}