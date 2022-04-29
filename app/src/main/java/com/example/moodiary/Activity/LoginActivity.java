package com.example.moodiary.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.moodiary.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText editEmail, editPw;
    private Button btnSignin, btnRegister;
    private FirebaseAuth mAuth;
    private TextView     forgotPw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editEmail   = findViewById(R.id.edtxt_SigninEmail);
        editPw      = findViewById(R.id.edtxt_SigninPassword);
        btnSignin   = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnSignup);
        forgotPw    = findViewById(R.id.txtForgotPw);
        mAuth       = FirebaseAuth.getInstance();

        forgotPw.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), ForgotPwActivity.class));
        });
        btnRegister.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), SignupActivity.class));
        });
        btnSignin.setOnClickListener(view -> {
            if (editEmail.getText().length() > 0 && editPw.getText().length() > 0) {
                login(editEmail.getText().toString(), editPw.getText().toString());
            } else {
                Toast.makeText(this, "Login information must be fully filled", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void login(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            if (task.getResult().getUser() != null) {
                                reload();
                            } else {
                                Toast.makeText(getApplicationContext(), "Login failed.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(), "Login failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void reload() {
        startActivity(new Intent(getApplicationContext(), ShowEntriesActivity.class));
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            reload();
        }
    }
}
