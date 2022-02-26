package com.example.moodiary;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignupActivity extends AppCompatActivity {
    private EditText editFullname, editEmail, editPw, editRetypePw;
    private Button btnRegister, btnTurnback;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        editFullname = findViewById(R.id.edtxt_SignupUsername);
        editEmail = findViewById(R.id.edtxt_SignupEmail);
        editPw = findViewById(R.id.edtxt_SignupPassword);
        editRetypePw = findViewById(R.id.edtxt_SignupRetypePw);
        btnRegister = findViewById(R.id.btnRegister);
        btnTurnback = findViewById(R.id.btnRegisterBack);
        mAuth = FirebaseAuth.getInstance();

        btnTurnback.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        });
        btnRegister.setOnClickListener(view -> {
            if (editFullname.getText().length()>0 &&
                editEmail.getText().length()>0 &&
                editPw.getText().length()>0 &&
                editRetypePw.getText().length()>0) {
                if (editPw.getText().toString().equals(editRetypePw.getText().toString())){
                    register(editFullname.getText().toString(),editEmail.getText().toString(),editPw.getText().toString());
                }
                else{
                    Toast.makeText(this, "Invalid retype password", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(this, "Fill in the blank!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void register(String name, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful() && task.getResult()!=null) {
                        FirebaseUser fbaseUser = task.getResult().getUser();
                        if (fbaseUser!=null){
                            UserProfileChangeRequest request = new UserProfileChangeRequest.Builder().setDisplayName(name).build();
                            fbaseUser.updateProfile(request).addOnCompleteListener(task1 -> reload());
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Register failed", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(getApplicationContext(), task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void reload(){
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            reload();
        }
    }
}
